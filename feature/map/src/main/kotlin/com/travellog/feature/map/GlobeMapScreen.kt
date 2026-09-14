package com.travellog.feature.map

import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mapbox.bindgen.Value
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CoordinateBounds
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.extension.style.layers.generated.FillLayer
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.gestures.gestures
import com.travellog.feature.map.model.Country

private const val HIGHLIGHT_SOURCE_ID = "highlight-source"
private const val HIGHLIGHT_LAYER_ID = "highlight-layer"
private const val MAPBOX_STYLE = "mapbox://styles/kate558299/cmt2gswt6000401sx7j287ceg"

@Composable
fun GlobeMapScreen(
    modifier: Modifier = Modifier,
    viewModel: GlobeViewModel = hiltViewModel(),
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    val highlightGeoJson by viewModel.highlightGeoJson.collectAsStateWithLifecycle()
    val nearbyCountries by viewModel.nearbyCountries.collectAsStateWithLifecycle()

    var isSearchVisible by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val mapViewRef = remember { mutableStateOf<MapView?>(null) }
    val highlightSourceRef = remember { mutableStateOf<GeoJsonSource?>(null) }
    val longClickPosState = remember { mutableStateOf<Pair<Dp, Dp>?>(null) }

    // 하이라이트 GeoJSON 변경 시 소스 업데이트
    LaunchedEffect(highlightGeoJson) {
        val source = highlightSourceRef.value ?: return@LaunchedEffect
        val json = highlightGeoJson
        if (json != null) {
            source.data(json)
        } else {
            source.featureCollection(FeatureCollection.fromFeatures(emptyList()))
        }
    }

    // 나라 선택 시 카메라 이동 — bounds로 줌 자동 계산
    LaunchedEffect(Unit) {
        viewModel.cameraTarget.collect { (sw, ne) ->
            val mapView = mapViewRef.value ?: return@collect
            val bounds = CoordinateBounds(sw, ne, false)
            val padding = EdgeInsets(80.0, 80.0, 80.0, 80.0)
            @Suppress("DEPRECATION")
            val cameraOptions = mapView.mapboxMap.cameraForCoordinateBounds(bounds, padding)
            mapView.camera.flyTo(
                cameraOptions,
                MapAnimationOptions.Builder().duration(1500).build(),
            )
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                MapView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )
                    mapViewRef.value = this

                    mapboxMap.setCamera(
                        CameraOptions.Builder()
                            .center(Point.fromLngLat(128.0, 35.9))
                            .zoom(2.0)
                            .build()
                    )

                    mapboxMap.loadStyle(MAPBOX_STYLE) { style ->
                        // Globe projection — low-level API로 설정 (extension-style 미의존)
                        style.setStyleProjectionProperty("name", Value.valueOf("globe"))

                        val source = GeoJsonSource.Builder(HIGHLIGHT_SOURCE_ID)
                            .featureCollection(FeatureCollection.fromFeatures(emptyList()))
                            .build()
                        source.bindTo(style)
                        highlightSourceRef.value = source

                        val layer = FillLayer(HIGHLIGHT_LAYER_ID, HIGHLIGHT_SOURCE_ID).apply {
                            fillColor("#FFFF00")
                            fillOpacity(0.35)
                            fillOutlineColor("#FFFF00")
                        }
                        layer.bindTo(style)
                    }

                    compass.updateSettings { enabled = false }

                    gestures.addOnMapClickListener { point ->
                        viewModel.clearNearbyCountries()
                        viewModel.onMapClick(point.longitude(), point.latitude())
                        true
                    }

                    gestures.addOnMapLongClickListener { point ->
                        val screenCoord = mapboxMap.pixelForCoordinate(point)
                        val density = ctx.resources.displayMetrics.density
                        longClickPosState.value =
                            (screenCoord.x.toFloat() / density).dp to (screenCoord.y.toFloat() / density).dp
                        viewModel.onMapLongClick(point.longitude(), point.latitude())
                        true
                    }
                }
            }
        )

        // 롱클릭 근방 나라 팝업
        val nearbyPos = longClickPosState.value
        if (nearbyCountries.isNotEmpty() && nearbyPos != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) { viewModel.clearNearbyCountries() },
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White,
                    shadowElevation = 8.dp,
                    modifier = Modifier
                        .offset(nearbyPos.first, nearbyPos.second)
                        .widthIn(min = 160.dp, max = 220.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) { },
                ) {
                    Column {
                        nearbyCountries.forEachIndexed { idx, country ->
                            Text(
                                text = country.name,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.selectCountryByCode(country.code)
                                        viewModel.clearNearbyCountries()
                                    }
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                            )
                            if (idx < nearbyCountries.lastIndex) {
                                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                            }
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .statusBarsPadding()
                .padding(12.dp)
                .widthIn(max = 320.dp),
        ) {
            AnimatedVisibility(
                visible = isSearchVisible,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White,
                    shadowElevation = 6.dp,
                ) {
                    Column {
                        TextField(
                            value = searchQuery,
                            onValueChange = { viewModel.onSearchQueryChange(it) },
                            placeholder = { Text("나라 검색", fontSize = 14.sp) },
                            singleLine = true,
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                                        Icon(Icons.Default.Close, contentDescription = null)
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() }),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester),
                        )

                        if (searchResults.isNotEmpty()) {
                            HorizontalDivider(color = Color.LightGray)
                            LazyColumn {
                                items(searchResults) { country ->
                                    CountryResultItem(
                                        country = country,
                                        onClick = {
                                            viewModel.selectCountryByCode(country.code)
                                            isSearchVisible = false
                                            viewModel.onSearchQueryChange("")
                                            keyboardController?.hide()
                                        },
                                    )
                                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                                }
                            }
                        }
                    }
                }
            }

            IconButton(
                onClick = {
                    if (isSearchVisible) {
                        isSearchVisible = false
                        viewModel.onSearchQueryChange("")
                        keyboardController?.hide()
                    } else {
                        isSearchVisible = true
                    }
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(50)),
            ) {
                Icon(
                    imageVector = if (isSearchVisible) Icons.Default.Close else Icons.Default.Search,
                    contentDescription = if (isSearchVisible) "닫기" else "검색",
                )
            }
        }
    }
}

@Composable
private fun CountryResultItem(
    country: Country,
    onClick: () -> Unit,
) {
    Text(
        text = country.name,
        fontSize = 14.sp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
    )
}
