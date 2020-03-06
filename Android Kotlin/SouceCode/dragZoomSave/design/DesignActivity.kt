package net.app.mvvmsampleapp.floor.design

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_floor_design.*
import net.app.mvvmsampleapp.R
import net.app.mvvmsampleapp.app.AppData
import net.app.mvvmsampleapp.databinding.ActivityFloorDesignBinding
import net.app.mvvmsampleapp.floor.design.add_table.AddTableListener
import net.app.mvvmsampleapp.floor.design.add_table.DialogAddTable
import net.app.mvvmsampleapp.floor.design.add_table.Table
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class DesignActivity : AppCompatActivity(), KodeinAware, AddTableListener {

    override val kodein by kodein()
    private val factory: DesignViewModelFactory by instance()
    var realm: Realm? = null

    var gestureDetector: GestureDetector? = null
    var scaleGestureDetector: ScaleGestureDetector? = null

    private var scaleFactor = 0f
    private val maxScaleAmount = 500
    private val minScaleAmount = 170

    private enum class GestureMode {
        NONE, DRAG, ZOOM
    }

    private var gestureMode = GestureMode.NONE


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityFloorDesignBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_floor_design)
        val viewModel = ViewModelProviders.of(this, factory).get(DesignViewModel::class.java)

        binding.designViewModel = viewModel

        iniObject()

        initSavedTables()
    }


    private fun iniObject() {
        realm = Realm.getDefaultInstance()
        gestureDetector = GestureDetector(this, GestureListener(this, this))
        scaleGestureDetector = ScaleGestureDetector(this, simpleOnScaleGestureListener)
    }


    private fun initSavedTables() {
        try {
            val results = realm?.where<Table>()?.findAll()
            for (i in 0 until results!!.size) {
                inflateTable(results.get(i)!!.id)
            }
        } catch (error: Exception) {
            Log.d("1111", "Error : ${error.printStackTrace()}")
        }
    }


    fun onAddTableClick(view: View) {
        DialogAddTable(this, this, "").show()
    }

    override fun onAddButtonClickedInDialoge() {
        CL_floor.removeAllViews()
        initSavedTables()
    }

    private fun inflateTable(id: String) {
        val tv_table_name: TextView?

        val layoutInflater: LayoutInflater = LayoutInflater.from(this)
        val layout_tables: View = layoutInflater.inflate(R.layout.layout_tables, CL_floor, false)
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

        layout_tables.setOnTouchListener(initTouchListener(id))
        CL_floor?.addView(layout_tables)
    }

    private fun initTouchListener(id: String): OnTouchListener {
        var dX = 0f
        var dY = 0f
        var _x = 0f
        var _y = 0f

        return OnTouchListener(function = { view, event ->
            Log.d("1111", "gesture Mode : $gestureMode")

            gestureDetector?.onTouchEvent(event)
            scaleGestureDetector?.onTouchEvent(event)

            when (event.action) {

                MotionEvent.ACTION_DOWN -> {
                    gestureMode = GestureMode.DRAG
                    AppData.lastClickedTableId = id

                    dX = view.x - event.rawX
                    dY = view.y - event.rawY
                }

                MotionEvent.ACTION_MOVE -> {
                    _x = event.rawX + dX
                    _y = event.rawY + dY

                    if (gestureMode == GestureMode.DRAG) {
                        moveTheView(view, _x, _y)

                    } else if (gestureMode == GestureMode.ZOOM) {
                        //scaleCurrentView()
                        scaleTheView(view)
                    }

                    updateTable(view)
                }

                MotionEvent.ACTION_UP -> {
                    gestureMode = GestureMode.NONE
                }
            }
            CL_floor.invalidate()

            true
        })
    }

    private fun moveTheView(view: View?, _x: Float, _y: Float) {
        view!!.animate()
            .x(_x)
            .y(_y)
            .setDuration(0)
            .start()

        view.invalidate()
    }


    private fun scaleTheView(view: View) {
        var scaleAmont = 0f
        if (scaleFactor > 1 && view.measuredWidth < maxScaleAmount) {              //zoom in and not reaching max scale
            scaleAmont = view.measuredWidth + scaleFactor * 5
        } else if (scaleFactor < 1 && view.measuredWidth > minScaleAmount) {       //zoom out and not reaching min scale
            scaleAmont = view.measuredWidth - scaleFactor * 5
        } else {
            return
        }

        view.layoutParams = ConstraintLayout.LayoutParams(
            scaleAmont.toInt(),
            scaleAmont.toInt()
        )

        view.invalidate()
    }


    private fun updateTable(
        view: View
    ) {
        try {
            realm?.executeTransaction { realm ->
                val table: Table =
                    realm.where<Table>().equalTo("id", AppData.lastClickedTableId).findFirst()!!
                table.position_x = view.x
                table.position_y = view.y
                table.width = view.measuredWidth
                table.height = view.measuredHeight
            }
        } catch (error: Exception) {
            Log.d("1111", "Error : ${error.printStackTrace()}")
        }
    }


    private class GestureListener(
        private val context: Context,
        private val add_table_listener: AddTableListener
    ) : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        // event when double tap occurs
        override fun onDoubleTap(e: MotionEvent): Boolean {
            DialogAddTable(context, add_table_listener, AppData.lastClickedTableId).show()
            return true
        }
    }


    private val simpleOnScaleGestureListener =
        object : ScaleGestureDetector.SimpleOnScaleGestureListener() {

            override fun onScale(detector: ScaleGestureDetector): Boolean {
                scaleFactor = detector.scaleFactor
                return true
            }

            override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
                //return super.onScaleBegin(detector)
                gestureMode = GestureMode.ZOOM
                return true;
            }
        }


    override fun onDestroy() {
        super.onDestroy()

        realm?.close()
    }

}
