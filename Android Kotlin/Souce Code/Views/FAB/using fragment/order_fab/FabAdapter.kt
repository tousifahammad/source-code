package com.webgrity.tisha.ui.fragment.order_fab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.webgrity.tisha.R
import com.webgrity.tisha.util.clickEffect
import com.webgrity.tisha.util.gone
import com.webgrity.tisha.util.loadImage
import com.webgrity.tisha.util.visible

class FabAdapter(private val list: ArrayList<Fab>, private val onClick: (fab: Fab) -> Unit) : RecyclerView.Adapter<FabAdapter.MyViewHolder>() {

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
        var tvFabName: TextView = itemView.findViewById(R.id.tv_fab_name)
        var tvFabCount: TextView = itemView.findViewById(R.id.tv_fab_count)
        var ivFabIcon: ImageView = itemView.findViewById(R.id.iv_fab_icon)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        try {
            val fab: Fab = list[position]
            viewHolder.tvFabName.text = fab.name
            viewHolder.ivFabIcon.loadImage(fab.icon)

            if (fab.type == "bill") {
                viewHolder.tvFabCount.gone()
            } else {
                viewHolder.tvFabCount.visible()
                viewHolder.tvFabCount.text = fab.count.toString()
            }

            viewHolder.itemView.setOnClickListener {
                it.clickEffect()
                onClick(fab)
            }
        } catch (error: Exception) {
            error.printStackTrace()
        }
    }
}