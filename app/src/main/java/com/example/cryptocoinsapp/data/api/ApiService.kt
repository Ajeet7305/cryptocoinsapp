package com.example.cryptocoinsapp.data.api

import com.example.cryptocoinsapp.model.CryptoCoin
import retrofit2.http.GET

interface ApiService {
    @GET("/")
    suspend fun getCryptoCoins(): List<CryptoCoin>
}