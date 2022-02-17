package com.example.cryptoapp.domain

import javax.inject.Inject

class GetCoinUseCase @Inject constructor(private val repository: CoinRepository) {
    operator fun invoke(fSyms: String) = repository.getCoinDetail(fSyms)

}