package com.example.cryptoapp.domain

import androidx.lifecycle.LiveData

interface CoinRepository {

    fun getCoinList(): LiveData<List<CoinFullInfo>>

    fun getCoinDetail(fromSymbols: String): LiveData<CoinFullInfo>

    suspend fun loadData()
}