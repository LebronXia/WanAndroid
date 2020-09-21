package com.xiamu.wanandroid.mvvm.view.widget.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import com.orhanobut.logger.Logger
import me.jessyan.autosize.utils.LogUtils
import java.util.*
import java.util.jar.Attributes
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

/**
 * Created by zhengxiaobo in 2020/9/21
 */
class DimPleView(context: Context?, attrs: AttributeSet?): View(context, attrs) {
    //粒子集合
    private var particleList = mutableListOf<Particle>()
    var paint = Paint()
    private var mWidth = 0f
    private var mHeight = 0f
    private var path = Path()
    private val pathMeasure = PathMeasure()
    //当前点在画布上的位置，有两个数值，分别为x，y坐标。
    private var pos = FloatArray(2)
    //当前点在曲线上的方向，使用 Math.atan2(tan[1], tan[0]) 获取到正切角的弧度值。
    private var tan = FloatArray(2)
    //定义动画
    private var animator = ValueAnimator.ofFloat(0f, 1f)
    private val random = Random()

    init {
        animator.duration = 2000
        animator.repeatCount = -1
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener {
            updateParticle(it.animatedValue as Float)
            invalidate()
        }
        Log.d("DimPleView:" ,"hahahhaha")
    }

    //更新粒子位置
    private fun updateParticle(value: Float){
        particleList.forEach{
            if (it.offset > it.maxOffset){
                it.offset = 0
                it.speed = (random.nextInt(10) + 5).toFloat()
            }
            it.alpha= ((1f - it.offset / it.maxOffset)  * 225f).toInt()
            it.x = (mWidth+ cos(it.angle) * (280f + it.offset)).toFloat()

            if (it.y > mHeight){
                it.y = (sin(it.angle) * (280f + it.offset) + mHeight).toFloat()
            } else {
                it.y = (mHeight - sin(it.angle) * (280f + it.offset)).toFloat()
            }

//            if(it.y - mHeight > it.maxOffset){
//                it.y = mHeight  //重新设置Y值
//                it.x = random.nextInt((mWidth * 2).toInt()).toFloat() //随机设置x值
//                it.speed = (random.nextInt(10) + 5).toFloat()  //随机设置速度
//
//                it.alpha = ((1f - (it.y-mHeight) / it.maxOffset) * 225f).toInt()
//            }
            it.offset += it.speed.toInt()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = (w/2).toFloat()
        mHeight = (h/2).toFloat()
        //val random = Random()
        var nextX = 0f
        var nextY = 0f
        //定义一个速度
        var speed = 0f
        var angle = 0.0
        //初始移动距离
        var offSet=0
        //最大距离
        var maxOffset = 0
        path.addCircle(mWidth, mHeight, 280f, Path.Direction.CCW)
        pathMeasure.setPath(path, false)
        for (i in 0..500){
            //按比例测量路径上每一点的值
            pathMeasure.getPosTan(i / 500f * pathMeasure.length, pos, tan)
            //初始化Y值 以起始点为最低值
            nextX = pos[0]+random.nextInt(6) - 3f //X值随机偏移
            nextY=  pos[1]+random.nextInt(6) - 3f//Y值随机偏移

            //反余弦函数可以得到角度值，是弧度
            angle=acos(((pos[0] - mWidth) / 280f).toDouble())
            speed = random.nextInt(2) + 2f
            offSet = random.nextInt(200)
            maxOffset = random.nextInt(200)

            particleList.add(
                    Particle(nextX, nextY,2f,speed,100,maxOffset.toFloat(), offSet, angle)
                    )
        }

        animator.start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = Color.WHITE
        paint.isAntiAlias = true

        Log.d("DimPleView:" , ""+  particleList.size)
        particleList.forEach{
            paint.alpha = it.alpha
            canvas?.drawCircle(it.x, it.y, it.radius, paint)
        }
    }
    
}