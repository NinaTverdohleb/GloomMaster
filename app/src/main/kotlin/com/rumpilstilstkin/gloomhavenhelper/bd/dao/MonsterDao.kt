package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterAbilityCardBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterAbilityCardTranslationBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterStatsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterTextStatsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterTranslationsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterWithNameBd

@Dao
interface MonsterDao {

    // Monster
    @Query("SELECT * FROM MonsterBd")
    suspend fun getAllMonsters(): List<MonsterBd>

    @Query(
        """
            SELECT * FROM MonsterAbilityCardBd 
            WHERE deckName IN (
                SELECT deckName 
                FROM MonsterBd 
                WHERE slug IN (:slugs)
            )
        """
    )
    suspend fun getCardsByMonsterSlugs(slugs: List<String>): List<MonsterAbilityCardBd>

    @Query(
        """
            SELECT 
                g.*, 
                COALESCE(t1.name, t2.name, g.slug) AS name
            FROM MonsterBd g
            LEFT JOIN MonsterTranslationsBd t1 ON g.slug = t1.slug AND t1.locale = :targetLocale
            LEFT JOIN MonsterTranslationsBd t2 ON g.slug = t2.slug AND t2.locale = :defaultLocale
            WHERE g.slug = :slug
         """
    )
    suspend fun getMonsterBySlug(
        slug: String,
        targetLocale: String,
        defaultLocale: String
    ): MonsterWithNameBd


    @Query("""
      SELECT g.*, COALESCE(t1.name, t2.name, g.slug) AS name
      FROM MonsterBd g
      LEFT JOIN MonsterTranslationsBd t1 ON g.slug = t1.slug AND t1.locale = :targetLocale
      LEFT JOIN MonsterTranslationsBd t2 ON g.slug = t2.slug AND t2.locale = :defaultLocale
      WHERE g.slug IN (:slugs)
  """)
    suspend fun getMonstersBySlugs(slugs: List<String>, targetLocale: String, defaultLocale: String): List<MonsterWithNameBd>

    @Query("SELECT * FROM MonsterStatsBd WHERE monsterSlug IN (:slugs) AND scenarioLevel = :level")
    suspend fun getStatsForMonsters(slugs: List<String>, level: Int): List<MonsterStatsBd>

    @Query("""
      SELECT * FROM MonsterTextStatsBd AS t
      WHERE t.monsterSlug IN (:slugs) AND t.scenarioLevel = :level
        AND (t.locale = :targetLocale
             OR (t.locale = :defaultLocale
                 AND NOT EXISTS (
                     SELECT 1 FROM MonsterTextStatsBd
                     WHERE monsterSlug = t.monsterSlug AND scenarioLevel = t.scenarioLevel
                       AND isElite = t.isElite AND locale = :targetLocale
                 )))
  """)
    suspend fun getTextStatsForMonsters(slugs: List<String>, level: Int, targetLocale: String, defaultLocale: String): List<MonsterTextStatsBd>

    @Query("SELECT * FROM MonsterAbilityCardBd WHERE deckName IN (:deckNames)")
    suspend fun getCardsByDeckNames(deckNames: List<String>): List<MonsterAbilityCardBd>

    @Query("""
      SELECT * FROM MonsterAbilityCardTranslationBd AS t
      WHERE t.deckName IN (:decks)
        AND (t.locale = :targetLocale
             OR (t.locale = :defaultLocale
                 AND NOT EXISTS (
                     SELECT 1 FROM MonsterAbilityCardTranslationBd
                     WHERE deckName = t.deckName AND cardId = t.cardId AND locale = :targetLocale
                 )))
  """)
    suspend fun getActionCardsByDecks(decks: List<String>, targetLocale: String, defaultLocale: String): List<MonsterAbilityCardTranslationBd>

    @Query(
        """
            SELECT 
                g.*, 
                COALESCE(t1.name, t2.name, g.slug) AS name
            FROM MonsterBd g
            LEFT JOIN MonsterTranslationsBd t1 ON g.slug = t1.slug AND t1.locale = :targetLocale
            LEFT JOIN MonsterTranslationsBd t2 ON g.slug = t2.slug AND t2.locale = :defaultLocale
            WHERE g.pack IN (:packs)
         """
    )
    suspend fun getMonstersByPacks(
        packs: List<String>,
        targetLocale: String,
        defaultLocale: String
    ): List<MonsterWithNameBd>

    @Insert
    suspend fun insertMonster(monster: MonsterBd): Long

    @Insert
    suspend fun insertMonsters(vararg monsters: MonsterBd)

    @Insert
    suspend fun insertTranslations(vararg translations: MonsterTranslationsBd)

    @Insert
    suspend fun insertTranslations(vararg translations: MonsterTextStatsBd)

    @Insert
    suspend fun insertCardTranslations(vararg translations: MonsterAbilityCardTranslationBd)

    @Query("DELETE FROM MonsterBd")
    suspend fun deleteAllMonsters()

    // Monster Stats

    @Query("SELECT * FROM MonsterStatsBd WHERE monsterSlug = :monsterSlug AND scenarioLevel = :level AND isElite = :isElite")
    suspend fun getStats(monsterSlug: String, level: Int, isElite: Boolean): MonsterStatsBd

    @Query(
        """
            SELECT * FROM MonsterTextStatsBd AS t
            WHERE monsterSlug = :monster AND scenarioLevel = :level AND isElite = :isElite
              AND (
                  t.locale = :targetLocale
                  OR (
                      t.locale = :defaultLocale
                      AND NOT EXISTS (
                          SELECT 1 FROM MonsterTextStatsBd
                          WHERE monsterSlug = t.monsterSlug AND scenarioLevel = t.scenarioLevel AND isElite = t.isElite AND locale = :targetLocale
                      )
                  )
              )
            LIMIT 1
            """
    )
    suspend fun getTextStats(
        monster: String,
        level: Int,
        isElite: Boolean,
        targetLocale: String,
        defaultLocale: String
    ): MonsterTextStatsBd?

    @Insert
    suspend fun insertStats(stats: MonsterStatsBd)

    @Insert
    suspend fun insertAllStats(vararg stats: MonsterStatsBd)

    @Insert
    suspend fun insertAllTextStats(vararg stats: MonsterTextStatsBd)

    @Query("DELETE FROM MonsterStatsBd")
    suspend fun deleteAllStats()

    // Monster Ability Cards
    @Query("SELECT * FROM MonsterAbilityCardBd WHERE deckName = :deckName")
    suspend fun getCardsByDeckName(deckName: String): List<MonsterAbilityCardBd>

    @Query(
        """
        SELECT * FROM MonsterAbilityCardTranslationBd as t
            WHERE deckName = :deck
            AND (t.locale = :targetLocale
              OR (
                  t.locale = :defaultLocale
                  AND NOT EXISTS (
                      SELECT 1 FROM MonsterAbilityCardTranslationBd
                      WHERE deckName = t.deckName AND cardId = t.cardId AND locale = :targetLocale
                  )
              ))
        """
    )
    suspend fun getActionCards(
        deck: String,
        targetLocale: String,
        defaultLocale: String
    ): List<MonsterAbilityCardTranslationBd>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: MonsterAbilityCardBd): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCards(vararg cards: MonsterAbilityCardBd)

    @Query("DELETE FROM MonsterAbilityCardBd")
    suspend fun deleteAllCards()
}
