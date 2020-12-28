package com.webgrity.tisha.ui.activities.select_table

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.webgrity.tisha.R
import com.webgrity.tisha.app.AppData


class FloorTableAdapter(private val viewModel: SelectTableViewModel) : RecyclerView.Adapter<FloorTableAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, i: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.row_floor_table, parent, false)
        return MyViewHolder(view)
    }


    override fun getItemCount(): Int {
        return viewModel.floorTableList.size
    }
	
	override fun getItemViewType(position: Int): Int {
        return position
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvFloorName: TextView = itemView.findViewById(R.id.tv_floor)
        var tvTableName: TextView = itemView.findViewById(R.id.tv_table)
        var tvOccupied: TextView = itemView.findViewById(R.id.tv_occupied)
        var divider: View = itemView.findViewById(R.id.divider)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        try {
            val floorTable = viewModel.floorTableList[position]
            if (floorTable.type == "floor") {
                viewHolder.tvFloorName.text = floorTable.floorName

                viewHolder.tvTableName.visibility = View.GONE
                viewHolder.tvOccupied.visibility = View.GONE
                viewHolder.divider.visibility = View.GONE

            } else if (floorTable.type == "table") {
                viewHolder.tvTableName.text = floorTable.tableName
                if (floorTable.occupied) viewHolder.tvOccupied.text = "Occupied"
                viewHolder.tvFloorName.visibility = View.GONE

                viewHolder.tvTableName.setOnClickListener {
                    viewModel.onTableClick()
                }
            }
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        }

    }
}