package com.example.motionlayoutdemo.generic

import android.util.Log

class GenTest2<T>(data: T) {
    init {
        Log.d("1111", "GenTest2 init: $data")
    }
}