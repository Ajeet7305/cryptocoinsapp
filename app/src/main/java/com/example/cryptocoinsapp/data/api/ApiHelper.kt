package com.example.cryptocoinsapp.data.api

class ApiHelper(private val apiService: ApiService) {
    suspend fun getCryptoCoins() = apiService.getCryptoCoins()
}