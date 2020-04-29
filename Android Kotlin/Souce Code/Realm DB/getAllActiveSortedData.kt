
    fun getAllActiveSortedData(): ArrayList<MenuItems>? {
        var resdata: ArrayList<MenuItems>? = null
        val realm = AppDatabase.getRealmInstance()
        try {
            val alldata: RealmResults<MenuItems>? = realm
                .where(MenuItems::class.java)
                .equalTo("status", Status.active)
                .sort("sortby")
                .findAll()
            resdata = realm.copyFromRealm(alldata) as ArrayList<MenuItems>?
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }

        return resdata
    }