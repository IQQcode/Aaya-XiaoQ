package com.imooc.module_chat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.imooc.module_chat.bean.MessageBean
import com.imooc.module_chat.R

class MessageAdapter(chatBeanList: List<MessageBean>, context: Context?) : BaseAdapter() {

    private val chatBeanList: List<MessageBean> = chatBeanList //聊天数据
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return chatBeanList.size
    }

    override fun getItem(position: Int): Any {
        return chatBeanList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // 重用convertView
        var convertView = convertView
        val holder: Holder = Holder()
        // 判断当前的消息是发送的消息还是接收到的消息，不同消息加载不同的view
        convertView = if (chatBeanList[position].state == MessageBean.RECEIVE_FROM) {
            // 加载左边布局，也就是机器人对应的布局信息
            layoutInflater.inflate(R.layout.item_chat_other, null)
        } else {
            // 加载右边布局，也就是用户对应的布局信息
            layoutInflater.inflate(R.layout.item_chat_me, null)
        }
        convertView.setBackgroundResource(R.drawable.item_selector);
        holder.chatContent = convertView.findViewById(R.id.mTextMessage)
        holder.chatContent!!.text = chatBeanList[position].message
        return convertView
    }

    internal inner class Holder {
        var chatContent: TextView? = null //聊天内容
    }
}