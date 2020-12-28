package com.webgrity.tisha.detectors

import android.view.ScaleGestureDetector
import com.webgrity.tisha.interfaces.DoubleTapListener
import com.webgrity.tisha.interfaces.ScaleListener

class MyScaleGestureDetector(private val scaleListener: ScaleListener) : ScaleGestureDetector.SimpleOnScaleGestureListener(){
    override fun onScale(detector: ScaleGestureDetector): Boolean {
        scaleListener.onScale(detector.scaleFactor)
        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
        scaleListener.onScaleBegin()
        return true;
    }
}