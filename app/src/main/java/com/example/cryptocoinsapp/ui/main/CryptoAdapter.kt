package com.example.cryptocoinsapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocoinsapp.R
import com.example.cryptocoinsapp.databinding.ItemCryptoBinding
import com.example.cryptocoinsapp.model.CryptoCoin

class CryptoAdapter(private val coins: List<CryptoCoin>) :
    RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    private var filteredCoins = coins.toMutableList()

    inner class CryptoViewHolder(private val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(coin: CryptoCoin) {
            with(binding) {
                coinName.text = coin.name
                coinSymbol.text = coin.symbol
                binding.newBadge.visibility = View.GONE
                if (coin.is_new) {
                    binding.newBadge.visibility = View.VISIBLE
                }
                when {
                    coin.type.lowercase() == "coin" && coin.is_active -> {
                        binding.coinTypeIcon.setImageResource(R.drawable.ic_coin_active)
                    }

                    coin.type.lowercase() == "token" && coin.is_active -> {
                        binding.coinTypeIcon.setImageResource(R.drawable.ic_token_active)
                    }

                    coin.type.lowercase() == "coin" && !coin.is_active -> {
                        binding.coinTypeIcon.setImageResource(R.drawable.ic_coin_inactive)
                    }

                    else -> {
                        binding.coinTypeIcon.setImageResource(R.drawable.ic_coin_inactive)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val binding = ItemCryptoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CryptoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.bind(filteredCoins[position])
    }

    override fun getItemCount(): Int = filteredCoins.size

    fun updateList(newCoins: List<CryptoCoin>) {
        filteredCoins = newCoins.toMutableList()
        notifyDataSetChanged()
    }
}