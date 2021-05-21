package com.imooc.module_setting.views


class AndroidOfficialActivity : NavigationBase() {

    private val url: String = "https://developer.android.google.cn/"

    override fun getNavigationUrl(): String {
        return url
    }
}