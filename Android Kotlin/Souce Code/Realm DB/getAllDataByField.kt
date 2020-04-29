    fun getAllDataByField(fieldName: String, fieldValue: Int): ArrayList<DiscountMenuCategory>? {
        var resdata: ArrayList<DiscountMenuCategory>? = null
        val realm = AppDatabase.getRealmInstance()
        try {
            val alldata: RealmResults<DiscountMenuCategory>? = realm.where(DiscountMenuCategory::class.java).equalTo(fieldName, fieldValue).findAll()
            resdata = realm.copyFromRealm(alldata) as ArrayList<DiscountMenuCategory>?
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }

        return resdata
    }