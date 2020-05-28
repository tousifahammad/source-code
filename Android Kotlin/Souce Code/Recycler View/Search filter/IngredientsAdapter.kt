package com.webgrity.tisha.ui.fragment.menu.menu_items.manage_recipe

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.webgrity.tisha.R
import com.webgrity.tisha.app.AppData
import com.webgrity.tisha.app.SharedMethods
import com.webgrity.tisha.data.entities.Ingredients
import com.webgrity.tisha.data.models.IngredientMeasurementUnit
import com.webgrity.tisha.data.repositories.IngredientsCategoryRepository
import com.webgrity.tisha.data.repositories.IngredientsRepository
import com.webgrity.tisha.data.repositories.RecipeUnitsRepository
import com.webgrity.tisha.interfaces.ManageRecipeClickListener
import com.webgrity.tisha.ui.dialogs.ManageRecipeDialog
import com.webgrity.tisha.util.toast
import kotlinx.android.synthetic.main.layout_row_ingredient.view.*
import org.json.JSONArray

class IngredientsAdapter(private val context: Context, private val listener: ManageRecipeClickListener) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>(), Filterable {

    private val ingredientsRepository = IngredientsRepository()
    private val ingredientsCategoryRepository = IngredientsCategoryRepository()
    private val recipeUnitsRepository = RecipeUnitsRepository()

    private var ingredientsList: ArrayList<Ingredients> = ArrayList()
    private var jaMeasurementUnit: JSONArray = JSONArray()
    private var ingredientsListFiltered: ArrayList<Ingredients> = ArrayList()


    init {
        getIngredientsList()
        jaMeasurementUnit = JSONArray(SharedMethods.getStringFromJsonFile(context, R.raw.measurement_units))
    }

    private fun getIngredientsList() {
        try {
            ingredientsList = ingredientsRepository.getAllData()!!
            ingredientsListFiltered.addAll(ingredientsList)
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_row_ingredient, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return ingredientsListFiltered.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val ingredient = ingredientsListFiltered[position]
            var categoryName = ingredientsCategoryRepository.getDataById(ingredient.ingredientsCategoryId)!!.name
            categoryName += ", " + ingredient.name
            holder.tv_item1.text = categoryName

            var unitPrice = SharedMethods.getValueByIdFromRawJSON(context, ingredient.ingredientsMeasurementUnitsId, R.raw.measurement_units, "measurementUnit")
            unitPrice += ", " + ingredient.purchaseCost
            holder.tv_quantity.text = unitPrice

            holder.cl_root.setOnClickListener {
                val measurementUnitsList = getMeasurementUnitsList(ingredient.id)
                if (measurementUnitsList.isEmpty())
                    context.toast("No data found related to this ingredient")
                else
                    ManageRecipeDialog(context, ingredient.id, measurementUnitsList, listener).show()
            }
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_item1 = view.tv_item1
        val tv_quantity = view.tv_quantity
        val cl_root = view.cl_root
    }

    private fun getMeasurementUnitsList(ingredientId: Int): ArrayList<IngredientMeasurementUnit> {
        val measurementUnitsList: ArrayList<IngredientMeasurementUnit> = ArrayList()

        try {
            measurementUnitsList.clear()
            recipeUnitsRepository.getDataByIngredientId(ingredientId)!!.forEach {

                IngredientMeasurementUnit().apply {
                    this.recipeUnitsId = it.id
                    this.unit = SharedMethods.getValueByIdFromRawJSON(
                        context,
                        it.ingredientsMeasurementUnitsId,
                        R.raw.measurement_units,
                        "measurementUnit"
                    )!!

                    measurementUnitsList.add(this)
                }
            }
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        }

        return measurementUnitsList
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    ingredientsListFiltered = ingredientsList
                } else {
                    val filteredList: ArrayList<Ingredients> = ArrayList()
                    for (row in ingredientsList) {
                        if (row.name.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    ingredientsListFiltered = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = ingredientsListFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                ingredientsListFiltered = filterResults.values as ArrayList<Ingredients>
                notifyDataSetChanged()
            }
        }
    }
}