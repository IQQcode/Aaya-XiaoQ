package com.imooc.module_voice_setting

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imooc.lib_base.utils.SpUtils
import com.imooc.lib_voice.manager.VoiceManager
import com.imooc.module_voice_setting.ItemBean


/**
 * @Author: iqqcode
 * @Date: 2021-05-12 19:10
 * @Description:
 */
class GridViewAdapter(data: List<ItemBean>?) : RecyclerView.Adapter<GridViewAdapter.InnerHolder>() {

    private var mTtsPeopleIndex: Array<String>? = null // 发音人对应的ID
    private var data: List<ItemBean>? = null


    // 3. 提供设置接口的方法（其实是外部实现）
    private lateinit var mOnItemClickListener: OnItemClickListener

    init {
        this.data = data
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view = View.inflate(parent?.context, R.layout.item_grid_view, null)
        return InnerHolder(view)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        // 在这里设置数据
        holder.setData(data!![position])
        holder.itemView.setOnClickListener {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (data == null) 0 else data!!.size
    }

    // 2.设置item的监听事件(定义接口内部的方法)
    fun setOnItemClickListener(listener: OnItemClickListener) {
        // 设置监听，即设置回调接口
        mOnItemClickListener = listener
    }

    /**
     * 【编写回调的步骤】
     * 1. 创建这个接口
     * 2. 定义接口内部的方法
     * 3. 提供设置接口的方法（其实是外部实现）
     * 4. 接口方法的调用
     */
    // 1.创建接口
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class InnerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val mIcon: ImageView = itemView.findViewById(R.id.icon)
        private val mTitle: TextView = itemView.findViewById(R.id.textViewSubTitle)
        private val mHomeCardImage: ImageView = itemView.findViewById(R.id.mHomeCardImage)
        private val mPosition = 0

        init {

            // 初始化控件
            itemView.setOnClickListener {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(mPosition)
                }
            }
        }

        fun setData(itemBean: ItemBean) {
            mIcon.setImageResource(itemBean.imageId)
            mTitle.text = itemBean.title
            mHomeCardImage.setImageResource(itemBean.subImageId)
        }
    }
}