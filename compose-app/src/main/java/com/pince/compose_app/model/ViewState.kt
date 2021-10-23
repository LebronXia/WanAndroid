package com.pince.compose_app.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector
import com.pince.compose_app.R

/**
 * Created by zxb in 2021/10/22
 */
data class UiModel<T: Any>(
    val showLoading: Boolean,
    val showError: String?,
    val showSuccess: T?
)

enum class MainScreenTab(
    val text: String,
    val icon: Int
) {
    Home(
        "首页",
        icon = R.drawable.ic_home_black_24dp
    ),
    KnowledgeSystem(
        "体系",
        icon = R.drawable.ic_apps_black_24dp,
    ),
    Wechat(
        "微信",
        icon = R.drawable.ic_wechat_black_24dp,
    ),
    Navigation(
        "导航",
        icon = R.drawable.ic_navigation_black_24dp
    ),
    Project(
        "项目",
        icon = R.drawable.ic_project_black_24dp
    );
}