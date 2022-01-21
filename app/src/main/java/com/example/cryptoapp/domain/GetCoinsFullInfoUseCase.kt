package com.example.cryptoapp.domain

class GetCoinsFullInfoUseCase(private val repository: CoinRepository) {
    operator fun invoke() = repository.getCoinList()
}