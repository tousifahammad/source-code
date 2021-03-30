package com.webgrity.tishakds.data.db

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import io.realm.OrderedRealmCollectionChangeListener
import io.realm.RealmModel
import io.realm.RealmResults

class RealmLiveData<T : RealmModel?> @MainThread constructor(results: RealmResults<T>) :
    LiveData<List<T>?>() {
    private val results: RealmResults<T>

    // The listener notifies the observers whenever a change occurs.
    // This could be expanded to also return the change set in a pair.
    private val listener =
        OrderedRealmCollectionChangeListener<RealmResults<T>> { results, changeSet ->
            this@RealmLiveData.setValue(
                results
            )
        }

    /**
     * Starts observing the RealmResults, if it is still valid.
     */
    /*override fun onActive() {
        super.onActive()
        if (results.isValid) { // invalidated results can no longer be observed.
            results.addChangeListener(listener)
        }
    }*/

    /**
     * Stops observing the RealmResults.
     */
    /*override fun onInactive() {
        super.onInactive()
        if (results.isValid) {
            results.removeChangeListener(listener)
        }
    }*/

    /**
     * Wraps the provided managed RealmResults as a LiveData.
     *
     * The provided object should be managed, and should be valid.
     *
     * @param object the managed RealmResults to wrap as LiveData
     */
    init {
        require(results.isManaged) { "LiveRealmResults only supports managed RealmModel instances!" }
        require(results.isValid) { "The provided RealmResults is no longer valid because the Realm instance that owns it is closed. It can no longer be observed for changes." }
        results.addChangeListener(listener)
        this.results = results
        if (results.isLoaded) {
            // we should not notify observers when results aren't ready yet (async query).
            // however, synchronous query should be set explicitly.
            value = results
        }
    }
}
