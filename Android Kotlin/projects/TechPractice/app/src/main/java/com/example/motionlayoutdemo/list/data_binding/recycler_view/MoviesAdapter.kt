package com.example.motionlayoutdemo.list.data_binding.recycler_view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.motionlayoutdemo.R
import com.example.motionlayoutdemo.databinding.RowMovieBinding

class MoviesAdapter(
    private val context: Context,
    private var list: MutableList<Movie>,
    private val itemClickListener: ItemListener
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    interface ItemListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RowMovieBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_movie, parent, false)
        return ViewHolder(binding, itemClickListener, context)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position, itemCount)

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(position)
        }

    }

    class ViewHolder(val binding: RowMovieBinding, val itemClickListener: ItemListener, val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie, position: Int, itemCount: Int) {
            binding.movie = item
            binding.executePendingBindings()
        }
    }

}