package com.example.cryptocoinsapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptocoinsapp.model.CryptoCoin

@Database(entities = [CryptoCoin::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cryptoCoinDao(): CryptoCoinDao
}
