package com.webgrity.tisha.ui.fragment.floor_plan

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.webgrity.tisha.R
import com.webgrity.tisha.app.AppData
import com.webgrity.tisha.data.entities.Floor
import com.webgrity.tisha.data.repositories.TableRepository
import com.webgrity.tisha.interfaces.FloorAdapterListener
import com.webgrity.tisha.data.models.Shape


class FloorPlanAdapter(
    private val list: ArrayList<Floor>?,
    private val listener: FloorAdapterListener
) : RecyclerView.Adapter<FloorPlanAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, i: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.layout_floor_or_section, parent, false)
        return MyViewHolder(view)
    }


    override fun getItemCount(): Int {
        return list!!.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvFloorName: TextView? = null
        var tvTableCount: TextView? = null
        var clFloor: ConstraintLayout? = null
        var ivEditFloor: ImageView? = null

        init {
            tvFloorName = itemView.findViewById(R.id.tv_floor_name)
            tvTableCount = itemView.findViewById(R.id.tv_table_count)
            clFloor = itemView.findViewById(R.id.cl_floor)
            ivEditFloor = itemView.findViewById(R.id.iv_edit_floor)
        }


    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        try {
            val floor = list!![position]
            viewHolder.tvFloorName?.text = floor.name
            viewHolder.tvTableCount?.text = "${getTotalTableCount(floor.id)} Tables"

            viewHolder.clFloor?.setOnClickListener {
                listener.onFloorClicked(floor.id, floor.name)
            }

            viewHolder.ivEditFloor?.setOnClickListener {
                listener.onEditFloorClicked(floor.id, floor.name)
            }


        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        }

    }

    companion object {
        fun getTotalTableCount(floorId: Int): Int? {
            var tableCount = 0
            try {
                val results = TableRepository().getAllDataByField("floorId", floorId)
                for (table in results!!) {
                    if (table.shape != Shape.obstacle) tableCount += 1
                }
            } catch (error: Exception) {
                Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
            }
            return tableCount
        }
    }
}