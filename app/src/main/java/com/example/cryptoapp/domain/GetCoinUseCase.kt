package com.example.cryptoapp.domain

class GetCoinUseCase(private val repository: CoinRepository) {
    operator fun invoke(fSyms: String) = repository.getCoinDetail(fSyms)

}