package com.example.cryptoapp.domain

import javax.inject.Inject

class GetCoinsFullInfoUseCase @Inject constructor(
    private val repository: CoinRepository
    ) {
    operator fun invoke() = repository.getCoinList()
}