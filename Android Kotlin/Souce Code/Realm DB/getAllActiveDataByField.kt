    
	fun getAllActiveDataByField(fieldName: String, fieldValue: Int): ArrayList<MenuItemsKOT>? {
        var resdata: ArrayList<MenuItemsKOT>? = null
        val realm = AppDatabase.getRealmInstance()
        try {
            val allData: RealmResults<MenuItemsKOT>? = realm
                .where(MenuItemsKOT::class.java)
                .equalTo(fieldName, fieldValue)
                .and()
                .equalTo("status", Status.active)
                .findAll()
            resdata = realm.copyFromRealm(allData) as ArrayList<MenuItemsKOT>?
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }

        return resdata
    }