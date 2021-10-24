package com.pince.compose_app.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pince.compose_app.model.entry.Article
import com.pince.compose_app.ui.theme.Colors
import java.lang.StringBuilder

/**
 * Created by zxb in 2021/10/24
 */
@Composable
fun ArticleItem(
    modifier: Modifier,
    article: Article,
    onClickItemClick: (Article) -> Unit,
    onCollectClick: () -> Unit ){

    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onClickItemClick(article)
            //
        }){
        Column(modifier = Modifier.padding(16.dp, 10.dp)) {
            //上面一行
            Row(Modifier.fillMaxWidth()) {
                article.tags.forEach{
                    Text(text =it.name,
                        Modifier
                            .align(Alignment.CenterVertically)
                            .border(0.5.dp, it.getColor(), RoundedCornerShape(3.dp))
                            .padding(2.dp, 1.dp),
                        it.getColor(),
                        10.sp
                        )
                    Spacer(modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .width(8.dp)
                        .height(0.dp))
                }

                Text(text = article.author,
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    Colors.text_h2,
                    12.sp)

                Spacer(modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(10.dp)
                    .height(0.dp)
                )
                Text(text = article.niceDate,
                    Modifier.align(Alignment.CenterVertically),
                    Colors.text_h2,
                    12.sp)
            }
            //中间标题
            Text(
                text = article.title,
                Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                color = Colors.text_h1,
                15.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
                )

            //下面一行
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)) {

                val chapter = StringBuilder(article.superChapterName)
                if (article.superChapterName!!.isNotEmpty() && article.chapterName.isNotEmpty()){
                    chapter.append(" / ")
                }
                chapter.append(article.chapterName)

                Text(
                    text = chapter.toString(),
                    Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    Colors.text_h2,
                    12.sp
                )

                val iconRes = if (article.collect) com.pince.compose_app.R.drawable.ic_like else com.pince.compose_app.R.drawable.ic_like_not
                val tint = if (article.collect) Colors.red else Colors.text_h2

                Icon(painter = painterResource(id = iconRes),
                    contentDescription = "收藏",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        //
                    },
                    tint = tint
                )

            }
        }
    }

}