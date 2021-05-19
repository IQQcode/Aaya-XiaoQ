package com.imooc.module_setting.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter

/**
 * @Author: iqqcode
 * @Date: 2021-05-19 18:09
 * @Description:WebView图片预览的Adapter
 */
class WebPagerAdapter(var listData: ArrayList<ImageView>) : PagerAdapter() {

    override fun getCount(): Int {
        return listData.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    /**
     * 向容器中添加View
     * @param container ViewGroup
     * @param position Int
     * @return Any
     */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(listData[position])
        return listData[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(listData[position])
    }
}