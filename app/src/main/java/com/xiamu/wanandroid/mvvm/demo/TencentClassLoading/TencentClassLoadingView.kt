package com.xiamu.wanandroid.mvvm.demo.TencentClassLoading

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.annotation.RequiresApi
import com.xiamu.baselibs.util.dp
import com.xiamu.baselibs.util.dp2px
import com.xiamu.wanandroid.R
import kotlin.math.max
import kotlin.math.min

class TencentClassLoadingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val defaultSize = dp2px(150)
    private val wavePath = Path()
    private lateinit var tencentClassBitmap: Bitmap
    private lateinit var waveBitmap: Bitmap
    private lateinit var waveCanvas: Canvas
    //混合模式
    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

    //波长
    private var waveLength = 0f
    //振幅
    private var amplitude= 0f
    private var amplitudeLight = 0f

    private val WAVE_COLOR = Color.parseColor("#E600A2E8")
    //浅蓝水波纹
    private val WAVE_COLOR_LIGHT = Color.parseColor("#9900A2E8")

    var offsetX: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    var offsetXLight: Float = 0f
        set(value){
            field = value
            invalidate()
        }

    var waveHeight: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private lateinit var animator: ObjectAnimator
    private val animatorSet = AnimatorSet()


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        var measureWidth = if (widthMode == MeasureSpec.EXACTLY) widthSize else defaultSize.toInt()
        val measuredHeight = if (heightMode == MeasureSpec.EXACTLY) heightSize else defaultSize.toInt()
        val measuredSize = min(measureWidth, measuredHeight)
        setMeasuredDimension(measuredSize, measuredSize)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        tencentClassBitmap = getImage(R.drawable.tencent_class, w)

        waveBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        waveCanvas = Canvas(waveBitmap)

        waveLength = w / 3f
        amplitude = h / 20f
        amplitudeLight = h / 25f

        animator = ObjectAnimator.ofFloat(this, "offsetX", 0f, waveLength).apply {
            duration = 500L
            interpolator = LinearInterpolator()
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
        }

        val animatorLight = ObjectAnimator.ofFloat(this, "offsetXLight", waveLength, 0f).apply {
            duration = 300L
            interpolator = LinearInterpolator()
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
        }

        val animatorHeight = ObjectAnimator.ofFloat(this, "waveHeight", h.toFloat() + max(amplitude, amplitudeLight), -max(amplitude, amplitudeLight)).apply {
            duration = 2000L
            startDelay = 200L
            interpolator = AccelerateInterpolator()
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
        }

        animatorSet.playTogether(animator, animatorLight, animatorHeight)
        animatorSet.start()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(tencentClassBitmap, 0f, 0f, paint)
        drawWaveOnWaveBitmap()

        //离屏缓存
        val saveCount = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), paint)
        //底部
        canvas.drawBitmap(waveBitmap, 0f, 0f, paint)
        paint.xfermode = xfermode
        //顶部
        canvas.drawBitmap(tencentClassBitmap, 0f, 0f, paint)
        paint.xfermode = null
        canvas.restoreToCount(saveCount)



//        val pts1 = floatArrayOf(
//            0f, height / 2f, width / 4f, height * 3 / 4f,
//            width / 4f, height * 3 / 4f, width / 2f, height / 2f,
//            width / 2f, height / 2f, width * 3 / 4f, height / 4f,
//            width * 3 / 4f, height / 4f, width * 1f, height / 2f,
//        )
//        paint.color = Color.BLUE
//        paint.strokeWidth = 2.dp
//        canvas.drawLines(pts1, paint)
//        paint.color = Color.RED
//        paint.style = Paint.Style.FILL
//        val pts2 = floatArrayOf(
//            0f, height / 2f,
//            width / 4f, height * 3 / 4f,
//            width / 2f, height / 2f,
//            width * 3 / 4f, height / 4f,
//            width * 1f, height / 2f,
//        )
//        canvas.drawPoints(pts2, paint)
    }

    private fun drawWaveOnWaveBitmap(){
        //清空一下 waveBitmap 对象的图像
        waveCanvas.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR)

        paint.color = WAVE_COLOR_LIGHT
        updateWavePath(offsetXLight, amplitudeLight)
        //画到bitmap上
        waveCanvas.drawPath(wavePath, paint)

        paint.color = WAVE_COLOR
        updateWavePath(offsetX, amplitude)
        waveCanvas.drawPath(wavePath, paint)
    }

    private fun updateWavePath(offsetX: Float, amplitude: Float){
        wavePath.apply {
            reset()
            //控件左边的起点-波长的地方
            var initialStart = offsetX - waveLength
            moveTo(initialStart, waveHeight)
            var waveStart = initialStart
            while (waveStart <= width){
                rQuadTo(waveLength / 4f, amplitude, waveLength / 2f, 0f)
                rQuadTo(waveLength / 4f, -amplitude, waveLength / 2f, 0f)
                waveStart += waveLength
            }
            lineTo(width.toFloat(), height.toFloat())
            lineTo(0f, height.toFloat())
            close()
        }

    }

    private fun getImage(drawable: Int, requestSize: Int): Bitmap{
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, drawable, options)
        options.inTargetDensity = requestSize
        options.inDensity = options.outWidth
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(resources, drawable, options)

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator.cancel()
    }

}