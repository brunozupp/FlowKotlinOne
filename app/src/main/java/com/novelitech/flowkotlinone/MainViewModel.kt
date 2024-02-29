package com.novelitech.flowkotlinone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val countDownFlow = flow<Int> {

        val startingValue = 10
        var currentValue = startingValue

        emit(currentValue)

        while(currentValue > 0) {
            delay(1000L)

            currentValue--

            emit(currentValue)
        }
    }

    init {
        collectFlow()
    }

    private fun collectFlow() {
        viewModelScope.launch {

            // This is executed to every single emition from the flow
//            countDownFlow.collect { time ->
//                println("The current time is $time")
//            }

            // This is executed as the same as above, but it will delay 1,5 seconds to appear in the Log
//            countDownFlow.collect { time ->
//                delay(1500L)
//                println("The current time is $time")
//            }

            // It will executes as the .collect, but if I have same delay in the block and in this
            // time another/other emittion(s) are executed, it will erase the previous one and
            // return the newest emittion. In this case, just the last one will appears in the Log (0)
            countDownFlow.collectLatest { time ->
                delay(1500L)
                println("The current time is $time")
            }
        }
    }
}