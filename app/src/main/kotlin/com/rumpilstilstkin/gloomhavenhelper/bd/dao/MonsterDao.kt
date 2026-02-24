package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterAbilityCardBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterStatsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.ScenarioMonsterBd
import kotlinx.coroutines.flow.Flow

@Dao
interface MonsterDao {

    // Monster
    @Query("SELECT * FROM MonsterBd")
    suspend fun getAllMonsters(): List<MonsterBd>

    @Query("SELECT * FROM MonsterBd WHERE monsterId = :id")
    suspend fun getMonsterById(id: Int): MonsterBd

    @Insert
    suspend fun insertMonster(monster: MonsterBd): Long

    @Insert
    suspend fun insertMonsters(vararg monsters: MonsterBd)

    // Monster Stats
    @Query("SELECT * FROM MonsterStatsBd WHERE monsterId = :monsterId")
    suspend fun getStatsByMonsterId(monsterId: Int): List<MonsterStatsBd>

    @Query("SELECT * FROM MonsterStatsBd WHERE monsterId = :monsterId AND scenarioLevel = :level AND isElite = :isElite")
    suspend fun getStats(monsterId: Int, level: Int, isElite: Boolean): MonsterStatsBd

    @Insert
    suspend fun insertStats(stats: MonsterStatsBd)

    @Insert
    suspend fun insertAllStats(vararg stats: MonsterStatsBd)

    // Monster Ability Cards
    @Query("SELECT * FROM MonsterAbilityCardBd WHERE monsterId = :monsterId")
    suspend fun getCardsByMonsterId(monsterId: Int): List<MonsterAbilityCardBd>

    @Query("SELECT * FROM MonsterAbilityCardBd WHERE cardId = :cardId")
    suspend fun getCardById(cardId: Int): MonsterAbilityCardBd

    @Insert
    suspend fun insertCard(card: MonsterAbilityCardBd): Long

    @Insert
    suspend fun insertCards(vararg cards: MonsterAbilityCardBd)

    // Scenario Monsters
    @Query("SELECT * FROM ScenarioMonsterBd WHERE scenarioNumber = :scenarioNumber")
    suspend fun getMonstersByScenario(scenarioNumber: Int): List<ScenarioMonsterBd>

    @Query("""
        SELECT m.* FROM MonsterBd m
        INNER JOIN ScenarioMonsterBd sm ON m.monsterId = sm.monsterId
        WHERE sm.scenarioNumber = :scenarioNumber
    """)
    suspend fun getMonstersForScenario(scenarioNumber: Int): List<MonsterBd>

    @Query("""
        SELECT m.* FROM MonsterBd m
        INNER JOIN ScenarioMonsterBd sm ON m.monsterId = sm.monsterId
        WHERE sm.scenarioNumber = :scenarioNumber
    """)
    fun getMonstersForScenarioFlow(scenarioNumber: Int): Flow<List<MonsterBd>>

    @Insert
    suspend fun insertScenarioMonster(scenarioMonster: ScenarioMonsterBd)

    @Insert
    suspend fun insertScenarioMonsters(vararg scenarioMonsters: ScenarioMonsterBd)
}
