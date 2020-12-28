package com.webgrity.tisha.ui.activities.floor.floor_plan

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.webgrity.tisha.R
import com.webgrity.tisha.app.AppData
import com.webgrity.tisha.app.SharedMethods
import com.webgrity.tisha.background_process.receivers.InternetReceiver
import com.webgrity.tisha.data.entities.Tables
import com.webgrity.tisha.data.models.Shape
import com.webgrity.tisha.data.repositories.InvoiceRepository
import com.webgrity.tisha.data.repositories.TableRepository
import com.webgrity.tisha.databinding.ActivityFloorPlanBinding
import com.webgrity.tisha.detectors.DoubleTapGestureDetector
import com.webgrity.tisha.detectors.MyScaleGestureDetector
import com.webgrity.tisha.detectors.RotationGestureDetector
import com.webgrity.tisha.interfaces.DoubleTapListener
import com.webgrity.tisha.interfaces.InternetListener
import com.webgrity.tisha.interfaces.RotationListener
import com.webgrity.tisha.interfaces.ScaleListener
import com.webgrity.tisha.ui.activities.base.BaseActivity
import com.webgrity.tisha.ui.dialogs.DialogAddTable
import com.webgrity.tisha.util.Alert
import com.webgrity.tisha.util.logD
import com.webgrity.tisha.util.toast
import kotlinx.android.synthetic.main.activity_floor.tv_internet
import kotlinx.android.synthetic.main.activity_floor_plan.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.*


class FloorPlanActivity : BaseActivity(), KodeinAware, RotationListener, DoubleTapListener, ScaleListener, InternetListener {

    override val kodein by kodein()
    private val tableRepository: TableRepository by instance()
    private val invoiceRepository: InvoiceRepository by instance()
    private lateinit var viewModel: FloorPlanViewModel

    private enum class GestureMode { NONE, DRAG, ZOOM, ROTATION, RESIZE }

    private var gestureMode = GestureMode.NONE
    private var doubleTapGestureDetector: GestureDetector? = null
    private var scaleGestureDetector: ScaleGestureDetector? = null
    private var rotationDetector: RotationGestureDetector? = null
    private var scaleFactor = 0f
    private val maxScaleAmount = 700
    private var minScaleWidth = 0
    private var minScaleHeight = 0
    private val resizeIconAreaAmount = 50
    private var rotationAngle = 0f
    private var endAngle = 0f
    private var lastClickedTableId = Shape.none
    private var currentFloorId = 0

    private var hmTableView: HashMap<Int, View> = HashMap()
    //private var anim: Animation? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityFloorPlanBinding = DataBindingUtil.setContentView(this, R.layout.activity_floor_plan)
        val factory = FloorPlanFactory(this.application)
        viewModel = ViewModelProvider(this, factory).get(FloorPlanViewModel::class.java)
        binding.viewModel = viewModel

