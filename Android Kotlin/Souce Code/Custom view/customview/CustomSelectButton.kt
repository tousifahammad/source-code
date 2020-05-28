package com.webgrity.tisha.ui.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputLayout
import com.webgrity.tisha.R
import com.webgrity.tisha.app.AppData


class CustomSelectButton(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    var attributes: TypedArray
    lateinit var tilSelect: TextInputLayout

    init {
        LinearLayout.inflate(context, R.layout.custom_view_select_button, this)
        attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomSelectButton)

        initViews()
    }

    private fun initViews() {
        tilSelect = findViewById(R.id.til_select)

        assignValues()
    }

    private fun assignValues() {
        try {
            tilSelect.hint = attributes.getString(R.styleable.CustomSelectButton_csb_hint)

        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        }
    }

}