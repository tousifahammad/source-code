package com.example.motionlayoutdemo.generic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.motionlayoutdemo.R

class GenericActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generic)

        generic1(1)
        generic2(2)

        GenTest().apply {
            generic1(11)
            generic2(22)
            generic2(true)
            generic2("TOUSIF")

            generic3("TOUSIF").apply {
                Log.d("1111", "GenTest generic3: $this")
            }

            val list = ArrayList<Int>()
            genericList(list).apply {
                Log.d("1111", "GenTest generic3: ${this.toString()}")
            }
        }

        GenTest2(2)
        GenTest2(true)
        GenTest2("TOUSIF")
    }

    private fun generic1(data: Int) {
        Log.d("1111", "generic: $data")
    }

    private fun <T> generic2(data: T) {
        Log.d("1111", "generic: ${data.toString()}")
    }

}