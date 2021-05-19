package com.imooc.module_setting.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.imooc.module_setting.R
import com.imooc.module_setting.bean.ItemBean


/**
 * @Author: iqqcode
 * @Date: 2021-05-18 23:31
 * @Description:主界面的适配器
 */
class DashBoardAdapter(mListData: List<ItemBean>, mContext: Context) :
    RecyclerView.Adapter<DashBoardAdapter.InnerHolder>() {

    private lateinit var mListData: List<ItemBean>
    private var mContext: Context? = null

    init {
        this.mListData = mListData
        this.mContext = mContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_dashboard, parent, false)
        return InnerHolder(view)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val itemBean: ItemBean = mListData[position]
        holder.imageView.setImageDrawable(mContext?.resources?.getDrawable(itemBean.imageID))
    }

    override fun getItemCount(): Int {
        return mListData.size
    }

    class InnerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView = itemView.findViewById(R.id.mImageView)
    }
}