# CryptoCoins App

The CryptoCoins App is a mobile application built in Android that displays a list of cryptocurrencies. Users can view details such as coin name, symbol, type, and status (active/inactive). The app includes powerful filtering and search capabilities and adheres to clean architecture principles.

---

## Features

- **List of Cryptocurrencies**: Displays a comprehensive list of coins and tokens with their details.
- **Filter Options**:
    - Filter by **Active Coins**.
    - Filter by **Coin Type** (Coin or Token).
    - Filter by **New Coins**.
- **Search Functionality**:
    - Search by **Coin Name**.
    - Search by **Symbol**.
- **Dynamic Icons**:
    - Different icons for active/inactive coins and tokens.
    - A badge for new coins.
- **Modern UI**:
    - Material Design components.
    - Responsive layout using ConstraintLayout.

---

## Technology Stack

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Networking**: Retrofit
- **Database**: Room
- **UI Components**: Material Design, RecyclerView
- **Testing**:
    - Unit Testing: JUnit, Mockito
    - UI Testing: Espresso

---

## Project Structure

```plaintext
com.example.cryptocoins
│
├── data
│   ├── api
│   │   ├── ApiService.kt         # Retrofit service interface for API calls
│   │   └── ApiHelper.kt          # Helper class for managing API calls
│   ├── database
│   │   ├── AppDatabase.kt        # Room database setup
│   │   └── CryptoCoinDao.kt      # DAO for accessing coin data
│   └── repository
│       └── CryptoRepository.kt   # Repository for handling data from API and database
│
├── model
│   └── CryptoCoin.kt             # Data model class for cryptocurrency
│
├── ui
│   ├── main
│   │   ├── MainActivity.kt       # Main entry point for the app
│   │   ├── MainViewModel.kt      # ViewModel for handling business logic
│   │   └── CryptoAdapter.kt      # RecyclerView adapter for displaying the coin list
│   └── components
│       ├── SearchView.kt         # Custom search functionality
│       └── FilterChips.kt        # Custom filter chips for filtering options
│
└── utils
    └── Extensions.kt             # Utility functions and extensions