package com.pince.compose_app.ui.public

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.pince.compose_app.model.pagging.CommonPagingSource
import com.pince.compose_app.model.repository.WxArticleRepo
import com.xiamu.baselibs.mvvm.BaseViewModel
import com.xiamu.wanandroid.mvvm.model.entry.TreeBean
import com.xiamu.wanandroid.mvvm.model.entry.TreeItemData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Created by zxb in 2021/11/9
 */
class PublicWechatTabViewModel(private val indexId: Int): BaseViewModel() {

    private val wxArticleRepo by lazy { WxArticleRepo() }

    var isFirstLoad = true;

    //公众号某个列表数据
    val publicNumListData: Flow<PagingData<TreeItemData>>
        get() = _publicNumListData

    private val _publicNumListData = Pager(PagingConfig(pageSize = 20)){
        CommonPagingSource<TreeItemData>{ nextPage: Int ->
            wxArticleRepo.getWxArticleList(nextPage, indexId)
        }
    }.flow.cachedIn(viewModelScope)

}