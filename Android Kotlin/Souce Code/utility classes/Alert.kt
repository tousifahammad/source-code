package com.webgrity.tisha.util

import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tapadoo.alerter.Alerter
import com.webgrity.tisha.R

object Alert {

    //only show message
    fun showMessage(activity: AppCompatActivity, message: String) {
        Alerter.create(activity)
            .setText(message)
            .setIcon(R.drawable.ic_bubble_chart)
            .setBackgroundColorRes(R.color.colorPrimary)
            .show()
    }

    //show message with title
    fun showFullMessage(activity: AppCompatActivity, title: String, message: String) {
        Alerter.create(activity)
            .setTitle(title)
            .setText(message)
            .setIcon(R.drawable.ic_bubble_chart)
            .setBackgroundColorRes(R.color.colorPrimary)
            .show()
    }


    //show Success message with title, green colour
    fun showSuccess(activity: AppCompatActivity, message: String) {
        Alerter.create(activity)
            .setTitle("Success")
            .setText(message)
            .setIcon(R.drawable.ic_success)
            .setBackgroundColorRes(R.color.SeaGreen)
            .show()
    }

    //show error message with title, rad colour
    fun showError(activity: AppCompatActivity, message: String) {
        Alerter.create(activity)
            .setTitle("Error found")
            .setText(message)
            .setIcon(R.drawable.ic_error)
            .setBackgroundColorRes(R.color.Red)
            .setDuration(3000)
            .show()
    }

    //show message with title, rad colour
    fun showAlert(activity: AppCompatActivity, message: String) {
        Alerter.create(activity)
            .setTitle("Alert")
            .setText(message)
            .setIcon(R.drawable.ic_alert)
            .setBackgroundColorRes(R.color.SlateGray)
            .setDuration(1000)
            .show()
    }

    //show message with title, orange colour
    fun showWarning(activity: AppCompatActivity, message: String) {
        Alerter.create(activity)
            .setTitle("Warning")
            .setText(message)
            .setIcon(R.drawable.ic_warning)
            .setBackgroundColorRes(R.color.Tomato)
            .setDuration(1000)
            .show()
    }

    //show message With Buttons
    fun askForConfirmation(activity: AppCompatActivity, req_message: String, onClick: View.OnClickListener) {
        Alerter.create(activity)
            .setText(req_message)
            .setIcon(R.drawable.ic_bubble_chart)
            .setBackgroundColorRes(R.color.DodgerBlue)
            .disableOutsideTouch()
            .setDuration(5000)
            .addButton("OKAY", R.style.AlertButton, onClick)
            .addButton("NOT NOW", R.style.AlertButton, View.OnClickListener {
                Alerter.hide()
            })
            .show()

        /*
        //Example
        Alert.askForConfirmation(this, "Are you sure, you want to logout?", View.OnClickListener {
            //do anything
            Alerter.hide()
        })
        */

    }

}