package com.imooc.aivoiceapp.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.imooc.aivoiceapp.R
import com.imooc.aivoiceapp.adapter.ChatListAdapter
import com.imooc.aivoiceapp.data.ChatList
import com.imooc.aivoiceapp.entity.AppConstants
import com.imooc.lib_base.helper.ARouterHelper
import com.imooc.lib_base.helper.NotificationHelper
import com.imooc.lib_base.helper.SoundPoolHelper
import com.imooc.lib_base.helper.WindowHelper
import com.imooc.lib_base.helper.`fun`.AppHelper
import com.imooc.lib_base.helper.`fun`.CommonSettingHelper
import com.imooc.lib_base.helper.`fun`.ConsTellHelper
import com.imooc.lib_base.helper.`fun`.ContactHelper
import com.imooc.lib_base.utils.L
import com.imooc.lib_base.utils.SpUtils
import com.imooc.lib_network.HttpManager
import com.imooc.lib_network.bean.JokeOneData
import com.imooc.lib_network.bean.RobotData
import com.imooc.lib_network.bean.WeatherData
import com.imooc.lib_voice.engine.VoiceEngineAnalyze
import com.imooc.lib_voice.impl.OnAsrResultListener
import com.imooc.lib_voice.impl.OnNluResultListener
import com.imooc.lib_voice.manager.VoiceManager
import com.imooc.lib_voice.tts.VoiceTTS
import com.imooc.lib_voice.words.WordsTools
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * FileName: VoiceService
 * Founder: LiuGuiLin
 * Profile: 语音服务
 */
class VoiceService : Service(), OnNluResultListener {

    private val mHandler = Handler()

    private lateinit var mFullWindowView: View
    private lateinit var mMainWindowView: View

    private lateinit var mChatListView: RecyclerView
    private lateinit var mLottieView: LottieAnimationView
    private lateinit var mLottieViewMainLeft: LottieAnimationView
    private lateinit var mLottieViewMainRight: LottieAnimationView

