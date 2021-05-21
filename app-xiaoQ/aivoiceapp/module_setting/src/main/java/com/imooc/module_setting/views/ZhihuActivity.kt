package com.imooc.module_setting.views


class ZhihuActivity : NavigationBase() {

    private val url: String = "https://www.zhihu.com/hot"

    override fun getNavigationUrl(): String {
        return url
    }

}