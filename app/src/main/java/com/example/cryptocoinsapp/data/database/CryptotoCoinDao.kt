package com.example.cryptocoinsapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptocoinsapp.model.CryptoCoin

@Dao
interface CryptoCoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cryptoCoins: List<CryptoCoin>)

    @Query("SELECT * FROM cryptocoins")
    suspend fun getAllCoins(): List<CryptoCoin>

    @Query("SELECT * FROM cryptocoins WHERE name LIKE :query OR symbol LIKE :query")
    suspend fun searchCryptoCoins(query: String): List<CryptoCoin>
}