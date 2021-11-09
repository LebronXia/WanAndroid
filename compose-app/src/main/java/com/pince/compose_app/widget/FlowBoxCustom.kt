package com.pince.compose_app.widget

import android.graphics.Point
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Created by zxb in 2021/11/7
 */
val DefaultFlowBoxGap = FlowBoxGap(0.dp)

data class FlowBoxGap(val start: Dp, val top: Dp, val end: Dp, val bottom: Dp){
    constructor(gap: Dp): this(gap, gap, gap, gap)
}
@Composable
fun FLowBox(modifier: Modifier = Modifier,
            itemGap: FlowBoxGap = DefaultFlowBoxGap,
            content: @Composable () -> Unit
            ){
    Layout(content = content,
        measurePolicy = flowBoxMeasurePolicy(itemGap))
}

/**
 * 自定义组件怎么布局
 * 子组件的内容
 * 父布局的限制
 */
fun flowBoxMeasurePolicy(itemGap: FlowBoxGap) = MeasurePolicy { measurables, constraints ->

    //批量拿到子组件的信息
    val placeables = measurables.map { placeable ->
        placeable.measure(constraints = constraints)
    }

    //存储子组件
    val positions = arrayListOf<Point>()
    //当前组件x点位置
    var xPosition = 0
    //当前组件y点位置
    var yPosition = 0

    //当前行高度最大的组件的高度
    var currentLineMaxHeight = 0

    //每个子组件的布局信息处理
    placeables.forEach{placeable ->
        //起始间隔和结束间隔, 相当于padding
        var horizontalGap = itemGap.start.roundToPx() + itemGap.end.roundToPx()
        var verticalGap = itemGap.top.roundToPx() + itemGap.bottom.roundToPx()

        //当组件的宽度大于父布局的最大宽度，执行换行
        if (placeable.width + horizontalGap + xPosition > constraints.maxWidth){
            //重新开始计算子组件的位置
            xPosition = 0
            yPosition += currentLineMaxHeight
        }

        //添加子组件的布局位置
        positions.add(
            Point(xPosition + itemGap.start.roundToPx(),
                yPosition + itemGap.top.roundToPx())
        )

        //下一个子组件的初始位置
        xPosition += placeable.width + horizontalGap
        //记录当前行的最大高度
        currentLineMaxHeight = currentLineMaxHeight.coerceAtLeast(placeable.height + verticalGap)
    }
    //所有子组件相加的高度
    val height = yPosition + currentLineMaxHeight

    //参数是设置FLowBox的宽高
    layout(constraints.maxWidth, height = height){
        positions.zip(placeables).forEach{(position, placeable) ->
            //遍历位置列表，设置子组件的位置
            placeable.placeRelative(position.x, position.y)
        }
    }
}



