//package com.imooc.module_setting.demo
//
//import android.os.Bundle
//import android.widget.ImageView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.viewpager.widget.ViewPager
//import com.squareup.picasso.Picasso
//import java.util.*
//
//class DisplayImageActivity : AppCompatActivity() {
//    var viewPager: ViewPager? = null
//
//    //    view的集合
//    var mDatas: MutableList<ImageView>? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_display)
//        viewPager = findViewById(R.id.id_vp)
//        val intent = intent
//        val position = intent.getIntExtra("position", 0)
//        val imgs = intent.getStringArrayExtra("imgs")
//        mDatas = ArrayList()
//        for (i in imgs!!.indices) {
//            val iv = ImageView(this)
//            Picasso.with(this).load(imgs[i]).into(iv)
//            iv.scaleType = ImageView.ScaleType.CENTER
//            mDatas.add(iv)
//        }
//        val adapter = DisplayAdapter(mDatas)
//        viewPager.setAdapter(adapter)
//        viewPager.setCurrentItem(position)
//    }
//}