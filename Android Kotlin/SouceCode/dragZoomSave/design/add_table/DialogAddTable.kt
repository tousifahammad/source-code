package net.app.mvvmsampleapp.floor.design.add_table

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.dialog_add_table.*
import net.app.mvvmsampleapp.R
import net.app.mvvmsampleapp.app.AppData
import java.util.*

class DialogAddTable(
    context: Context,
    add_table_listener: AddTableListener,
    private val lastClickedTableId: String
) :
    Dialog(context) {
    private var add_table_listener: AddTableListener? = null
    private var table_id = ""
    private var table_type = ""
    private var table_size = 1
    private val default_table_width = 150
    private val default_table_height = 150


    init {
        setCancelable(true)
        this.add_table_listener = add_table_listener;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_add_table)

        initUI()

        initListener()

        initTableProperties()
    }

    private fun initUI() {
    }

    private fun initTableProperties() {
        if (lastClickedTableId.isNotEmpty()) {
            try {
                val realm = Realm.getDefaultInstance()
                val table: Table =
                    realm.where<Table>().equalTo("id", lastClickedTableId).findFirst()!!

                table_id = table.id
                table_type = table.shape
                table_size = ((table.width - default_table_width) / 4).toInt()

                et_table_name.setText(table.number)
                tv_selected_shape.text = "Shape : ${table_type}"
                tv_size.text = "Size : ${table_size}%"
                sb_table_size.progress = table_size

                btn_add.setText("update table")

            } catch (error: Exception) {
                Log.d("1111", "Error : ${error.printStackTrace()}")
            }
        }
    }

    private fun initListener() {
        btn_add?.setOnClickListener {
            if (validateEveryField()) {
                if (table_id.isEmpty()) { //create
                    addTableToDB()
                } else { //update
                    updateTable()
                }

                add_table_listener?.onAddButtonClickedInDialoge()
                dismiss()
            }
        }

        iv_table_square?.setOnClickListener {
            table_type = "Square"
            tv_selected_shape?.text = "Shape : ${table_type}"
        }

        iv_table_circle?.setOnClickListener {
            table_type = "Circle"
            tv_selected_shape?.text = "Shape : ${table_type}"
        }

        iv_table_vertical?.setOnClickListener {
            table_type = "Rectangle vertical"
            tv_selected_shape?.text = "Shape : ${table_type}"
        }

        iv_table_horizontal?.setOnClickListener {
            table_type = "Rectangle horizontal"
            tv_selected_shape?.text = "Shape : ${table_type}"
        }

        sb_table_size?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
                tv_size?.text = "Size : ${progress}%"
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                table_size = seek.progress * 4
            }
        })
    }


    private fun validateEveryField(): Boolean {
        if (et_table_name?.text.isNullOrEmpty()) {
            Toast.makeText(context, "Please enter table name", Toast.LENGTH_SHORT).show();
            return false
        }

        return true
    }


    private fun addTableToDB() {
        val realm = Realm.getDefaultInstance()
        try {
            realm?.beginTransaction()

            val table: Table = realm!!.createObject(Table::class.java, UUID.randomUUID().toString())
            table.number = et_table_name?.text.toString()
            table.shape = table_type
            table.width = default_table_width + table_size
            table.height = default_table_height + table_size
            table.position_x = 0f
            table.position_y = 0f

            realm.commitTransaction()

        } catch (error: Exception) {
            Log.d("1111", "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }
    }


    private fun updateTable() {
        val realm = Realm.getDefaultInstance()
        try {
            realm?.executeTransaction { realm ->
                val table: Table = realm.where<Table>().equalTo("id", table_id).findFirst()!!
                table.number = et_table_name?.text.toString()
                table.shape = table_type
                table.width = default_table_width + table_size
                table.height = default_table_height + table_size
            }
        } catch (error: Exception) {
            Log.d("1111", "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }
    }
}