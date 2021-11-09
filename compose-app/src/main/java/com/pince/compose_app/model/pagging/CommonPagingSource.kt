package com.pince.compose_app.model.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.lang.Exception

/**
 * Created by zxb in 2021/10/29
 */
private const val START_INDEX = 0

class CommonPagingSource<T: Any>(private val block: suspend (nextPage: Int) -> CommonListPageModel<T>):  PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val nextPage = params.key ?: START_INDEX
            val response = block.invoke(nextPage)
            LoadResult.Page(
                data = response.data.datas,
                prevKey = if (nextPage  == START_INDEX) null else nextPage - 1,
                nextKey = if (nextPage < response.data.pageCount) nextPage + 1 else null
            )
        } catch (e: Exception){
            LoadResult.Error(e)
        }

    }
}