package com.xiamu.baselibs.base

/**
 * Created by zhengxiaobo in 2019-12-16
 */
 interface IView{

    /**
     * 页面加载中
     */
    fun showPageLoading()

    /**
     * 空白页面
     */
     fun showPageEmpty()

    /**
     * 页面加载失败
     */
     fun showPageError()

    /**
     * 展示页面内容
     */
     fun showPageContent()

     fun showLoading()

     fun hideLoading()

     fun showError(errorMsg: String)

     fun showMsg(msg: String)
}