package com.webgrity.tishakds.utilities.extensions


import com.webgrity.tishakds.app.MyApplication
import com.webgrity.tishakds.data.constants.Status
import com.webgrity.tishakds.data.db.RealmLiveData
import com.webgrity.tishakds.data.entities.Restaurant
import com.webgrity.tishakds.utilities.Coroutines
import io.realm.*
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList


fun <T : RealmModel> RealmResults<T>.asLiveData() = RealmLiveData<T>(this)

/**Realm Query Extension*/
/*fun <T : RealmModel> get(ofType: Class<T>, realm: Realm): List<T>? {
    return realm.where(ofType).equalTo("restaurantId", MyApplication.restaurantId)
        .and()
        .equalTo("backupFlag", false).findAll()
}*/
/**Realm Default Instance Extension*/
/*inline val mRealm: Realm
    get() = Realm.getDefaultInstance()*/


/**
 * Realm Utility extension for modifying database. Create a transaction, run the function passed as argument,
 * commit transaction and close realm instance.
 */
fun Realm.transaction(action: (Realm) -> Unit) {
    use {
        if (!isInTransaction) {
            executeTransaction { action(this) }
        } else {
            action(this)
        }
    }
}

fun Realm.closeRealmMain() {
    Coroutines.main {
        if (!isClosed) close()
    }
}

suspend inline fun Realm.getActiveRestaurant(): Restaurant? {
    return withContext(Dispatchers.Main) {
        val item = where<Restaurant>()
            .equalTo("id", MyApplication.restaurantId)
            .equalTo("status", Status.activeRestaurant)
            .findFirstAsync()
        item.load()
        if (item.isLoaded && item.isValid) copyFromRealm(item) else null
    }
}

/**Realm Query Extension for insert Or update data by param*/
/*suspend inline fun <reified T : RealmObject> Realm.insertOrUpdateExt(item: T): Int{
    return withContext(Dispatchers.IO) {
        var id = 0
        try {
            executeTransaction { realmTransaction ->
                val data: T? = getFirst<T>(0)
                data?.let {
                    val result: MutableList<T>? = getAllData<T>()
                    id = if (result?.size == 0) 1 else {
                        Objects.requireNonNull<Number>(getMaxCount<T>()).toInt() + 1
                    }
                }
//                val rest: PaymentDetails? = realmTransaction.where<PaymentDetails>().equalTo("id", res.id).findFirst()
//                if (rest == null && res.id == 0) {
//                    val results: RealmResults<PaymentDetails> =
//                        realmTransaction.where(PaymentDetails::class.java).findAll()
//                    id = if (results.size == 0) 1 else {
//                        Objects.requireNonNull<Number>(
//                            realmTransaction.where(PaymentDetails::class.java).max("id")
//                        ).toInt() + 1
//                    }
//                    res.id = id
//                }
//                if (res.restaurantId == 0) res.restaurantId = MyApplication.restaurantId
//                res.backupFlag = backupFlag
//                realmTransaction.insertOrUpdate(res)
//                Log.d(AppData.TAG, "One row inserted")
            }
        } catch (error: Exception) {
            id = 0
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        }
        id
    }
}*/
/**Realm Query Extension for get Max Count*/
inline fun <reified T : RealmObject> Realm.getMaxCount(): Number? = where<T>().max("id")

/**Realm Query Extension for find all data*/
suspend inline fun <reified T : RealmObject> Realm.getAllData(): MutableList<T>? =
    withContext(Dispatchers.Main) { where<T>().findAllAsync() }

/**Realm Query Extension for get all data with restaurantId*/
inline fun <reified T : RealmObject> Realm.getAll(): List<T>? =
    where<T>().equalTo("restaurantId", MyApplication.restaurantId).findAll()

/**Realm Query Extension for find all data by param*/
inline fun <reified T : RealmObject> Realm.getAllByParam(fieldName: String, value: Int): List<T>? =
    where<T>().equalTo("restaurantId", MyApplication.restaurantId).equalTo(fieldName, value)
        .findAll()

