    fun getAllData(): ArrayList<Floor>? {
        var resdata: ArrayList<Floor>? = null
        val realm = AppDatabase.getRealmInstance()
        try {
            val alldata: RealmResults<Floor>? = realm.where(Floor::class.java).findAll()
            resdata = realm.copyFromRealm(alldata) as ArrayList<Floor>?
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }

        return resdata
    }