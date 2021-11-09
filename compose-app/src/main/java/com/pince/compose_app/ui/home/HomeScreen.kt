package com.pince.compose_app.ui.home

import android.graphics.Bitmap
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.pince.compose_app.LoginScreen
import com.pince.compose_app.R
import com.pince.compose_app.model.entry.Article
import com.pince.compose_app.model.entry.BannerData
import com.pince.compose_app.ui.home.viewmodel.HomeViewModel
import com.pince.compose_app.ui.theme.Colors
import com.pince.compose_app.ui.theme.Typography
import com.pince.compose_app.ui.theme.WanAndroidTheme
import com.pince.compose_app.util.PagingStateUtil
import com.pince.compose_app.util.UpLoadError
import com.pince.compose_app.util.UpLoadLoading
import com.pince.compose_app.util.showToast
import com.pince.compose_app.widget.Banner
import com.xiamu.wanandroid.mvvm.model.entry.BannerBean
import com.zj.refreshlayout.SwipeRefreshLayout
import com.zj.refreshlayout.SwipeRefreshStyle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * Created by zxb in 2021/10/23
 */
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun HomeScreen(paddingValues: PaddingValues){

    val viewModel: HomeViewModel = viewModel()
    //首页数据
    val homeListLazyPagingItems = viewModel.homeListData.collectAsLazyPagingItems()

    val bannerListData = viewModel.bannerListData.collectAsState()
    WanAndroidTheme {

//        LaunchedEffect(refreshing){
//            launch {
//                viewModel.homeScreenState.collect{
//                    it.showSuccess?.let {
//                        refreshing = false
//                        list = it
//                    }
//                    it.showError?.let {
//                        refreshing = false
//                        showToast(it.toString())
//                    }
//                }
//            }
//        }

        val refreshState = rememberSwipeRefreshState(false)

        //从组件树移除时
        DisposableEffect(key1 = Unit ){
            onDispose {

            }
        }

        SwipeRefreshLayout(
            isRefreshing = refreshState.isRefreshing,
            onRefresh = {
                homeListLazyPagingItems.refresh()
            }) {

            Scaffold(modifier = Modifier
                .padding(bottom = paddingValues.calculateBottomPadding())
                .fillMaxSize()
            ) {

                PagingStateUtil().pagingStateUtil(
                    pagingData = homeListLazyPagingItems,
                    refreshState = refreshState,
                    viewModel = viewModel) {
                    LazyColumn(
                        Modifier
                            .fillMaxSize()
                            .background(Colors.white)){

                        item {
                            Banner(list = bannerListData.value){

                            }
                        }

                        itemsIndexed(homeListLazyPagingItems){index, item ->
                            ArticleItem(
                                modifier = Modifier,
                                article = item!!,
                                onClickItemClick = {

                                },
                                onCollectClick = {

                                }
                            )
                            Divider(Modifier.padding(16.dp, 0.dp), thickness = 0.5.dp)
                        }

                        when(homeListLazyPagingItems.loadState.append){
                            is LoadState.Error -> item {
                                UpLoadError(onClick = {

                                })
                            }

                            is LoadState.Loading -> item {
                                UpLoadLoading()
                            }
                        }
                    }
                }
            }

        }
    }

}




