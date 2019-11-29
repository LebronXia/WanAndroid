package com.xiamu.wanandroid.mvvm.view.activity

import android.content.Intent
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.MenuItemCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.hss01248.dialog.StyledDialog
import com.hss01248.dialog.interfaces.MyDialogListener
import com.orhanobut.logger.Logger
import com.xiamu.baselibs.base.BaseActivity
import com.xiamu.baselibs.base.BaseModelActivity
import com.xiamu.baselibs.base.BaseVMFragment
import com.xiamu.baselibs.util.toast
import com.xiamu.wanandroid.constant.AppConstant
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.databinding.MainBinding
import com.xiamu.wanandroid.mvvm.model.event.LoginEvent
import com.xiamu.wanandroid.mvvm.view.fragment.*
import com.xiamu.wanandroid.mvvm.viewmodel.LoginViewModel
import com.xiamu.wanandroid.mvvm.viewmodel.MainViewModel
import com.xiamu.wanandroid.util.Preference
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.simple.eventbus.EventBus
import org.simple.eventbus.Subscriber
import org.simple.eventbus.ThreadMode

class MainActivity: BaseModelActivity<MainViewModel>() {

    private var isLogin by Preference(AppConstant.LOGIN_KEY, false)
    var userName: String by Preference(AppConstant.USER_NAME, "")

    private val FRAGMENT_HOME = 0x01
    private val FRAGMENT_KNOWLEDGE = 0x02
    private val FRAGMENT_NAVIGATION = 0x03
    private val FRAGMENT_PROJECT = 0x04
    private val FRAGMENT_WECHAT = 0x05

    private var mIndex = FRAGMENT_HOME

    private var mHomeFragment: HomeFragment?= null
    private var mKnowledgeTreeFragment: KnowledgeTreeFragment ?= null
    private var mNavigationFragment: NaviFragment?= null
    private var mProjectFragment: ProjectFragment ?= null
    private var mWeChatFragment: WxArticleFragment ?= null

    //nav_view
    private var tv_tologin: TextView ?= null
    private var tv_user_grade: TextView ?= null
    private var tv_user_rank: TextView ?= null
    private var nav_score : TextView ?= null
    private var nav_logout : MenuItem?= null

