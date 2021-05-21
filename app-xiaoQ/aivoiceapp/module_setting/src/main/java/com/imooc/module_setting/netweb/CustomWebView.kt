package com.imooc.module_setting.netweb

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

/**
 * Created by Jay on 2015/9/11 0011.
 */
class CustomWebView : WebView {

    var onScrollChangedCallback: OnScrollChangedCallback? = null

    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (onScrollChangedCallback != null) {
            onScrollChangedCallback!!.onScroll(l - oldl, t - oldt)
        }
    }

    interface OnScrollChangedCallback {
        // 这里的dx和dy代表的是x轴和y轴上的偏移量，你也可以自己把l, t, oldl, oldt四个参数暴露出来
        fun onScroll(dx: Int, dy: Int)
    }
}