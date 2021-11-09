package com.pince.compose_app.ui.public

import com.xiamu.baselibs.mvvm.BaseViewModel
import com.xiamu.wanandroid.mvvm.model.entry.TreeBean
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Created by zxb in 2021/11/9
 */
class PublicWechatViewModel: BaseViewModel() {

    private val _publicNumListData = MutableStateFlow<List<TreeBean>>(emptyList())


}