/**Realm Query Extension for find all Active data with restaurantId*/
inline fun <reified T : RealmObject> getActiveAll(realm: Realm): List<T>? =
    realm.where<T>().equalTo("restaurantId", MyApplication.restaurantId).and()
        .equalTo("status", Status.active).findAll()

/**Realm Query Extension for find all Active data with restaurantId and param*/
inline fun <reified T : RealmObject> Realm.getAllActiveByParam(
    fieldName: String,
    value: Int
): List<T>? =
    where<T>().equalTo("restaurantId", MyApplication.restaurantId).equalTo(fieldName, value).and()
        .equalTo("status", Status.active).findAll()

suspend inline fun <reified T : MutableList<RealmResults<*>>> Realm.deleteDataFromRealm(results: T) {
    withContext(Dispatchers.Main) {
        this@deleteDataFromRealm.executeTransaction { trans ->
            results.forEach {
                it.deleteAllFromRealm()
            }
        }
    }
}

/** Warning: it delete the RealmObject not status.
 * Realm Query Extension for delete RealmObject by id
 */
suspend inline fun <reified T : RealmObject> Realm.deleteById(id: Int, restaurantId: Int = 0) {
    withContext(Dispatchers.Main) {
        try {
            executeTransaction {
                val item = where<T>()
                    .equalTo(
                        "restaurantId",
                        if (restaurantId == 0) MyApplication.restaurantId else restaurantId
                    )
                    .equalTo("id", id)
                    .findFirstAsync()
                item.load()
                if (item.isLoaded && item.isValid) {
                    item.deleteFromRealm()
                }
            }
        } catch (error: Exception) {
            error.printStackTrace()
        }
    }
}

/**Realm Query Extension for delete all async data with fieldName and fieldValue*/
suspend inline fun <reified T : RealmObject> Realm.deleteAllByParamAsync(
    fieldName: String,
    fieldValue: Int
): Boolean {
    return withContext(Dispatchers.Main) {
        try {
            executeTransaction {
                val result = where<T>()
                    .equalTo("restaurantId", MyApplication.restaurantId)
                    .equalTo(fieldName, fieldValue)
                    .findAllAsync()
                result.load()
                result.deleteAllFromRealm()
            }
        } catch (error: Exception) {
            error.printStackTrace()
        }
        false
    }
}

/**Realm Query Extension for Search find all data*/
inline fun <reified T : RealmObject> Realm.getSearchAllActiveByParam(
    fieldName: String,
    value: String
): MutableList<T>? =
    where<T>().equalTo("restaurantId", MyApplication.restaurantId)
        .contains(fieldName, value, Case.INSENSITIVE)
        .and()
        .equalTo("status", Status.active)
        .findAll()

/**Realm Query Extension for find all async data with restaurantId*/
suspend inline fun <reified T : RealmObject> Realm.getAllAsync(): MutableList<T>? {
    return withContext(Dispatchers.Main) {
        val result = where<T>().equalTo("restaurantId", MyApplication.restaurantId).findAllAsync()
        copyFromRealm(result) as MutableList<T>
    }
}

/**Realm Query Extension for find all Active async data with restaurantId*/
suspend inline fun <reified T : RealmObject> Realm.getActiveAllAsync(): MutableList<T>? {
    return withContext(Dispatchers.Main) {
        val result = where<T>().equalTo("restaurantId", MyApplication.restaurantId)
            .and()
            .equalTo("status", Status.active).findAllAsync()
        copyFromRealm(result)
    }
}

inline fun <reified T : RealmObject> Realm.getAllJunkData(restaurantId: Int? = null): RealmResults<T>? {
    val result = where<T>().equalTo("restaurantId", restaurantId ?: MyApplication.restaurantId)
        .equalTo("status", Status.deleted)
        .equalTo("backupFlag", true)
        .findAllAsync()
    result.load()
    return result
}

