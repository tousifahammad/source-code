package com.example.motionlayoutdemo.list.data_binding.recycler_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.motionlayoutdemo.R
import com.example.motionlayoutdemo.databinding.ActivityMoviesBinding
import com.example.motionlayoutdemo.util.getViewModel

class MoviesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoviesBinding
    private lateinit var viewModel: MoviesViewModel

    private var movieList = ArrayList<Movie>()
    private var adapter: MoviesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movies)
        viewModel = getViewModel { MoviesViewModel() }
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        movieList = ArrayList()
        adapter = MoviesAdapter(this, movieList, clickListener)

        binding.staffRV.adapter = adapter
        for (i in 1..20) {
            Movie().apply {
                this.id = "$i."
                this.name = "name $i"
                movieList.add(this)
            }
        }

        adapter!!.notifyDataSetChanged()
    }

    private val clickListener = object : MoviesAdapter.ItemListener {
        override fun onItemClick(position: Int) {
            Toast.makeText(this@MoviesActivity, movieList[position].name, Toast.LENGTH_SHORT).show()
        }
    }

}