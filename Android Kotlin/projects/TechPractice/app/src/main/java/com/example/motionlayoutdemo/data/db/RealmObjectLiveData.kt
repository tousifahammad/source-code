package com.example.motionlayoutdemo.data.db

import androidx.lifecycle.LiveData
import io.realm.RealmChangeListener
import io.realm.RealmObject

class RealmObjectLiveData<T : RealmObject>(realmObject: T) : LiveData<T>() {

    private val listener = RealmChangeListener<T> { result ->
        if (realmObject.isValid && realmObject.isLoaded) {
            value = result
        }
    }

    init {
        realmObject.addChangeListener(listener)
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