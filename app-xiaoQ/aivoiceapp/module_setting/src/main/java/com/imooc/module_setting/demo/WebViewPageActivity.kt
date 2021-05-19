//package com.imooc.module_setting.demo
//
//import android.annotation.TargetApi
//import android.os.Build
//import android.os.Bundle
//import android.webkit.WebChromeClient
//import android.webkit.WebResourceRequest
//import android.webkit.WebView
//import android.webkit.WebViewClient
//import androidx.appcompat.app.AppCompatActivity
//
///**
// * @author iqqcode
// */
//class WebViewPageActivity : AppCompatActivity() {
//    private var webView: WebView? = null
//    private val url =
//        "http://www.moviebase.cn/uread/app/viewArt/viewArt-0985242225a84c7eabe3eb62c9fa91bf.html"
//
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_webviewpage)
//        webView = findViewById(R.id.id_netweb)
//
//        // 设置WebView的信息配置
//        val settings = webView.getSettings()
//        settings.javaScriptEnabled = true
//        settings.javaScriptCanOpenWindowsAutomatically = true
//        settings.allowFileAccess = true
//        settings.allowFileAccessFromFileURLs = true
//
//        // 加载网页信息
//        webView.loadUrl(url)
//        webView.setWebViewClient(object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(
//                view: WebView,
//                request: WebResourceRequest
//            ): Boolean {
//                view.loadUrl(url)
//                return false
//            }
//
//            override fun onPageFinished(view: WebView, url: String) {
//                super.onPageFinished(view, url)
//                // 监听webview已经将网页加载完成了，添加图片的监听
//                addImageClickListener(view)
//            }
//        })
//        webView.setWebChromeClient(WebChromeClient())
//        // 因为在js当中调用了android的代码，所以需要设置通道
//        webView.addJavascriptInterface(NetJavaScriptInterface(this), "listener")
//    }
//
//    /**
//     * 添加网页当中的图片监听的函数
//     * @param webView
//     */
//    fun addImageClickListener(webView: WebView) {
//        webView.loadUrl(
//            "javascript:(function(){" +
//                    "var objs = document.getElementsByTagName(\"img\"); " +
//                    "var head=objs[0].src;" +
//                    "var lazys = document.getElementsByClassName(\"lazy\");" +
//                    "var arr=[];" +
//                    "arr[0]=head;" +
//                    "for(var i=1;i<lazys.length;i++)  " +
//                    "{"
//                    + "      arr[i]=lazys[i-1].getAttribute('data-original');" +
//                    "}" +
//                    "for(var i=0;i<lazys.length;i++)  " +
//                    "{"
//                    + "    lazys[i].onclick=function()  " +
//                    "    {  "
//                    + "        window.listener.openImage(this.getAttribute('data-original'),arr);  " +
//                    "    }  " +
//                    "}" +
//                    "objs[0].onclick=function(){window.listener.openImage(this.src,arr);}" +
//                    "})()"
//        )
//    }
//}