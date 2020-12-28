package util

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tapadoo.alerter.Alerter
import com.webgrity.tisha.R

class Alert(activity: AppCompatActivity, type: Int = 1, message: String, title: String = "") {

    companion object {
        const val message = 1
        const val success = 2
        const val error = 3
        const val warning = 4
        const val fullMessage = 5
        const val alert = 6
    }

    init {
        when (type) {
            1 -> showMessage(activity, message)
            2 -> showSuccess(activity, message)
            3 -> showError(activity, message)
            4 -> showWarning(activity, message)
            5 -> showFullMessage(activity, title, message)
            6 -> showAlert(activity, message)
        }
    }

    class Params(var type: Int = 1, var message: String, var title: String = "")

    fun alert(activity: AppCompatActivity, type: Int, message: String, title: String = "") {
        when (type) {
            1 -> showMessage(activity, message)
            2 -> showSuccess(activity, message)
            3 -> showError(activity, message)
            4 -> showWarning(activity, message)
            5 -> showFullMessage(activity, title, message)
            6 -> showAlert(activity, message)
        }
    }


    //only show message
    private fun showMessage(activity: AppCompatActivity, message: String) {
        Alerter.create(activity)
            .setText(message)
            .setIcon(R.drawable.ic_message)
            .setBackgroundColorRes(R.color.colorPrimary)
            .show()
    }

    //show message with title
    private fun showFullMessage(activity: AppCompatActivity, title: String, message: String) {
        Alerter.create(activity)
            .setTitle(title)
            .setText(message)
            .setIcon(R.drawable.ic_message)
            .setBackgroundColorRes(R.color.colorPrimary)
            .show()
    }


    //show Success message with title, green colour
    private fun showSuccess(activity: AppCompatActivity, message: String, duration: Long = 1000) {
        Alerter.create(activity)
            .setTitle("Success")
            .setText(message)
            .setIcon(R.drawable.ic_success)
            .setBackgroundColorRes(R.color.SeaGreen)
            .setDuration(duration)
            .show()
    }

    //show error message with title, rad colour
    private fun showError(activity: AppCompatActivity, message: String) {
        Alerter.create(activity)
            .setTitle("Error found")
            .setText(message)
            .setIcon(R.drawable.ic_error)
            .setBackgroundColorRes(R.color.Red)
            .setDuration(3000)
            .show()
    }

    //show message with title, rad colour
    private fun showAlert(activity: AppCompatActivity, message: String) {
        Alerter.create(activity)
            .setTitle("Alert")
            .setText(message)
            .setIcon(R.drawable.ic_error)
            .setBackgroundColorRes(R.color.SlateGray)
            .setDuration(1000)
            .show()
    }

    //show message with title, orange colour
    private fun showWarning(activity: AppCompatActivity, message: String) {
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