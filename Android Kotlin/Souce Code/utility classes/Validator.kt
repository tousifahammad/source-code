package net.simplifiedcoding.mvvmsampleapp.util

import android.content.Context
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.regex.Matcher
import java.util.regex.Pattern


//Validate name, email, mobile, etc

object Validator : KodeinAware {
    override val kodein by kodein()
    private val context: Context by instance()

    fun isNameValid(txt: String?): Boolean {
        val regx = "^[\\p{L} .'-]+$"
        val pattern: Pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(txt)
        //return matcher.find();
        if (matcher.find()) {
            return true
        }
        //Alert.showError(activity, "Invalid Name")
        context.toast("Invalid Name")
        return false
    }

    fun isEmailValid(email: String): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val inputStr: CharSequence = email
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(inputStr)
        if (!matcher.matches()) {
            context.toast("Invalid email")
            return false
        }
        return true
    }


    fun isPasswordValid(password: String): Boolean {
        if (password.length < 5) {
            context.toast("Password must contains at least 5 character")
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
        val p: Pattern = Pattern.compile("(0/91)?[6-9][0-9]{9}")

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        val m: Matcher = p.matcher(s)
        if (m.find() && m.group().equals(s)) {
            return true
        }
        context.toast("Invalid mobile number")
        return false
    }
}