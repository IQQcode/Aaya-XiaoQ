package com.imooc.module_constellation.fragment

import android.widget.Toast
import com.imooc.lib_base.base.BaseFragment
import com.imooc.lib_base.utils.L
import com.imooc.lib_network.HttpManager
import com.imooc.lib_network.bean.WeekData
import com.imooc.module_constellation.R
import kotlinx.android.synthetic.main.fragment_week.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * FileName: WeekFragment
 * Founder: LiuGuiLin
 * Profile: 本周星座运势
 */
class WeekFragment(val name: String) : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_week
    }

    override fun initView() {
        loadWeekData()
    }

    private fun loadWeekData() {
        HttpManager.queryWeekConsTellInfo(name, object : Callback<WeekData> {
            override fun onFailure(call: Call<WeekData>, t: Throwable) {
                Toast.makeText(activity, getString(R.string.text_load_fail), Toast.LENGTH_LONG)
                    .show()
            }

            override fun onResponse(call: Call<WeekData>, response: Response<WeekData>) {
                val data = response.body()
                data?.let {
                    L.i("it:$it")
                    tvName.text = it.name
                    tvData.text = it.date
                    tvWeekth.text = getString(R.string.text_week_select, it.weekth)
                    tvHealth.text = it.health
                    tvJob.text = it.job
                    tvLove.text = it.love
                    tvMoney.text = it.money
                    tvWork.text = it.work
                }
            }

        })
    }
}