 
    fun deleteRealmObjectByID(id: Int): Boolean {
        val realm = AppDatabase.getRealmInstance()
        try {
            val objects: Floor? = realm.where<Floor>().equalTo("id", id).findFirst()
            realm.executeTransaction { objects?.deleteFromRealm() }
            Log.d(AppData.TAG, "One row deleted")
            return true
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }

        return false
    }