fun insertOrUpdateToRealm(res: Floor): Int {
        var id: Int = 0
        val realm = AppDatabase.getRealmInstance()
        try {
            realm.executeTransaction { realm ->
                val rest: Floor? = getDataById(res.id)
                if (rest == null) {
                    val results: RealmResults<Floor> =
                        realm.where(Floor::class.java).findAll()
                    id = if (results.size == 0) 1 else {
                        Objects.requireNonNull<Number>(
                            realm.where(Floor::class.java).max("id")
                        ).toInt() + 1
                    }
                    res.id = id
                }
                realm.insertOrUpdate(res)
                Log.d(AppData.TAG, "One row inserted")
            }
        } catch (error: Exception) {
            id = 0
            Log.d(AppData.TAG, "Error : ${error.printStackTrace()}")
        } finally {
            realm.close()
        }
        return id
    }