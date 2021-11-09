package com.pince.compose_app.ui.public

import android.util.Log
import androidx.collection.LongSparseArray
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pince.compose_app.model.pagging.CommonPagingSource
import com.pince.compose_app.model.repository.MainHomeRepo
import com.pince.compose_app.model.repository.WxArticleRepo
import com.xiamu.baselibs.mvvm.BaseViewModel
import com.xiamu.baselibs.util.onFailure
import com.xiamu.baselibs.util.onSuccess
import com.xiamu.wanandroid.mvvm.model.entry.TreeBean
import com.xiamu.wanandroid.mvvm.model.entry.TreeDetailBean
import com.xiamu.wanandroid.mvvm.model.entry.TreeItemData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Created by zxb in 2021/11/9
 */
class PublicWechatViewModel: BaseViewModel() {

    private val wxArticleRepo by lazy { WxArticleRepo() }

    val tabViewModelMap = LongSparseArray<PublicWechatTabViewModel>()

    private val _publicNumChapter = MutableStateFlow<List<TreeBean>>(emptyList())
    val publicNumChapter = _publicNumChapter

    init {
        getWxArticles()
    }

    /**
     * 获取公众号列表
     */
    private fun getWxArticles(){
        launch {
            wxArticleRepo.getWxArticle().onSuccess {
                _publicNumChapter.value = this
            }.onFailure {
                Log.e("xxx", "获取公众号列表 顶部指示器数据 接口异常 ${it.message}")
            }
        }
    }


}