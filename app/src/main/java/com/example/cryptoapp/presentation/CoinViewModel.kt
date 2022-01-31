package com.example.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cryptoapp.data.repository.CoinRepositoryImpl
import com.example.cryptoapp.domain.GetCoinUseCase
import com.example.cryptoapp.domain.GetCoinsFullInfoUseCase
import com.example.cryptoapp.domain.LoadDataUseCase


class CoinViewModel(application: Application) : AndroidViewModel(application) {


    private val repository = CoinRepositoryImpl(application)

    private val getCoinsFullInfoUseCase = GetCoinsFullInfoUseCase(repository)
    private val getCoinUseCase = GetCoinUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    init {
        loadDataUseCase()
    }

    val coinInfoList = getCoinsFullInfoUseCase()

    fun getDetailInfo(fSym: String) = getCoinUseCase(fSym)


}