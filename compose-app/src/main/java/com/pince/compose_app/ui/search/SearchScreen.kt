package com.pince.compose_app.ui.search

import android.content.Context
import android.view.ViewManager
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pince.compose_app.model.db.SearchHistoryBean
import com.pince.compose_app.model.db.SearchHistoryHelper
import com.pince.compose_app.model.db.SearchHistoryHelper.clearAllHistory
import com.pince.compose_app.model.db.SearchHistoryHelper.deleteHistory
import com.pince.compose_app.model.db.SearchHistoryHelper.insertSearchHistory
import com.pince.compose_app.widget.FLowBox
import com.pince.compose_app.widget.FlowBoxGap
import com.pince.compose_app.widget.RowTextIcon
import com.pince.compose_app.widget.TitleBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by zxb in 2021/11/4
 */
@Composable
fun SearchScreen(navController: NavHostController){

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val viewModel: SearchViewModel = viewModel()

    var textFiledValue by remember { mutableStateOf(TextFieldValue(""))}

    Scaffold(topBar = {
        TitleBar(
            textFieldValue = textFiledValue,
            onValueChange = {
                textFiledValue = it
            },
            onLeftClick = {
                navController.navigateUp()
            },
            onRightClick = {
                if (textFiledValue.text.isBlank()){
                    return@TitleBar
                }

                scope.launch {
                    insertSearchHistory(SearchHistoryBean(textFiledValue.text) )
                }
            }
        )
        },
    ) {
        SearchPage(context, scope, viewModel)
    }
}

//标题栏下面内容
@Composable
private fun SearchPage(
    context: Context,
    scope: CoroutineScope,
    searchViewModel: SearchViewModel
){

    //热门关键字
    val hotKeyList = searchViewModel.searchHotKeys.collectAsState()

    //历史搜索数据
    val searchHistoryListData = searchViewModel.searchHistory.collectAsState()

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        TopTextIconSection("热门搜索")

        //流式布局
        FLowBox(itemGap = FlowBoxGap(start = 0.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)) {
            if (hotKeyList == null || hotKeyList.value?.isEmpty() == true ){
                Text(text = "暂无热刺",
                    fontWeight = FontWeight.Light,  //字体粗细
                    color = MaterialTheme.colors.secondaryVariant
                )
                return@FLowBox
            }

            hotKeyList.value.forEachIndexed{index, hotKey ->
                Button(onClick = { }) {
                    Text(text = hotKey.name ?: "")
                }
            }
        }

        TopTextIconSection("搜索历史", true){
            scope.launch {
                    clearAllHistory()
            }
        }

        searchHistoryListData.value?.let {
            SearchHistoryPage(context = context, it.reversed())
        }
    }
}

//中间标题栏
@Composable
private fun TopTextIconSection(
    text: String = "",
    showDeleteIcon: Boolean = false,
    deleteClick: () -> Unit = {}
){
    Row(modifier = Modifier
        .height(30.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = text,
            fontSize = 18.sp,
            color = MaterialTheme.colors.primary)
        if (showDeleteIcon){
            Icon(
                Icons.Default.Delete,
                contentDescription = null,
                modifier = Modifier
                    .clickable(
                        onClick = deleteClick
                    )
                    .size(16.dp),
                tint = MaterialTheme.colors.primary
            )
        }
    }
}

//搜索历史
@Composable
private fun SearchHistoryPage(context: Context, list: List<SearchHistoryBean>){
    LazyColumn{
        itemsIndexed(list){index: Int, item: SearchHistoryBean ->
            RowTextIcon(text = item.key, paddingTop = 10.dp){
                deleteHistory(item)
            }
        }
    }

}

