package com.xiamu.wanandroid.mvvm.view.activity

import android.opengl.Visibility
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.xiamu.baselibs.base.BaseActivity
import com.xiamu.wanandroid.constant.AppConstant
import kotlinx.android.synthetic.main.activity_knowtree_detail.*
import kotlinx.android.synthetic.main.activity_web.*
import android.webkit.WebView
import android.view.KeyEvent
import android.view.View
import android.webkit.WebResourceRequest
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.xiamu.wanandroid.R
import kotlinx.android.synthetic.main.activity_knowtree_detail.toolbar
import kotlinx.android.synthetic.main.toolbar.*


/**
 * Created by zhengxiaobo in 2019-11-28
 */
class WebActivity : BaseActivity(){

    private var agentWeb: AgentWeb ?= null
    private lateinit var urlLink: String

    override fun getLayoutResId(): Int = R.layout.activity_web

    override fun initView() {

        toolbar.apply {
            title = ""
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                finish()
            }
        }

        tv_title.apply {
            text = getString(R.string.web_isloading)
            visibility = View.VISIBLE
            postDelayed({
                tv_title.isSelected = true
            }, 2000)
        }

        initWebView()
    }

    override fun initData() {
        urlLink = intent.extras?.getString(AppConstant.EXTRA_URL_KEY).toString()
    }

    private fun initWebView() {

        val layoutParams = CoordinatorLayout.LayoutParams(-1, -1)
        layoutParams.behavior = AppBarLayout.ScrollingViewBehavior()

        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(cl_main, layoutParams)
            .useDefaultIndicator()// 使用默认进度条
            .setWebChromeClient(mWebChromeClient)
            .setWebViewClient(mWebViewClient)
            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
            .createAgentWeb()//
            .ready()
            .go(urlLink)
    }



    private val mWebViewClient = object : WebViewClient() {

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }
    }

    private val mWebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            title?.let {
               // toolbar.title = it
                tv_title.text = it
            }
        }
    }

    override fun onResume() {
        agentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onPause() {
        agentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        agentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (agentWeb?.handleKeyEvent(keyCode, event)!!) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}