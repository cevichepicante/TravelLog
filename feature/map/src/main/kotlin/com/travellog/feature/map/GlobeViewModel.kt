package com.travellog.feature.map

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapbox.geojson.Point
import com.travellog.feature.map.model.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class GlobeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    private val featuresJson = mutableListOf<JSONObject>()
    private val featuresByCode = mutableMapOf<String, JSONObject>()

    val searchResults: StateFlow<List<Country>> = combine(_searchQuery, _countries) { query, countries ->
        if (query.isBlank()) emptyList()
        else countries.filter { it.name.contains(query, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _highlightGeoJson = MutableStateFlow<String?>(null)
    val highlightGeoJson: StateFlow<String?> = _highlightGeoJson

    private val _nearbyCountries = MutableStateFlow<List<Country>>(emptyList())
    val nearbyCountries: StateFlow<List<Country>> = _nearbyCountries

    // SW(southwest), NE(northeast) — Screen에서 cameraForCoordinateBounds로 줌 자동 계산
    private val _cameraTarget = MutableSharedFlow<Pair<Point, Point>>(extraBufferCapacity = 1)
    val cameraTarget: SharedFlow<Pair<Point, Point>> = _cameraTarget

    init {
        loadCountries()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun clearNearbyCountries() {
        _nearbyCountries.value = emptyList()
    }

    fun onMapLongClick(lon: Double, lat: Double) {
        viewModelScope.launch(Dispatchers.Default) {
            _nearbyCountries.value = findNearbyCountries(lon, lat)
        }
    }

    fun onMapClick(lon: Double, lat: Double) {
        viewModelScope.launch(Dispatchers.Default) {
            val feature = findCountryAt(lon, lat)
            if (feature != null) {
                selectFeature(feature)
            } else {
                _highlightGeoJson.value = null
            }
        }
    }

    fun selectCountryByCode(code: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val feature = featuresByCode[code] ?: return@launch
            selectFeature(feature)
        }
    }

    private fun selectFeature(feature: JSONObject) {
        _highlightGeoJson.value = """{"type":"FeatureCollection","features":[$feature]}"""
        _cameraTarget.tryEmit(computeBounds(feature))
    }

    private fun computeCentroid(feature: JSONObject): Pair<Double, Double>? {
        val geometry = feature.optJSONObject("geometry") ?: return null
        var sumLon = 0.0; var sumLat = 0.0; var count = 0
        fun processRing(ring: JSONArray) {
            for (i in 0 until ring.length()) {
                val c = ring.getJSONArray(i)
                sumLon += c.getDouble(0); sumLat += c.getDouble(1); count++
            }
        }
        when (geometry.optString("type")) {
            "Polygon" -> processRing(geometry.getJSONArray("coordinates").getJSONArray(0))
            "MultiPolygon" -> {
                val polys = geometry.getJSONArray("coordinates")
                for (i in 0 until polys.length()) processRing(polys.getJSONArray(i).getJSONArray(0))
            }
            else -> return null
        }
        return if (count > 0) sumLon / count to sumLat / count else null
    }

    private fun findNearbyCountries(lon: Double, lat: Double, maxCount: Int = 3, maxDist: Double = 1.0): List<Country> {
        data class Entry(val country: Country, val dist: Double)
        return featuresJson.mapNotNull { feature ->
            val (cLon, cLat) = computeCentroid(feature) ?: return@mapNotNull null
            val dist = Math.hypot(lon - cLon, lat - cLat)
            if (dist > maxDist) return@mapNotNull null
            val props = feature.optJSONObject("properties") ?: return@mapNotNull null
            val name = props.optString("name").takeIf { it.isNotEmpty() } ?: return@mapNotNull null
            val rawCode = props.optString("ISO3166-1-Alpha-3")
            val code = if (rawCode.isNotEmpty() && rawCode != "-99") rawCode else name
            Entry(Country(name, code), dist)
        }
            .sortedBy { it.dist }
            .take(maxCount)
            .map { it.country }
    }

    private fun findCountryAt(lon: Double, lat: Double): JSONObject? =
        featuresJson.find { feature ->
            val geometry = feature.optJSONObject("geometry") ?: return@find false
            pointInGeometry(lon, lat, geometry)
        }

    private fun pointInGeometry(lon: Double, lat: Double, geometry: JSONObject): Boolean =
        when (geometry.optString("type")) {
            "Polygon" -> pointInRing(lon, lat, geometry.getJSONArray("coordinates").getJSONArray(0))
            "MultiPolygon" -> {
                val polys = geometry.getJSONArray("coordinates")
                (0 until polys.length()).any { i ->
                    pointInRing(lon, lat, polys.getJSONArray(i).getJSONArray(0))
                }
            }
            else -> false
        }

    private fun pointInRing(lon: Double, lat: Double, ring: JSONArray): Boolean {
        var inside = false
        val n = ring.length()
        var j = n - 1
        for (i in 0 until n) {
            val pi = ring.getJSONArray(i)
            val pj = ring.getJSONArray(j)
            val xi = pi.getDouble(0); val yi = pi.getDouble(1)
            val xj = pj.getDouble(0); val yj = pj.getDouble(1)
            if ((yi > lat) != (yj > lat) && lon < (xj - xi) * (lat - yi) / (yj - yi) + xi) {
                inside = !inside
            }
            j = i
        }
        return inside
    }

    private fun computeBounds(feature: JSONObject): Pair<Point, Point> {
        val geometry = feature.getJSONObject("geometry")
        var minLon = Double.MAX_VALUE; var maxLon = -Double.MAX_VALUE
        var minLat = Double.MAX_VALUE; var maxLat = -Double.MAX_VALUE

        fun processRing(ring: JSONArray) {
            for (i in 0 until ring.length()) {
                val coord = ring.getJSONArray(i)
                val lo = coord.getDouble(0); val la = coord.getDouble(1)
                if (lo < minLon) minLon = lo; if (lo > maxLon) maxLon = lo
                if (la < minLat) minLat = la; if (la > maxLat) maxLat = la
            }
        }

        when (geometry.optString("type")) {
            "Polygon" -> processRing(geometry.getJSONArray("coordinates").getJSONArray(0))
            "MultiPolygon" -> {
                val polys = geometry.getJSONArray("coordinates")
                for (i in 0 until polys.length()) processRing(polys.getJSONArray(i).getJSONArray(0))
            }
        }

        val sw = Point.fromLngLat(minLon, minLat)
        val ne = Point.fromLngLat(maxLon, maxLat)
        return sw to ne
    }

    private fun loadCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val json = context.assets.open("countries.geojson").bufferedReader().readText()
                val features = JSONObject(json).getJSONArray("features")
                val list = buildList {
                    for (i in 0 until features.length()) {
                        val feature = features.getJSONObject(i)
                        val props = feature.getJSONObject("properties")
                        val name = props.optString("name").takeIf { it.isNotEmpty() } ?: continue
                        val rawCode = props.optString("ISO3166-1-Alpha-3")
                        val code = if (rawCode.isNotEmpty() && rawCode != "-99") rawCode else name
                        featuresJson.add(feature)
                        featuresByCode[code] = feature
                        add(Country(name, code))
                    }
                }.sortedBy { it.name }
                _countries.value = list
            } catch (e: Exception) {
                Log.e("GlobeViewModel", "나라 목록 로드 실패", e)
            }
        }
    }
}
