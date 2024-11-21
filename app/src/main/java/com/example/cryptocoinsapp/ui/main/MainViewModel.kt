package com.example.cryptocoinsapp.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocoinsapp.data.repository.CryptoRepository
import com.example.cryptocoinsapp.model.CryptoCoin
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CryptoRepository) : ViewModel() {

    val cryptoCoins = MutableLiveData<List<CryptoCoin>>()
    val filteredCoins = MutableLiveData<List<CryptoCoin>?>()

    fun loadCryptoCoins() {
        viewModelScope.launch {
            val data = repository.fetchCryptoCoins()
            cryptoCoins.postValue(data)
            filteredCoins.postValue(data) // Initially show all
        }
    }

    // Apply filters
    fun applyFilters(isActive: Boolean?, coinType: String?, isNew: Boolean?) {
        if (cryptoCoins.value.isNullOrEmpty()) {
            Log.d("applyFilters", "cryptoCoins is null or empty!")
        }

        val filteredList = cryptoCoins.value?.filter { coin ->
            val matchesActive = isActive?.let { coin.is_active == it } ?: true
            val matchesType = coinType?.let { coin.type == it } ?: true
            val matchesNew = isNew?.let { coin.is_new == it } ?: true
            matchesActive && matchesType && matchesNew
        }

        Log.d("applyFilters", "Filtered List Size: ${filteredList?.size ?: 0}")
        filteredCoins.postValue(filteredList)
    }

    // Search coins by name or symbol
    fun searchCoins(query: String) {
        val searchResult = filteredCoins.value?.filter { coin ->
            coin.name.contains(query, ignoreCase = true) || coin.symbol.contains(query, ignoreCase = true)
        }
        filteredCoins.postValue(searchResult)
    }

    fun resetFilters() {
        filteredCoins.postValue(cryptoCoins.value) // Reset to original list
    }

}