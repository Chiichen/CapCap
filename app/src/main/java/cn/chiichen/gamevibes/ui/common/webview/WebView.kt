package cn.chiichen.gamevibes.ui.common.webview

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.launch


@Composable
fun CustomWebView(
    modifier: Modifier = Modifier,
    url: String,
    onBack: (webView: WebView?) -> Unit,
    onProgressChange: (progress: Int) -> Unit = {},
    initSettings: (webSettings: WebSettings?) -> Unit = {},
    onReceivedError: (error: WebResourceError?) -> Unit = {},
    overrideUrlLoading: (
        view: WebView?,
        request: WebResourceRequest?
    ) -> Boolean = { _: WebView?, _: WebResourceRequest? -> false },
) {
    val webViewChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            //回调网页内容加载进度
            onProgressChange(newProgress)
            super.onProgressChanged(view, newProgress)
        }
    }
    val webViewClient = object : WebViewClient() {
        override fun onPageStarted(
            view: WebView?, url: String?,
            favicon: Bitmap?
        ) {
            super.onPageStarted(view, url, favicon)
            onProgressChange(-1)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            onProgressChange(100)
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            if (null == request?.url) return false
            val showOverrideUrl = request.url.toString()
            try {
                if (!showOverrideUrl.startsWith("http://")
                    && !showOverrideUrl.startsWith("https://")
                ) {
                    //处理非http和https开头的链接地址
                    Intent(Intent.ACTION_VIEW, Uri.parse(showOverrideUrl)).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        view?.context?.applicationContext?.startActivity(this)
                    }
                    return true
                }
            } catch (e: Exception) {
                //没有安装和找到能打开(「xxxx://openlink.cc....」、「weixin://xxxxx」等)协议的应用
                return true
            }
            if (overrideUrlLoading(view, request)) {
                return true
            }
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)
            //自行处理....
            onReceivedError(error)
        }
    }
    var webView: WebView? = null
    val coroutineScope = rememberCoroutineScope()
    AndroidView(modifier = modifier, factory = { ctx ->
        WebView(ctx).apply {
            this.webViewClient = webViewClient
            this.webChromeClient = webViewChromeClient
            //回调webSettings供调用方设置webSettings的相关配置
            initSettings(this.settings)
            webView = this
            loadUrl(url)
        }
    })
    BackHandler {
        coroutineScope.launch {
            //自行控制点击了返回按键之后，关闭页面还是返回上一级网页
            onBack(webView)
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Preview(showBackground = true)
@Composable
fun PreviewWebview() {

    var rememberWebViewProgress: Int by remember { mutableIntStateOf(-1) }
    Box {
        CustomWebView(
            modifier = Modifier.fillMaxSize(),
            url = "https://www.baidu.com/",
            onProgressChange = { progress ->
                rememberWebViewProgress = progress
            },
            initSettings = { settings ->
                settings?.apply {
                    //支持js交互
                    javaScriptEnabled = true
                    //将图片调整到适合webView的大小
                    useWideViewPort = true
                    //缩放至屏幕的大小
                    loadWithOverviewMode = true
                    //缩放操作
                    setSupportZoom(true)
                    builtInZoomControls = true
                    displayZoomControls = true
                    //是否支持通过JS打开新窗口
                    javaScriptCanOpenWindowsAutomatically = true
                    //不加载缓存内容
                    cacheMode = WebSettings.LOAD_NO_CACHE
                }
            }, onBack = { webView ->
                if (webView?.canGoBack() == true) {
                    webView.goBack()
                }
            }, onReceivedError = {
            }
        )
    }
}
