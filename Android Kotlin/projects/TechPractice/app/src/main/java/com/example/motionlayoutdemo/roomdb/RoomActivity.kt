package com.example.motionlayoutdemo.roomdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.motionlayoutdemo.R
import com.example.motionlayoutdemo.data.local.DatabaseBuilder
import com.example.motionlayoutdemo.data.local.DatabaseHelperImpl
import com.example.motionlayoutdemo.data.local.entity.User
import com.example.motionlayoutdemo.util.logD
import kotlinx.android.synthetic.main.activity_room.*
import kotlinx.coroutines.launch

class RoomActivity : AppCompatActivity() {
    //private val dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
    private val userList: MutableList<User> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
    }

    fun add(view: View) {
        lifecycleScope.launch {
            try {
                val dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
                userList.clear()
                for (i in 1..10) {
                    userList.add(User(i, "name $i", "email $i", "avatar $i"))
                }

                dbHelper.insertAll(userList)
                show(textView5)
            } catch (e: Exception) {
                // handler error
            }
        }
    }

    fun show(view: View) {
        lifecycleScope.launch {
            try {
                val dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
                val usersFromDb = dbHelper.getUsers()
                textView5.text = ""
                usersFromDb.forEach {
                    textView5.text = "${textView5.text} $it \n"
                }
            } catch (e: Exception) {
                // handler error
            }
        }
    }

    fun delete(view: View) {
        lifecycleScope.launch {
            try {
                val dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
                dbHelper.deleteAll(userList)
                show(textView5)
            } catch (e: Exception) {
                // handler error
            }
        }
    }
}