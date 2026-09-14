package com.travellog.feature.map

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewClientCompat
import com.travellog.feature.map.model.Country
import org.json.JSONObject

@Composable
fun GlobeMapScreen(
    modifier: Modifier = Modifier,
    viewModel: GlobeViewModel = hiltViewModel(),
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()

    var isSearchVisible by remember { mutableStateOf(false) }
    var webView by remember { mutableStateOf<WebView?>(null) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier = modifier.fillMaxSize()) {
        GlobeWebView(
            modifier = Modifier.fillMaxSize(),
            onCreated = { webView = it },
        )

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
                                            webView?.evaluateJavascript(
                                                "window.selectCountryByCode('${country.code}')",
                                                null,
                                            )
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

@Composable
private fun GlobeWebView(
    modifier: Modifier = Modifier,
    onCreated: (WebView) -> Unit = {},
) {
    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            WebView(ctx).apply {
                WebView.setWebContentsDebuggingEnabled(true)

                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )

                webChromeClient = object : WebChromeClient() {
                    override fun onConsoleMessage(message: ConsoleMessage): Boolean {
                        val tag = "CesiumMap"
                        val log = "${message.sourceId()}:${message.lineNumber()} — ${message.message()}"
                        when (message.messageLevel()) {
                            ConsoleMessage.MessageLevel.ERROR -> Log.e(tag, log)
                            ConsoleMessage.MessageLevel.WARNING -> Log.w(tag, log)
                            else -> Log.d(tag, log)
                        }
                        return true
                    }
                }

                val assetLoader = WebViewAssetLoader.Builder()
                    .addPathHandler("/", WebViewAssetLoader.AssetsPathHandler(ctx))
                    .build()

                webViewClient = object : WebViewClientCompat() {
                    override fun shouldInterceptRequest(
                        view: WebView,
                        request: WebResourceRequest,
                    ): WebResourceResponse? = assetLoader.shouldInterceptRequest(request.url)
                }

                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    useWideViewPort = true
                    loadWithOverviewMode = true
                }

                addJavascriptInterface(object {
                    @JavascriptInterface
                    fun postMessage(json: String) {
                        Handler(Looper.getMainLooper()).post {
                            Log.d("CesiumMap", "웹에서 받은 메시지: $json")
                            try {
                                val payload = JSONObject(json)
                                if (payload.optString("type") == "countrySelected") {
                                    val name = payload.optString("name", "Unknown")
                                    val code = payload.optString("code", "")
                                    Toast.makeText(ctx, "$name ($code)", Toast.LENGTH_SHORT).show()
                                }
                            } catch (e: Exception) {
                                Log.e("CesiumMap", "메시지 파싱 오류", e)
                            }
                        }
                    }
                }, "NativeBridge")

                clearCache(true)
                loadUrl("https://appassets.androidplatform.net/www/index.html")
            }.also(onCreated)
        },
    )
}
