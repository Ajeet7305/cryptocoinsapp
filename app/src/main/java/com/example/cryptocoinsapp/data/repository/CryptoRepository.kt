package com.example.cryptocoinsapp.data.repository

import com.example.cryptocoinsapp.data.api.ApiHelper
import com.example.cryptocoinsapp.data.database.CryptoCoinDao
import com.example.cryptocoinsapp.model.CryptoCoin

class CryptoRepository(
    private val apiHelper: ApiHelper,
    private val dao: CryptoCoinDao // Add the DAO here
) {

    suspend fun fetchCryptoCoins(): List<CryptoCoin> {
        return apiHelper.getCryptoCoins() // Fetch data from API
    }

    // Optionally: Handle local database operations
    suspend fun getAllCoinsFromDB(): List<CryptoCoin> {
        return dao.getAllCoins() // Fetch coins from the database
    }

    suspend fun insertCoins(cryptoCoins: List<CryptoCoin>) {
        dao.insertAll(cryptoCoins) // Insert data into the database
    }
}
