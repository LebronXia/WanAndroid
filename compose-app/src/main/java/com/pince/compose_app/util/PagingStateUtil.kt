package com.pince.compose_app.util

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.pince.compose_app.extend.sleepTime
import com.pince.compose_app.widget.ErrorComposable

/**
 * Created by zxb in 2021/10/31
 */
class PagingStateUtil {

    //是否显示无数据的布局
    var showNullScreen = false

    /**
     * Paging数据状态管理方法
     * 错误  加载 成功
     */
    @Composable
    fun <T: Any> pagingStateUtil(
        pagingData: LazyPagingItems<T>,
        refreshState: SwipeRefreshState,
        viewModel: ViewModel,
        content: @Composable () -> Unit
    ){

        //page状态为刷新
        when(pagingData.loadState.refresh){
            //加载成功
            is LoadState.NotLoading -> NotLoading(refreshState = refreshState, viewModel = viewModel){
                when(pagingData.itemCount){
                    0 -> {
                        if (!showNullScreen) showNullScreen = true
                        else
                            ErrorComposable("暂无数据， 请点击重试") {
                                pagingData.refresh()
                            }

                    }
                    else -> content()
                }
            }
            //加载失败
            is LoadState.Error -> Error(pagingData, refreshState)

            //加载中
            is LoadState.Loading -> Loading(refreshState)
        }

        //下拉加载中
//        when(pagingData.loadState.append){
//            //加载失败
//            is LoadState.Error -> Error(pagingData, refreshState)
//            //加载中
//            is LoadState.Loading -> {}
//
//        }
    }


    /**
     * 未加载且未观察到错误
     */
    @Composable
    private fun NotLoading(
        refreshState: SwipeRefreshState,
        viewModel: ViewModel,
        content: @Composable () -> Unit
    ) {

        content()

//        viewModel.sleepTime(3000) {
//            refreshState.isRefreshing = false
//        }
    }

    /**
     * 加载失败
     */
    @Composable
    private fun <T : Any> Error(
        pagingData: LazyPagingItems<T>,
        refreshState: SwipeRefreshState
    ) {
        refreshState.isRefreshing = false
        ErrorComposable {
            pagingData.refresh()
        }
    }

    /**
     * 加载中
     */
    @Composable
    private fun Loading(refreshState: SwipeRefreshState) {
        Row(modifier = Modifier.fillMaxSize()) { }
        //显示刷新头
        if (!refreshState.isRefreshing) {
            refreshState.isRefreshing = true
        }
    }
}

@Composable
 fun UpLoadLoading(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ){
        Text(text = "加载中....")
    }
}

@Composable
 fun UpLoadError(onClick: () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ){
        Button(onClick = onClick){
            Text(text = "重试")
        }
    }
}