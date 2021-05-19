package com.imooc.module_setting

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.imooc.module_setting.adapter.DashBoardAdapter
import com.imooc.module_setting.bean.ItemBean
import kotlinx.android.synthetic.main.activity_dashboard.*

/**
 * @Author: iqqcode
 * @Date: 2021-05-18 23:50
 * @Description:
 */
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
    }

    private fun initData() {
        mList = ArrayList()
        mList.add(ItemBean(R.drawable.work))
        mList.add(ItemBean(R.drawable.education))
        mList.add(ItemBean(R.drawable.settings))
        mList.add(ItemBean(R.drawable.startup))
        mList.add(ItemBean(R.drawable.profile))
        mList.add(ItemBean(R.drawable.info))
        mList.add(ItemBean(R.drawable.calendar))
        mList.add(ItemBean(R.drawable.notes))
    }
}