package net.app.mvvmsampleapp.floor.design

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModel
import io.realm.Realm
import io.realm.kotlin.where
import net.app.mvvmsampleapp.R
import net.app.mvvmsampleapp.floor.design.add_table.AddTableListener
import net.app.mvvmsampleapp.floor.design.add_table.DialogAddTable
import net.app.mvvmsampleapp.floor.design.add_table.Table


class DesignViewModel(private val repository: DesignRepository, private val context: Context) :
    ViewModel()/*, AddTableListener*/ {

   var clFloor: ConstraintLayout? = null
    var realm: Realm? = null

    /*
    init {
        realm = Realm.getDefaultInstance()

        initSavedTables()
    }


    fun onAddTableClick(view: View) {
        DialogAddTable(view.context, this).show()
    }

    override fun onAddButtonClickedInDialoge(id: String) {
        inflateTable(id)
    }

    private fun initSavedTables() {
        try {
            val query = realm?.where<Table>()
            val results = query?.findAll()

            Log.d("1111", "size : ${results?.size}")

            for (i in 0 until results!!.size) {
                //Log.d("1111", "table no : ${results.get(i)?.number}")
                Log.d("1111", "results ===== : ${results[i]}")

                inflateTable(results[i]!!.id)
            }
        } catch (error: Exception) {
            Log.d("1111", "Error : ${error.printStackTrace()}")
        }
    }

    private fun inflateTable(id: String) {
        val tv_table_name: TextView?

        try {
            val layoutInflater: LayoutInflater = LayoutInflater.from(context)
            val layout_tables: View = layoutInflater.inflate(R.layout.layout_tables, clFloor, false)
            tv_table_name = layout_tables.findViewById(R.id.tv_table_name)

            val table: Table = realm?.where<Table>()?.equalTo("id", id)?.findFirst()!!

            layout_tables.x = table.position_x
            layout_tables.y = table.position_y

            layout_tables.layoutParams = LinearLayout.LayoutParams(
                table.width.toInt(),
                table.height.toInt()
            )

            when (table.shape) {
                "Square" -> {
                    layout_tables.setBackgroundResource(R.drawable.square)
                    tv_table_name.text = "Table  ${table.number}"
                }
                "Circle" -> {
                    layout_tables.setBackgroundResource(R.drawable.circle)
                    tv_table_name.text = "Table  ${table.number}"
                }

                "Rectangle vertical" -> {
                    layout_tables.setBackgroundResource(R.drawable.ractangle)

                    layout_tables.layoutParams = LinearLayout.LayoutParams(
                        table.width.toInt() / 3,
                        table.height.toInt()
                    )


                    tv_table_name.text = table.number
                }

                "Rectangle horizontal" -> {
                    layout_tables.setBackgroundResource(R.drawable.ractangle)
                    layout_tables.layoutParams = LinearLayout.LayoutParams(
                        table.width.toInt(),
                        table.height.toInt() / 3
                    )

                    tv_table_name.text = "Table  ${table.number}"
                }
            }

            initTouchListener(layout_tables, id)
            clFloor?.addView(layout_tables)

        } catch (error: Exception) {
            Log.d("1111", "Error : ${error.printStackTrace()}")
        }
    }

    private fun initTouchListener(layoutTables: View, id: String) {
        var dX = 0f
        var dY = 0f
        var _x = 0f
        var _y = 0f

        val listener = View.OnTouchListener(function = { view, event ->

            //gestureDetector?.onTouchEvent(event)

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    _x = event.rawX + dX
                    _y = event.rawY + dY
                    view.animate()
                        .x(_x)
                        .y(_y)
                        .setDuration(0)
                        .start()


                    updateTable(id, _x, _y)
                }
            }

            true
        })

        layoutTables.setOnTouchListener(listener)
    }


    private fun updateTable(id: String, _x: Float, _y: Float) {
        try {
            realm?.executeTransaction { realm ->
                val table: Table = realm.where<Table>().equalTo("id", id).findFirst()!!
                table.position_x = _x
                table.position_y = _y

//                Log.d("1111", "update success")
//                Log.d("1111", "Error : ${table.position_x}")
//                Log.d("1111", "Error : ${table.position_y}")
            }
        } catch (error: Exception) {
            Log.d("1111", "Error : ${error.printStackTrace()}")
        }
    }*/

}