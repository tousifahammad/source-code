package com.webgrity.tishakds.data.db

import io.realm.DynamicRealm
import io.realm.RealmMigration

class RealmMigration : RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        var oldVersion = oldVersion

        // DynamicRealm exposes an editable schema
        val schema = realm.schema

        if (oldVersion == 0L) {
            schema.get("Restaurant")!!
                .addField("id", Long::class.javaPrimitiveType)
        }
    }
}