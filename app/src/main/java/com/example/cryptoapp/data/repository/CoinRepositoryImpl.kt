package com.example.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.database.CoinPriceInfoDao
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.worker.RefreshDataWorker
import com.example.cryptoapp.domain.CoinFullInfo
import com.example.cryptoapp.domain.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    val application: Application,
    val mapper: CoinMapper,
    var coinDao: CoinPriceInfoDao
) : CoinRepository {

    //private val coinDao = AppDatabase.getInstance(application).coinPriceInfoDao()




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

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            RefreshDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest()
        )
    }

}