package com.example.firstmobileapp2

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data")
data class DataEntity(
    @PrimaryKey
    @NonNull
    val uid: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "number") val number: Long?
)