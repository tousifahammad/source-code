package com.webgrity.tisha.ui.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.switchmaterial.SwitchMaterial
import com.webgrity.tisha.R
import com.webgrity.tisha.app.AppData


class CustomViewSwitch(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    var attributes: TypedArray
    lateinit var cvsSwitch: SwitchMaterial

    init {
        LinearLayout.inflate(context, R.layout.custom_view_switch, this)
        attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomViewSwitch)

        initViews()
    }

    private fun initViews() {
        cvsSwitch = findViewById(R.id.cvs_switch)

        assignValues()
    }

    private fun assignValues() {
        try {
            cvsSwitch.text = attributes.getString(R.styleable.CustomViewSwitch_cvs_text)

        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        }
    }
}