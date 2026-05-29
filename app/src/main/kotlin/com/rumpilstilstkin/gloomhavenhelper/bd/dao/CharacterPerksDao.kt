package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPerkBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPerkWithNameBd
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterPerksDao {

    @Insert
    suspend fun insert(characterPerk: CharacterPerkBd)

    @Delete
    suspend fun delete(characterPerk: CharacterPerkBd)

    @Transaction
    @Query("DELETE FROM CharacterPerkBd WHERE perkId = :perkId AND characterId = :characterId")
    suspend fun deleteById(perkId: Int, characterId: Int)

    @Query(
        """
            SELECT 
                g.*, 
                COALESCE(t1.text, t2.text, 'not found') AS text
            FROM CharacterPerkBd g
            LEFT JOIN PerkTranslationBd t1 ON g.id = t1.perkId AND t1.locale = :targetLocale
            LEFT JOIN PerkTranslationBd t2 ON g.id = t2.perkId AND t2.locale = :defaultLocale
            WHERE g.characterId = :characterId
             """
    )
    fun getCharacterPerksFlow(
        characterId: Int,
        targetLocale: String,
        defaultLocale: String
    ): Flow<List<CharacterPerkWithNameBd>>

    @Transaction
    @Query("SELECT * FROM CharacterPerkBd WHERE characterId = :characterId")
    suspend fun getCharacterPerks(characterId: Int): List<CharacterPerkBd>


}