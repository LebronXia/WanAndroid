package com.pince.compose_app.ui.public

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.pince.compose_app.model.entry.CommonArticleBean
import com.pince.compose_app.ui.home.ArticleItem
import com.pince.compose_app.ui.home.viewmodel.HomeViewModel
import com.pince.compose_app.ui.theme.WanAndroidTheme
import com.pince.compose_app.widget.SwipeRefreshList
import com.xiamu.wanandroid.mvvm.model.entry.TreeBean
import com.xiamu.wanandroid.mvvm.model.entry.TreeItemData
import kotlinx.coroutines.launch

/**
 * 微信公众号
 * Created by zxb in 2021/11/8
 */
@ExperimentalPagerApi   //试验性的特性
@Composable
fun PublicWechatScreen(paddingValues: PaddingValues) {

    val viewModel: PublicWechatViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val publicWechatChapterData = viewModel.publicNumChapter.collectAsState()

    WanAndroidTheme {

        if (publicWechatChapterData.value.isNotEmpty()){
            val pagerState = rememberPagerState(
                pageCount = publicWechatChapterData.value.size, //总页数
                initialOffscreenLimit = 2, //预加载的个数
                infiniteLoop = true, //无限循环
                initialPage = 0, //初始页面
            )

            Column(modifier = Modifier.fillMaxSize()) {

                //可以超出屏幕宽度并且可以滑动
                ScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    modifier = Modifier
                        .wrapContentSize()
                        .height(54.dp),
                    edgePadding = 0.dp   //边缘padding
                ) {
                    publicWechatChapterData.value.forEachIndexed { index, treeBean ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.scrollToPage(index)
                                }
                            },
                            text = {
                                Text(text = treeBean.name ?: "")
                            })
                    }

                }

                //左右滑动的ViewPager
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f),
                ) { index ->
                    PublicWechatTab(viewModel, publicWechatChapterData, viewModel.publicNumChapter.value[index].id)
                }
            }
        }
    }
}

@Composable
fun PublicWechatTab(viewModel: PublicWechatViewModel,
                    publicWechatChapterData: State<List<TreeBean>>,
                    id: Int
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        //某个公众号历史文章列表数据
       var wechatTabViewModel = viewModel.tabViewModelMap[id.toLong()]
        if (wechatTabViewModel == null){
            wechatTabViewModel = PublicWechatTabViewModel(id)
            viewModel.tabViewModelMap.put(id.toLong(), wechatTabViewModel)
        }

        val publicNumListData = wechatTabViewModel.publicNumListData.collectAsLazyPagingItems()

        if (wechatTabViewModel.isFirstLoad){
            wechatTabViewModel.isFirstLoad = false
            publicNumListData.refresh()
        }

        SwipeRefreshList(
            collectAsLazyPagingItems = publicNumListData,
            viewModel = viewModel
        ){

            itemsIndexed(publicNumListData) { index, item ->
                item?.run {
                    var bean = CommonArticleBean(
                        author,
                        fresh,
                        false,
                        niceDate ?: "刚刚",
                        title ?: "",
                        superChapterName ?: "未知",
                        chapterName ?: "未知",
                        collect,
                        tags
                    )

                    ArticleItem(
                        modifier = Modifier,
                        article = bean,
                        onClickItemClick = {

                        },
                        onCollectClick = {

                        }
                    )
                }
                Divider(Modifier.padding(16.dp, 0.dp), thickness = 0.5.dp)
            }
        }
    }
}