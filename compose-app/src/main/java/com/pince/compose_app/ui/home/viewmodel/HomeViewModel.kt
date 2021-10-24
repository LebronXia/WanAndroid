package com.pince.compose_app.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pince.compose_app.extend.isSuccess
import com.pince.compose_app.model.UiModel
import com.pince.compose_app.model.repository.MainHomeModel
import com.xiamu.baselibs.http.Result
import com.xiamu.baselibs.mvvm.BaseViewModel
import com.xiamu.wanandroid.mvvm.model.entry.ArticleList
import com.xiamu.wanandroid.mvvm.model.entry.LoginBean
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * Created by zxb in 2021/10/23
 */
class HomeViewModel: ViewModel() {
    val homeScreenState = MutableStateFlow(UiModel<List<Any>>(false, null, null))
    private val homeModel by lazy { MainHomeModel() }
    private var currentPage = 0

    init {
        firstLoad()
    }

    private fun firstLoad(){
        viewModelScope.launch {
            dispatchViewState(true)
            val bannerDeffer = async { homeModel.getBanner() }
            val articleDeffer = async { homeModel.getArticleList() }

            val bannerRes = bannerDeffer.await()
            val articleRes = articleDeffer.await()
            if (bannerRes.isSuccess() && articleRes is Result.Success){
                val list = mutableListOf<Any>().apply {
                    add(bannerRes.data!!)
                    addAll(articleRes.data!!.datas)
                }

                dispatchViewState(false, null, list)
            } else {
                if (articleRes is Result.Error)
                    dispatchViewState(false, articleRes.exception.message)
            }
        }
    }



    private fun dispatchViewState(showLoading: Boolean = false,
                                  showError: String ?= null,
                                  showSuccess: List<Any>?= null){
        val uiModel = UiModel(showLoading, showError, showSuccess)
        homeScreenState.value = uiModel
    }

}