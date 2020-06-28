package com.webgrity.tisha.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import com.webgrity.tisha.R

class CustomNumberPicker(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    var value: TextView? = null
    var myminValue: Int
    var mymaxValue: Int

    init {
        LinearLayout.inflate(context, R.layout.custom_number_picker, this)

        val picker: LinearLayout = findViewById(R.id.pickers)
        value = findViewById(R.id.display)
        val plus: TextView = findViewById(R.id.plus)
        val minus: TextView = findViewById(R.id.minus)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.MyNumberPicker)
        // Initialize a new LayoutParams instance, CardView width and height
        val width = attributes.getDimension(R.styleable.MyNumberPicker_width, 0F).toInt()
        val height = attributes.getDimension(R.styleable.MyNumberPicker_height, 0F).toInt()
        //val params = LayoutParams(width, height) // parent params
        val params = picker.layoutParams
        params.width = width
        params.height = height
        /*Set the card view layout params*/
        picker.layoutParams = params // child layout

        // Set the view background color

        value?.setTextColor(attributes.getColor(R.styleable.MyNumberPicker_mytextcolor, 0))
        plus.setTextColor(attributes.getColor(R.styleable.MyNumberPicker_mytextcolor, 0))
        minus.setTextColor(attributes.getColor(R.styleable.MyNumberPicker_mytextcolor, 0))

        myminValue = attributes.getInteger(R.styleable.MyNumberPicker_myminValue, 0)
        mymaxValue = attributes.getInteger(R.styleable.MyNumberPicker_mymaxValue, 0)
        val qty = attributes.getString(R.styleable.MyNumberPicker_myvalue)

        value?.text = qty

        plus.setOnClickListener {
            /*value.text = (value.text as String?)?.plus(1)*/
            if (value?.text.toString().toInt() < mymaxValue)
                increaseInteger()
        }

        minus.setOnClickListener {
            if (value?.text.toString().toInt() > myminValue)
                decreaseInteger()
        }

        attributes.recycle()

    }

    private fun increaseInteger() {
        display(value?.text.toString().toInt() + 1)
    }

    private fun decreaseInteger() {
        display(value?.text.toString().toInt() - 1)
    }

    private fun display(number: Int) {
        value?.text = "$number"
    }

    public fun getQuantity(): Int {
        return value?.text.toString().toInt()
    }

    public fun setQuantity(number: Int) {
        if(value!!.text.equals(0)){
            if (number < mymaxValue && number > myminValue)
                value?.text = "$number"
        }else{
            var currentQty: String = value?.text.toString()
            currentQty += number
            Log.d("TAG",currentQty)
            if (currentQty.toInt() < mymaxValue && currentQty.toInt() > myminValue)
                value?.text = currentQty
        }
    }

    public fun clear(){
        var currentValue: String = value!!.text.toString()
        if (currentValue.isNotEmpty()) {

            if(currentValue.length>1){
                currentValue = currentValue.substring(0, currentValue.length - 1);
                value!!.text = currentValue
            }

        }
    }


}
