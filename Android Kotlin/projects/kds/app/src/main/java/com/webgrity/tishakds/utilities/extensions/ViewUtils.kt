package com.webgrity.tishakds.utilities.extensions

import android.app.Activity
import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.webgrity.tishakds.R
import com.webgrity.tishakds.app.MyApplication


fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toast(message: String, gravity: Int) {
    val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
    toast.setGravity(gravity, 0, 0)
    toast.show()
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.toggleVisibility() {
    visibility = if (visibility == View.GONE) View.VISIBLE else View.GONE
}

fun TextView.addReadMore(maxLength: Int) {
    if (text.length <= maxLength) return
    var text: String = this.text.toString()
    if (text.contains("...Less")) text = text.replace("...Less", "")
    val ss = SpannableString(text.substring(0, maxLength) + "...More")
    val clickableSpan: ClickableSpan = object : ClickableSpan() {
        override fun onClick(view: View) {
            addReadLess(text, maxLength)
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
//            ds.color = resources.getColor(R.color.colorPrimary)
            ds.color = MyApplication.textColor
        }
    }
    ss.setSpan(clickableSpan, ss.length - 7, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    this.text = ss
    this.movementMethod = LinkMovementMethod.getInstance()
}

fun TextView.addReadLess(text: String, maxLength: Int) {
    val ss = SpannableString("$text...Less")
    val clickableSpan: ClickableSpan = object : ClickableSpan() {
        override fun onClick(view: View) {
            addReadMore(maxLength)
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
//            ds.color = resources.getColor(R.color.colorPrimary)
            ds.color = MyApplication.textColor
        }
    }
    ss.setSpan(clickableSpan, ss.length - 7, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    this.text = ss
    this.movementMethod = LinkMovementMethod.getInstance()
}

fun ImageView.loadImage(byteArray: ByteArray?) {
    Glide.with(this)
        .asBitmap()
        .load(byteArray)
        .placeholder(R.color.Gainsboro)
        .error(R.drawable.ic_alert)
        .into(this)
}

fun ImageView.loadImage(imageId: Int) {
    Glide.with(this.context)
        .asBitmap()
        .load(imageId)
        .placeholder(R.color.Gainsboro)
        .error(R.drawable.ic_alert)
        .into(this)
}

fun ImageView.loadGif(imageId: Int) {
    Glide.with(this)
        .asGif()
        .load(imageId)
        .into(this)
}

/*fun ImageView.loadImage(imageId: Int, progressBar:ProgressBar) {
    progressBar.visibility = View.VISIBLE

    Glide.with(this)
        .load("https://moodle.htwchur.ch/pluginfile.php/124614/mod_page/content/4/example.jpg")
        .listener(object : RequestListener<Drawable?> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                progressBar.visibility = View.GONE
                return false // important to return false so the error placeholder can be placed
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                progressBar.visibility = View.GONE
                return false
            }
        })
        .into(this)
}*/


/**View Visibility Ext*/
fun View.gone() {
    if (visibility != View.GONE) visibility = View.GONE
}

fun View.visible() {
    if (visibility != View.VISIBLE) visibility = View.VISIBLE
}

fun View.invisible() {
    if (visibility != View.INVISIBLE) visibility = View.INVISIBLE
}

fun View.clickEffect() {
    AnimationUtils.loadAnimation(context, R.anim.anim_click_effect)?.let {
        startAnimation(it)
    }
}