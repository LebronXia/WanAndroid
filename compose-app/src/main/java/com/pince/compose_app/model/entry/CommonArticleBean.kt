package com.pince.compose_app.model.entry

/**
 * Created by zxb in 2021/11/9
 */
class CommonArticleBean (
    //作者
    val author: String,
    //新的内容
    val fresh: Boolean,
    //是否置顶,
    val stick: Boolean = false,
    //发布时间
    val niceDate: String,
    //标题
    val title: String,
    //来源
    val superChapterName: String,
    //来源子类
    val chapterName: String,
    //是否收藏
    val collect: Boolean,
    //标签颜色
    val tags: MutableList<ArticleTag>,
    //是否显示具体时间
    val isSpecific: Boolean = true
)