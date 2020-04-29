   
   
   fun isActiveFieldPresent(fieldName: String, fieldValue: String): Boolean {
        val realm = AppDatabase.getRealmInstance()
        try {
            val result = realm.where<Discount>()
                .beginGroup()
                .equalTo(fieldName, fieldValue)
                .or()
                .equalTo("status", Status.active)
                .endGroup()
                .findFirst()
            return result != null
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }
        return false
    }