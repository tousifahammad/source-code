package com.webgrity.tisha.ui.customview

import android.content.Context
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.createBalloon
import com.webgrity.tisha.R

class ProfileBalloonFactory : Balloon.Factory() {

    override fun create(context: Context, lifecycle: LifecycleOwner?): Balloon {
        return createBalloon(context) {
            setLayout(R.layout.layout_tooltip)
            setArrowSize(14)
            setArrowOrientation(ArrowOrientation.TOP)
            setArrowPosition(0.85f)
            setCornerRadius(10f)
            setBackgroundColorResource(R.color.tooltip_background)
            setDismissWhenShowAgain(true)
            setLifecycleOwner(lifecycle)
            setOnBalloonOutsideTouchListener { view: View, motionEvent: MotionEvent ->  }

        }
    }
}