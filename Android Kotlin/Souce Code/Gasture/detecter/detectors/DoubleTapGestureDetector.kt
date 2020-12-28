package com.webgrity.tisha.detectors

import android.view.GestureDetector
import android.view.MotionEvent
import com.webgrity.tisha.interfaces.DoubleTapListener

class DoubleTapGestureDetector(
    private val doubleTapListener: DoubleTapListener
) : GestureDetector.SimpleOnGestureListener() {

    override fun onDown(e: MotionEvent): Boolean {
        return true
    }

    // event when double tap occurs
    override fun onDoubleTap(e: MotionEvent): Boolean {
        doubleTapListener.onDoubleTap(true)
        return true
    }

}