 

    fun getActiveDataById(id: Int): MenuItems? {
        val realm = AppDatabase.getRealmInstance()
        var mMenuItem: MenuItems? = null
        try {
            mMenuItem = realm.where<MenuItems>()
                .equalTo("id", id)
                .and()
                .equalTo("status", Status.active)
                .findFirst()
            if (mMenuItem != null) {
                mMenuItem = realm.copyFromRealm(mMenuItem)
            }
        } catch (error: Exception) {
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }
        return mMenuItem
    }