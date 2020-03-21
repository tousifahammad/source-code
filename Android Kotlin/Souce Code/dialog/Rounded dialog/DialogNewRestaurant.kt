package com.webgrity.tisha.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import kotlinx.android.synthetic.main.dialog_new_restaurant.*
import com.webgrity.tisha.R
import com.webgrity.tisha.data.entities.Restaurant
import com.webgrity.tisha.data.repositories.RestaurantRepository
import com.webgrity.tisha.interfaces.DialogNewRestaurantListener
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class DialogNewRestaurant(
    context: Context,
    clickListener: DialogNewRestaurantListener
) : Dialog(context, R.style.RoundedCornersDialog), KodeinAware {

    override val kodein by kodein()
    private val restaurantRepository: RestaurantRepository by instance()

    private var clickListener: DialogNewRestaurantListener? = null


    init {
        setCancelable(true)
        this.clickListener = clickListener;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_new_restaurant)

        initUI()

        initListener()

    }

    private fun initUI() {
    }


    private fun initListener() {
        btn_start.setOnClickListener {
            if (validateEveryField()) {
                val restaurant: Restaurant = Restaurant()
                restaurant.id = 1
                restaurant.name = et_name.text.toString()
                restaurantRepository.addRestaurant(restaurant)
                clickListener?.onStartClicked()
                dismiss()
            }
        }
    }


    private fun validateEveryField(): Boolean {
//        if (et_table_name?.text.isNullOrEmpty()) {
//            Toast.makeText(context, "Please enter table name", Toast.LENGTH_SHORT).show();
//            return false
//        }

        return true
    }
}