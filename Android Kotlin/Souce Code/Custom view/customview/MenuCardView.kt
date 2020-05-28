package com.webgrity.tisha.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.webgrity.tisha.R
import kotlinx.coroutines.NonCancellable.start


class MenuCardView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    init {
        LinearLayout.inflate(context, R.layout.custom_menucard, this)

        val menuCard: CardView = findViewById(R.id.menuCard)
        val simpleViewFlipper: ViewFlipper = findViewById(R.id.simpleViewFlipper)
        val imageView: ImageView = findViewById(R.id.image)
        val textView: TextView = findViewById(R.id.label)
        val desc: TextView = simpleViewFlipper.findViewById(R.id.descLabel)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.MenuCardView)
        // Initialize a new LayoutParams instance, CardView width and height
        val width = attributes.getDimension(R.styleable.MenuCardView_card_width, 0F).toInt()
        val height = attributes.getDimension(R.styleable.MenuCardView_card_height, 0F).toInt()
//        val params = LayoutParams(width, height) // parent params
        val params = menuCard.layoutParams
        params.width = width
        params.height = height
        /*Set the card view layout params*/
        menuCard.layoutParams = params // child layout

        // Set the card view background color
        menuCard.setCardBackgroundColor(attributes.getColor(R.styleable.MenuCardView_valuecolor, 0))
        imageView.setImageDrawable(attributes.getDrawable(R.styleable.MenuCardView_image))
        textView.text = attributes.getString(R.styleable.MenuCardView_text)
        textView.setTextColor(attributes.getColor(R.styleable.MenuCardView_textcolor, 0))
        desc.text = attributes.getString(R.styleable.MenuCardView_desc)

        // Declare in and out animations and load them using AnimationUtils class
        val `in` = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_bootm)
        val out = AnimationUtils.loadAnimation(context, R.anim.slide_out_from_top)
        // set the animation type to ViewFlipper
        simpleViewFlipper.inAnimation = `in`
        simpleViewFlipper.outAnimation = out
        // set interval time for flipping between views
        simpleViewFlipper.flipInterval = 7000;
        // whether we have to flip or not
        val flip = attributes.getBoolean(R.styleable.MenuCardView_flip,true)
        if (!flip) {
            simpleViewFlipper.stopFlipping()
        } else {
            // set auto start for flipping between views
            simpleViewFlipper.isAutoStart = true;
        }

        attributes.recycle()
    }
}