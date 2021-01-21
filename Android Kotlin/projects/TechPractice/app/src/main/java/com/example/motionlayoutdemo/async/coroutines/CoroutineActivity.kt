package com.example.motionlayoutdemo.async.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.motionlayoutdemo.R

class CoroutineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)

        //CoroutineOne()
        //CoroutineTwo()
        CoroutineThree()
    }
}