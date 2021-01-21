package com.example.motionlayoutdemo.async.flow

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.motionlayoutdemo.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class FlowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow)
    }

    private val flow = flow {
        emit(fun0())

        for (i in 1..10) {
            emit(i)
            delay(1000)
        }

        emit(fun1())

        emit(fun2())
    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch {
            flow.collect {
                Log.d("1111", "count: $it")
            }
        }
    }

    private suspend fun fun0(): String {
        for (i in 1..10) {
            Log.d("1111", "fun0: $i")
            delay(500)
        }
        return "fun 0"
    }

    private suspend fun fun1(): String {
        delay(1000)
        return "fun 1"
    }

    private suspend fun fun2(): String {
        delay(1000)
        return "fun 2"
    }

}