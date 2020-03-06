package com.app.theshineindia.utils;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static boolean isNameValid(AppCompatActivity activity, String txt) {
        String regx = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txt);
        //return matcher.find();

        if (matcher.find()) {
            return true;
        }

        Alert.showError(activity, "Invalid Name");
        return false;
    }

    public static boolean isEmailValid(AppCompatActivity activity, String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (!matcher.matches()) {
            Alert.showError(activity, "Invalid email");
            return false;
        }
        return true;
    }


    public static boolean isPasswordValid(AppCompatActivity activity, String password) {
        if (password.length() < 5) {
            Alert.showError(activity, "Password must contains at least 5 character");
            return false;
        }
        return true;
    }

    public static boolean isMobileValid(AppCompatActivity activity, String s) {
        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number.
        // 1) Begins with 0 or 91
        // 2) Then contains 6 or 7 or 8 or 9.
        // 3) Then contains 9 digits
        Pattern p = Pattern.compile("(0/91)?[6-9][0-9]{9}");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(s);
        if (m.find() && m.group().equals(s)) {
            return true;
        }

        Alert.showError(activity, "Invalid mobile");
        return false;
    }
}