suspend inline fun <reified T : RealmObject> Realm.getAllActiveAsyncSingleParam(
    fieldName: String,
    fieldValue: Int
): RealmResults<T>? {
    return where<T>().equalTo("restaurantId", MyApplication.restaurantId)
        .equalTo(fieldName, fieldValue)
        .findAllAsync()
}

/**Realm Query Extension for find all Active async data with restaurantId and param*/
suspend inline fun <reified T : RealmObject> Realm.getAllActiveAsyncParam(
    fieldName: String,
    fieldValue: Int
): ArrayList<T>? {
    return withContext(Dispatchers.Main) {
        val result = where<T>().equalTo("restaurantId", MyApplication.restaurantId)
            .equalTo(fieldName, fieldValue)
            .and()
            .equalTo("status", Status.active).findAllAsync()
        copyFromRealm(result) as ArrayList<T>
    }
}

/**Realm Query Extension for find all Active Greater than async data with restaurantId and param*/
suspend inline fun <reified T : RealmObject> Realm.getAllActiveGreaterThanZeroAsyncParam(
    fieldName: String,
    fieldValue: Int,
    fieldName2: String
): ArrayList<T>? {
    return withContext(Dispatchers.Main) {
        val result = where<T>().equalTo("restaurantId", MyApplication.restaurantId)
            .equalTo(fieldName, fieldValue)
            .greaterThan(fieldName2, 0)
            .and()
            .equalTo("status", Status.active).findAllAsync()
        copyFromRealm(result) as ArrayList<T>
    }
}

/**Realm Query Extension for find all async data with restaurantId and param*/
suspend inline fun <reified T : RealmObject> Realm.getAllAsyncParam(
    fieldName: String,
    fieldValue: Int
): ArrayList<T>? {
    return withContext(Dispatchers.Main) {
        val result = where<T>().equalTo("restaurantId", MyApplication.restaurantId)
            .equalTo(fieldName, fieldValue).findAllAsync()
        copyFromRealm(result) as ArrayList<T>
    }
}

suspend inline fun <reified T : RealmObject> Realm.getAllActiveAsyncSearch(
    fieldName: String,
    fieldValue: String
): MutableList<T>? {
    return withContext(Dispatchers.Main) {
        val result = where<T>().equalTo("restaurantId", MyApplication.restaurantId)
            .equalTo("status", Status.active)
            .contains(fieldName, fieldValue, Case.INSENSITIVE).findAllAsync()
        copyFromRealm(result)
    }
}

suspend inline fun <reified T : RealmObject> Realm.getAllActiveAsyncBooleanParam(
    fieldName: String,
    fieldValue: Boolean
): ArrayList<T>? {
    return withContext(Dispatchers.Main) {
        val result = where<T>().equalTo("restaurantId", MyApplication.restaurantId)
            .equalTo(fieldName, fieldValue)
            .and()
            .equalTo("status", Status.active)
            .findAllAsync()
        copyFromRealm(result) as ArrayList<T>
    }
}

suspend inline fun <reified T : RealmObject> Realm.getAllActiveAsyncStringParam(
    fieldName: String,
    fieldValue: String
): MutableList<T>? {
    return withContext(Dispatchers.Main) {
        val result = where<T>().equalTo("restaurantId", MyApplication.restaurantId)
            .equalTo(fieldName, fieldValue)
            .and()
            .equalTo("status", Status.active)
            .findAllAsync()
        copyFromRealm(result)
    }
}

/**Realm Query Extension for find all Active async data By Multi Param with boolean using restaurantId*/
suspend inline fun <reified T : RealmObject> Realm.getAllActiveAsyncMultiBoolParam(
    fieldName: String,
    fieldValue: Int,
    fieldName2: String,
    fieldValue2: Boolean
): ArrayList<T>? {
    return withContext(Dispatchers.Main) {
        val result = where<T>().equalTo("restaurantId", MyApplication.restaurantId)
            .equalTo(fieldName, fieldValue)
            .and()
            .equalTo(fieldName2, fieldValue2)
            .and()
            .equalTo("status", Status.active)
            .findAllAsync()
        copyFromRealm(result) as ArrayList<T>
    }
}

