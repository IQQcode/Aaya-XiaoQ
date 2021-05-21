package com.imooc.module_constellation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.imooc.module_constellation.R
import com.imooc.module_constellation.bean.ItemBean
import com.imooc.module_constellation.listener.OnItemClickListener

/**
 * @Author: iqqcode
 * @Date: 2021-05-20 20:35
 * @Description:主界面Grid的适配器
 */
class GridConstellAdapter(listData: List<ItemBean>, mContext: Context) : RecyclerView.Adapter<GridConstellAdapter.InnerViewHolder>() {

    private var listData: List<ItemBean> = listData
    private var mContext: Context? = null

    init {
        this.mContext = mContext
    }

    // 3. 提供接口设置的方法
    private var mOnItemClickListener: OnItemClickListener? = null

    /**
     *  2.设置item的监听事件(定义接口内部的方法)
     * @param listener OnItemClickListener
     */
    fun setOnItemClickListener(listener: OnItemClickListener) {
        // 设置监听，即设置回调接口
        this.mOnItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_constellation_view, parent, false)
        return InnerViewHolder(view)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        val itemBean: ItemBean = listData[position]
        holder.imageView.setImageDrawable(mContext?.resources?.getDrawable(itemBean.imageID))
        holder.imageView.tag = position

        // 绑定监听点击事件
        holder.itemView.setOnClickListener {
            if (mOnItemClickListener != null) {
                mOnItemClickListener!!.onItemClick(position)
                Log.e("TAG", "BaseAdapter click")
            }
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    inner class InnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView = itemView.findViewById(R.id.mConstrllImage)
        private val mPosition = 0

        init {
            itemView.setOnClickListener {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener!!.onItemClick(mPosition)
                    Log.e("CONS", "click")
                }
            }
        }
    }
}