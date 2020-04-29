 fun getDataById(id: Int): Floor? {
        val realm = AppDatabase.getRealmInstance()
        var table: Floor? = null
        try {
            val mTable: Floor? = realm.where<Floor>().equalTo("id", id).findFirst()
            if (mTable != null) {
                table = realm.copyFromRealm(mTable)
            }
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }
        return table
    }