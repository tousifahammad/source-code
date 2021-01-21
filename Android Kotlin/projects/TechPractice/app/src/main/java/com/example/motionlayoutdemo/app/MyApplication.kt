package com.example.motionlayoutdemo.app

import android.app.Application
import android.content.Intent
import io.realm.Realm
import io.realm.RealmConfiguration
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import java.time.LocalDateTime
import java.util.*

class MyApplication : Application(), KodeinAware {

    companion object {
        var serverRunning: Boolean = false
        var currentNavItem: Int = 0
        var userTypeId: Int = 0
        var loggedInUserId: Int = 0
        var restaurantId = 0
        var userId: Int = 0
        const val adminPassword: String = "123456"
        var loggedInTime: LocalDateTime? = null
        var lastBackup: Date = Date()
        var isBackupAllowed = false
    }

    private var myService: Intent? = null

    override fun onCreate() {
        super.onCreate()

        /*if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this)*/

        Realm.init(this)
        //RealmConfiguration config = new RealmConfiguration.Builder().name("roasteryapp.realm").deleteRealmIfMigrationNeeded().build();
        val config = RealmConfiguration.Builder()
            .name("TechPractice.realm")
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .schemaVersion(29)
            //.migration(RealmMigration())
            .build()
        Realm.setDefaultConfiguration(config)
    }

    override fun onTerminate() {
        Realm.getDefaultInstance().close()
        myService?.let { stopService(it) }
        super.onTerminate()
    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))

        //bind() from singleton { AppDatabase() }
        //bind() from singleton { PreferenceProvider(instance()) }

        //view models
        //bind() from provider { FloorViewModel(kodein) }
        //bind() from provider { OrderFabViewModel(kodein) }

    }

    /*private fun initStethoForRealm() {
        //To initialize Stetho and Stetho-Realm.
        if (!BuildConfig.DEBUG) return

        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(
                    RealmInspectorModulesProvider.builder(this)
                        .withDeleteIfMigrationNeeded(true) //if there is any changes in database schema then rebuild bd.
                        .withMetaTables() //extract table meta data
                        .withLimit(10000) //by default limit of data id 250, but you can increase with this
                        .build()
                )
                .build()
        )
    }*/


}