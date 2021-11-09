package com.pince.compose_app.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pince.compose_app.model.UiModel
import com.pince.compose_app.model.entry.Article
import com.pince.compose_app.model.entry.BannerData
import com.pince.compose_app.model.pagging.CommonPagingSource
import com.pince.compose_app.model.repository.MainHomeRepo
import com.xiamu.baselibs.http.Result
import com.xiamu.baselibs.mvvm.BaseViewModel
import com.xiamu.baselibs.util.onFailure
import com.xiamu.baselibs.util.onSuccess
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * Created by zxb in 2021/10/23
 */
class HomeViewModel: BaseViewModel() {
    val homeScreenState = MutableStateFlow(UiModel<List<Any>>(false, null, null))
    private val homeRepo by lazy { MainHomeRepo() }

    val homeListData: Flow<PagingData<Article>>
        get() = _homeListData

    private val _homeListData = Pager(PagingConfig(pageSize = 20)){
        CommonPagingSource{nextPage: Int ->
            homeRepo.getHomeList(nextPage)
        }
    }.flow.cachedIn(viewModelScope)

    private val _bannerListData = MutableStateFlow<List<BannerData>>(emptyList())
    val bannerListData = _bannerListData


    init {
        getBannerData()
    }

    private fun getBannerData(){
        launch {
            homeRepo.getBanner().onSuccess {
                val bannerList = mutableListOf<BannerData>()
                this?.forEach{data ->
                    bannerList.add(BannerData(data.imagePath.toString(), data.url.toString()))
                }
                _bannerListData.value = bannerList
            }.onFailure {
                Log.e("xxx", "接口异常：${it.message}")
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