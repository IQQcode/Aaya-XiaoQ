package com.imooc.lib_base.base

import android.app.Application
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import com.imooc.lib_base.helper.ARouterHelper
import com.imooc.lib_base.helper.NotificationHelper
import com.imooc.lib_base.map.MapManager
import com.imooc.lib_base.service.InitService
import com.imooc.lib_base.utils.CommonUtils

/**
 * FileName: BaseApp
 * Founder: LiuGuiLin
 * Profile: 基类App
 */
open class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()

        //只有主进程才能初始化
        val processName = CommonUtils.getProcessName(this)
        if (!TextUtils.isEmpty(processName)) {
            if (processName == packageName) {
                initApp()
            }
        }
    }

    //初始化App
    private fun initApp() {
        ARouterHelper.initHelper(this)
        NotificationHelper.initHelper(this)
        MapManager.initMap(this) // 初始化地图

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(this, InitService::class.java))
        } else {
            startService(Intent(this, InitService::class.java))
        }
    }
}