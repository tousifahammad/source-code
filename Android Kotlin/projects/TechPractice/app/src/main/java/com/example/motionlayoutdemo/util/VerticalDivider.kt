package com.example.motionlayoutdemo.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

class VerticalDivider(context: Context, orientation:Int, private val mDrawable: Drawable): DividerItemDecoration(context,orientation) {
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingStart
        val right = parent.width - parent.paddingEnd
        val childCount = parent.childCount
        for (i in 0 until childCount){

            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDrawable.intrinsicHeight
            mDrawable.setBounds(left,top, right, bottom)
            mDrawable.draw(c)
        }
    }
}