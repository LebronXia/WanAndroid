package com.pince.compose_app.model.entry

import androidx.compose.ui.graphics.Color
import com.pince.compose_app.ui.theme.Colors
import java.io.Serializable

/**
 * Created by zhengxiaobo in 2019-10-29
 */
data class Article( val id: Int,
                    val originId: Int,
                    val title: String,
                    val chapterId: Int,
                    val chapterName: String,
                    val envelopePic: String,
                    val link: String,
                    val author: String,
                    val origin: String,
                    val publishTime: Long,
                    val zan: Int,
                    val desc: String,
                    val visible: Int,
                    val niceDate: String,
                    val niceShareDate: String,
                    val courseId: Int,
                    var collect: Boolean,
                    val apkLink:String,
                    val projectLink:String,
                    val superChapterId:Int,
                    val superChapterName:String?,
                    val type:Int,
                    val fresh:Boolean,
                    val audit:Int,
                    val prefix:String,
                    val selfVisible:Int,
                    val shareDate:Long,
                    val shareUser:String,
                    val tags: MutableList<ArticleTag>, // Not sure
                    val userId:Int
):Serializable

data class ArticleTag(val name: String, val url: String){

    fun getColor(): Color {
        return when(name){
            "置顶" -> Colors.red
            "本站发布" -> Color(0xFF2196F3)
            "问答" -> Color(0xFF00BCD4)
            "公众号" -> Color(0xFF4CAF50)
            "项目" -> Color(0xFF009688)
            else -> Colors.main
        }
    }
}

data class BannerData(
    val imageUrl: String,
    val linkUrl: String
)