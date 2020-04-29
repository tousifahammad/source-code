 

    fun isFieldPresent(fieldName: String, value: String): Boolean {
        val realm = AppDatabase.getRealmInstance()
        try {
            val result = realm.where<Floor>()
                .equalTo(fieldName, value)
                .findFirst()
            return result != null
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }
        return false
    }