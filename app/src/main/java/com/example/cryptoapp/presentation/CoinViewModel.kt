package com.example.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.data.repository.CoinRepositoryImpl
import com.example.cryptoapp.domain.CoinFullInfo
import com.example.cryptoapp.domain.GetCoinUseCase
import com.example.cryptoapp.domain.GetCoinsFullInfoUseCase
import com.example.cryptoapp.domain.LoadDataUseCase
import kotlinx.coroutines.launch


class CoinViewModel(application: Application) : AndroidViewModel(application) {


    private val repository = CoinRepositoryImpl(application)

    private val getCoinsFullInfoUseCase = GetCoinsFullInfoUseCase(repository)
    private val getCoinUseCase = GetCoinUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }

    val coinInfoList = getCoinsFullInfoUseCase()

    fun getDetailInfo(fSym: String) = getCoinUseCase(fSym)


}