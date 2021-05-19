package com.imooc.module_setting

import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.imooc.module_setting.netweb.NetJavaScriptInterface
import kotlinx.android.synthetic.main.activity_netview.*

/**
 * @Author: iqqcode
 * @Date: 2021-05-19 15:25
 * @Description:加载网页试图，点击图片跳转二级页面ViewPager
 */
class NetViewActivity : Activity() {

    // private val url: String = "http://www.moviebase.cn/uread/app/viewArt/viewArt-0985242225a84c7eabe3eb62c9fa91bf.html?appVersion=1.7.0&osType=null&platform=2"

    private val url: String = "https://zhuanlan.zhihu.com/p/58691238"

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_netview)

        // 设置WebView的信息配置
        val setting = mWebView.settings
        setting.javaScriptEnabled = true // 允许加载JS
        setting.allowFileAccess = true // 允许访问文件
        setting.allowFileAccessFromFileURLs = true // 允许访问URL中的资源
        setting.javaScriptCanOpenWindowsAutomatically = true;

        // 加载网页信息
        mWebView.loadUrl(url)

        mWebView.webViewClient = object : WebViewClient() {

            /**
             * 默认的URL是加载到本地默认的浏览器打开，要设置成本地客户端打开
             * @param view WebView
             * @param request WebResourceRequest
             * @return Boolean
             */
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest?
            ): Boolean {
                view.loadUrl(url)
                return false
            }

            /**
             * WebView监听网页是载完成
             * @param view WebView
             * @param url String
             */
            override fun onPageFinished(view: WebView, url: String?) {
                super.onPageFinished(view, url)
                // 监听WebView的网页加载完成，添加图片的的监听
                // 监听webview已经将网页加载完成了，添加图片的监听
                addImageClickListener(view)
            }
        }

        // 设置JS处理
        mWebView.webChromeClient = WebChromeClient()

        // JS中调用了Android代码，需要设置通道
        mWebView.addJavascriptInterface(NetJavaScriptInterface(this), "listener")
    }


    /**
     * 添加网页当中的图片监听的函数
     * @param webView WebView
     */
    fun addImageClickListener(webView: WebView) {
        webView.loadUrl(
            // 后面记得加 () ,匿名函数代表参数
            "javascript:(function(){" +
                    "var objs = document.getElementsByTagName(\"img\"); " +
                    "var head = objs[0].src;" +
                    "var lazys = document.getElementsByClassName(\"lazy\");" +
                    "var arr=[];" +
                    "arr[0]=head;" +
                    "for(var i = 1;i < lazys.length;i++)  " +
                    "{"
                    + "      arr[i] = lazys[i-1].getAttribute('data-original');" +
                    "}" +
                    "for(var i = 0 ;i < lazys.length;i++)  " +
                    "{"
                    + "    lazys[i].onclick=function()  " +
                    "    {  "
                    + "        window.listener.openImage(this.getAttribute('data-original'),arr);  " +
                    "    }  " +
                    "}" +
                    "objs[0].onclick=function(){window.listener.openImage(this.src,arr);}" +
                    "})()"
        )
    }
}