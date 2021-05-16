package com.imooc.module_chat

import com.alibaba.android.arouter.facade.annotation.Route
import com.imooc.lib_base.base.BaseActivity
import com.imooc.lib_base.helper.ARouterHelper
import android.view.View
import com.gcssloop.widget.ArcSeekBar
import com.imooc.lib_voice.manager.VoiceManager
import kotlinx.android.synthetic.main.activity_chat.*

@Route(path = ARouterHelper.PATH_CHAT)
class ChatActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_chat
    }

    override fun getTitleText(): String {
        return getString(com.imooc.lib_base.R.string.app_title_chat)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {
        mArcSeekBar.setArcColors(R.array.arc_colors_custom)
        mArcSeekBar.setMaxValue(200)
        mArcSeekBar.setMinValue(50)
        setEngry(mArcSeekBar.progress)
        var ans = 0
        mArcSeekBar.setOnProgressChangeListener(object : ArcSeekBar.OnProgressChangeListener {
            override fun onProgressChanged(seekBar: ArcSeekBar, progress: Int, isUser: Boolean) {
                setEngry(seekBar.progress)
            }

            override fun onStartTrackingTouch(seekBar: ArcSeekBar) {}
            override fun onStopTrackingTouch(seekBar: ArcSeekBar) {
                setEngry(seekBar.progress)
            }
        })
        mButton01.setOnClickListener(View.OnClickListener { mArcSeekBar.progress = 0 })
        mButton90.setOnClickListener(View.OnClickListener {
            mArcSeekBar.progress = 90
        })
    }

    private fun setEngry(progress: Int) {
        mProgressText!!.text = "POWER $progress %"
    }
}