/**Realm Query Extension for find all Active async data By Multi Param with restaurantId*/
suspend inline fun <reified T : RealmObject> Realm.getAllActiveAsyncByMultiParam(
    fieldName: String,
    fieldValue: Int,
    fieldName2: String,
    fieldValue2: Int
): ArrayList<T>? {
    return withContext(Dispatchers.Main) {
        val result = where<T>().equalTo("restaurantId", MyApplication.restaurantId)
            .equalTo(fieldName, fieldValue)
            .and()
            .equalTo(fieldName2, fieldValue2)
            .and()
            .equalTo("status", Status.active)
            .findAllAsync()
        copyFromRealm(result) as ArrayList<T>
    }
}

/**Realm Query Extension for get first Active Async data by Third param with restaurantId*/
suspend inline fun <reified T : RealmObject> Realm.getFirstActiveAsyncByThirdMultiParam(
    fieldName1: String,
    value1: Int,
    fieldName2: String,
    value2: Int,
    fieldName3: String,
    fieldValue3: Int,
    restaurantId: Int = 0
): T? {
    return withContext(Dispatchers.Main) {
        val item = where<T>().equalTo(
            "restaurantId",
            if (restaurantId == 0) MyApplication.restaurantId else restaurantId
        )
            .equalTo(fieldName1, value1)
            .equalTo(fieldName2, value2)
            .equalTo(fieldName3, fieldValue3)
            .and()
            .equalTo("status", Status.active)
            .findFirstAsync()
        item.load()
        if (item.isLoaded && item.isValid) copyFromRealm(item) else null
    }
}

/**Realm Query Extension for find all async data By Multi Param with restaurantId*/
suspend inline fun <reified T : RealmObject> Realm.getAllAsyncByMultiParam(
    fieldName: String,
    fieldValue: Int,
    fieldName2: String,
    fieldValue2: Int
): ArrayList<T>? {
    return withContext(Dispatchers.Main) {
        val result = where<T>().equalTo("restaurantId", MyApplication.restaurantId)
            .equalTo(fieldName, fieldValue)
            .and()
            .equalTo(fieldName2, fieldValue2).findAllAsync()
        copyFromRealm(result) as ArrayList<T>
    }
}

/**Realm Query Extension for get All isSynced data*/
inline fun <reified T : RealmObject> Realm.isSyncedData(): List<T>? =
    where<T>().equalTo("restaurantId", MyApplication.restaurantId)
        .and()
        .equalTo("backupFlag", false).findAll()

/**Realm Query Extension for get All Async isSynced data with restaurantId*/
suspend inline fun <reified T : RealmObject> Realm.isSyncedDataAsync(): List<T>? {
    return withContext(Dispatchers.Main) {
        val result = where<T>().equalTo("restaurantId", MyApplication.restaurantId)
            .and()
            .equalTo("backupFlag", false).findAllAsync()
        copyFromRealm(result)
    }
}

/**Realm Query Extension for get All Async isSynced data and return Boolean*/
suspend inline fun <reified T : RealmObject> Realm.isSyncedDataAsyncBoolean(): Boolean {
    return withContext(Dispatchers.Main) {
        val result = where<T>().equalTo("restaurantId", MyApplication.restaurantId)
            .and()
            .equalTo("backupFlag", false).findAllAsync()
        copyFromRealm(result).isNotEmpty()
    }
}

