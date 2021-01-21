package com.example.motionlayoutdemo.fab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.motionlayoutdemo.R


class FabAdapter(private val list: ArrayList<Fab>) : RecyclerView.Adapter<FabAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.row_fab, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView2: TextView = itemView.findViewById(R.id.textView2)
        var textView3: TextView = itemView.findViewById(R.id.textView3)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        try {
            val fab:Fab = list[position]
            viewHolder.textView2.text = fab.name
            viewHolder.textView3.text = fab.count.toString()
        } catch (error: Exception) {
            //Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        }

    }
}