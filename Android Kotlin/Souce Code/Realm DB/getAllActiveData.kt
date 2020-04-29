    fun getAllActiveData(): ArrayList<Discount>? {
        var resdata: ArrayList<Discount>? = null
        val realm = AppDatabase.getRealmInstance()
        try {
            val alldata: RealmResults<Discount>? = realm
                .where(Discount::class.java)
                .equalTo("status", Status.active)
                .findAll()
            resdata = realm.copyFromRealm(alldata) as ArrayList<Discount>?
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }

        return resdata
    }