    override fun providerVMClass(): Class<MainViewModel>? = MainViewModel::class.java

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        bottom_nav.run {
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        }
        initToolBar()
        initDrawerLayout()
        initNavView()
        showFragment(mIndex)
    }

    private fun initToolBar() {
        toolbar.run {
            setSupportActionBar(this)
        }

    }

    private fun initNavView() {
        nav_view.run {
            setNavigationItemSelectedListener (onDrawerNavigationItemSelectedListener)
            tv_tologin = getHeaderView(0).findViewById(R.id.tv_tologin)
            tv_user_grade = getHeaderView(0).findViewById(R.id.tv_user_grade)
            tv_user_rank = getHeaderView(0).findViewById(R.id.tv_user_rank)
            nav_score = MenuItemCompat.getActionView(this.menu.findItem(R.id.nav_score)) as TextView
            nav_score?.gravity = Gravity.CENTER_VERTICAL
            nav_logout = this.menu.findItem(R.id.nav_logout)
            nav_logout?.isVisible = isLogin
        }

        tv_tologin?.run {
            text = if (!isLogin) getString(R.string.login) else userName
            setOnClickListener {
                if (!isLogin)
                    goLogin()
            }
        }

    }

    private fun initDrawerLayout() {
        drawer_layout.run {
            val toggle = ActionBarDrawerToggle(
                this@MainActivity,
                this,
                toolbar
                , R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
            addDrawerListener(toggle)
            toggle.syncState()
        }
    }

    override fun initData() {
        mViewModel.getCoinUserInfo()
    }

    @Subscriber(mode = ThreadMode.MAIN , tag = "main")
    private fun afterLoginUpdateUI(event: LoginEvent){
        if (event.isLogin){
            toast("接收到登录数据")
            tv_tologin?.text = userName
            mViewModel.getCoinUserInfo()
            mHomeFragment?.lazyLoad()
            nav_logout?.isVisible = isLogin
        } else {
            tv_tologin?.text = resources.getString(R.string.login)
            tv_user_grade?.text = "--"
            tv_user_rank?.text = "--"
            nav_logout?.isVisible = false
            mHomeFragment?.lazyLoad()
            nav_logout?.isVisible = isLogin
        }
    }

    override fun startObserve() {
        super.startObserve()

        mViewModel.coinUserState.observe(this, Observer {

            it?.let {
                tv_user_grade?.text = it.level.toString()
                tv_user_rank?.text = it.rank.toString()
                nav_score?.text = it.coinCount.toString()
            }
        })

        mViewModel.logotState.observe(this, Observer {

            if (it){
                GlobalScope.launch {
                    withContext(Dispatchers.IO){
                        Preference.clearPreference()
                    }

                    withContext(Dispatchers.Main){
                        isLogin = false
                        EventBus.getDefault().post(LoginEvent(false), "main")
                    }

                }

            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.menu_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }


    /**
     * 展示Fragment
     * @param index
     */
    private fun showFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        mIndex = index
        when (index) {
            FRAGMENT_HOME // 首页
            -> {
                toolbar.title = getString(R.string.app_name)
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment()
                    transaction.add(R.id.container, mHomeFragment!!, "home")
                } else {
                    transaction.show(mHomeFragment!!)
                }
            }
            FRAGMENT_KNOWLEDGE // 知识体系
            -> {
                toolbar.title = getString(R.string.knowledge_system)
                if (mKnowledgeTreeFragment == null) {
                    mKnowledgeTreeFragment = KnowledgeTreeFragment()
                    transaction.add(R.id.container, mKnowledgeTreeFragment!!, "knowledge")
                } else {
                    transaction.show(mKnowledgeTreeFragment!!)
                }
            }
            FRAGMENT_NAVIGATION // 导航
            -> {
                toolbar.title = getString(R.string.navigation)
                if (mNavigationFragment == null) {
                    mNavigationFragment = NaviFragment()
                    transaction.add(R.id.container, mNavigationFragment!!, "navigation")
                } else {
                    transaction.show(mNavigationFragment!!)
                }
            }
            FRAGMENT_PROJECT // 项目
            -> {
                toolbar.title = getString(R.string.project)
                if (mProjectFragment == null) {
                    mProjectFragment = ProjectFragment()
                    transaction.add(R.id.container, mProjectFragment!!, "project")
                } else {
                    transaction.show(mProjectFragment!!)
                }
            }
            FRAGMENT_WECHAT // 公众号
            -> {
                toolbar.title = getString(R.string.wechat)
                if (mWeChatFragment == null) {
                    mWeChatFragment = WxArticleFragment()
                    transaction.add(R.id.container, mWeChatFragment!!, "wechat")
                } else {
                    transaction.show(mWeChatFragment!!)
                }
            }
        }
        transaction.commit()
    }

    /**
     * 隐藏所有的Fragment
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        mHomeFragment?.let { transaction.hide(it) }
        mKnowledgeTreeFragment?.let { transaction.hide(it) }
        mNavigationFragment?.let { transaction.hide(it) }
        mProjectFragment?.let { transaction.hide(it) }
        mWeChatFragment?.let { transaction.hide(it) }
    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {item ->
            return@OnNavigationItemSelectedListener when(item.itemId){
                R.id.action_home -> {
                    showFragment(FRAGMENT_HOME)
                    true
                }
                R.id.action_knowledge_system -> {
                    showFragment(FRAGMENT_KNOWLEDGE)
                    true
                }
                R.id.action_navigation -> {
                    showFragment(FRAGMENT_NAVIGATION)
                    true
                }
                R.id.action_project -> {
                    showFragment(FRAGMENT_PROJECT)
                    true
                }
                R.id.action_wechat -> {
                    showFragment(FRAGMENT_WECHAT)
                    true
                }
                else -> {
                    false
                }
            }
        }

    private val onDrawerNavigationItemSelectedListener =
        NavigationView.OnNavigationItemSelectedListener {item ->
            when(item.itemId){
                R.id.nav_score -> {

                }
                R.id.nav_collect -> {
                    if (isLogin){
                        startActivity(Intent(this@MainActivity, CollectActivity::class.java))
                        //toast("你已登陆成功")
                    } else {
                        toast(resources.getString(R.string.please_login))
                        goLogin()
                    }
                }
                R.id.nav_logout -> {
                    StyledDialog.buildMdAlert("", "确认退出登录", object : MyDialogListener() {
                        override fun onSecond() {
                        }

                        override fun onFirst() {
                            mViewModel.logot()
                        }

                    }).setBtnSize(14)
                        .setBtnText("确定", "取消")
                        .show()
                }
            }
            return@OnNavigationItemSelectedListener  true
        }

    private fun goLogin(){
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

}