        getIntentData()
        iniObject()
        initSavedTables()
    }

    private fun getIntentData() {
        currentFloorId = intent.getIntExtra("floorId", -1)
        if (currentFloorId == -1) {
            toast("Error while getting floor id")
            return
        }
        tv_floor_name.text = intent.getStringExtra("floorName")
    }

    private fun iniObject() {
        doubleTapGestureDetector = GestureDetector(this, DoubleTapGestureDetector(this))
        scaleGestureDetector = ScaleGestureDetector(this, MyScaleGestureDetector(this))
        rotationDetector = RotationGestureDetector(this)
    }


    private fun initSavedTables() {
        try {
            cl_floor_plan_body.removeAllViews()
            hmTableView.clear()

            //val results = tableRepository.getAllDataByField("floorId", currentFloorId) ?: return
            val results = viewModel.getAllActiveTables(currentFloorId) ?: return
            for (i in 0 until results.size) {
                inflateTable(results[i])
            }
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        }
    }


    fun onEditFloorClick(view: View) {
        try {
            viewModel.isEditFloorEnable = !viewModel.isEditFloorEnable

            //only click on save
            //if (!floorPlanViewModel.isEditFloorEnable) updateAllTableDetails()

            changeVisibilityInEditMode()

        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        }
    }

    fun onAddTableClick(view: View) {
        if (viewModel.isEditFloorEnable) {
            val dialog = DialogAddTable(this, Shape.none, currentFloorId, object : DialogAddTable.AddTableListener {
                override fun onAddTable(table: Tables) {
                    inflateTable(table)
                }
            })

            dialog.window?.attributes?.windowAnimations = R.style.DialogAnimationZooming
            dialog.show()
        } else
            finish()
    }

    fun onLogoutClick(view: View) {
        SharedMethods.logout(this)
    }

    private fun changeVisibilityInEditMode() {
        if (viewModel.isEditFloorEnable) {
            tv_header_right.text = "Done"

            tv_header_left.text = "Add Tables/Objects"
            tv_header_left.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            //tv_header_left.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_input_add, 0, 0, 0)

            cl_root_floor.background = ContextCompat.getDrawable(this, R.drawable.ic_graph)
            iv_hint_edit_table.visibility = View.VISIBLE

            /*anim = AnimationUtils.loadAnimation(applicationContext, R.anim.anim_zoom_in_out)
            tv_header_right.startAnimation(anim)
            tv_header_right.setTypeface(null, Typeface.BOLD)*/
        } else {
            tv_header_right.text = "Edit Floor Layout"
            tv_header_left.text = "Floor Plan"
            tv_header_left.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_chevron_left, 0, 0, 0)

            cl_root_floor.setBackgroundColor(ContextCompat.getColor(this, R.color.GhostWhite))
            iv_hint_edit_table.visibility = View.GONE

            /*tv_header_right.clearAnimation()
            tv_header_right.setTypeface(null, Typeface.NORMAL)*/
        }

        for (view: View in SharedMethods.getAllChildrenFromViewGroup(cl_floor_plan_body)) {
            if (view.id == R.id.iv_floor_delete || view.id == R.id.iv_floor_scale)
                if (viewModel.isEditFloorEnable) {
                    view.visibility = View.VISIBLE
                } else {
                    view.visibility = View.GONE
                }
        }
    }


    private fun inflateTable(table: Tables) {
        val layoutInflater: LayoutInflater = LayoutInflater.from(this)

        val rootLayout = layoutInflater.inflate(R.layout.layout_tables, cl_floor_plan_body, false)
        val tvTableName: TextView = rootLayout.findViewById(R.id.tv_table_name)
        val tvSeatCount: TextView = rootLayout.findViewById(R.id.tv_seat_count)
        val ivScale: ImageView = rootLayout.findViewById(R.id.iv_floor_scale)
        val ivDelete: ImageView = rootLayout.findViewById(R.id.iv_floor_delete)

        val clTableContainer = rootLayout.findViewById<ConstraintLayout>(R.id.cl_table_container)
        val clTableShape = rootLayout.findViewById<ConstraintLayout>(R.id.cl_table_shape)


        clTableContainer.x = table.positionX
        clTableContainer.y = table.positionY

        clTableContainer.rotation = table.rotation

        tvTableName.text = table.name.toUpperCase(Locale.ROOT)
        tvSeatCount.text = "${table.seatingCapacity} Seat"

        //containerTable.layoutParams = ConstraintLayout.LayoutParams(table.width, table.height)
        clTableContainer.layoutParams = ConstraintLayout.LayoutParams(table.width, table.height)
        when (table.shape) {
            Shape.tableCircle -> {
                clTableShape.setBackgroundResource(R.drawable.bg_circle_light_blue)
            }
            Shape.obstacle -> {
                clTableShape.setBackgroundResource(R.drawable.ic_obstacle)
                clTableShape.removeAllViews()
            }
            else -> clTableShape.setBackgroundResource(R.drawable.bg_round_border_light_blue)
        }

        ivDelete.setOnClickListener {
            if (viewModel.isEditFloorEnable) deleteThisTable(table.id, clTableContainer)
        }

        clTableContainer.setOnTouchListener(initTouchListener(table.id, table.shape))
        cl_floor_plan_body?.addView(clTableContainer)
        hmTableView[table.id] = clTableContainer
        changeVisibilityInEditMode()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initTouchListener(tableId: Int, shape: Int): View.OnTouchListener {
        var dX = 0f
        var dY = 0f
        var _x: Float
        var _y: Float
        var x: Int
        var y: Int
        var width: Int
        var height: Int

        return View.OnTouchListener(function = { view, event ->
            scaleGestureDetector?.onTouchEvent(event)
            if (shape != Shape.obstacle)
                doubleTapGestureDetector?.onTouchEvent(event)
            if (shape == Shape.obstacle || shape == Shape.tableRectangle)   //only apply for rectangle table and obstacle
                rotationDetector?.onTouchEvent(event)

            x = event.x.toInt()
            y = event.y.toInt()
            width = view.layoutParams.width
            height = view.layoutParams.height

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastClickedTableId = tableId

                    if (viewModel.isEditFloorEnable) {
                        if (
                            ((x - width) in 1..resizeIconAreaAmount || (width - x) in 1..resizeIconAreaAmount)
                            &&
                            ((y - height) in 1..resizeIconAreaAmount || (height - y) in 1..resizeIconAreaAmount)
                        ) {
                            gestureMode = GestureMode.RESIZE

                            if (shape != Shape.obstacle) {
                                minScaleWidth = 150
                                minScaleHeight = 150
                            } else {
                                minScaleWidth = 80
                                minScaleHeight = 120
                            }

                        } else {
                            gestureMode = GestureMode.DRAG
                            dX = view.x - event.rawX
                            dY = view.y - event.rawY
                        }
                    }
                }

                MotionEvent.ACTION_MOVE -> {
                    if (viewModel.isEditFloorEnable) {
                        //floorPlanViewModel.isTableEdited = true

                        when (gestureMode) {
                            GestureMode.DRAG -> {
                                _x = event.rawX + dX
                                _y = event.rawY + dY
                                moveTheView(view, _x, _y)
                            }
                            GestureMode.ZOOM -> {
                                scaleTheView(view)
                            }
                            GestureMode.RESIZE -> {
                                if (shape == Shape.tableSquare || shape == Shape.tableCircle)
                                    resizeTheView(view, y, y)
                                else
                                    resizeTheView(view, x, y)
                            }
                            GestureMode.ROTATION -> {
                                view.rotation += rotationAngle
                            }
                            else -> {
                                //do nothing
                            }
                        }
                    }
                }

                MotionEvent.ACTION_UP -> {
                    if (gestureMode == GestureMode.ROTATION) fixViewRotation(view)

                    updateTableDetails(tableId, view)

                    gestureMode = GestureMode.NONE
                    rotationAngle = 0f
                }
            }
            cl_floor_plan_body.invalidate()

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
        val scaledWidth: Float
        val scaledHeight: Float
        if (scaleFactor > 1 && view.measuredWidth < maxScaleAmount) {              //zoom in and not reaching max scale
            scaledWidth = view.measuredWidth + scaleFactor * 5
            scaledHeight = view.measuredHeight + scaleFactor * 5
        } else if (scaleFactor < 1 && view.measuredWidth > minScaleWidth) {       //zoom out and not reaching min scale
            scaledWidth = view.measuredWidth - scaleFactor * 5
            scaledHeight = view.measuredHeight - scaleFactor * 5
        } else {
            return
        }

        view.layoutParams = ConstraintLayout.LayoutParams(
            scaledWidth.toInt(),
            scaledHeight.toInt()
        )

        view.invalidate()
    }

    private fun resizeTheView(view: View, x: Int, y: Int) {
        val scaledWidth: Int
        val scaledHeight: Int

        if (x > minScaleWidth && y > minScaleHeight) {
            scaledWidth = x
            scaledHeight = y
        } else if (x <= minScaleWidth && y > minScaleHeight) {
            scaledWidth = minScaleWidth
            scaledHeight = y
        } else if (x > minScaleWidth && y <= minScaleHeight) {
            scaledWidth = x
            scaledHeight = minScaleHeight
        } else {
            return
        }

        view.layoutParams = ConstraintLayout.LayoutParams(scaledWidth, scaledHeight)
        view.requestLayout()
    }

    private fun fixViewRotation(view: View?) {
        val viewRotation = view!!.rotation

        //clock wise rotation
        if (viewRotation > 0f && viewRotation < 45f) {
            endAngle = 0f
        } else if (viewRotation > 45f && viewRotation < 135f) {
            endAngle = 90f
        } else if (viewRotation > 135f && viewRotation < 225f) {
            endAngle = 180f
        } else if (viewRotation > 225f && viewRotation < 315f) {
            endAngle = 270f
        } else if (viewRotation > 315f && viewRotation < 360f) {
            endAngle = 0f

            //anti-clock wise rotation
        } else if (viewRotation < 0f && viewRotation > -45f) {
            endAngle = 0f
        } else if (viewRotation < -45f && viewRotation > -135f) {
            endAngle = -90f
        } else if (viewRotation < -135f && viewRotation > -225f) {
            endAngle = -180f
        } else if (viewRotation < -225f && viewRotation > -315f) {
            endAngle = -270f
        } else if (viewRotation < -315f && viewRotation > -360f) {
            endAngle = 0f
        }

        rotateTheView(view, viewRotation, endAngle)
    }

    private fun rotateTheView(view: View?, startAngle: Float, endAngle: Float) {
        try {
            if (startAngle != endAngle) {
                val rotate = ObjectAnimator.ofFloat(view, "rotation", startAngle, endAngle)
                //rotate.setRepeatCount(10);
                rotate.duration = 400
                rotate.start()
            }
        } catch (error: Exception) {
            Log.d("1111", "Error : ${error.printStackTrace()}")
        } finally {
            view!!.rotation = endAngle
            view.invalidate()
        }
    }

    /* private fun updateAllTableDetails() {
         try {
             for (tableId: Int in hmTableView.keys) {
                 val table: Tables = tableRepository.getDataById(tableId)!!
                 val view: View? = hmTableView[tableId]

                 table.width = view!!.measuredWidth
                 table.height = view.measuredHeight
                 table.positionX = view.x
                 table.positionY = view.y
                 table.rotation = view.rotation
                 tableRepository.insertOrUpdateToRealm(table)
             }
         } catch (error: Exception) {
             Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
         }
     }*/


    private fun updateTableDetails(tableId: Int, view: View) {
        try {
            tableRepository.getDataById(tableId)?.let {
                it.width = view.measuredWidth
                it.height = view.measuredHeight
                it.positionX = view.x
                it.positionY = view.y
                it.rotation = view.rotation
                tableRepository.insertOrUpdateToRealm(it)
            }
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        }
    }

    private fun deleteThisTable(tableId: Int, view: View) {
        MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
            .setTitle("DELETE")
            .setMessage("Are you sure, you want to delete?")
            .setPositiveButton("Delete") { _, _ ->
                deleteTable(tableId, view)
            }
            .setNegativeButton("Not now", null)
            .show()
    }

    private fun deleteTable(tableId: Int, view: View) {
        try {
            invoiceRepository.getDataByTableId(tableId)?.let {
                Alert.showError(this, "This table is occupied. You can not delete this table")
                return
            }

            if (tableRepository.deleteRealmObjectByID(currentFloorId, tableId)) {
                cl_floor_plan_body.removeView(view)
            }
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : autoCreateTable()", error)
        }
    }

    override fun onDoubleTap(isDoubleTap: Boolean) {
        if (viewModel.isEditFloorEnable)
            DialogAddTable(this, lastClickedTableId, currentFloorId, object : DialogAddTable.AddTableListener {
                override fun onAddTable(table: Tables) {
                    updateTable(table)
                }
            }).apply {
                this.window?.attributes?.windowAnimations = R.style.DialogAnimationZooming
                this.show()
            }
    }

    private fun updateTable(table: Tables) {
        try {
            val tableContainer = hmTableView[table.id]
            val name = tableContainer?.findViewById<TextView>(R.id.tv_table_name)
            val seat = tableContainer?.findViewById<TextView>(R.id.tv_seat_count)
            name?.text = table.name
            seat?.text = "${table.seatingCapacity} Seat"
        } catch (error: Exception) {
            logD("Error:  ==> $error")
        }
    }

    override fun onScale(scaleFactor: Float) {
        this.scaleFactor = scaleFactor
    }

    override fun onScaleBegin() {
        gestureMode = GestureMode.ZOOM
    }

    override fun onRotation(rotationDetector: RotationGestureDetector) {
        rotationAngle = -rotationDetector.angle
        //Log.d("1111", "angle : $rotationAngle")

        if (rotationAngle < -5 || rotationAngle > 5) {
            gestureMode = GestureMode.ROTATION
        }
    }


    //==================== Internet Connectivity ==========================
    override fun onResume() {
        super.onResume()

        registerReceiver(
            InternetReceiver(this),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onPause() {
        super.onPause()
        try {
            unregisterReceiver(InternetReceiver(this))
        } catch (error: IllegalArgumentException) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        }

    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (isConnected) {
            /* tv_internet.text = "Connected"
             tv_internet.setTextColor(resources.getColor(R.color.Green))
             tv_internet.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_wifi_24, 0, 0, 0);
             tv_internet.compoundDrawableTintList = applicationContext.getColorStateList(R.color.Green)*/
            tv_internet.setImageResource(R.drawable.ic_baseline_wifi_24)
        } else {
            /* tv_internet.text = "Not Connected"
             tv_internet.setTextColor(resources.getColor(R.color.Red))
 //            tv_internet.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_alert_2, 0, 0, 0);
             tv_internet.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_wifi_24, 0, 0, 0);
             tv_internet.compoundDrawableTintList = applicationContext.getColorStateList(R.color.Red)*/
            tv_internet.setImageResource(R.drawable.ic_baseline_wifi_off_24)
        }
    }
    //==================== Internet Connectivity ===========================


    override fun onBackPressed() {
        //if (floorPlanViewModel.isTableEdited) requestToSave() else finish()
        finish()
    }

    /*private fun requestToSave() {
        MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
            .setMessage(getString(R.string.save_message))
            .setPositiveButton("Save") { _, _ ->
                updateAllTableDetails()
                finish()
            }
            .setNegativeButton(getString(R.string.discard)) { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .show()
    }*/

/*    fun autoCreateTable(tableCount: Int) {
        val (screenWidth, screenHeight) = SharedMethods.getScreenResolution(application)
        var column = 0
        var row = 0
        val tableWidth = (screenWidth - 400) / AppData.totalTableInRow

        try {
            Coroutines.io {
                for (i in 1..tableCount) {
                    Tables().apply {
                        this.name = " TABLE $i"
                        this.seatingCapacity = i
                        this.password = AppData.addTableDefaultPassword
                        this.shape = Shape.tableRectangle
                        this.floorId = tableRepository.getFirstFloorId()
                        this.width = tableWidth
                        this.height = tableWidth
                        this.positionX = 50f + (column * tableWidth)
                        this.positionY = 50f + (tableWidth * row)

                        if (column < AppData.totalTableInRow - 1) {
                            column += 1
                        } else {
                            column = 0
                            row += 1
                        }
                        tableRepository.insertOrUpdateToRealm(this)
                    }
                }
            }
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : autoCreateTable()", error)
        }
    }

    fun autoDeleteTable(view: View) {
        tableRepository.deleteAllDataByField("floorId", currentFloorId)
        initSavedTables()
    }*/
}
