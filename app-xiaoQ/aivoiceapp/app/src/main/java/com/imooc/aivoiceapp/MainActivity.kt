package com.imooc.aivoiceapp

import android.Manifest
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.imooc.aivoiceapp.data.MainListData
import com.imooc.aivoiceapp.service.VoiceService
import com.imooc.lib_base.BuildConfig
import com.imooc.lib_base.base.BaseActivity
import com.imooc.lib_base.base.adapter.BasePagerAdapter
import com.imooc.lib_base.helper.ARouterHelper
import com.imooc.lib_base.helper.`fun`.ContactHelper
import com.yanzhenjie.permission.Action
import com.zhy.magicviewpager.transformer.ScaleInTransformer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    // 根据用户使用的场景动态获取权限
    private val permission = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CALL_PHONE,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.VIBRATE,
        Manifest.permission.CAMERA
    )


    private val mList = ArrayList<MainListData>()
    private val mListView = ArrayList<View>()

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getTitleText(): String {
        return getString(R.string.app_name)
    }

    override fun isShowBack(): Boolean {
        return false
    }

    override fun initView() {
//        EventManager.register(this)
        // 获取权限
        if (checkPermission(permission)) {
            linkService()
        } else {
            requestPermission(permission, Action<List<String>> { linkService() })
        }

        // 窗口权限
        if (!checkWindowPermission()) {
            requestWindowPermission(packageName)
        }

        initPagerData()

        initPagerView()

        // 底部动画初始化
//        mLottieViewLeft.playAnimation()
//        mLottieViewRight.playAnimation()

        // 设置状态栏为全透明
        setStatusBarFullTransparent();
        setFitSystemWindow(false);
    }

    /**
     * 初始化ViewPager
     */
    private fun initPagerView() {
        mViewPager.pageMargin = 18 // 设置页面之间的边距
        mViewPager.offscreenPageLimit =
            mList.size // 设置在空闲状态下视图层次结构中应保留到当前页面任一侧的页面数。 超出此限制的页面将在需要时从适配器重新创建。
        mViewPager.adapter = BasePagerAdapter(mListView)
        mViewPager.setPageTransformer(true, ScaleInTransformer()) // 设置偏移量, 层叠效果
    }


    /**
     * 初始化首页数据
     */
    private fun initPagerData() {
        // 资源数组列表
        val title = resources.getStringArray(R.array.MainTitleArray)
        val color = resources.getIntArray(R.array.MainColorArray)
        val icon = resources.obtainTypedArray(R.array.MainIconArray)

        for ((index, value) in title.withIndex()) {
            mList.add(MainListData(value, icon.getResourceId(index, 0), color[index]))
        }

        // 非调试版本去除工程模式
        if (!BuildConfig.DEBUG) {
            mList.removeAt(mList.size - 1)
        }

        val windowHeight = windowManager.defaultDisplay.height
        mList.forEach {

            val view = View.inflate(this, R.layout.layout_main_list, null)

            val mCvMainView = view.findViewById<CardView>(R.id.mCvMainView)
            val mTvMainText = view.findViewById<TextView>(R.id.mTvMainText)
            // val mIvMainIcon = view.findViewById<ImageView>(R.id.mIvMainIcon)

            mCvMainView.setBackgroundResource(it.icon)
            mCvMainView.radius = 20F
            // mIvMainIcon.setImageResource(it.icon)
            mTvMainText.text = it.title

            // 卡片偏移位置
            mCvMainView.layoutParams?.let { lp ->
                lp.height = windowHeight / 5 * 3
            }

            // Pager的点击事件
            view.setOnClickListener { _ ->
                when (it.icon) {
                    R.drawable.img_main_weather -> ARouterHelper.startActivity(ARouterHelper.PATH_WEATHER)
                    R.drawable.img_main_constellation -> ARouterHelper.startActivity(ARouterHelper.PATH_CONSTELLATION)
                    R.drawable.img_main_joke_icon -> ARouterHelper.startActivity(ARouterHelper.PATH_JOKE)
                    R.drawable.img_main_map_icon -> ARouterHelper.startActivity(ARouterHelper.PATH_MAP)
                    R.drawable.img_main_chat_icon -> ARouterHelper.startActivity(ARouterHelper.PATH_CHAT)
                    R.drawable.img_main_app_manager -> ARouterHelper.startActivity(ARouterHelper.PATH_APP_MANAGER)
                    R.drawable.img_main_voice_setting -> ARouterHelper.startActivity(ARouterHelper.PATH_VOICE_SETTING)
                    R.drawable.img_main_system_setting -> ARouterHelper.startActivity(ARouterHelper.PATH_SETTING)
                    R.drawable.img_main_developer -> ARouterHelper.startActivity(ARouterHelper.PATH_DEVELOPER)
                }
            }
            mListView.add(view)
        }
    }


    // 连接服务
    private fun linkService() {
        // 读取联系人
        ContactHelper.initHelper(this)

        startService(Intent(this, VoiceService::class.java))
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
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 半透明状态栏
     */
    private fun setHalfTransparent() {
        if (Build.VERSION.SDK_INT >= 21) { //21表示5.0
            val decorView: View = window.decorView
            val option: Int =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        } else if (Build.VERSION.SDK_INT >= 19) { //19表示4.4
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //虚拟键盘也透明
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
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

    private fun getStatusBarHeight(): Int {
        val resources: Resources = this.resources
        val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }
}
