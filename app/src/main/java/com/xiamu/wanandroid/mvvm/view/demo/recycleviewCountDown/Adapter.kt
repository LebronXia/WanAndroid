package com.xiamu.wanandroid.mvvm.view.demo.recycleviewCountDown

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xiamu.wanandroid.R

/**
 * Created by zxb in 2022/1/6
 */
class Adapter(private val datas: List<BaseItemBean>): RecyclerView.Adapter<Adapter.BaseVH>() {

    private val TAG = "Adapter"

    var onItemDeleteClick: ((position: Int) -> Unit) ?= null

    //ViewHolder的工厂
    private val vhs = SparseArray<VHFactory>()

    private val timerHandler = Handler(Looper.getMainLooper())

    init {
        vhs.put(ItemType.ITEM_BASE, BaseVHFactory())
        vhs.put(ItemType.ITEM_ON_SALE, OnSaleVHFactory())
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH = vhs.get(viewType).createVh(parent.context, parent)

    //滑入屏幕
    override fun onBindViewHolder(holder: BaseVH, position: Int) = holder.display(datas[position])

    override fun getItemCount(): Int = datas.size

    override fun onViewRecycled(holder: BaseVH) = holder.onRecycled()

    override fun getItemViewType(position: Int): Int = datas[position].type

    //基础ViewHolder
    open inner class BaseVH(itemView: View): RecyclerView.ViewHolder(itemView) {
        val TAG = "BaseVH"
        private val tvTimer = itemView.findViewById<TextView>(R.id.tv_time)

        private val btnDelete = itemView.findViewById<TextView>(R.id.btn_delete)

        init {
            btnDelete.setOnClickListener {
                onItemDeleteClick?.invoke(adapterPosition)
            }
        }

        private var delay = 0L
        private val timerRunnable = Runnable{
            Log.d(TAG, "run: ${hashCode()}")
            delay -= 1000
            updateTimerState()
        }

        private fun startTimer(){
            timerHandler.postDelayed(timerRunnable, 1000)
        }

        private fun endTimer(){
            timerHandler.removeCallbacks(timerRunnable)
        }

        private fun updateTimerState(){
            if (delay <= 0){
                tvTimer.text = "已结束"
                itemView.setBackgroundColor(Color.GRAY)
                endTimer()
            } else {
                tvTimer.text = "${delay / 1000}s"
                itemView.setBackgroundColor(Color.parseColor("#FFBB86FC"))
                startTimer()
            }
        }

        open fun display(bean: BaseItemBean){
            delay = bean.terminaTime - System.currentTimeMillis()
            updateTimerState()
        }

        fun onRecycled(){
            endTimer()
        }
    }

    inner class OnSaleVH(itemView: View): BaseVH(itemView){
        private val tvName = itemView.findViewById<TextView>(R.id.tv_name)

        override fun display(bean: BaseItemBean) {
            super.display(bean)
            tvName.text ="${bean.id}在售"
        }
    }

    //定义抽象工厂
    abstract class VHFactory {
        abstract fun createVh(context: Context, parent: ViewGroup): BaseVH
    }

    /**
     * BaseViewHolder工厂
     */
    inner class BaseVHFactory: VHFactory(){
        override fun createVh(context: Context, parent: ViewGroup): BaseVH {
            return BaseVH(LayoutInflater.from(context).inflate(R.layout.item_base, parent, false))
        }
    }

    inner class OnSaleVHFactory: VHFactory(){
        override fun createVh(context: Context, parent: ViewGroup): BaseVH {
            return OnSaleVH(LayoutInflater.from(context).inflate(R.layout.item_on_sale, parent, false))
        }
    }

    object ItemType {
        const val ITEM_BASE = 0x001
        const val ITEM_ON_SALE = 0x002
    }
}