package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterGoodsDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GoodsDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamGoodDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterGoodBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamGoodBd
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.localized
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toDomain
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Good
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.localization.LocaleSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.contains
import kotlin.collections.map

@Singleton
class GoodsRepository @Inject constructor(
    private val goodsDao: GoodsDao,
    private val teamGoodDao: TeamGoodDao,
    private val characterGoodsDao: CharacterGoodsDao,
    private val translationRepository: TranslationRepository,
    private val localeSource: LocaleSource,
) {
    suspend fun getGoods(packs: Set<PackType>): List<Good> {
        val resolver = translationRepository.resolver(localeSource.current)
        return goodsDao
            .getAll()
            .map { it.toDomain().localized(resolver) }
            .filter { it.pack in packs }
    }

    suspend fun getGood(goodId: Int): Good? =
        goodsDao.getGoodById(goodId)?.toDomain()

    fun getGoodsForTeam(teamId: Int): Flow<List<Good>> =
        teamGoodDao.getGoodsForTeam(teamId)
            .combine(resolverFlow()) { goods, resolver ->
                goods.map { it.good.toDomain().localized(resolver) }
            }

    suspend fun getTeamGoodsNumbers(teamId: Int): List<Int> = teamGoodDao.getGoodsForTeamSync(teamId).map { it.goodId }

    suspend fun getCharacterGoodIds(characterId: Int): List<Int> =
        characterGoodsDao.getCharacterGoods(characterId).map { it.good.goodId }

    fun getCharacterGoodIds(characterIds: List<Int>): Flow<List<Int>> =
        characterGoodsDao.getCharactersGoodIds(characterIds)

    suspend fun getGoodsByNumbers(numbers: List<Int>): List<Good> =
        goodsDao.getGoodsByNumbers(numbers).map { it.toDomain() }

    suspend fun delete(teamId: Int, goodId: Int) {
        teamGoodDao.delete(teamId, goodId)
    }

    suspend fun addGoodsToTeam(teamId: Int, goodIds: List<Int>) {
        val entities = goodIds.map { TeamGoodBd(teamId = teamId, goodId = it) }
        teamGoodDao.insertAll(entities)
    }

    suspend fun removeGoodFromTeam(teamId: Int, goodId: Int) {
        teamGoodDao.delete(teamId, goodId)
    }

    suspend fun addCharacterGoods(characterId: Int, goodIds: List<Int>) {
        val entities = goodIds.map { CharacterGoodBd(characterId = characterId, goodId = it) }
        characterGoodsDao.insertAll(entities)
    }

    suspend fun deleteCharacterGood(goodId: Int, characterId: Int) {
        characterGoodsDao.delete(
            characterId = characterId,
            goodId = goodId
        )
    }

    fun getCharacterGoods(characterId: Int) =
        characterGoodsDao.getCharacterGoodsFlow(characterId)
            .combine(resolverFlow()) { goods, resolver ->
                goods.map { it.toDomain().localized(resolver) }
            }

    /**
     * Active-locale resolver as a stream: switches when the language changes and re-emits when
     * the translation store is (re)seeded, so item names refresh without a restart.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun resolverFlow(): Flow<TextResolver> =
        localeSource.locale.flatMapLatest { translationRepository.resolverFlow(it) }
}