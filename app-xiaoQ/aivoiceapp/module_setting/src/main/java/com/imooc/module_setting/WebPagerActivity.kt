package com.imooc.module_setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView
import android.widget.Toast
import com.imooc.module_setting.adapter.WebPagerAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_webpager.*

/**
 * @Author: iqqcode
 * @Date: 2021-05-19 15:25
 * @Description:加载网页试图浏览的ViewPager
 */
class WebPagerActivity : AppCompatActivity() {

    // 数据源View的集合
    private var mList : ArrayList<ImageView> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpager);

        val getIntentData = intent
        val position = getIntentData.getIntExtra("position", 0)
        val imageArray = getIntentData.getStringArrayExtra("imageArray")

        if (imageArray != null) {
            for (i in imageArray.indices) {
                val mImageView = ImageView(this)
                Picasso.with(this).load(imageArray[i]).into(mImageView)
                mImageView.scaleType = ImageView.ScaleType.CENTER
                mList.add(mImageView)
            }
        } else {
            Toast.makeText(this@WebPagerActivity, "数据源为空", Toast.LENGTH_SHORT).show()
        }

        val webAdapter = WebPagerAdapter(mList)
        mViewPager.adapter = webAdapter

        // ViewPager设定显示当前位置的View(默认为第一个)
        mViewPager.currentItem = position
    }
}