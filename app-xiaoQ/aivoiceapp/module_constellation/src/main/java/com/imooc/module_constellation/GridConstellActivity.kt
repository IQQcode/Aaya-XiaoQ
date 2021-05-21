package com.imooc.module_constellation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.imooc.lib_base.helper.ARouterHelper
import com.imooc.module_constellation.adapter.GridConstellAdapter
import com.imooc.module_constellation.bean.ItemBean
import com.imooc.module_constellation.listener.OnItemClickListener
import kotlinx.android.synthetic.main.activity_grid_constell.*

/**
 * @Author: iqqcode
 * @Date: 2021-05-20 20:17
 * @Description:星座视图列表
 */

@Route(path = ARouterHelper.PATH_CONSTELLATION)
class GridConstellActivity : AppCompatActivity() {

    private lateinit var mList: ArrayList<ItemBean>
    private lateinit var gridAdapter: GridConstellAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid_constell)

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(this, 2)

        initData()

        gridAdapter = GridConstellAdapter(mList, this)
        mRecyclerView.adapter = gridAdapter

        gridAdapter.setOnItemClickListener(object : OnItemClickListener {

            override fun onItemClick(position: Int) {
                Toast.makeText(this@GridConstellActivity,"查看" +  mList[position].contellname + "详情", Toast.LENGTH_SHORT).show()

                // 跳转到星座详情页
                val intent = Intent(this@GridConstellActivity, ConstellationActivity::class.java)
                intent.putExtra("constellation_name", mList[position].contellname)
                startActivity(intent)
            }

            override fun onItemLongClick(view: View, position: Int) {

            }
        })
    }

    private fun initData() {
        mList = ArrayList()
        mList.add(ItemBean(R.drawable.img_con_tell_01, "白羊座"))
        mList.add(ItemBean(R.drawable.img_con_tell_02, "金牛座"))
        mList.add(ItemBean(R.drawable.img_con_tell_03, "双子座"))
        mList.add(ItemBean(R.drawable.img_con_tell_04, "巨蟹座"))
        mList.add(ItemBean(R.drawable.img_con_tell_05, "狮子座"))
        mList.add(ItemBean(R.drawable.img_con_tell_06, "处女座"))
        mList.add(ItemBean(R.drawable.img_con_tell_07, "天秤座"))
        mList.add(ItemBean(R.drawable.img_con_tell_08, "天蝎座"))
        mList.add(ItemBean(R.drawable.img_con_tell_09, "射手座"))
        mList.add(ItemBean(R.drawable.img_con_tell_10, "摩羯座"))
        mList.add(ItemBean(R.drawable.img_con_tell_11, "水平座"))
        mList.add(ItemBean(R.drawable.img_con_tell_12, "双鱼座"))
    }
}