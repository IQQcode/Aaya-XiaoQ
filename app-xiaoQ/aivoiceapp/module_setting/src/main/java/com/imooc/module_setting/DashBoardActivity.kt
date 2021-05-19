package com.imooc.module_setting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.imooc.lib_base.helper.ARouterHelper
import com.imooc.module_setting.adapter.DashBoardAdapter
import com.imooc.module_setting.bean.ItemBean
import com.imooc.module_setting.listener.OnItemClickListener
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * @Author: iqqcode
 * @Date: 2021-05-18 23:50
 * @Description:设置仪表盘界面
 */
@Route(path = ARouterHelper.PATH_SETTING)
class DashBoardActivity : Activity() {

    private lateinit var mList: ArrayList<ItemBean>
    private lateinit var dashBoardAdapter: DashBoardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(this, 2)

        initData()

        dashBoardAdapter = DashBoardAdapter(mList, this)
        mRecyclerView.adapter = dashBoardAdapter

        dashBoardAdapter.setOnItemClickListener(object : OnItemClickListener {

            override fun onItemClick(position: Int) {
                Toast.makeText(this@DashBoardActivity, "点击了第 $position 个item...", Toast.LENGTH_SHORT).show()

                when (position) {
                    0 -> startActivity(Intent(this@DashBoardActivity, SettingActivity::class.java))
                    1 -> startActivity(Intent(this@DashBoardActivity, SettingActivity::class.java))
                    2 -> startActivity(Intent(this@DashBoardActivity, SettingActivity::class.java))
                    3 -> startActivity(Intent(this@DashBoardActivity, SettingActivity::class.java))
                    4 -> startActivity(Intent(this@DashBoardActivity, SettingActivity::class.java))
                    5 -> startActivity(Intent(this@DashBoardActivity, SettingActivity::class.java))
                    6 -> startActivity(Intent(this@DashBoardActivity, SettingActivity::class.java))
                    7 -> startActivity(Intent(this@DashBoardActivity, SettingActivity::class.java))
                }
            }

            override fun onItemLongClick(view: View, position: Int) {

            }
        })
    }

    private fun initData() {
        mList = ArrayList()
        mList.add(ItemBean(R.drawable.settings))
        mList.add(ItemBean(R.drawable.info))
        mList.add(ItemBean(R.drawable.profile))
        mList.add(ItemBean(R.drawable.notes))
        mList.add(ItemBean(R.drawable.folder_android))
        mList.add(ItemBean(R.drawable.bilibili))
        mList.add(ItemBean(R.drawable.csdn))
        mList.add(ItemBean(R.drawable.juejin))
    }
}