/**Realm Query Extension for field present taking multi Param  and return Boolean*/
suspend inline fun <reified T : RealmObject> Realm.isFieldPresent(
    fieldName: String,
    fieldValue: Int,
    fieldName2: String,
    fieldValue2: String,
    id: Int = 0
): Boolean {
    return withContext(Dispatchers.Main) {
        var exist = false
        val result = if (id == 0)
            where<T>()
                .equalTo("restaurantId", MyApplication.restaurantId)
                .and()
                .equalTo(fieldName, fieldValue)
                .and()
                .equalTo(fieldName2, fieldValue2)
                .and()
                .equalTo("status", Status.active)
                .findFirstAsync()
        else
            where<T>()
                .equalTo("restaurantId", MyApplication.restaurantId)
                .and()
                .equalTo(fieldName, fieldValue)
                .and()
                .equalTo(fieldName2, fieldValue2)
                .and()
                .equalTo("status", Status.active)
                .notEqualTo("id", id)
                .findFirstAsync()
        result.load()
        if (result.isLoaded && result.isValid) exist = true
        exist
    }
}

/**Realm Query Extension for field present taking single Param  and item id return Boolean*/
suspend inline fun <reified T : RealmObject> Realm.isFieldPresentBySingleFieldAndid(
    fieldName: String,
    fieldValue: String,
    id: Int = 0
): Boolean {
    return withContext(Dispatchers.Main) {
        var exist = false
        val result = if (id == 0)
            where<T>()
                .equalTo("restaurantId", MyApplication.restaurantId)
                .and()
                .equalTo(fieldName, fieldValue)
                .and()
                .equalTo("status", Status.active)
                .findFirstAsync()
        else
            where<T>()
                .equalTo("restaurantId", MyApplication.restaurantId)
                .and()
                .equalTo(fieldName, fieldValue)
                .and()
                .equalTo("status", Status.active)
                .notEqualTo("id", id)
                .findFirstAsync()
        result.load()
        if (result.isLoaded && result.isValid) exist = true
        exist
    }
}

/**Realm Query Extension for get All Async isSynced Restaurant data and return Boolean*/
suspend inline fun <reified T : RealmObject> Realm.isSyncedRestaurantDataAsyncBoolean(): Boolean {
    return withContext(Dispatchers.Main) {
        val result = where<T>().equalTo("id", MyApplication.restaurantId)
            .and()
            .equalTo("backupFlag", false).findAllAsync()
        copyFromRealm(result).isNotEmpty()
    }
}

/**Realm Query Extension for get first data by id*/
inline fun <reified T : RealmObject> Realm.getFirst(id: Int): T? =
    where<T>().equalTo("id", id).findFirst()

/**Realm Query Extension for get first data by param*/
inline fun <reified T : RealmObject> Realm.getFirstByParam(fieldName: String, value: Int): T? =
    where<T>().equalTo(fieldName, value).findFirst()

/**Realm Query Extension for get restaurant's first data*/
inline fun <reified T : RealmObject> Realm.getFirstData(): T? =
    where<T>().equalTo("restaurantId", MyApplication.restaurantId).findFirst()

/**Realm Query Extension for get first data by param with restaurantId*/
inline fun <reified T : RealmObject> Realm.getFirstDataByParam(fieldName: String, value: Int): T? =
    where<T>().equalTo("restaurantId", MyApplication.restaurantId).equalTo(fieldName, value)
        .findFirst()

/**Realm Query Extension for get first Async data by id*/
/*inline fun <reified T : RealmModel> Realm.getFirstAsync(fieldName1: String, value1: Int, restaurantId: Int = 0): T? {
    val item = where<T>().equalTo("restaurantId", if (restaurantId == 0) MyApplication.restaurantId else restaurantId)
        .and()
        .equalTo(fieldName1, value1).findFirstAsync()
    item.load()
    return if(item.isLoaded() && item.isValid()) copyFromRealm(item) else null
}*/

