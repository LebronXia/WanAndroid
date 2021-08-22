package com.xiamu.wanandroid.mvvm.demo.paging3.adapter

import android.view.ViewGroup
import androidx.paging.*
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xiamu.wanandroid.mvvm.demo.paging3.DifferData
import kotlinx.coroutines.Dispatchers

/**
 *
 * 自定义PagerAdapter
 * Created by zxb in 2021/8/19
 */
class PagingWrapAdapter<T: DifferData, VH: RecyclerView.ViewHolder>(
        private val innerAdapter: RecyclerView.Adapter<VH>,
        private val callback: ((List<T>) -> Unit)
) : RecyclerView.Adapter<VH>() {

    private val differ = AsyncPagingDataDiffer<T>(
            diffCallback = com.xiamu.wanandroid.mvvm.demo.paging3.DifferCallback(),
            updateCallback = AdapterListUpdateCallback(this),
            mainDispatcher = Dispatchers.Main,
            workerDispatcher = Dispatchers.Default
    )

    init {
        differ.addLoadStateListener {
            if (it.append is LoadState.NotLoading) {
                val items = differ.snapshot().items
                callback.invoke(items)
            }
        }
    }

    fun addLoadStateListener(listener: (CombinedLoadStates) -> Unit) {
        differ.addLoadStateListener(listener)
    }

    fun withLoadStateFooter(
            footer: LoadStateAdapter<*>
    ): ConcatAdapter {
        addLoadStateListener { loadStates ->
            footer.loadState = loadStates.append
        }
        return ConcatAdapter(this, footer)
    }

    fun retry() {
        differ.retry()
    }

    suspend fun submitList(pagingData: PagingData<T>) {
        differ.submitData(pagingData)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return innerAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        differ.getItem(position)
        innerAdapter.onBindViewHolder(holder, position)
    }

    override fun getItemCount(): Int {
        return innerAdapter.itemCount
    }

}