package com.example.motionlayoutdemo.list.realm_rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.motionlayoutdemo.R
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults


class ModuleAdapter(private val realmResults: RealmResults<Module>) : RealmRecyclerViewAdapter<Module, ModuleAdapter.ViewHolder>(realmResults, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_module, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvNo: TextView = v.findViewById(R.id.tv_no)
        val typeName: TextView = v.findViewById(R.id.type_name)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val module: Module = realmResults[position]!!
        holder.tvNo.text = module.id.toString()
        holder.typeName.text = "value:" + module.name
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}






