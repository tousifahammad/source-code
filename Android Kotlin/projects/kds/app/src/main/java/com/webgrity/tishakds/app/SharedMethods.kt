package com.webgrity.tishakds.app

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.webgrity.tishakds.utilities.Coroutines
import com.webgrity.tishakds.R
import com.webgrity.tishakds.utilities.extensions.logd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


object SharedMethods {

    //get all child view from group view
    fun getAllChildrenFromViewGroup(view: View): ArrayList<View> {
        val viewList: ArrayList<View> = ArrayList()
        try {
            if (view !is ViewGroup) {
                viewList.add(view)
                return viewList
            }

            for (i in 0 until view.childCount) {
                val childView: View = view.getChildAt(i)
                //Do not add any parents, just add child elements
                getAllChildrenFromViewGroup(childView)?.let { viewList.addAll(it) }
            }
        } catch (error: Exception) {
//            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        }

        return viewList
    }

    fun getStringFromJsonFile(context: Context, fileId: Int): String {
        val inputStream: InputStream = context.resources.openRawResource(fileId)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            val reader: Reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            var n = 0
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
            return writer.toString()
        } catch (error: Exception) {
//            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            inputStream.close()
        }

        return ""
    }


    fun getResizedBitmapFromUri(c: Context, uri: Uri, requiredSize: Int): Bitmap? {
        try {
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            BitmapFactory.decodeStream(c.contentResolver.openInputStream(uri), null, o)
            var width_tmp = o.outWidth
            var height_tmp = o.outHeight
            var scale = 1
            while (true) {
                if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize) break
                width_tmp /= 2
                height_tmp /= 2
                scale *= 2
            }
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            return BitmapFactory.decodeStream(c.contentResolver.openInputStream(uri), null, o2)
        } catch (error: Exception) {
//            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        }

        return null
    }

    /* set convert resource image (Bitmap) To ByteArray */
    fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray? {
        try {
            /*
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, ByteArrayOutputStream())
            return stream.toByteArray()
            */

            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()
            bitmap.recycle()

            return byteArray

        } catch (error: Exception) {
//            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        }
        return null
    }

    /* set byte array into image view */
    fun setImageFromByteArray(imageView: ImageView, byteArray: ByteArray?) {
        try {
            byteArray?.let {
                Coroutines.io {
                    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    withContext(Dispatchers.Main) { imageView.setImageBitmap(bitmap) }
                }
            }
        } catch (error: Exception) {
//            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        }
    }


    /* get byte array from image Uri */
    fun getByteArrayFromImageUri(context: Context, uri: Uri): ByteArray? {
        var byteArray: ByteArray? = null
        try {
            val inputStream: InputStream = context.contentResolver.openInputStream(uri)!!
            val byteBuffer = ByteArrayOutputStream()
            val bufferSize = 1024
            val buffer = ByteArray(bufferSize)
            var len = 0
            while (inputStream.read(buffer).also { len = it } != -1) {
                byteBuffer.write(buffer, 0, len)
            }
            byteArray = byteBuffer.toByteArray()

        } catch (error: Exception) {
//            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        }
        return byteArray
    }

    /* get string date as DATE */
    fun stringAsDate(str: String?): Date? {
        val df: DateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        var date: Date? = null
        try {
            date = df.parse(str)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    /* get string time as DATE */
    fun stringAsTime(str: String?): Date? {
        val df: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        var date: Date? = null
        try {
            date = df.parse(str)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    /* get DATE as String */
    fun dateAsString(date: Date?): String? {
        val df: DateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return df.format(date)
    }

    /* get DATE time as String */
    fun timeAsString(date: Date?): String? {
        if (date == null) {
            return ""
        }
        val df: DateFormat = SimpleDateFormat("HH:mm a", Locale.getDefault())
        return df.format(date)
    }

    fun isTwoArrayListsWithSameValues(list1: ArrayList<Any?>?, list2: ArrayList<Any?>?): Boolean {
        try {
            if (list1 == null && list2 == null) return true
            if (list1 == null && list2 != null || list1 != null && list2 == null) return false
            if (list1!!.size != list2!!.size) return false
            for (itemList1 in list1) {
                if (!list2.contains(itemList1)) return false
            }
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : isTwoArrayListsWithSameValues()", error)
        }
        return false
    }

    fun imageToBitmap(image: ImageView): ByteArray {
        val bitmap = (image.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

        return stream.toByteArray()
    }

    fun loadGif(imageView: ImageView, gif: Int, resources: Resources) {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                val decodedAnimation = ImageDecoder.decodeDrawable(ImageDecoder.createSource(resources, gif))
                imageView.setImageDrawable(decodedAnimation)
                (decodedAnimation as? AnimatedImageDrawable)?.start()
            }
        } catch (error: Exception) {
            logd("Error:  ==> $error")
        }
    }

    fun getScreenResolution(context: Context): Pair<Int, Int> {
        try {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val metrics = DisplayMetrics()
            display.getMetrics(metrics)
            val width = metrics.widthPixels
            val height = metrics.heightPixels
            //Log.d(AppData.TAG, "screenSize: $width, $height")
            return Pair(width, height)

        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : autoCreateTable()", error)
        }

        return Pair(0, 0)
    }

    fun setStatusBarColor(activity: Activity, colorId: Int) {
        val window: Window = activity.getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.setStatusBarColor(colorId)
    }

    fun alertDialog(context: Context, title: String, message: String) {
        MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Got it") { _, _ ->
            }
            .setCancelable(false)
            .show()
    }
}