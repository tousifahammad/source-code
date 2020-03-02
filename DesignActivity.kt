package net.app.mvvmsampleapp.floor.design

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_floor_design.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.root_layout
import net.app.mvvmsampleapp.R
import net.app.mvvmsampleapp.auth.common.AuthListener
import net.app.mvvmsampleapp.data.db.entities.User
import net.app.mvvmsampleapp.databinding.ActivityFloorDesignBinding
import net.app.mvvmsampleapp.floor.design.add_table.AddTableListener
import net.app.mvvmsampleapp.floor.design.add_table.DialogAddTable
import net.app.mvvmsampleapp.floor.design.add_table.Table
import net.app.mvvmsampleapp.util.hide
import net.app.mvvmsampleapp.util.show
import net.app.mvvmsampleapp.util.snackbar
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.*


class DesignActivity : AppCompatActivity(), AuthListener, KodeinAware, AddTableListener {

    override val kodein by kodein()
    private val factory: DesignViewModelFactory by instance()

    //var gestureDetector: GestureDetector? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityFloorDesignBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_floor_design)
        val viewModel = ViewModelProviders.of(this, factory).get(DesignViewModel::class.java)
        binding.designViewModel = viewModel


        //gestureDetector = GestureDetector(this, GestureListener())

        iniUI()

        initListener()
    }

    private fun iniUI() {

    }

    private fun initListener() {
    }


    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(user: User) {
        progress_bar.hide()
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }

    fun onAddTableClick(view: View) {
        DialogAddTable(this, this).show()
    }

    override fun addButtonClick(
        context: Context,
        table_type: String,
        table_name: String,
        table_size: Int
    ) {
        var tv_table_name: TextView? = null

        val layoutInflater: LayoutInflater = LayoutInflater.from(this)
        var layout_tables: View = layoutInflater.inflate(R.layout.layout_tables, CL_floor, false)

        tv_table_name = layout_tables.findViewById(R.id.tv_table_name)


        if (table_size > 0) {
            layout_tables.scaleX += table_size / 40
            layout_tables.scaleY += table_size / 40
        }

        when (table_type) {
            "Square" -> {
                layout_tables.setBackgroundResource(R.drawable.square)
                tv_table_name.text = "Table no : ${table_name}"
            }
            "Circle" -> {
                layout_tables.setBackgroundResource(R.drawable.circle)
                tv_table_name.text = "Table no : ${table_name}"
            }

            "Rectangle vertical" -> {
                layout_tables.setBackgroundResource(R.drawable.ractangle)
                val layoutParams: ViewGroup.LayoutParams = layout_tables.getLayoutParams()
                layoutParams.width = 90
                layoutParams.height = 300
                layout_tables.setLayoutParams(layoutParams)


                tv_table_name.text = table_name
            }

            "Rectangle horizontal" -> {
                layout_tables.setBackgroundResource(R.drawable.ractangle)
                val layoutParams: ViewGroup.LayoutParams = layout_tables.getLayoutParams()
                layoutParams.width = 300
                layoutParams.height = 90
                layout_tables.setLayoutParams(layoutParams)

                tv_table_name.text = "Table no : ${table_name}"
            }
        }

        initTouchListener(layout_tables)
        CL_floor?.addView(layout_tables)

        addTableToDB(table_name, table_type, layout_tables)
    }


    private fun initTouchListener(layoutTables: View) {
        var dX = 0f
        var dY = 0f

        var listener = View.OnTouchListener(function = { view, event ->

            //gestureDetector?.onTouchEvent(event)

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY
                }
                MotionEvent.ACTION_MOVE -> {

                    view.animate()
                        .x(event.rawX + dX)
                        .y(event.rawY + dY)
                        .setDuration(0)
                        .start()
                }
                else -> {
                    false
                }
            }



            true
        })

        layoutTables.setOnTouchListener(listener)
    }


    private fun addTableToDB(tableName: String, tableType: String, layoutTables: View) {
        val realm = Realm.getDefaultInstance()

        try {
            realm.beginTransaction()

            val table: Table =
                realm.createObject<Table>(Table::class.java, UUID.randomUUID().toString())
            //table.id = UUID.randomUUID().toString()
            table.number = tableName
            table.shape = tableType
            table.width = layoutTables.measuredWidth
            table.height = layoutTables.measuredHeight
            table.position_x = layoutTables.x
            table.position_y = layoutTables.y

            realm.commitTransaction()

        } catch (error: Exception) {
            Log.d("1111", "Error : ${error.message}")
        } finally {
            realm.close()
        }

        viewTable()
    }

    private fun viewTable() {
        val realm = Realm.getDefaultInstance()

        try {
            realm.beginTransaction()
            val query = realm.where<Table>()
            val result1 = query.findAll()

            Log.d("1111", "result1 : ${result1.toString()}")

        } catch (error: Exception) {
            Log.d("1111", "Error : ${error.message}")
        } finally {
            realm.close()
        }
    }

/*    private class GestureListener : SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        // event when double tap occurs
        override fun onDoubleTap(e: MotionEvent): Boolean {
            println("====onDoubleTap")
            //DesignActivity().onAddTableClick(null)
            return true
        }
    }*/


}
