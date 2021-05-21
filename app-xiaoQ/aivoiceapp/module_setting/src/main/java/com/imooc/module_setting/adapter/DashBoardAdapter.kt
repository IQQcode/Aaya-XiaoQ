package com.imooc.module_setting.adapter


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.imooc.module_setting.R
import com.imooc.module_setting.bean.ItemBean
import com.imooc.module_setting.listener.OnItemClickListener


/**
 * @Author: iqqcode
 * @Date: 2021-05-18 23:31
 * @Description:主界面的适配器
 */
class DashBoardAdapter(mListData: List<ItemBean>, mContext: Context) :
    RecyclerView.Adapter<DashBoardAdapter.InnerHolder>() {

    private var mListData: List<ItemBean> = mListData
    private var mContext: Context? = null

    // 3. 提供设置接口的方法（其实是外部实现）
    private var mOnItemClickListener: OnItemClickListener? = null

    init {
        this.mContext = mContext
    }

    /**
     * 2.设置item的监听事件(定义接口内部的方法)
     * @param listener OnRecyclerItemClickListener
     */
    fun setOnItemClickListener(listener: OnItemClickListener) {
        // 设置监听，即设置回调接口
        this.mOnItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_dashboard, parent, false)
        return InnerHolder(view)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val itemBean: ItemBean = mListData[position]
        holder.imageView.setImageDrawable(mContext?.resources?.getDrawable(itemBean.imageID))
        holder.imageView.tag = position

        //绑定监听点击事件
        holder.itemView.setOnClickListener {
            if (mOnItemClickListener != null) {
                mOnItemClickListener!!.onItemClick(position)
                Log.e("TAG", "BaseAdapter click")
            }
        }
    }

    override fun getItemCount(): Int {
        return mListData.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class InnerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView = itemView.findViewById(R.id.mImageView)
        private val mPosition = 0

        init {
            itemView.setOnClickListener {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener!!.onItemClick(mPosition)
                    Log.e("TAG", "click")
                }
            }
        }
    }
}