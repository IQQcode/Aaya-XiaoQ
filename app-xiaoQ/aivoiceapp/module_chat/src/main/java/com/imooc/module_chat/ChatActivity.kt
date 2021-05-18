package com.imooc.module_chat

import com.alibaba.android.arouter.facade.annotation.Route
import com.imooc.lib_base.base.BaseActivity
import com.imooc.lib_base.helper.ARouterHelper
import android.view.View
import kotlinx.android.synthetic.main.activity_chat.*
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.imooc.module_chat.adapter.MessageAdapter
import com.imooc.module_chat.bean.MessageBean
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*
import kotlin.system.exitProcess

@Route(path = ARouterHelper.PATH_CHAT)
class ChatActivity : BaseActivity() {

    private var adapter: MessageAdapter? = null
    private var chatBeanList: MutableList<MessageBean>? = null //存放所有聊天数据的集合

    private var sendMsg: String? = null //发送的信息
    private lateinit var welcome: Array<String> //存储欢迎信息
    private var mHandler: MHandler? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_chat
    }

    override fun getTitleText(): String {
        return getString(com.imooc.lib_base.R.string.app_title_chat)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {
        chatBeanList = ArrayList()
        mHandler = MHandler()
        //获取内置的欢迎信息
        welcome = resources.getStringArray(R.array.welcome_text)

        initMessageView() //初始化界面控件
    }

    private fun initMessageView() {
        adapter = MessageAdapter(chatBeanList!!, this)
        mListView!!.adapter = adapter
        mSendButton!!.setOnClickListener {
            sendData() //点击发送按钮，发送信息
        }
        mEditTextInput!!.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action ==
                KeyEvent.ACTION_DOWN
            ) {
                sendData() //点击Enter键也可以发送信息
            }
            false
        }
        val position = (Math.random() * welcome.size - 1).toInt() //获取一个随机数
        showData(welcome[position]) //用随机数获取机器人的首次聊天信息
    }

    private fun sendData() {
        sendMsg = mEditTextInput!!.text.toString() //获取你输入的信息
        if (TextUtils.isEmpty(sendMsg)) { //判断是否为空
            Toast.makeText(this, "您还未输入任何信息哦", Toast.LENGTH_LONG).show()
            return
        }
        mEditTextInput.setText("")
        //替换空格和换行
        sendMsg =
            sendMsg!!.replace(" ".toRegex(), "").replace("\n".toRegex(), "").trim { it <= ' ' }
        val chatBean = MessageBean()
        chatBean.message = sendMsg as String
        chatBean.state = MessageBean.SEND_TO //SEND表示自己发送的信息
        chatBeanList!!.add(chatBean) //将发送的信息添加到chatBeanList集合中
        adapter!!.notifyDataSetChanged() //更新ListView列表
        mEditTextInput.text.clear()
        dataFromServer //从服务器获取机器人发送的信息
    }

    private val dataFromServer: Unit
        // 开启异步线程访问网络
        private get() {
            val okHttpClient = OkHttpClient()
            // [图林机器人] Request request = new Request.Builder().url(WEB_SITE + "?key=" + KEY + "&info=" + sendMsg).build();
            val request = Request.Builder().url(WEB_SITE + sendMsg).build()
            Log.i("TAG", "Get请求的数据为: $request")
            val call = okHttpClient.newCall(request)
            // 开启异步线程访问网络
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val res = response.body()!!.string()
                    val msg = Message()
                    msg.what = MSG_OK
                    msg.obj = res
                    mHandler!!.sendMessage(msg)
                }
            })
        }

    // 事件捕获
    internal inner class MHandler : Handler() {
        override fun dispatchMessage(msg: Message) {
            super.dispatchMessage(msg)
            when (msg.what) {
                MSG_OK -> if (msg.obj != null) {
                    val vlResult = msg.obj as String
                    paresData(vlResult)
                }
            }
        }
    }

    /**
     * Json解析
     * @param JsonData String
     */
    private fun paresData(JsonData: String) {
        try {
            val obj = JSONObject(JsonData)
            //获取的机器人信息
            val content = obj.getJSONObject("data").getJSONObject("info").getString("text")
            Log.i("TAG", "返回的响应消息: $content")

            showData(content)
        } catch (e: JSONException) {
            e.printStackTrace()
            showData("主人，你的网络不好哦")
        }
    }

    /**
     * 展示返回的消息
     * @param message String
     */
    private fun showData(message: String) {
        val chatBean = MessageBean()
        chatBean.message = message
        chatBean.state = MessageBean.RECEIVE_FROM //机器人发送的消息
        chatBeanList!!.add(chatBean) //将机器人发送的消息添加到chatBeanList集合中
        adapter!!.notifyDataSetChanged()
    }

    /**
     * 更新视图
     * @param code Int
     * @param content String
     */
    private fun updateView(code: Int, content: String) {
        //code有很多形状，在此例举几种。
        when (code) {
            4004 -> showData("主人，今天我累了，我要休息了，明天再来找我耍吧")
            40005 -> showData("主人，你说的是火星语吗？")
            40006 -> showData("主人，我今天要去约会哦，改天再聊哦。")
            40007 -> showData("主人，明天再和你耍啦，我感冒了，呜呜呜。。。")
            else -> showData(content)
        }
    }

    private var exitTime: Long = 0 //记录第一次点击时的时间

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK
            && event.action == KeyEvent.ACTION_DOWN
        ) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(this@ChatActivity, "再按一次退出聊天程序", Toast.LENGTH_SHORT).show()
                exitTime = System.currentTimeMillis()
            } else {
                finish()
                exitProcess(0)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 随机颜色函数将生成一个随机的七个字符的颜色十六进制，例如#FF0000
     *
     * @return String
     */
    private val randomColor: String
        private get() {
            val random = Random()
            val sb = StringBuffer("#")
            while (sb.length < 7) {
                sb.append(Integer.toHexString(random.nextInt()))
            }
            return sb.toString().substring(0, 7)
        }

    companion object {
        // 接口地址
        private const val WEB_SITE = "https://api.ownthink.com/bot?appid=xiaosi&userid=user&spoken="
        const val MSG_OK = 1 //获取数据
    }

}