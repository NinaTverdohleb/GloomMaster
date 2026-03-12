package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GoodsDao
import javax.inject.Inject

class GoodJsonFiller @Inject constructor(
    private val jsonDataLoader: JsonDataLoader,
    private val goodsDao: GoodsDao
) {
    suspend fun fill(version: Int) {
        val entities = jsonDataLoader.loadGoods(version).flatMap { good ->
            List(good.count) {
                good.toEntity()
            }
        }
        goodsDao.insertAll(*entities.toTypedArray())
    }
}
