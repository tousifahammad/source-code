package com.webgrity.tisha.ui.test

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.webgrity.tisha.R

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        findViewById<SmoothBottomBar>(R.id.bottomBar).setOnTabSelectedListener { showTab(it) }
    }

    @SuppressLint("SetTextI18n")
    private fun showTab(i: Int) {
        findViewById<TextView>(R.id.labelView).text = "Selected Tab - $i"
        Log.d("MainActivity", "onTabSelected: $i")
    }


}