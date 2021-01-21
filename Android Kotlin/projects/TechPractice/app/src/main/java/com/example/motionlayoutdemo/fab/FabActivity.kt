package com.example.motionlayoutdemo.fab

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.motionlayoutdemo.R

class FabActivity : AppCompatActivity() {
    private var list1 = ArrayList<Fab>()
    private var list2 = ArrayList<Fab>()
    private var adapter: FabAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fab)

        val rv_fab = findViewById<RecyclerView>(R.id.rv_fab)
        val button3 = findViewById<Button>(R.id.button3)

        list1.add(Fab("TABLE 1", 11))
        list1.add(Fab("TABLE 2", 22))
        list1.add(Fab("TABLE 3", 33))
        list1.add(Fab("TABLE 4", 44))
        adapter = FabAdapter(list2)
        rv_fab.adapter = adapter

        button3.setOnClickListener {
            if (list2.size == 0) {
                list2.addAll(list1)
                adapter?.notifyItemRangeInserted(0, list2.size)
            } else {
                list2.clear()
                adapter?.notifyDataSetChanged()
            }
        }

    }
}