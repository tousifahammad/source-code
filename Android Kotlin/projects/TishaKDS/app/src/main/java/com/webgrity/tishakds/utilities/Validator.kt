package com.webgrity.tishakds.utilities

import android.util.Patterns
import android.widget.AutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Matcher
import java.util.regex.Pattern


//Validate name, email, mobile, etc

object Validator {
    //input constants
    const val text = 1
    const val name = 2
    const val mobile = 3
    const val emailAddress = 4
    const val password = 5
    const val ipAddress = 6
    const val webAddress = 7
    const val description = 8

    private fun isNameValid(txt: String?): Boolean {
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

    private fun isPasswordValid(password: String): Boolean {
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
        val p: Pattern = Pattern.compile("^(\\+91[\\-\\s]?)?[0]?(91)?[6789]\\d{9}\$")

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

    private fun isWebAddressValid(url: String): Boolean {
        return Patterns.WEB_URL.matcher(url).matches()
    }

    private fun isIpAddressValid(ipAddress: String): Boolean {
        return Patterns.IP_ADDRESS.matcher(ipAddress).matches()
    }


    //is TextInputLayout(TIL) and AutoCompleteTextView (ACTV) Valid
    fun isTILandACTVValid(til: TextInputLayout?, actv: AutoCompleteTextView?, selectMessage: String = "", errorMessage: String): Boolean {
        return if (actv?.text.toString().isEmpty() || actv?.text.toString() == selectMessage) {
            til?.error = errorMessage
            //textInputLayout?.requestFocus()
            false
        } else {
            til?.error = ""
            til?.isErrorEnabled = false
            true
        }
    }

    //is TextInputLayout(TIL) and TextInputEditText (TIET) Valid
    fun isTextValid(til: TextInputLayout, tie: TextInputEditText, inputType: Int = text, mandatory: Boolean = true): Boolean {
        val textString = tie.text.toString().trim()
        when {
            textString.isEmpty() -> {
                return if (mandatory) {
                    til.error = "Field can't be empty"
                    false
                } else {
                    til.error = null
                    til.isErrorEnabled = false
                    true
                }
            }
//            inputType == name && !isNameValid(textString) -> {
//                til.error = "Please enter valid name"
//                return false
//            }
            inputType == mobile && !isMobileValid(textString) -> {
                til.error = "Please enter valid phone number"
                return false
            }
            inputType == emailAddress && !isEmailValid(textString) -> {
                til.error = "Please enter valid email address"
                return false
            }
            inputType == password && !isPasswordValid(textString) -> {
                til.error = "Password must contains at least 5 character"
                return false
            }
            inputType == ipAddress && !isIpAddressValid(textString) -> {
                til.error = "Please enter valid IP address"
                return false
            }
            inputType == webAddress && !isWebAddressValid(textString) -> {
                til.error = "Please enter valid web address"
                return false
            }
            else -> {
                til.error = null
                til.isErrorEnabled = false
                return true
            }
        }
    }
    fun isTextValid(til: TextInputLayout, tie: AutoCompleteTextView, inputType: Int = text, mandatory: Boolean = true): Boolean {
        val textString = tie.text.toString().trim()
        when {
            textString.isEmpty() -> {
                return if (mandatory) {
                    til.error = "Field can't be empty"
                    false
                } else {
                    til.error = null
                    til.isErrorEnabled = false
                    true
                }
            }
//            inputType == name && !isNameValid(textString) -> {
//                til.error = "Please enter valid name"
//                return false
//            }
            inputType == mobile && !isMobileValid(textString) -> {
                til.error = "Please enter valid phone number"
                return false
            }
            inputType == emailAddress && !isEmailValid(textString) -> {
                til.error = "Please enter valid email address"
                return false
            }
            inputType == password && !isPasswordValid(textString) -> {
                til.error = "Password must contains at least 5 character"
                return false
            }
            inputType == ipAddress && !isIpAddressValid(textString) -> {
                til.error = "Please enter valid IP address"
                return false
            }
            inputType == webAddress && !isWebAddressValid(textString) -> {
                til.error = "Please enter valid web address"
                return false
            }
            else -> {
                til.error = null
                til.isErrorEnabled = false
                return true
            }
        }
    }

}