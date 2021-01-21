package com.example.motionlayoutdemo.data.db

import androidx.lifecycle.LiveData
import io.realm.RealmChangeListener
import io.realm.RealmModel
import io.realm.RealmResults

class RealmLiveData<T : RealmModel>(realmResults: RealmResults<T>) : LiveData<RealmResults<T>>() {

    private val listener = RealmChangeListener<RealmResults<T>> { results ->
        value = results
    }

    init {
        if (realmResults.isValid) {
            realmResults.addChangeListener(listener)
        }
    }

    /*override fun onActive() {
        logD("added ChangeListener")
        realmResults.addChangeListener(listener)
    }

    override fun onInactive() {
        logD("removed ChangeListener")
        realmResults.removeChangeListener(listener)
    }*/
}