    private lateinit var tvVoiceTips: TextView
    private lateinit var ivCloseWindow: ImageView
    private val mList = ArrayList<ChatList>()
    private lateinit var mChatAdapter: ChatListAdapter

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        L.i("语音服务启动")
        initCoreVoiceService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        bindNotification()
        return START_STICKY_COMPATIBILITY
    }

    //绑定通知栏
    private fun bindNotification() {
        startForeground(
            1000,
            NotificationHelper.bindVoiceService(getString(R.string.text_voice_run_text))
        )
    }


    // 初始化语音服务
    private fun initCoreVoiceService() {

        WindowHelper.initHelper(this)
        mFullWindowView = WindowHelper.getView(R.layout.layout_window_item)
        mMainWindowView = WindowHelper.getView(R.layout.activity_main)
        mChatListView = mFullWindowView.findViewById<RecyclerView>(R.id.mChatListView)
        mLottieView = mFullWindowView.findViewById<LottieAnimationView>(R.id.mLottieView)
        mLottieViewMainLeft = mMainWindowView.findViewById<LottieAnimationView>(R.id.mLottieViewMainLeft)
        mLottieViewMainRight = mMainWindowView.findViewById<LottieAnimationView>(R.id.mLottieViewMainRight)
        ivCloseWindow = mFullWindowView.findViewById<ImageView>(R.id.ivCloseWindow)
        tvVoiceTips = mFullWindowView.findViewById<TextView>(R.id.tvVoiceTips)
        mChatListView.layoutManager = LinearLayoutManager(this)
        mChatAdapter = ChatListAdapter(mList)
        mChatListView.adapter = mChatAdapter

        ivCloseWindow.setOnClickListener {
            hideTouchWindow()
        }

        VoiceManager.initManager(this, object : OnAsrResultListener {

            override fun wakeUpReady() {
                L.i("唤醒准备就绪")

                //发声人
                VoiceManager.setPeople(
                    resources.getStringArray(R.array.TTSPeopleIndex)[SpUtils.getInt(
                        "tts_people",
                        6
                    )]
                )
                //语速
                VoiceManager.setVoiceSpeed(SpUtils.getInt("tts_speed", 5).toString())
                //音量
                VoiceManager.setVoiceVolume(SpUtils.getInt("tts_volume", 5).toString())

                val isHello = SpUtils.getBoolean("isHello", true)
                if (isHello) {
                    addAiText("唤醒引擎准备就绪，我是你的智能助手小Q~")
                }
            }

            override fun asrStartSpeak() {
                L.i("开始说话")
            }

            override fun asrStopSpeak() {
                L.i("结束说话")
            }

            override fun wakeUpSuccess(result: JSONObject) {
                L.i("唤醒成功：$result")
                //当唤醒词是 "你好小Q || 小Q同学 || 小Q小Q" 的时候，才开启识别
                val errorCode = result.optInt("errorCode")
                //唤醒成功
                if (errorCode == 0) {
                    //唤醒词
                    val word = result.optString("word")
                    if ((word == getString(R.string.text_voice_wakeup_keyword_one)) or (word == getString(
                            R.string.text_voice_wakeup_keyword_two
                        )) or (word == getString(R.string.text_voice_wakeup_keyword_three))
                    ) {
                        wakeUpFix()
                    }
                }
            }

            override fun updateUserText(text: String) {
                updateTips(text)
            }

            override fun asrResult(result: JSONObject) {
                L.i("====================RESULT======================")
                L.i("result：$result")
            }

            override fun nluResult(nlu: JSONObject) {
                L.i("====================NLU======================")
                L.i("nlu：$nlu")
                addMineText(nlu.optString("raw_text"))
                VoiceEngineAnalyze.analyzeNlu(nlu, this@VoiceService)

            }

            override fun voiceError(text: String) {
                L.e("发生错误：$text")
                hideWindow()
            }
        })
    }

    /**
     * 唤醒成功之后的操作
     */
    private fun wakeUpFix() {
        showWindow()
        updateTips(getString(R.string.text_voice_wakeup_tips))
        SoundPoolHelper.play(R.raw.record_start)
        //应答
        val wakeupText = WordsTools.wakeupWords()
        addAiText(wakeupText,
            object : VoiceTTS.OnTTSResultListener {
                override fun ttsEnd() {
                    //开启识别
                    VoiceManager.startAsr()
                }
            })
    }

    //显示窗口
    private fun showWindow() {
        L.i("======显示窗口======")
        mLottieView.playAnimation()
        WindowHelper.show(mFullWindowView)
    }

    //隐藏窗口
    private fun hideWindow() {
        L.i("======隐藏窗口======")
        mHandler.postDelayed({
            WindowHelper.hide(mFullWindowView)
            mLottieView.pauseAnimation()
            mLottieViewMainLeft.pauseAnimation()
            mLottieViewMainRight.pauseAnimation()
            SoundPoolHelper.play(R.raw.record_over)
        }, 2 * 1000)
    }

    //直接隐藏窗口
    private fun hideTouchWindow() {
        L.i("======隐藏窗口======")
        WindowHelper.hide(mFullWindowView)
        mLottieView.pauseAnimation()
        mLottieViewMainLeft.pauseAnimation()
        mLottieViewMainRight.pauseAnimation()
        SoundPoolHelper.play(R.raw.record_over)
        VoiceManager.stopAsr()
    }

    // 打开APP
    override fun openApp(appName: String) {
        if (!TextUtils.isEmpty(appName)) {
            L.i("Open App $appName")
            val isOpen = AppHelper.launcherApp(appName)
            if (isOpen) {
                addAiText(getString(R.string.text_voice_app_open, appName))
            } else {
                addAiText(getString(R.string.text_voice_app_not_open, appName))
            }
        }
        hideWindow()
    }

    // 卸载App
    override fun unInstallApp(appName: String) {
        if (!TextUtils.isEmpty(appName)) {
            L.i("unInstall App $appName")
            val isUninstall = AppHelper.unInstallApp(appName)
            if (isUninstall) {
                addAiText(getString(R.string.text_voice_app_uninstall, appName))
            } else {
                addAiText(getString(R.string.text_voice_app_not_uninstall))
            }
        }
        hideWindow()
    }

    // 其他App
    override fun otherApp(appName: String) {
        //全部跳转应用市场
        if (!TextUtils.isEmpty(appName)) {
            val isIntent = AppHelper.launcherAppStore(appName)
            if (isIntent) {
                addAiText(getString(R.string.text_voice_app_option, appName))
            } else {
                addAiText(WordsTools.noAnswerWords())
            }
        }
        hideWindow()
    }

    //返回
    override fun back() {
        addAiText(getString(R.string.text_voice_back_text))
        CommonSettingHelper.back()
        hideWindow()
    }

    //主页
    override fun home() {
        addAiText(getString(R.string.text_voice_home_text))
        CommonSettingHelper.home()
        hideWindow()
    }

    //音量+
    override fun setVolumeUp() {
        addAiText(getString(R.string.text_voice_volume_add))
        CommonSettingHelper.setVolumeUp()
        hideWindow()
    }

    // 音量-
    override fun setVolumeDown() {
        addAiText(getString(R.string.text_voice_volume_sub))
        CommonSettingHelper.setVolumeDown()
        hideWindow()
    }

    // 退下
    override fun quit() {
        addAiText(WordsTools.quitWords(), object : VoiceTTS.OnTTSResultListener {
            override fun ttsEnd() {
                hideTouchWindow()
            }

        })
    }

    /**
     * 星座时间
     * @param name String
     */
    override fun conTellTime(name: String) {
        L.i("conTellTime:$name")
        val text = ConsTellHelper.getConsTellTime(name)
        addAiText(text, object : VoiceTTS.OnTTSResultListener {
            // 播放完成后隐藏窗口
            override fun ttsEnd() {
                hideWindow()
            }
        })
    }

    /**
     * 星座详情
     * @param name String
     */
    override fun conTellInfo(name: String) {
        L.i("conTellInfo:$name")
        addAiText(
            getString(R.string.text_voice_query_con_tell_info, name),
            object : VoiceTTS.OnTTSResultListener {
                override fun ttsEnd() {
                    hideWindow()
                }
            })
        // 跳转到该星座的详情页
        ARouterHelper.startActivity(ARouterHelper.PATH_CONSTELLATION_DETAIL, "name", name)
    }

    // 拨打联系人
    override fun callPhoneForName(name: String) {
        val list = ContactHelper.mContactList.filter { it.phoneName == name }
        if (list.isNotEmpty()) {
            addAiText(
                getString(R.string.text_voice_call, name),
                object : VoiceTTS.OnTTSResultListener {
                    override fun ttsEnd() {
                        ContactHelper.callPhone(list[0].phoneNumber)
                    }
                })
        } else {
            addAiText(getString(R.string.text_voice_no_friend))
        }
        hideWindow()
    }

    //拨打号码
    override fun callPhoneForNumber(phone: String) {
        addAiText(getString(R.string.text_voice_call), object : VoiceTTS.OnTTSResultListener {
            override fun ttsEnd() {
                ContactHelper.callPhone(phone)
            }
        })
        hideWindow()
    }

    //播放笑话
    override fun playJoke() {
        HttpManager.queryJoke(object : Callback<JokeOneData> {
            override fun onFailure(call: Call<JokeOneData>, t: Throwable) {
                L.i("onFailure:$t")
                jokeError()
            }

            override fun onResponse(call: Call<JokeOneData>, response: Response<JokeOneData>) {
                L.i("Joke onResponse")
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.error_code == 0) {
                            //根据Result随机抽取一段笑话进行播放
                            val index = WordsTools.randomInt(it.result.size)
                            L.i("index:$index")
                            if (index < it.result.size) {
                                val data = it.result[index]
                                addAiText(data.content, object : VoiceTTS.OnTTSResultListener {
                                    override fun ttsEnd() {
                                        hideWindow()
                                    }
                                })
                            }
                        } else {
                            jokeError()
                        }
                    }
                } else {
                    jokeError()
                }
            }
        })
    }

    //笑话列表
    override fun jokeList() {
        addAiText(getString(R.string.text_voice_query_joke))
        ARouterHelper.startActivity(ARouterHelper.PATH_JOKE)
        hideWindow()
    }

    //机器人
    override fun aiRobot(text: String) {
        //请求机器人回答
        HttpManager.aiRobotChat(text, object : Callback<RobotData> {

            override fun onFailure(call: Call<RobotData>, t: Throwable) {
                addAiText(WordsTools.noAnswerWords())
                hideWindow()
            }

            override fun onResponse(
                call: Call<RobotData>,
                response: Response<RobotData>
            ) {
                L.i("机器人结果:" + response.body().toString())
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.intent.code == 10004) {
                            //回答
                            if (it.results.isEmpty()) {
                                addAiText(WordsTools.noAnswerWords())
                                hideWindow()
                            } else {
                                addAiText(it.results[0].values.text)
                                hideWindow()
                            }
                        } else {
                            addAiText(WordsTools.noAnswerWords())
                            hideWindow()
                        }
                    }
                }
            }

        })
    }

    //查询天气
    override fun queryWeather(city: String) {
        HttpManager.run {
            queryWeather(city, object : Callback<WeatherData> {
                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    addAiText(getString(R.string.text_voice_query_weather_error, city))
                    hideWindow()
                }

                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            it.result.realtime.apply {
                                //在UI上显示
                                addWeather(
                                    city,
                                    wid,
                                    info,
                                    temperature,
                                    object : VoiceTTS.OnTTSResultListener {
                                        override fun ttsEnd() {
                                            hideWindow()
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            })
        }
    }

    //天气详情
    override fun queryWeatherInfo(city: String) {
        addAiText(getString(R.string.text_voice_query_weather, city))
        ARouterHelper.startActivity(ARouterHelper.PATH_WEATHER, "city", city)
        hideWindow()
    }

    //周边搜索
    override fun nearByMap(poi: String) {
        L.i("nearByMap:$poi")
        addAiText(getString(R.string.text_voice_query_poi, poi))
        ARouterHelper.startActivity(ARouterHelper.PATH_MAP, "type", "poi", "keyword", poi)
        hideWindow()
    }

    //线路规划 + 导航
    override fun routeMap(address: String) {
        L.i("routeMap:$address")
        addAiText(getString(R.string.text_voice_query_navi, address))
        ARouterHelper.startActivity(ARouterHelper.PATH_MAP, "type", "route", "keyword", address)
        hideWindow()
    }

    //无法应答
    override fun nluError() {
        //暂不支持
        addAiText(WordsTools.noSupportWords())
        hideWindow()
    }

    /**
     * 添加我的文本
     */
    private fun addMineText(text: String) {
        val bean = ChatList(AppConstants.TYPE_MINE_TEXT)
        bean.text = text
        baseAddItem(bean)
    }

    /**
     * 添加AI文本
     */
    private fun addAiText(text: String) {
        val bean = ChatList(AppConstants.TYPE_AI_TEXT)
        bean.text = text
        baseAddItem(bean)
        VoiceManager.ttsStart(text)
    }

    /**
     * 添加AI文本
     */
    private fun addAiText(text: String, mOnTTSResultListener: VoiceTTS.OnTTSResultListener) {
        val bean = ChatList(AppConstants.TYPE_AI_TEXT)
        bean.text = text
        baseAddItem(bean)
        VoiceManager.ttsStart(text, mOnTTSResultListener)
    }

    /**
     * 添加天气
     */
    private fun addWeather(
        city: String, wid: String, info: String,
        temperature: String, mOnTTSResultListener: VoiceTTS.OnTTSResultListener
    ) {
        val bean = ChatList(AppConstants.TYPE_AI_WEATHER)
        bean.city = city
        bean.wid = wid
        bean.info = info
        bean.temperature = "$temperature°"
        baseAddItem(bean)
        val text = city + "今天天气" + info + temperature + "°"
        VoiceManager.ttsStart(text, mOnTTSResultListener)
    }


    /**
     * 添加基类
     */
    private fun baseAddItem(bean: ChatList) {
        mList.add(bean)
        mChatAdapter.notifyItemInserted(mList.size - 1)
    }

    /**
     * 更新提示语
     */
    private fun updateTips(text: String) {
        tvVoiceTips.text = text
    }

    /**
     * 笑话错误
     */
    private fun jokeError() {
        hideWindow()
        addAiText(getString(R.string.text_voice_query_joke_error))
    }
}