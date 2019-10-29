package com.xiamu.wanandroid.mvvm.model.entry

import luyao.wanandroid.model.bean.Article
import java.io.Serializable

/**
 * Created by zhengxiaobo in 2019-10-29
 */
data class ArticleList( val offset: Int,
                        val size: Int,
                        val total: Int,
                        val pageCount: Int,
                        val curPage: Int,
                        val over: Boolean,
                        val datas: List<Article>):Serializable