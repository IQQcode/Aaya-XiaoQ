//package com.imooc.module_setting.demo
//
//import android.content.Context
//import android.content.Intent
//import android.webkit.JavascriptInterface
//
///**
// * @Author: iqqcode
// * @Date: 2021-05-19 18:57
// * @Description:
// */
//class NetJavaScriptInterface(var context: Context) {
//    @JavascriptInterface
//    fun openImage(img: String, imgs: Array<String>) {
//        val size = imgs.size
//        var position = 0
//        for (i in 0 until size) {
//            if (img == imgs[i]) {
//                position = i
//            }
//        }
//        //跳转图片界面
//        val intent = Intent(context, DisplayImageActivity::class.java)
//        intent.putExtra("position", position)
//        intent.putExtra("imgs", imgs)
//        context.startActivity(intent)
//    }
//}