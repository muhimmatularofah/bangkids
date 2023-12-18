package com.example.sellsmartly.data.menu

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MenuDao {
    @Query("SELECT * FROM menu")
    fun getMenu(): LiveData<List<MenuEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMenu(menu: List<MenuEntity>)

    @Query("DELETE FROM menu")
    fun deleteMenu()
}