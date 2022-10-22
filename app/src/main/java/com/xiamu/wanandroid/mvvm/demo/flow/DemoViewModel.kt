package com.xiamu.wanandroid.mvvm.demo.flow

import com.xiamu.baselibs.mvvm.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by zxb in 2021/8/7
 */
class DemoViewModel: BaseViewModel() {

    private val _countState = MutableStateFlow(0)
    val countState: StateFlow<Int> = _countState

    fun incrementCount() {
        _countState.value++
    }

    fun decrementCount() {
        _countState.value--
    }
}

//https://juejin.cn/post/6864825233200250894
//class NineDemoFlowViewModel(private val tickerIdList: List<String>) : ViewModel(),
//    LifecycleObserver {
//
//    private var tickerModel: MarketTickerModel = MarketTickerModel(tickerIdList)
//    private var chartModel: NineChartModel = NineChartModel(tickerIdList)
//
//    val liveData = liveData<ChartUIEvent> {
//        try {
//            emitSource(flowData())
//        } catch (e: Throwable) {
//            e.printStackTrace()
//        }
//    }
//
//    private suspend fun flowData(): LiveData<ChartUIEvent> {
//        return flow {
//            while (true) {
//                delay(5000)
//                emit(1)
//            }
//        }.retry(3)
//            .map {
//                tickerModel.loadData().zip(chartModel.loadData()) { t1, t2 ->
//                    StockViewModel(
//                        t1.tickerId,
//                        t1.type,
//                        t1.name ?: "--",
//                        CountyResManager.getDrawableResByCounty(t1.regionCode),
//                        t1.close ?: "",
//                        t1.changeRatio ?: "",
//                        t2.entryList ?: arrayListOf<Entry>(),
//                        t2.maxLength ?: 0
//                    )
//                }
//            }.map { ChartUIEvent.ShowTickerData(it) }
//            .catch { Log.e("NineDemoFlowViewModel", "flowData: ${it.message}") }
//            .onCompletion { Log.i("NineDemoFlowViewModel", "flowData: onCompletion") }
//            .flowOn(Dispatchers.IO)
//            .asLiveData()
//    }
//}
