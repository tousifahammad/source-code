package com.app.qcare.baseclasses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.app.qcare.R;
import com.app.qcare.shared.SearchFragment;
import com.app.qcare.utils.Alert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//use this class to write methods, which are used in many other classes
public class SharedMethods {
    public static boolean isSuccess(String response, Activity activity) {
        if (response != null) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt(WebService.result) == 1) {
                    return true;
                } else {
                    Toast.makeText(activity, jsonObject.getString(WebService.message), Toast.LENGTH_LONG).show();
                    return false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(activity, "No response from server. Please try again", Toast.LENGTH_LONG).show();
        }

        return false;
    }

    public static JSONArray getDataArray(String response, Activity activity) {
        JSONArray jsonArray = null;
        if (response != null) {
            try {
                //Log.d(activity.getClass().getSimpleName(), "response: " + response);
                JSONObject jsonObject = new JSONObject(response);

                if (jsonObject.getInt(WebService.result) == 1) {
                    jsonArray = jsonObject.getJSONArray(WebService.data);

                    if (jsonArray.length() > 0) {
                        return jsonArray;
                    } else {
                        Toast.makeText(activity, activity.getString(R.string.no_data), Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(activity, jsonObject.getString(WebService.message), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Toast.makeText(activity, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(activity, "No response from server. Please try again", Toast.LENGTH_LONG).show();
        }
        return jsonArray;
    }

    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("BankList.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public static void writeToFile(String data) {
        if (data != null) {
            String path = Environment.getExternalStorageDirectory() + File.separator + "Qcare";
            // Create the folder.
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Create the file.
            File file = new File(folder, "response.txt");

            // Save your stream, don't forget to flush() it before closing it.

            try {
                file.createNewFile();
                FileOutputStream fOut = new FileOutputStream(file);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append(data);

                myOutWriter.close();

                fOut.flush();
                fOut.close();
                //Toast.makeText(this, "File created " + file.getPath(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
                //Toast.makeText(this, "File error " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void writeToFile(String file_name,String folder_name, String data) {
        if (data != null) {
            String path = Environment.getExternalStorageDirectory() + File.separator + "GlocalFirm"+ File.separator + folder_name;
            // Create the folder.
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Create the file.
            File file = new File(folder, file_name + ".txt");

            // Save your stream, don't forget to flush() it before closing it.

            try {
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileOutputStream fOut = new FileOutputStream(file, true);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append(data);

                myOutWriter.close();

                fOut.flush();
                fOut.close();
                //Toast.makeText(this, "File created " + file.getPath(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
                //Toast.makeText(this, "File error " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    //1 minute = 60 seconds
//1 hour = 60 x 60 = 3600
//1 day = 3600 x 24 = 86400
    public static long getDateDifference(String startDate, String endDate) {
        long different = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy");
            Date date1 = simpleDateFormat.parse(startDate);
            Date date2 = simpleDateFormat.parse(endDate);
            //milliseconds
            different = date2.getTime() - date1.getTime();

//      System.out.println("startDate : " + startDate);
//      System.out.println("endDate : " + endDate);
//      System.out.println("different : " + different);
//
//      long secondsInMilli = 1000;
//      long minutesInMilli = secondsInMilli * 60;
//      long hoursInMilli = minutesInMilli * 60;
//      long daysInMilli = hoursInMilli * 24;
//
//      long elapsedDays = different / daysInMilli;
//      different = different % daysInMilli;
//
//      long elapsedHours = different / hoursInMilli;
//      different = different % hoursInMilli;
//
//      long elapsedMinutes = different / minutesInMilli;
//      different = different % minutesInMilli;
//
//      long elapsedSeconds = different / secondsInMilli;
//
//      System.out.printf(
//        "%d days, %d hours, %d minutes, %d seconds%n",
//        elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return different;
    }

    public static List<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            //Do not add any parents, just add child elements        result.addAll(getAllChildren(child));
        }
        return result;
    }

    public static boolean isPackageInstalled(String packageName, Context context) {
        boolean found = true;
        try {
            PackageManager packageManager = context.getPackageManager();
            packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            found = false;
        }
        return found;
    }

    public static int getPositionByName(ArrayList<String> list, String name) {
        for (String str : list) {
            if (str.equals(name)) {
                return list.indexOf(str);
            }
        }
        return -1;
    }

    public static TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(250);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }

//    public static void openSearch(Activity activity, ArrayList<String> name_list, int container_view_id, String title) {
//        try {
//            if (name_list.size() > 0) {
//                Bundle bundle = new Bundle();
//                bundle.putStringArrayList("name_list", name_list);
//                bundle.putString("title", title);
//                //bundle.putStringArrayList("code_list", biler_code_list);
//
//                Fragment fragment = new SearchFragment();
//                fragment.setArguments(bundle);
//
//                FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
//                transaction.replace(container_view_id, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
//                transaction.addToBackStack(null);  // this will manage backstack
//                transaction.commit();
//            } else {
//                Alert.showError(activity, "No data found");
//            }
//        } catch (Exception e) {
//            Alert.showError(activity, e.getMessage());
//        }
//    }


    public static String getCurrentDateTime() {
        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void openSearch(Activity activity, ArrayList<String> name_list, int container_view_id, String title) {
        try {
            if (name_list.size() > 0) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("name_list", name_list);
                bundle.putString("title", title);
                //bundle.putStringArrayList("code_list", biler_code_list);

                Fragment fragment = new SearchFragment();
                fragment.setArguments(bundle);

                FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
                transaction.replace(container_view_id, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
                transaction.addToBackStack(null);  // this will manage backstack
                transaction.commit();
            } else {
                Alert.showError(activity, "No data found");
            }
        } catch (Exception e) {
            Alert.showError(activity, e.getMessage());
        }
    }

    public static String encodeImageBitmap(Bitmap bm) {
        String encImage = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            byte[] b = baos.toByteArray();
            encImage = Base64.encodeToString(b, Base64.DEFAULT);

        } catch (Exception e) {
            Log.d("HomeActivity", "encodeImage: " + e.getMessage());
        }
        return encImage;
    }
}
