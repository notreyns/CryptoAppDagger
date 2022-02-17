package com.example.cryptoapp.presentation

import androidx.lifecycle.ViewModel
import com.example.cryptoapp.data.repository.CoinRepositoryImpl
import com.example.cryptoapp.domain.GetCoinUseCase
import com.example.cryptoapp.domain.GetCoinsFullInfoUseCase
import com.example.cryptoapp.domain.LoadDataUseCase
import javax.inject.Inject


class CoinViewModel @Inject constructor(
    private val getCoinsFullInfoUseCase: GetCoinsFullInfoUseCase,
    private val getCoinUseCase: GetCoinUseCase,
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {


   // private val repository = CoinRepositoryImpl(application)


    init {
        loadDataUseCase()
    }

    val coinInfoList = getCoinsFullInfoUseCase()

    fun getDetailInfo(fSym: String) = getCoinUseCase(fSym)


}