package com.travellog.feature.map

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.travellog.feature.map.model.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class GlobeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _countries = MutableStateFlow<List<Country>>(emptyList())

    val searchResults: StateFlow<List<Country>> = combine(_searchQuery, _countries) { query, countries ->
        if (query.isBlank()) emptyList()
        else countries.filter { it.name.contains(query, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        loadCountries()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    private fun loadCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val json = context.assets.open("www/countries.geojson").bufferedReader().readText()
                val features = JSONObject(json).getJSONArray("features")
                val list = buildList {
                    for (i in 0 until features.length()) {
                        val props = features.getJSONObject(i).getJSONObject("properties")
                        val name = props.optString("name").takeIf { it.isNotEmpty() } ?: continue
                        val code = props.optString("code")
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
