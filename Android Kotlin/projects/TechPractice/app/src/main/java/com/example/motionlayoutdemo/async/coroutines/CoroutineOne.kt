package com.example.motionlayoutdemo.async.coroutines

import android.util.Log
import kotlinx.coroutines.*

class CoroutineOne() {
    init {
        setView()
        //setView2()
    }

    private fun setView() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                //Log.d("CoroutinesTest:", fun1())
                Log.d("CoroutinesTest:", fun2())
                fun21()
                Log.d("CoroutinesTest:", fun3())
                fun31()
                Log.d("CoroutinesTest:", fun4())
                Log.d("CoroutinesTest:", "finished")

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                Log.d("CoroutinesTest:", "finally printed")
            }
        }
    }

    private fun setView2() {
        Coroutines.main {
            try {
                Log.d("CoroutinesTest:", fun1())
                Log.d("CoroutinesTest:", fun2())
                Log.d("CoroutinesTest:", fun3())
                Log.d("CoroutinesTest:", "finished")
            } catch (c: Exception) {

            } finally {
                Log.d("CoroutinesTest:", "printed")
            }
        }
    }

    private suspend fun fun1(): String {
        delay(2000)
        return "Test 1"
    }

    private suspend fun fun2(): String {
        withContext(Dispatchers.IO) {
            delay(2000)
        }
        return "Test 2"
    }

    private suspend fun fun21() {
        withContext(Dispatchers.Main) {
            delay(2000)
            Log.d("CoroutinesTest:", "fun21")
        }
    }

    private suspend fun fun3(): String {
        withContext(Dispatchers.Main) {
            delay(4000)
        }
        return "Test 3"
    }

    private suspend fun fun31(): String {
        GlobalScope.launch(Dispatchers.Main) {
            delay(4000)
        }
        return "Test 3"
    }

    private suspend fun fun4(): String {
        fun5()
        return "Test 4"
    }

    private suspend fun fun5(): String {
        withContext(Dispatchers.IO) {
            delay(2000)
        }
        return "Test 5"
    }
}