/**Realm Query Extension for get find first Async data by id*/
suspend inline fun <reified T : RealmObject> Realm.getFindFirstAsync(id: Int): T? {
    return withContext(Dispatchers.Main) {
        val item = where<T>().equalTo("id", id).findFirstAsync()
        item.load()
        if (item.isLoaded && item.isValid) copyFromRealm(item) else null
    }
}

suspend inline fun <reified T : RealmObject> Realm.getFindFirstActiveAsync(id: Int): T? {
    return withContext(Dispatchers.Main) {
        val item = where<T>().equalTo("id", id).equalTo("status", Status.active).findFirstAsync()
        item.load()
        if (item.isLoaded && item.isValid) copyFromRealm(item) else null
    }
}

/**Realm Query Extension for get first Async data by restaurantId*/
suspend inline fun <reified T : RealmObject> Realm.getFirstAsync(restaurantId: Int = 0): T? {
    return withContext(Dispatchers.Main) {
        val item = where<T>().equalTo(
            "restaurantId",
            if (restaurantId == 0) MyApplication.restaurantId else restaurantId
        )
            .findFirstAsync()
        item.load()
        if (item.isLoaded && item.isValid) copyFromRealm(item) else null
    }
}

/**Realm Query Extension for get first Async data by field with restaurantId*/
suspend inline fun <reified T : RealmObject> Realm.getFirstAsyncByField(
    fieldName1: String,
    value1: Int,
    restaurantId: Int = 0
): T? {
    return withContext(Dispatchers.Main) {
        val item = where<T>().equalTo(
            "restaurantId",
            if (restaurantId == 0) MyApplication.restaurantId else restaurantId
        )
            .and()
            .equalTo(fieldName1, value1).findFirstAsync()
        item.load()
        if (item.isLoaded && item.isValid) copyFromRealm(item) else null
    }
}

/**Realm Query Extension for get first Async data by Third param with restaurantId*/
suspend inline fun <reified T : RealmObject> Realm.getFirstAsyncByThirdMultiParam(
    fieldName1: String,
    value1: Int,
    fieldName2: String,
    value2: Int,
    fieldName3: String,
    fieldValue3: Int,
    restaurantId: Int = 0
): T? {
    return withContext(Dispatchers.Main) {
        val item = where<T>().equalTo(
            "restaurantId",
            if (restaurantId == 0) MyApplication.restaurantId else restaurantId
        )
            .equalTo(fieldName1, value1)
            .equalTo(fieldName2, value2)
            .and()
            .equalTo(fieldName3, fieldValue3)
            .findFirstAsync()
        item.load()
        if (item.isLoaded && item.isValid) copyFromRealm(item) else null
    }
}

suspend inline fun <reified T : RealmObject> Realm.getFirstAsyncByActiveIntField(
    fieldName1: String,
    value1: Int,
    restaurantId: Int = 0
): T? {
    return withContext(Dispatchers.Main) {
        val item = where<T>().equalTo(
            "restaurantId",
            if (restaurantId == 0) MyApplication.restaurantId else restaurantId
        )
            .and()
            .equalTo(fieldName1, value1)
            .and()
            .equalTo("status", Status.active)
            .findFirstAsync()
        item.load()
        if (item.isLoaded && item.isValid) copyFromRealm(item) else null
    }
}

suspend inline fun <reified T : RealmObject> Realm.getMaxdate(
    fieldName1: String,
    value1: Int,
    restaurantId: Int = 0
): Date? {
    return withContext(Dispatchers.Main) {
        where<T>().equalTo(
            "restaurantId",
            if (restaurantId == 0) MyApplication.restaurantId else restaurantId
        )
            .equalTo(fieldName1, value1)
            .maximumDate("date")
    }
}

suspend inline fun <reified T : RealmObject> Realm.getFirstAsyncByActiveField(
    fieldName1: String,
    value1: String,
    restaurantId: Int = 0
): T? {
    return withContext(Dispatchers.Main) {
        val item = where<T>().equalTo(
            "restaurantId",
            if (restaurantId == 0) MyApplication.restaurantId else restaurantId
        )
            .and()
            .equalTo(fieldName1, value1)
            .and()
            .equalTo("status", Status.active)
            .findFirstAsync()
        item.load()
        if (item.isLoaded && item.isValid) copyFromRealm(item) else null
    }
}

