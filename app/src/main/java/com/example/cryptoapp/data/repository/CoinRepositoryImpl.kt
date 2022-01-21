package com.example.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.domain.CoinFullInfo
import com.example.cryptoapp.domain.CoinRepository
import kotlinx.coroutines.delay

class CoinRepositoryImpl(application: Application) : CoinRepository {

    private val coinDao = AppDatabase.getInstance(application).coinPriceInfoDao()

    private val apiService = ApiFactory.apiService

    private val mapper = CoinMapper()


    override fun getCoinList(): LiveData<List<CoinFullInfo>> {
        return Transformations.map(coinDao.getPriceList()) {
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun getCoinDetail(fromSymbols: String): LiveData<CoinFullInfo> {
        return Transformations.map(
            coinDao.getPriceInfoAboutCoin(fromSymbols)
        ) {
            mapper.mapDbModelToEntity(it)
        }
    }

    override suspend fun loadData() {
        while(true){
            val topCoins = apiService.getTopCoinsInfo(limit = 50)
            val fromSymbols = mapper.mapNameListToString(topCoins)
            val jsonContainer = apiService.getFullPriceList(fSyms = fromSymbols)
            val coinsListDto = mapper.mapJsonToList(jsonContainer)
            val dbModels = coinsListDto.map {
                mapper.mapDtoToDbModel(it)
            }
            coinDao.insertPriceList(dbModels)
            delay(10000)
        }

    }

}