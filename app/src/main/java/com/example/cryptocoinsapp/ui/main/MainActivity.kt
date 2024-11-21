package com.example.cryptocoinsapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.cryptocoinsapp.R
import com.example.cryptocoinsapp.databinding.ActivityMainBinding
import com.example.cryptocoinsapp.data.api.ApiHelper
import com.example.cryptocoinsapp.data.api.ApiService
import com.example.cryptocoinsapp.data.database.AppDatabase
import com.example.cryptocoinsapp.data.database.CryptoCoinDao
import com.example.cryptocoinsapp.data.repository.CryptoRepository
import com.example.cryptocoinsapp.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Create the ApiService instance
    private val apiService = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    // Create the ApiHelper instance
    private val apiHelper = ApiHelper(apiService)

    // Initialize the Room Database and CryptoCoinDao
    private lateinit var database: AppDatabase
    private lateinit var cryptoCoinDao: CryptoCoinDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the Room database after onCreate to ensure context is available
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "crypto-db"
        ).build()

        cryptoCoinDao = database.cryptoCoinDao()

        // Now, create the CryptoRepository instance using ApiHelper and CryptoCoinDao
        val repository = CryptoRepository(apiHelper, cryptoCoinDao)

        // Use the MainViewModelFactory to pass the repository to the ViewModel
        val viewModel: MainViewModel by viewModels {
            MainViewModelFactory(repository)
        }


        val adapter = CryptoAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Observe filteredCoins LiveData and update UI
        viewModel.filteredCoins.observe(this) { coins ->
            Log.d("MainActivity", "Updating UI with ${coins?.size} items")
            coins?.let { adapter.updateList(it) }
        }

        // Load coins on start
        viewModel.loadCryptoCoins()

        // Set up search functionality
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    viewModel.resetFilters() // Show full list when query is cleared
                } else {
                    viewModel.searchCoins(query) // Filter based on query
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewModel.resetFilters() // Show full list when query is cleared
                } else {
                    viewModel.searchCoins(newText) // Filter based on query
                }
                return true
            }
        })

        // Set up filters
        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedId ->
            if (checkedId.isNotEmpty()) {
                when (checkedId[0]) { // Only process the first checked chip
                    R.id.chipActive -> {
                        // Handle "Active Coins" chip click
                        viewModel.applyFilters(isActive = true, coinType = null, isNew = null)
                    }
                    R.id.chipNew -> {
                        // Handle "New Coins" chip click
                        viewModel.applyFilters(isActive = null, coinType = null, isNew = true)
                    }
                    R.id.chipType -> {
                        // Handle "Coin Type" chip click
                        viewModel.applyFilters(isActive = null, coinType = "specificType", isNew = null)
                    }
                }
            } else {
                // Handle case where no chip is selected (optional)
                viewModel.applyFilters(isActive = null, coinType = null, isNew = null)
            }
        }
    }
}
