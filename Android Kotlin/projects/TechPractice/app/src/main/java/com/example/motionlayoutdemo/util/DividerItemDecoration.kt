package com.example.motionlayoutdemo.util

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class DividerItemDecoration(context: Context, orientation: Int) :
    RecyclerView.ItemDecoration() {

    private val ATTRS = intArrayOf(
        android.R.attr.listDivider
    )

    val HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL

    val VERTICAL_LIST = LinearLayoutManager.VERTICAL

    private var mDivider: Drawable? = null

    private var mOrientation = 0

    init {
        val a: TypedArray = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        a.recycle()
        setOrientation(orientation)
    }

    private fun setOrientation(orientation: Int) {
        require(!(orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST)) { "invalid orientation" }
        mOrientation = orientation
    }

    override fun onDraw(c: Canvas, parent: RecyclerView) {
        if (mOrientation == VERTICAL_LIST) {
            parent.adapter?.let { adapter ->
                parent.children
                    .forEach { view ->
                        val childAdapterPosition = parent.getChildAdapterPosition(view)
                            .let { if (it == RecyclerView.NO_POSITION) return else it }

                        when (adapter.getItemViewType(childAdapterPosition)) {
                            1 -> {
                                val child = parent.getChildAt(childAdapterPosition)
                                if (child != null){
                                    val params = child
                                        .layoutParams as RecyclerView.LayoutParams
                                    val left = parent.paddingLeft
                                    val right = parent.width - parent.paddingRight
                                    val top = child.bottom + params.bottomMargin
                                    val bottom = top + mDivider!!.intrinsicHeight
                                    mDivider!!.setBounds(left, top, right, bottom)
                                    mDivider!!.draw(c)
                                    mDivider!!.draw(c)
                                }
                            }
                            else -> Unit
                        }
                    }
            }
           // drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    private fun drawVertical(c: Canvas?, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount -1) {
            val child = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider!!.intrinsicHeight
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(c!!)
            mDivider!!.draw(c)
        }
    }

    private fun drawHorizontal(c: Canvas?, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + mDivider!!.intrinsicHeight
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(c!!)
        }
    }
    override fun getItemOffsets(rect: Rect, view: View, parent: RecyclerView, s: RecyclerView.State) {
        parent.adapter?.let { adapter ->
            val childAdapterPosition = parent.getChildAdapterPosition(view)
                .let { if (it == RecyclerView.NO_POSITION) return else it }

            rect.bottom = when (adapter.getItemViewType(childAdapterPosition)) {

                1 -> {
                    mDivider!!.intrinsicHeight
                }
               // CustomAdapter.ODD_ITEM_ID -> decorationBlue.intrinsicWidth
                else -> {
                    0
                }
            }
        }
    }
/*    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        if (mOrientation == VERTICAL_LIST) {
            outRect[0, 0, 0] = mDivider!!.intrinsicHeight
*//*            parent.adapter?.let { adapter ->
                parent.children
                    .forEach { view ->
                        val childAdapterPosition = parent.getChildAdapterPosition(view)
                            .let { if (it == RecyclerView.NO_POSITION) return else it }
                        if (adapter.getItemViewType(childAdapterPosition) == 1){
                            outRect[0, 0, 0] = mDivider!!.intrinsicHeight
                        }
                    }
            }*//*
        } else {
            outRect[0, 0, mDivider!!.intrinsicWidth] = 0
        }
    }*/

}