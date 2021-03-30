package com.webgrity.tishakds.data.db

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration


class AppDatabase(context: Context) {

    /*init {
        buildDatabase()
    }

    private fun buildDatabase() =
        RealmConfiguration.Builder()
            .name("tishaclient.realm")
            .schemaVersion(1)
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            //.migration(RealmMigration())
            .deleteRealmIfMigrationNeeded()
            .build()*/

    companion object {
        fun getRealmInstance(): Realm = Realm.getDefaultInstance()

        fun closeRealmInstance(realm: Realm) = realm.close()
    }
}