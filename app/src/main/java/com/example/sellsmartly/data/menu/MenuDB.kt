package com.example.sellsmartly.data.menu

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MenuEntity::class], version = 1)
abstract class MenuDB : RoomDatabase() {
    abstract fun menuDao(): MenuDao

    companion object {
        @Volatile
        private var instance: MenuDB? = null

        fun getInstance(context: Context): MenuDB =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context, MenuDB::class.java, "menu_db").build()
            }.also { instance = it }
    }
}