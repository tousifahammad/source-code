package com.webgrity.tishalite.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.webgrity.tishalite.R
import com.webgrity.tishalite.data.model.Login
import com.webgrity.tishalite.util.ResponseStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewmodel : SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        //window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark

        //getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.colorPrimaryDark));// set status background white
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            val restaurant = viewmodel.getRestaurant(1)
        }

        viewmodel.login(Login("anandbaid1", "password")).observe(this, {
            when (it.status) {
                ResponseStatus.SUCCESS -> {
                    it.data?.let { result->
                        Toast.makeText(this, "${result.isSuccessful}", Toast.LENGTH_LONG).show()
                    }
                    //it.data?.let { users -> renderList(users) }
                    //recyclerView.visibility = View.VISIBLE
                }
                ResponseStatus.LOADING -> {
                    //recyclerView.visibility = View.GONE
                }
                ResponseStatus.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}