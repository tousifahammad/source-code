package com.webgrity.tishakds.utilities

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemClickListener(mContext:Context,recyclerView: RecyclerView,listener: OnItemClickListener) : RecyclerView.OnItemTouchListener {

    companion object {
        interface OnItemClickListener {
            fun onItemClick(view:View, position:Int)
            fun onItemLongClick(view: View,position: Int)
        }
    }
    private var mListener: OnItemClickListener = listener
    private var  mGestureDetector: GestureDetector = GestureDetector(mContext, object : GestureDetector.SimpleOnGestureListener(){
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return true
        }

        override fun onLongPress(e: MotionEvent?) {
            val childView = recyclerView.findChildViewUnder(e!!.x,e.y)
            if (childView !=null){
                mListener.onItemLongClick(childView,recyclerView.getChildAdapterPosition(childView))
            }
        }
    })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val childView = rv.findChildViewUnder(e.x,e.y)
        if (childView != null && mGestureDetector.onTouchEvent(e)){
            mListener.onItemClick(childView, rv.getChildAdapterPosition(childView))
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
}