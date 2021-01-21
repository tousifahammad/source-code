package com.example.motionlayoutdemo.util

import android.util.Patterns
import android.widget.AutoCompleteTextView
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Matcher
import java.util.regex.Pattern


//Validate name, email, mobile, etc

object Validator {

    //input types
    const val text = 1
    const val name = 2
    const val phone = 3
    const val emailAddress = 4
    const val password = 5
    const val ipAddress = 6
    const val webAddress = 7
    const val description = 8

    fun isNameValid(txt: String?): Boolean {
        val regx = "^[\\p{L} .'-]+$"
        val pattern: Pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(txt)
        //return matcher.find();
        if (matcher.find()) {
            return true
        }
        //Alert.showError(activity, "Invalid Name")
        //context.toast("Invalid Name")
        return false
    }

    fun isEmailValid(email: String): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val inputStr: CharSequence = email
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(inputStr)
        if (!matcher.matches()) {
            //context.toast("Invalid email")
            return false
        }
        return true
    }


    fun isPasswordValid(password: String): Boolean {
        if (password.length < 5) {
            //context.toast("Password must contains at least 5 character")
            return false
        }
        return true
    }

    fun isMobileValid(s: String?): Boolean {
        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number.
        // 1) Begins with 0 or 91
        // 2) Then contains 6 or 7 or 8 or 9.
        // 3) Then contains 9 digits
        //val p: Pattern = Pattern.compile("(0/91)?[6-9][0-9]{9}")
        val p: Pattern = Pattern.compile("^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}\$")

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        val m: Matcher = p.matcher(s)
        if (m.find() && m.group() == s) {
            return true
        }
        //context.toast("Invalid mobile number")
        return false
    }

    fun isIpAddressValid(ipAddress: String): Boolean {
        return Patterns.IP_ADDRESS.matcher(ipAddress).matches()
    }

    fun isNullOrEmpty(editText: EditText, textInputLayout: TextInputLayout?, message: String): Boolean {
        return if (editText.text.isNullOrEmpty()) {
            textInputLayout?.error = message
            //textInputLayout?.requestFocus()
            true
        } else {
            textInputLayout?.error = ""
            false
        }
    }

    //is TextInputLayout(TIL) and TextInputEditText (TIET) Valid
    fun isTILandTIETValid(til: TextInputLayout?, tiet: TextInputEditText?, message: String): Boolean {
        return if (tiet?.text.toString().trim().isEmpty()) {
            til?.error = message
            //textInputLayout?.requestFocus()
            false
        } else {
            til?.error = ""
            true
        }
    }

    //is TextInputLayout(TIL) and AutoCompleteTextView (ACTV) Valid
    fun isTILandACTVValid(til: TextInputLayout?, actv: AutoCompleteTextView?, selectMessage: String, errorMessage: String): Boolean {
        return if (actv?.text.toString() == selectMessage || actv?.text.toString().isEmpty()) {
            til?.error = errorMessage
            //textInputLayout?.requestFocus()
            false
        } else {
            til?.error = ""
            true
        }
    }


    fun isWebAddressValid(url: String): Boolean {
        //if (url.isNullOrEmpty()) return false
        return Patterns.WEB_URL.matcher(url).matches()
    }


    // is text input edit text length check
    fun isValidTextLength(textInputEditText: TextInputEditText, message: String, textInputLayout:TextInputLayout, count: Int): Boolean {
        var isValidTextLength = false
        try {
            if (textInputEditText.text.toString().trim().length > count) {
                textInputLayout.error = message
                isValidTextLength = false
            } else {
                textInputLayout.error = ""
                isValidTextLength = true
            }
        } catch (error: Exception) {
            error.printStackTrace()        }
        return isValidTextLength
    }

    //is auto complete text input layout Valid
    fun isCsValidText(cs: AutoCompleteTextView, selectMessage: String, message: String, cill:TextInputLayout): Boolean {
        var isCsValidText = false
        try {
            if (cs.text.toString().isEmpty() || cs.text.toString() == selectMessage) {
                cill.error = message
                isCsValidText = false
            } else {
                cill.error = ""
                isCsValidText = true
            }
        } catch (error: Exception) {
            error.printStackTrace()        }
        return isCsValidText
    }

    //is TextInputLayout(TIL) and TextInputEditText (TIET) Valid
    fun isTextValid(til: TextInputLayout, tie: TextInputEditText, inputType: Int = text): Boolean {

        if (tie.text.toString().trim().isEmpty()) {
            til.error = "Field can't be empty"
            return false
        }

        when (inputType) {
            name -> {
                if (!isNameValid(tie.text.toString().trim())) {
                    til.error = "Please enter valid name"
                    return false
                }
            }
            phone -> {
                if (!isMobileValid(tie.text.toString().trim())) {
                    til.error = "Please enter valid phone number"
                    return false
                }
            }
            emailAddress -> {
                if (!isEmailValid(tie.text.toString().trim())) {
                    til.error = "Please enter valid email address"
                    return false
                }
            }
            password -> {
                if (!isPasswordValid(tie.text.toString().trim())) {
                    til.error = "Password must contains at least 5 character"
                    return false
                }
            }
            ipAddress -> {
                if (!isIpAddressValid(tie.text.toString().trim())) {
                    til.error = "Please enter valid IP address"
                    return false
                }
            }
            webAddress -> {
                if (!isWebAddressValid(tie.text.toString().trim())) {
                    til.error = "Please enter valid web address"
                    return false
                }
            }
        }

        til.error = ""
        return true
    }
}