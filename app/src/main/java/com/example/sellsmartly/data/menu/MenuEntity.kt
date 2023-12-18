package com.example.sellsmartly.data.menu

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu")
data class MenuEntity (


    @field:ColumnInfo(name = "name")
    val name: String,

    @field:ColumnInfo(name = "price")
    val description: Double,

    @field:ColumnInfo(name = "id")
    @PrimaryKey
    val id: String,

)