package com.imooc.module_voice_setting

import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.gcssloop.widget.ArcSeekBar
import com.imooc.lib_base.base.BaseActivity
import com.imooc.lib_base.base.adapter.CommonAdapter
import com.imooc.lib_base.base.adapter.CommonViewHolder
import com.imooc.lib_base.helper.ARouterHelper
import com.imooc.lib_base.utils.SpUtils
import com.imooc.lib_voice.manager.VoiceManager
import kotlinx.android.synthetic.main.activity_voice_setting.*
import kotlinx.android.synthetic.main.content_main.*


/**
 * FileName: VoiceSetting
 * Founder: LiuGuiLin
 * Profile: 语音设置
 */
@Route(path = ARouterHelper.PATH_VOICE_SETTING)
class VoiceSettingActivity : BaseActivity() {

    private var mTtsPeople: Array<out  String>? = null  // 发音人列表数据源
    private var mTtsPeopleIndex: Array<String>? = null // 发音人对应的ID

    private var adapter: GridViewAdapter? = null
    private var listData = ArrayList<ItemBean>()


    override fun getLayoutId(): Int {
        return R.layout.activity_voice
    }

    override fun getTitleText(): String {
        return getString(com.imooc.lib_base.R.string.app_title_voice_setting)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {

        // 设置状态栏为全透明
        setStatusBarFullTransparent();
        setFitSystemWindow(false);

        initData()
        initRecyclerViewAndVoice()

        // 语音设置默认值
        mVoiceSpeed.progress = 5
        mVoiceVolume.progress = 5


        // 设置最大值
        mVoiceSpeed.max = 15
        mVoiceVolume.max = 15

    }

    /**
     * 全透状态栏
     */
    private fun setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) { //21表示5.0
            val window: Window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= 19) { //19表示4.4
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // 虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 如果需要内容紧贴着StatusBar
     * 应该在对应的xml布局文件中，设置根布局fitsSystemWindows=true。
     */
    private var contentViewGroup: View? = null

    private fun setFitSystemWindow(fitSystemWindow: Boolean) {
        if (contentViewGroup == null) {
            contentViewGroup = (findViewById<ViewGroup>(android.R.id.content)).getChildAt(0)
        }
        contentViewGroup?.fitsSystemWindows = fitSystemWindow
    }

    /**
     * 初始化RecyclerView 数据源
     * 初始化语音
     */
    private fun initData() {
        // 发声人
        mTtsPeople = resources.getStringArray(R.array.TTSPeople)
        // 发声人对应ID
        mTtsPeopleIndex = resources.getStringArray(R.array.TTSPeopleIndex)
        for (i in DataItem.icons.indices) {
            val data = ItemBean(DataItem.icons[i], (mTtsPeople as Array<out String>)[i], DataSubItem.subIcons[i])
            listData.add(data)
        }
    }

    /**
     * GridView样式控制
     */
    private fun initRecyclerViewAndVoice() {
        // 创建布局管理器
        val gridLayoutManager = GridLayoutManager(this, 2)
        // mRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        // 设置布局管理器
        mRecyclerView.layoutManager = gridLayoutManager

        // 创建适配器
        adapter = GridViewAdapter(listData)

        // 设置适配器
        mRecyclerView.adapter = adapter

        // 初始化item的点击事件
        initListener()
    }

    private fun initListener() {
        // GridView item点击事件
        adapter?.setOnItemClickListener(object : GridViewAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.e("TAG", "VoiceActivity item clicked!")

                // TODO: 随机词汇
                VoiceManager.ttsStart(getString(R.string.text_test_tts))

                mTtsPeopleIndex?.let {
                    VoiceManager.setPeople(it[position])
                }
                SpUtils.getInt("tts_people", position)

                Toast.makeText(
                    this@VoiceSettingActivity,
                    (mTtsPeople as Array<out String>)[position],
                    Toast.LENGTH_SHORT
                ).show()
            }
        })


        // 语速监听
        mVoiceSpeed.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mVoiceSpeed.progress = progress
                VoiceManager.setVoiceSpeed(progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        // 音量监听
        mVoiceVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mVoiceVolume.progress = progress
                VoiceManager.setVoiceVolume(progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }
}