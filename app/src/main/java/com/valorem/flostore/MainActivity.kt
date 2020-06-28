package com.valorem.flostore

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        webView.apply {
            webViewClient = CustomWebClient()
            webChromeClient = CustomWebChromeClient()
            settings.loadsImagesAutomatically = true
            settings.javaScriptEnabled = true
            settings.builtInZoomControls = true
            settings.displayZoomControls = false
            settings.setSupportMultipleWindows(true)
            isVerticalScrollBarEnabled = true
            settings.cacheMode = WebSettings.LOAD_NO_CACHE // load online by default
            if (Build.VERSION.SDK_INT >= 21) {
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
            loadUrl("https://flobooks.in/store/rishabh_enterprises")
        }
    }

    inner class CustomWebClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            progressBar?.visibility = View.VISIBLE
            return true
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)
            progressBar?.visibility = View.GONE
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            progressBar?.visibility = View.GONE
        }
    }

    inner class CustomWebChromeClient : WebChromeClient() {

        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
            super.onConsoleMessage(consoleMessage)
            //Timber.i(consoleMessage?.message())
            return true
        }

        @SuppressLint("SetJavaScriptEnabled")
        override fun onCreateWindow(
            view: WebView?,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message?
        ): Boolean {
            val newWebView = WebView(this@MainActivity.applicationContext)
            newWebView.settings.javaScriptEnabled = true
            newWebView.settings.loadsImagesAutomatically = true
            newWebView.settings.builtInZoomControls = true
            newWebView.settings.displayZoomControls = false
            view?.addView(newWebView)

            resultMsg?.let {
                val transport = it.obj as WebView.WebViewTransport
                transport.webView = newWebView
                it.sendToTarget()

                newWebView.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView,
                        url: String
                    ): Boolean {
                        view.loadUrl(url)
                        return true
                    }
                }
            }

            return true
        }
    }
}