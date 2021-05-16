package com.imooc.aivoiceapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_splash.*


/**
 * @Author: iqqcode
 * @Date: 2021-05-12 08:36
 * @Description:启动闪屏页
 */

class SplashActivity : Activity() {

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 隐藏标题栏
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_splash)

        // 设置状态栏为全透明
        setStatusBarFullTransparent();
        setFitSystemWindow(false);

        val topAnnotation = loadAnimation(this, R.anim.top_animation)
        val bottomAnnotation = loadAnimation(this, R.anim.bottom_animation)

        splashImageView.animation = topAnnotation
        mAppTextLogo.animation = bottomAnnotation
        mTextSlogan.animation = bottomAnnotation

        // "fonts/english.ttf"  为assets下的相对路径
        mAppTextLogo.typeface = Typeface.createFromAsset(assets, "bangers.ttf");

        // 模拟耗时操作
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 2000)
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