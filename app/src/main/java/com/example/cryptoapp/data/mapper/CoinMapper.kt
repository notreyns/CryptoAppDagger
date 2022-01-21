package com.example.cryptoapp.data.mapper

import com.example.cryptoapp.data.database.model.CoinInfoDbModel
import com.example.cryptoapp.data.model.CoinInfoDto
import com.example.cryptoapp.data.model.CoinInfoJsonContainerDto
import com.example.cryptoapp.data.model.CoinNameListDto
import com.example.cryptoapp.domain.CoinFullInfo
import com.google.gson.Gson

class CoinMapper {
    fun mapDtoToDbModel(dto: CoinInfoDto): CoinInfoDbModel {
        return CoinInfoDbModel(
            fromSymbol = dto.fromSymbol,
            toSymbol = dto.toSymbol,
            price = dto.price,
            lastUpdate = dto.lastUpdate,
            highDay = dto.highDay,
            lowDay = dto.lowDay,
            lastMarket = dto.lastMarket,
            imageUrl = dto.imageUrl
        )
    }
    fun mapDbModelToEntity(dbModel: CoinInfoDbModel) = CoinFullInfo(
        fromSymbol = dbModel.fromSymbol,
        toSymbol = dbModel.toSymbol,
        price = dbModel.price,
        lastUpdate = dbModel.lastUpdate,
        highDay = dbModel.highDay,
        lowDay = dbModel.lowDay,
        lastMarket = dbModel.lastMarket,
        imageUrl = dbModel.imageUrl
    )

    fun mapNameListToString(list: CoinNameListDto): String {
        return list.coinNameContainers?.map {
            it.coinName?.name
        }?.joinToString(",") ?: ""
    }

    fun mapJsonToList(container: CoinInfoJsonContainerDto): List<CoinInfoDto>{
        val result = ArrayList<CoinInfoDto>()
        val jsonObject = container.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

}