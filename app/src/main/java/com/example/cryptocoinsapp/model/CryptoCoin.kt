package com.example.cryptocoinsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cryptocoins")
data class CryptoCoin(
    @PrimaryKey val id: String, // Room requires a primary key
    val name: String,
    val symbol: String,
    val type: String,
    val is_active: Boolean,
    val is_new: Boolean
)
