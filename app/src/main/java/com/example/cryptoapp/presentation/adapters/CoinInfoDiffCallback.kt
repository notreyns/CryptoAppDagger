package com.example.cryptoapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.cryptoapp.domain.CoinFullInfo

object CoinInfoDiffCallback: DiffUtil.ItemCallback<CoinFullInfo>() {
    override fun areItemsTheSame(oldItem: CoinFullInfo, newItem: CoinFullInfo): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
    }

    override fun areContentsTheSame(oldItem: CoinFullInfo, newItem: CoinFullInfo): Boolean {
        return oldItem == newItem
    }
}