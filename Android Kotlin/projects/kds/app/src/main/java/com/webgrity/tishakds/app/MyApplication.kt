package com.webgrity.tishakds.app

import android.app.Application
import com.webgrity.tishakds.data.db.AppDatabase
import com.webgrity.tishakds.data.db.RealmMigration
import com.webgrity.tishakds.data.preferences.PreferenceProvider
import com.webgrity.tishakds.data.repository.*
import io.realm.Realm
import io.realm.RealmConfiguration
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class MyApplication : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .name("tishakds.realm")
                .allowWritesOnUiThread(true)
                .allowQueriesOnUiThread(true)
                .schemaVersion(26)
                .migration(RealmMigration())
                .build()
        Realm.setDefaultConfiguration(config)
    }

    companion object {
        var type = 0
        var restaurantId = 0
        var restaurantName = ""
        var tableId = 0
        var staffId = 0
        var staffname = ""
        var textColor = -16744705
        var statusBarColor = -16744705
    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))

        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        //repository
        bind() from singleton { AuthRepository() }
    }
}