suspend inline fun <reified T : RealmObject> Realm.getFirstAsyncByCaseActiveField(
    fieldName1: String,
    value1: String,
    case: Case,
    restaurantId: Int = 0
): T? {
    return withContext(Dispatchers.Main) {
        val item = where<T>().equalTo(
            "restaurantId",
            if (restaurantId == 0) MyApplication.restaurantId else restaurantId
        )
            .and()
            .equalTo(fieldName1, value1, case)
            .and()
            .equalTo("status", Status.active)
            .findFirstAsync()
        item.load()
        if (item.isLoaded && item.isValid) copyFromRealm(item) else null
    }
}

suspend inline fun <reified T : RealmObject> Realm.getFirstAsyncByField(
    fieldName1: String,
    value1: String,
    restaurantId: Int = 0
): T? {
    return withContext(Dispatchers.Main) {
        val item = where<T>().equalTo(
            "restaurantId",
            if (restaurantId == 0) MyApplication.restaurantId else restaurantId
        )
            .and()
            .equalTo(fieldName1, value1)
            .findFirstAsync()
        item.load()
        if (item.isLoaded && item.isValid) copyFromRealm(item) else null
    }
}

suspend inline fun <reified T : RealmObject> Realm.getFirstAsyncByActiveDateField(
    fieldName1: String,
    value1: Int,
    fieldName2: String,
    value2: Date,
    restaurantId: Int = 0
): T? {
    return withContext(Dispatchers.Main) {
        val item = where<T>().equalTo(
            "restaurantId",
            if (restaurantId == 0) MyApplication.restaurantId else restaurantId
        )
            .and()
            .equalTo(fieldName1, value1)
            .and()
            .equalTo(fieldName2, value2)
            .and()
            .equalTo("status", Status.active)
            .findFirstAsync()
        item.load()
        if (item.isLoaded && item.isValid) copyFromRealm(item) else null
    }
}

/**Realm Query Extension for get first active Async data by multi Int param with restaurantId*/
suspend inline fun <reified T : RealmObject> Realm.getFirstAsyncByActiveMultiIntParam(
    fieldName1: String,
    value1: Int,
    fieldName2: String,
    value2: Int,
    restaurantId: Int = 0
): T? {
    return withContext(Dispatchers.Main) {
        val item = where<T>().equalTo(
            "restaurantId",
            if (restaurantId == 0) MyApplication.restaurantId else restaurantId
        )
            .equalTo(fieldName1, value1)
            .and()
            .equalTo(fieldName2, value2)
            .and()
            .equalTo("status", Status.active)
            .findFirstAsync()
        item.load()
        if (item.isLoaded && item.isValid) copyFromRealm(item) else null
    }
}

suspend inline fun <reified T : RealmObject> Realm.getFirstAsyncByActiveMultiStringParam(
    fieldName1: String,
    value1: Int,
    fieldName2: String,
    value2: String,
    restaurantId: Int = 0
): T? {
    return withContext(Dispatchers.Main) {
        val item = where<T>().equalTo(
            "restaurantId",
            if (restaurantId == 0) MyApplication.restaurantId else restaurantId
        )
            .equalTo(fieldName1, value1)
            .and()
            .equalTo(fieldName2, value2)
            .and()
            .equalTo("status", Status.active)
            .findFirstAsync()
        item.load()
        if (item.isLoaded && item.isValid) copyFromRealm(item) else null
    }
}

