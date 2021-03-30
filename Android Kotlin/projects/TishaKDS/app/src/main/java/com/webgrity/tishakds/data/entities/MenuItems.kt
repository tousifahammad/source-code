package com.webgrity.tishakds.data.entities

import androidx.annotation.Keep
import com.webgrity.tishakds.data.constants.Status
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable

@Keep
@RealmClass
open class MenuItems() : RealmObject(), Serializable {

    @PrimaryKey
    var id: Int = 0
    var restaurantId: Int = 0
    var sortBy: Int = 0
    var menuCategoryId: Int = 0
    var image: ByteArray? = null
    var name: String = ""
    var price: Float = 0F
    var quantity: Int = 0
    var status: Int = Status.active
    var description: String = ""
    var shortName: String = ""
    var course: String = ""
    var attributes: String = ""
    var vegetarian: String = ""
    var servePerson: Int = 0
    var glutenFree: String = ""
    var allergens: String = ""
    var preparation: String = ""
    var crossSelling: Boolean = false
    var spicyLevel: String = ""
    var displayIngredients: String = ""
    var itemStatus: Boolean = false
    var salesCategoryId: Int = 0 //unnecessary
    var recipeId: Int = 0  //unnecessary
    var backupFlag: Boolean = false
    var upcBarcode: String? = ""
    var selected: Boolean = false
    var isAttributeEnable: Boolean = false
    var isBarcodeEnable: Boolean = false
    var isRecipeEnable: Boolean = false
    var isAdvanceEnable: Boolean = false
}