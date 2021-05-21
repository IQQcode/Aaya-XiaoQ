package com.imooc.module_constellation.listener

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author: iqqcode
 * @Date: 2021-05-19 12:19
 * @Description:自定义的一个触摸监听器
 */

/**
 * 【编写回调的步骤】
 * 1. 创建这个接口
 * 2. 定义接口内部的方法
 * 3. 提供设置接口的方法（其实是外部实现）
 * 4. 接口方法的调用
 */

interface OnItemClickListener {

    // item点击事件
    fun onItemClick(position: Int)

    // item长按
    fun onItemLongClick(view: View, position: Int)
}