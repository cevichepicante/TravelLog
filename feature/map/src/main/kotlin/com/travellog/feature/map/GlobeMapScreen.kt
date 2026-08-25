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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewClientCompat
import org.json.JSONObject

@Composable
fun GlobeMapScreen(
    modifier: Modifier = Modifier,
) {
    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            WebView(ctx).apply {
                WebView.setWebContentsDebuggingEnabled(true)

                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
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
            }
        }
    )
}