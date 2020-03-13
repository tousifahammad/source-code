package net.app.mvvmsampleapp.floor.design

import android.content.Context
import android.util.Log
import android.view.View
import io.realm.Realm
import net.app.mvvmsampleapp.database.RealmDB
import net.app.mvvmsampleapp.floor.design.add_table.Table
import java.util.*


/*

class DesignRepository(){
}

*/



class DesignRepository(private val db: RealmDB) {
    var realm: Realm? = null

    private fun addTableToDB(tableName: String, tableType: String, layoutTables: View) {
        realm = RealmDB.getDBInstance()

/*

        try {
            realm?.beginTransaction()

            val table: Table =
                realm!!.createObject<Table>(Table::class.java, UUID.randomUUID().toString())
            //table.id = UUID.randomUUID().toString()
            table.number = tableName
            table.shape = tableType
            table.width = layoutTables.measuredWidth
            table.height = layoutTables.measuredHeight
            table.position_x = layoutTables.x
            table.position_y = layoutTables.y

            realm?.commitTransaction()

        } catch (error: Exception) {
            Log.d("1111", "Error : ${error.message}")
        } finally {
            realm?.close()
        }

*/
    }


    fun clearAllTable() {

    }
}
