package com.example.motionlayoutdemo.data.db

import io.realm.*
import kotlinx.coroutines.suspendCancellableCoroutine

object AppDB {
    fun getRealm():Realm = Realm.getDefaultInstance()
    fun closeRealm() = Realm.getDefaultInstance().close()
    suspend fun <S: RealmObject> RealmQuery<S>.await() = findAllAwait(this)

    suspend fun <S: RealmObject> RealmQuery<S>.awaitFirst() = findFirstAwait(this)

    private suspend fun <T: RealmObject, S: RealmQuery<T>> findAllAwait(query: S): RealmResults<T> = suspendCancellableCoroutine { continuation ->
        val listener = RealmChangeListener<RealmResults<T>> { t -> continuation.resumeWith(Result.success(t)) }
        query.findAllAsync().addChangeListener(listener)
    }

    private suspend fun <T: RealmObject, S: RealmQuery<T>> findFirstAwait(query: S): T? = suspendCancellableCoroutine { continuation ->
        val listener = RealmChangeListener { t: T? -> continuation.resumeWith(Result.success(t)) }
        query.findFirstAsync().addChangeListener(listener)
    }
}