suspend inline fun <reified T : RealmObject> Realm.getFirstAsyncBySingleField(
    fieldName1: String,
    value1: Int
): T? {
//    val item = where<T>()
//        .equalTo(fieldName1, value1).findFirstAsync()
//    item.load()
//    return if (item.isLoaded && item.isValid) copyFromRealm(item) else null
    return withContext(Dispatchers.Main) {
        val item = where<T>()
            .equalTo(fieldName1, value1).findFirstAsync()
        item.load()
        if (item.isLoaded && item.isValid) copyFromRealm(item) else null
    }
}

suspend inline fun <reified T : RealmObject> Realm.findFirstActiveByBoolParam(
    fieldName: String,
    bool: Boolean,
    restaurantId: Int = 0
): T? {
    return withContext(Dispatchers.Main) {
        val item = where<T>().equalTo(
            "restaurantId",
            if (restaurantId == 0) MyApplication.restaurantId else restaurantId
        )
            .equalTo(fieldName, bool)
            .and()
            .equalTo("status", Status.active).findFirstAsync()
        item.load()
        if (item.isLoaded && item.isValid) copyFromRealm(item) else null
    }
}

/**Realm Query Extension for get first Async data by multi param with restaurantId*/
suspend inline fun <reified T : RealmObject> Realm.getFirstAsyncByMultiParam(
    fieldName1: String,
    value1: Int,
    fieldName2: String,
    value2: Int,
    restaurantId: Int = 0
): T? {
    return withContext(Dispatchers.Main) {
        val item = where<T>().equalTo(
            "restaurantId",
            if (restaurantId == 0) MyApplication.restaurantId else restaurantId
        )
            .equalTo(fieldName1, value1)
            .and()
            .equalTo(fieldName2, value2).findFirstAsync()
        item.load()
        if (item.isLoaded && item.isValid) copyFromRealm(item) else null
    }
}


/**Realm Query Extension for find all active future data with today date*/
suspend inline fun <reified T : RealmObject> Realm.isFieldPresentBySingleFieldAndids(
    fieldName: String,
    value1: Date,
    value2: Date,
    id: Int = 0
): Boolean {
    return withContext(Dispatchers.Main) {
        var exist = false
        val result = if (id == 0)
            where<T>()
                .equalTo("restaurantId", MyApplication.restaurantId)
                .and()
                .between(fieldName, value1, value2)
                .and()
                .equalTo("status", Status.active)
                .findFirstAsync()
        else
            where<T>()
                .equalTo("restaurantId", MyApplication.restaurantId)
                .and()
                .between("bookingDate", value1, value2)
                .and()
                .equalTo("status", Status.active)
                .notEqualTo("id", id)
                .findFirstAsync()
        result.load()
        if (result.isLoaded && result.isValid) {

            exist = true
        }
        exist
    }
}

/**Realm Query Extension for find all active future data with today date*/
suspend inline fun <reified T : RealmObject> Realm.getBookingDataBybetweeentime(
    fieldName: String,
    value1: Date,
    value2: Date,
    fieldName2: String,
    fieldValue: String,
    id: Int = 0,
    restaurntId: Int = 0
): MutableList<T>? {
    return withContext(Dispatchers.Main) {
        val result = where<T>().equalTo(
            "restaurantId",
            if (restaurntId == 0) MyApplication.restaurantId else restaurntId
        )
            .notEqualTo("id", id)
            .between(fieldName, value1, value2)
            .equalTo(fieldName2, fieldValue)
            .equalTo("status", Status.active)
            .findAllAsync()
        copyFromRealm(result) as MutableList<T>
    }
}


/**Realm Query Extension for find all active Table excluding obstacle*/
suspend inline fun <reified T : RealmObject> Realm.getAllActiveTableAsyncParam(
    fieldName: String,
    fieldValue: Int
): ArrayList<T>? {
    return withContext(Dispatchers.Main) {
        val result = where<T>().equalTo("restaurantId", MyApplication.restaurantId)
            .notEqualTo(fieldName, fieldValue)
            .equalTo("status", Status.active).findAllAsync()
        copyFromRealm(result) as ArrayList<T>
    }
}
