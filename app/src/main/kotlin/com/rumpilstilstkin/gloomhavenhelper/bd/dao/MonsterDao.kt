package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterAbilityCardBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterStatsBd

@Dao
interface MonsterDao {

    // Monster
    @Query("SELECT * FROM MonsterBd")
    suspend fun getAllMonsters(): List<MonsterBd>

    @Query("SELECT * FROM MonsterBd WHERE monsterId = :id")
    suspend fun getMonsterById(id: Int): MonsterBd

    @Query("SELECT * FROM MonsterBd WHERE name = :name")
    suspend fun getMonsterByName(name: String): MonsterBd

    @Query("SELECT * FROM MonsterBd WHERE pack IN (:packs)")
    suspend fun getMonstersByPacks(packs: List<String>): List<MonsterBd>

    @Insert
    suspend fun insertMonster(monster: MonsterBd): Long

    @Insert
    suspend fun insertMonsters(vararg monsters: MonsterBd)

    // Monster Stats

    @Query("SELECT * FROM MonsterStatsBd WHERE monsterId = :monsterId AND scenarioLevel = :level AND isElite = :isElite")
    suspend fun getStats(monsterId: Int, level: Int, isElite: Boolean): MonsterStatsBd

    @Insert
    suspend fun insertStats(stats: MonsterStatsBd)

    @Insert
    suspend fun insertAllStats(vararg stats: MonsterStatsBd)

    // Monster Ability Cards
    @Query("SELECT * FROM MonsterAbilityCardBd WHERE deckName = :deckName")
    suspend fun getCardsByDeckName(deckName: String): List<MonsterAbilityCardBd>

    @Query("SELECT * FROM MonsterAbilityCardBd WHERE cardId = :cardId")
    suspend fun getCardById(cardId: Int): MonsterAbilityCardBd

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: MonsterAbilityCardBd): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCards(vararg cards: MonsterAbilityCardBd)
}
