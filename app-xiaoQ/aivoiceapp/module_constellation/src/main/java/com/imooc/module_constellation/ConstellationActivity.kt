package com.imooc.module_constellation

import android.graphics.Color
import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.imooc.lib_base.base.BaseActivity
import com.imooc.lib_base.helper.ARouterHelper
import com.imooc.lib_base.utils.SpUtils
import com.imooc.module_constellation.fragment.MonthFragment
import com.imooc.module_constellation.fragment.ToDayFragment
import com.imooc.module_constellation.fragment.WeekFragment
import com.imooc.module_constellation.fragment.YearFragment
import kotlinx.android.synthetic.main.activity_constellation.*


/**
 * FileName: ConstellationActivity
 * Founder: LiuGuiLin
 * Profile: 星座详情
 * TODO: 好运拉满100%
 */

@Route(path = ARouterHelper.PATH_CONSTELLATION_DETAIL)
class ConstellationActivity : BaseActivity() {

    private lateinit var mTodayFragment: ToDayFragment
    private lateinit var mTomorrowFragment: ToDayFragment
    private lateinit var mWeekFragment: WeekFragment
    private lateinit var mMonthFragment: MonthFragment
    private lateinit var mYearFragment: YearFragment

    private val mListFragment = ArrayList<Fragment>()

    override fun getLayoutId(): Int {
        return R.layout.activity_constellation
    }

    override fun getTitleText(): String {
        return getString(com.imooc.lib_base.R.string.app_title_constellation)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {

        // 请求的星座名称
        val name = intent.getStringExtra("name")

        if (!TextUtils.isEmpty(name)) {
            // 语音问答进来的
            name?.let { initFragment(it) }
        } else {
            // 主页[星座Pager] "点" 进来的, 读取历史(默认射手座)
                // TODO: 开篇UI优化
//            val consTellName = SpUtils.getString("consTell")

            val consItemName = intent.getStringExtra("constellation_name")

            consItemName?.let {
                if (!TextUtils.isEmpty(it)) {
                    initFragment(it)
                } else {
                    consItemName?.let { it1 -> initFragment(it1) }
                }
            }
        }

        // View控制
        mTvToday.setOnClickListener {
            checkTab(true, 0)
        }
        mTvTomorrow.setOnClickListener {
            checkTab(true, 1)
        }
        mTvWeek.setOnClickListener {
            checkTab(true, 2)
        }
        mTvMonth.setOnClickListener {
            checkTab(true, 3)
        }
        mTvYear.setOnClickListener {
            checkTab(true, 4)
        }
    }

    /**
     * ViewPage + Fragment 实现滑动切换页面
     * @param name String
     */
    private fun initFragment(name: String) {

        SpUtils.putString("consTell", name)

        // 设置标题
        supportActionBar?.title = name

        mTodayFragment = ToDayFragment(true, name)
        mTomorrowFragment = ToDayFragment(false, name)
        mWeekFragment = WeekFragment(name)
        mMonthFragment = MonthFragment(name)
        mYearFragment = YearFragment(name)

        mListFragment.add(mTodayFragment)
        mListFragment.add(mTomorrowFragment)
        mListFragment.add(mWeekFragment)
        mListFragment.add(mMonthFragment)
        mListFragment.add(mYearFragment)


        initViewPager()
    }

    // 初始化页面
    private fun initViewPager() {
        mViewPager.adapter = PageFragmentAdapter(supportFragmentManager)
        // 预加载偏移量
        mViewPager.offscreenPageLimit = mListFragment.size

        // 监听滑动事件
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) { }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) { }

            override fun onPageSelected(position: Int) {
                checkTab(false, position)
            }

        })

        // 等待全部初始化之后采取做UI控制操作
        checkTab(false, 0)
    }

    /**
     * 适配器
     * @constructor
     */
    inner class PageFragmentAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return mListFragment[position]
        }

        override fun getCount(): Int {
            return mListFragment.size
        }
    }

    /**
     * 切换选项卡
     * @param isClick Boolean 是否是点击，点击才切换
     * @param index Int
     */
    private fun checkTab(isClick: Boolean, index: Int) {

        if (isClick) mViewPager.currentItem = index

        mTvToday.setTextColor(if (index == 0) Color.RED else Color.BLACK)
        mTvTomorrow.setTextColor(if (index == 1) Color.RED else Color.BLACK)
        mTvWeek.setTextColor(if (index == 2) Color.RED else Color.BLACK)
        mTvMonth.setTextColor(if (index == 3) Color.RED else Color.BLACK)
        mTvYear.setTextColor(if (index == 4) Color.RED else Color.BLACK)
    }
}