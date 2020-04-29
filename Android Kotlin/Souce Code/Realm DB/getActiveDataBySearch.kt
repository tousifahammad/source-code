

    fun getActiveDataBySearch(fieldName: String, value: String): ArrayList<MenuItems>? {
        var resdata: ArrayList<MenuItems>? = null
        val realm = AppDatabase.getRealmInstance()
        try {
            val alldata: RealmResults<MenuItems>? = realm
                .where(MenuItems::class.java)
                .equalTo("status", Status.active)
                .contains(fieldName, value).findAll()
            resdata = realm.copyFromRealm(alldata) as ArrayList<MenuItems>?
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }

        return resdata
    }