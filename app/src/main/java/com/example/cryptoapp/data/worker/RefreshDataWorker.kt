package com.example.cryptoapp.data.worker

import android.content.Context
import androidx.work.*
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.ApiFactory
import kotlinx.coroutines.delay

class RefreshDataWorker(
    context: Context,
    workerParameters: WorkerParameters)
    : CoroutineWorker(context, workerParameters) {

    private val coinDao = AppDatabase.getInstance(context).coinPriceInfoDao()
    private val apiService = ApiFactory.apiService
    private val mapper = CoinMapper()

    override suspend fun doWork(): Result {
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

    companion object{
       const val NAME = "RefreshWorker"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
        }
    }
}