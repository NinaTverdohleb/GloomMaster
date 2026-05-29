package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GoodsDao
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.GoodJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.GoodTranslationJson
import javax.inject.Inject

class GoodJsonFiller @Inject constructor(
    private val jsonDataLoader: JsonDataLoader,
    private val goodsDao: GoodsDao
) {
    suspend fun fill(pack: String) {
        val file = "goods.json"
        val data = jsonDataLoader.loadDictionaryList<GoodJson>(file, pack)
        val entities = data.flatMap { good ->
            List(good.count) { good.toEntity() }
        }
        goodsDao.insertAll(*entities.toTypedArray())
        jsonDataLoader.getLocalesForPack(pack).forEach { locale ->
            val translations = jsonDataLoader.loadDictionaryList<GoodTranslationJson>(
                file, "$pack/$locale"
            )
            val translationsEntities = translations.map { it.toEntity(locale) }
            goodsDao.insertAll(*translationsEntities.toTypedArray())
        }
    }
}
