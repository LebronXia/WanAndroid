package com.xiamu.wanandroid.mvvm.demo.paging3

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.xiamu.baselibs.http.Result
import com.xiamu.wanandroid.mvvm.model.api.WanService
import com.xiamu.wanandroid.mvvm.model.entry.Article
import com.xiamu.wanandroid.mvvm.model.entry.ArticleList
import com.xiamu.wanandroid.mvvm.model.repository.MainHomeModel
import java.lang.Exception

/**
 * 数据源
 * Created by zxb in 2021/8/16
 */

private const val START_INDEX = 0
class CustomPageDataSource(private val wanService: WanService): PagingSource<Int, Article>() {  //第一个参数有 页数的数据源类型， 第二个参数 每一项数据对应的对象类型

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {

        return try {
            val page = params.key ?: START_INDEX   //params.key  当前页数
            val pageSize = params.loadSize    //一页多少数据
            val repoResponse = wanService.getHomeArticles(page)
            val articleList = repoResponse.data!!.datas
            val prevKey = if (page  == START_INDEX) null else page - 1 //如果可以往上加载更多就设置该参数，否则不设置
            val nextKey = if(page == repoResponse.data!!.pageCount) null else page + 1  ////加载下一页的key 如果传null就说明到底了
            LoadResult.Page(articleList , prevKey, nextKey)  //构建一个LoadResult对象并返回

        } catch (e: Exception){
            e.printStackTrace()
            LoadResult.Error(e)
        }
//        val page = params.key ?: START_INDEX   //params.key  当前页数
//        val pageSize = params.loadSize    //一页多少数据
//        val result = mainHomeModel.getArticleList(page)
//        if (result is Result.Success){
//            val articleList = result.data!!.datas
//            val prevKey = if (page == 0) null else page  //如果可以往上加载更多就设置该参数，否则不设置
//            val nextKey = if(articleList.isNotEmpty()) page + 1 else null  ////加载下一页的key 如果传null就说明到底了
//            return LoadResult.Page(articleList , prevKey, nextKey)  //构建一个LoadResult对象并返回
//        } else if (result is Result.Error){
//           return LoadResult.Error(result.exception)
//        }

    }

    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? = null  //高级的用法

}