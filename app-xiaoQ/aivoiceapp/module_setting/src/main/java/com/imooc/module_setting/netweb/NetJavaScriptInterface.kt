package com.imooc.module_setting.netweb


import android.content.Context
import android.content.Intent
import android.util.Log
import android.webkit.JavascriptInterface
import com.imooc.module_setting.WebPagerActivity

/**
 * @Author: iqqcode
 * @Date: 2021-05-19 16:24
 * @Description:点击WebView URL中的每个图片调用此函数(为图片添加点击事件)
 */
class NetJavaScriptInterface(var mContext: Context) {

    /**
     * 点击跳转到打开图片
     * @param imageArray Array<String> 图片数组
     * @param url String URL
     */
    @JavascriptInterface
    fun openImage(url: String, imageArray: Array<String>) {
        Log.i("Web", "***************************************")

        val size = imageArray.size
        var position = 0
        for (i in 0 until size) {
            if (url == imageArray[i]) {
                position = i
            }
        }

        Log.i("Web", "openImage **==> position: $position")
        Log.i("Web", "openImage ||==> url: $url")

        //跳转图片界面
        val intent = Intent(mContext, WebPagerActivity::class.java)
        intent.putExtra("position", position)
        intent.putExtra("imageArray", imageArray)
        mContext?.startActivity(intent)
    }
}