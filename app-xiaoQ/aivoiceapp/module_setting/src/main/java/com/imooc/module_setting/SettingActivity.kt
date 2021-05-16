package com.imooc.module_setting

import com.alibaba.android.arouter.facade.annotation.Route
import com.imooc.lib_base.base.BaseActivity
import com.imooc.lib_base.helper.ARouterHelper
import com.imooc.lib_base.utils.SpUtils
import kotlinx.android.synthetic.main.activity_setting.*


/**
 * FileName: SettingActivity
 * Founder: LiuGuiLin
 * Profile:
 */
@Route(path = ARouterHelper.PATH_SETTING)
class SettingActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun getTitleText(): String {
        return getString(com.imooc.lib_base.R.string.app_title_system_setting)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {

        //欢迎词
        val isHello = SpUtils.getBoolean("isHello", true)
        mSwHello.isChecked = isHello

        mSwHello.setOnClickListener {
            !mSwHello.isChecked
            SpUtils.putBoolean("isHello", mSwHello.isChecked)
        }

        //笑话
        val isJoke = SpUtils.getBoolean("isJoke", true)
        mSwJokeBackground.isChecked = isJoke

        mSwJokeBackground.setOnClickListener {
            !mSwJokeBackground.isChecked
            SpUtils.putBoolean("isJoke", mSwJokeBackground.isChecked)
        }


        val version = packageManager.getPackageInfo(packageName, 0).versionName
        tvVersion.text = getString(R.string.text_version_ui, version)
    }
}