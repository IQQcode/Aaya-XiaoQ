package com.imooc.module_setting.views

import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Toast
import com.imooc.module_setting.R
import com.imooc.module_setting.netweb.CustomWebView
import com.imooc.module_setting.netweb.NetJavaScriptInterface
import kotlinx.android.synthetic.main.activity_netview.*

/**
 * @Author: iqqcode
 * @Date: 2021-05-20 15:56
 * @Description:导航页父类，CsdnActivity / JuejiActivity 等直接实现该类(仅仅替换URL)
 */
abstract class NavigationBase : Activity() {

    private var exitTime: Long = 0

    /**
     * 设置该页面WebView跳转的URL
     * @return String
     */
    abstract fun getNavigationUrl(): String

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reuse_view)

        // 设置WebView的信息配置
        val setting = mCustomWebView!!.settings
        setting.useWideViewPort = true //设定支持viewport
        setting.loadWithOverviewMode = true //自适应屏幕
        setting.builtInZoomControls = true
        setting.displayZoomControls = false
        setting.setSupportZoom(true) //设定支持缩放
        setting.javaScriptEnabled = true // 允许加载JS
        setting.allowFileAccess = true // 允许访问文件
        setting.allowFileAccessFromFileURLs = true // 允许访问URL中的资源
        setting.javaScriptCanOpenWindowsAutomatically = true;
        val cookieManager = CookieManager.getInstance()

        // 加载网页信息
        mCustomWebView.loadUrl(getNavigationUrl())

        // 帮助WebView处理各种通知、请求事件
        mCustomWebView.webViewClient = object : WebViewClient() {

            /**
             * 默认的URL是加载到本地默认的浏览器打开，要设置成本地客户端打开
             * @param view WebView
             * @param request WebResourceRequest
             * @return Boolean
             */
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest?): Boolean {
                // 在WebView里打开新链接
                view.loadUrl(getNavigationUrl())
                return false
            }

            /**
             * WebView监听网页是载完成
             * @param view WebView
             * @param url String
             */
            override fun onPageFinished(view: WebView, url: String?) {
                super.onPageFinished(view, url)
                // 监听WebView已经将网页加载完成了，添加图片的监听
                addImageClickListener(view)
                //获取屏幕高度，另外因为网页可能进行缩放了，所以需要乘以缩放比例得出的才是实际的尺寸
                val cookieManager = CookieManager.getInstance()
                val CookieStr = cookieManager.getCookie(getNavigationUrl())
                Log.e("Web", "Cookies = $CookieStr")
            }
        }

        // 设置JS处理
        mCustomWebView.webChromeClient = WebChromeClient()


        // JS中调用了Android代码，需要设置通道
        mCustomWebView.addJavascriptInterface(NetJavaScriptInterface(this), "listener")

        //比如这里做一个简单的判断，当页面发生滚动，显示那个Button
        mCustomWebView!!.onScrollChangedCallback = object : CustomWebView.OnScrollChangedCallback {
            override fun onScroll(dx: Int, dy: Int) {
                if (dy > 0) {
                    mImageViewToTop!!.visibility = View.VISIBLE
                } else {
                    mImageViewToTop!!.visibility = View.GONE
                }
            }
        }

        mImageViewToTop!!.setOnClickListener {
            mCustomWebView!!.scrollY = 0
            mImageViewToTop!!.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        if (mCustomWebView!!.canGoBack()) {
            mCustomWebView!!.goBack()
        } else {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(applicationContext, "再按一次退出程序", Toast.LENGTH_SHORT).show()
                exitTime = System.currentTimeMillis()
            } else {
                finish()
            }
        }
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