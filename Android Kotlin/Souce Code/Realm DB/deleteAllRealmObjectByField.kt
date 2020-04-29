    fun deleteAllRealmObjectByField(fieldName: String, fieldValue: Int): Boolean {
        val realm = AppDatabase.getRealmInstance()
        try {
            val results: RealmResults<DiscountMenuCategory>? =
                realm.where(DiscountMenuCategory::class.java)
                    .equalTo(fieldName, fieldValue)
                    .findAll()

            realm.executeTransaction {
                results?.deleteAllFromRealm()
            }

            Log.d(AppData.TAG, "All row deleted")
            return true
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }

        return false
    }