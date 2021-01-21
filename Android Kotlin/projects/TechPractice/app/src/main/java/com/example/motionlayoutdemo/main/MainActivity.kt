package com.example.motionlayoutdemo.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.motionlayoutdemo.R
import com.example.motionlayoutdemo.async.coroutines.CoroutineActivity
import com.example.motionlayoutdemo.async.flow.FlowActivity
import com.example.motionlayoutdemo.list.data_binding.recycler_view.MoviesActivity
import com.example.motionlayoutdemo.fab.FabActivity
import com.example.motionlayoutdemo.generic.GenericActivity
import com.example.motionlayoutdemo.list.realm_rv.RealmRvActivity
import com.example.motionlayoutdemo.motion.MotionActivity
import com.example.motionlayoutdemo.roomdb.RoomActivity
import com.example.motionlayoutdemo.util.gotToActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openFab(view: View) {
        Intent(this, FabActivity::class.java).apply {
            startActivity(this)
        }
    }

    fun coroutine(view: View) {
        Intent(this, CoroutineActivity::class.java).apply {
            startActivity(this)
        }
    }

    fun generic(view: View) {
        Intent(this, GenericActivity::class.java).apply {
            startActivity(this)
        }
    }

    fun movies(view: View) {
        Intent(this, MoviesActivity::class.java).apply {
            startActivity(this)
        }
    }

    fun motion(view: View) {
        gotToActivity(MotionActivity::class.java)
    }

    fun flow(view: View) {
        gotToActivity(FlowActivity::class.java)
    }

    fun room(view: View) {
        gotToActivity(RoomActivity::class.java)
    }

    fun realmRecyclerView(view: View) {
        gotToActivity(RealmRvActivity::class.java)
    }
}