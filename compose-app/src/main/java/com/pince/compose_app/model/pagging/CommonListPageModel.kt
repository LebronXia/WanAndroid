package com.pince.compose_app.model.pagging

/**
 * Created by zxb in 2021/10/29
 */
class CommonListPageModel<T>(
    val `data`: Data<T>
) {
    data class Data<T>(
        val curPage: Int,
        val datas: List<T>,
        val offset: Int,
        val over: Boolean,
        val pageCount: Int,
        val size: Int,
        val total: Int
    )
}