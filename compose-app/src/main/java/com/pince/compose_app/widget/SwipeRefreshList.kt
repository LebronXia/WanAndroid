package com.pince.compose_app.widget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.pince.compose_app.extend.sleepTime
import com.zj.refreshlayout.SwipeRefreshLayout

/**
 * Created by zxb in 2021/11/9
 */

//var refreshing = mutableStateOf(false)

//上拉刷新 下拉加载
@Composable
fun <T: Any> SwipeRefreshList(collectAsLazyPagingItems: LazyPagingItems<T>, viewModel: ViewModel, content : LazyListScope.() -> Unit) {

    var refreshing by remember { mutableStateOf(false) }

    //第一次展示
    var showFirstScreen by remember {
        mutableStateOf(false)
    }

    SwipeRefreshLayout(
        isRefreshing = refreshing,
        onRefresh = { collectAsLazyPagingItems.refresh() }) {

        refreshing =
            collectAsLazyPagingItems.loadState.refresh is LoadState.Loading

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            //传进来的主要的布局  LazyListScope参数
            content()

            collectAsLazyPagingItems.apply {
                when{
                    //加载更多时，就在底部显示loading的item
                    loadState.append is LoadState.Loading -> {
                        item {
                            LoadingItem()
                        }
                    }
                    //加载更多的时候出错了，就在底部显示错误的item
                    loadState.append is LoadState.Error -> {
                        item{
                            ErrorItem {
                                collectAsLazyPagingItems.retry()
                            }
                        }
                    }

                    //刷新的时候出错
                    loadState.refresh is LoadState.Error -> {
                        refreshing = false
                        //如果itemCount小于0，说明是第一次进来，出错了显示一个大的错误内容
                        if (collectAsLazyPagingItems.itemCount <= 0){
                            item {
                                ErrorPage(collectAsLazyPagingItems )
                            }
                        } else {
                            item {
                                ErrorItem {
                                    collectAsLazyPagingItems.retry()
                                }
                            }
                        }
                    }

                    //刷新数据成功
                    loadState.refresh is LoadState.NotLoading -> {
                        viewModel.sleepTime(3000) {
                            refreshing = false
                        }
                    }

                    loadState.refresh is LoadState.Loading -> {
//                        if(collectAsLazyPagingItems.itemCount == 0){
//                            item {
//                                Column(modifier = Modifier.
//                                    fillMaxSize(),
//                                    verticalArrangement = Arrangement.Center,
//                                    horizontalAlignment = Alignment.CenterHorizontally
//                                ) {
//                                    CircularProgressIndicator(modifier = Modifier.padding(10.dp))
//                                }
//                            }
//                        } else {
//                            refreshing =
//                                collectAsLazyPagingItems.loadState.refresh is LoadState.Loading
//                        }
                    }
                }
            }

        }
    }
}

/**
 * 加载失败
 */
@Composable
fun <T : Any> ErrorPage(
    pagingData: LazyPagingItems<T>
) {
    ErrorComposable {
        pagingData.retry()
    }
}

//加载中item
@Composable
fun LoadingItem() {
    CircularProgressIndicator(modifier = Modifier.padding(10.dp))
}

//加载失败item
@Composable
fun ErrorItem(retry: () -> Unit) {
    Button(onClick = { retry() }, modifier = Modifier.padding(10.dp)) {
        Text(text = "重试")
    }
}

