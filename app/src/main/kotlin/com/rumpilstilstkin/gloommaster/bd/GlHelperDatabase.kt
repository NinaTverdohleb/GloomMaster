package com.rumpilstilstkin.gloommaster.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rumpilstilstkin.gloommaster.bd.dao.AchievementDao
import com.rumpilstilstkin.gloommaster.bd.dao.CharacterDao
import com.rumpilstilstkin.gloommaster.bd.dao.CharacterGoodsDao
import com.rumpilstilstkin.gloommaster.bd.dao.CharacterPerksDao
import com.rumpilstilstkin.gloommaster.bd.dao.CharacterPersonalQuestDao
import com.rumpilstilstkin.gloommaster.bd.dao.GameLevelInfoDao
import com.rumpilstilstkin.gloommaster.bd.dao.GoodsDao
import com.rumpilstilstkin.gloommaster.bd.dao.LocationsDao
import com.rumpilstilstkin.gloommaster.bd.dao.MonsterDao
import com.rumpilstilstkin.gloommaster.bd.dao.PerksDao
import com.rumpilstilstkin.gloommaster.bd.dao.PersonalQuestDao
import com.rumpilstilstkin.gloommaster.bd.dao.ScenarioDao
import com.rumpilstilstkin.gloommaster.bd.dao.ScenarioGameStateDao
import com.rumpilstilstkin.gloommaster.bd.dao.TeamCharacterClassDao
import com.rumpilstilstkin.gloommaster.bd.dao.TeamDao
import com.rumpilstilstkin.gloommaster.bd.dao.TeamGoodDao
import com.rumpilstilstkin.gloommaster.bd.dao.TeamScenarioDao
import com.rumpilstilstkin.gloommaster.bd.entity.AchievementBd
import com.rumpilstilstkin.gloommaster.bd.entity.AchievementTranslateBd
import com.rumpilstilstkin.gloommaster.bd.entity.CharacterBd
import com.rumpilstilstkin.gloommaster.bd.entity.CharacterGoodBd
import com.rumpilstilstkin.gloommaster.bd.entity.CharacterPerkBd
import com.rumpilstilstkin.gloommaster.bd.entity.CharacterPersonalQuestBd
import com.rumpilstilstkin.gloommaster.bd.entity.GameLevelInfoBd
import com.rumpilstilstkin.gloommaster.bd.entity.GoodBd
import com.rumpilstilstkin.gloommaster.bd.entity.GoodTranslationsBd
import com.rumpilstilstkin.gloommaster.bd.entity.LocationBd
import com.rumpilstilstkin.gloommaster.bd.entity.LocationTranslateBd
import com.rumpilstilstkin.gloommaster.bd.entity.MonsterAbilityCardBd
import com.rumpilstilstkin.gloommaster.bd.entity.MonsterAbilityCardTranslationBd
import com.rumpilstilstkin.gloommaster.bd.entity.MonsterBd
import com.rumpilstilstkin.gloommaster.bd.entity.MonsterStatsBd
import com.rumpilstilstkin.gloommaster.bd.entity.MonsterTextStatsBd
import com.rumpilstilstkin.gloommaster.bd.entity.MonsterTranslationsBd
import com.rumpilstilstkin.gloommaster.bd.entity.PerkBd
import com.rumpilstilstkin.gloommaster.bd.entity.PerkTranslationBd
import com.rumpilstilstkin.gloommaster.bd.entity.PersonalQuestBd
import com.rumpilstilstkin.gloommaster.bd.entity.PersonalQuestTaskTranslationsBd
import com.rumpilstilstkin.gloommaster.bd.entity.PersonalQuestTranslationsBd
import com.rumpilstilstkin.gloommaster.bd.entity.ScenarioBd
import com.rumpilstilstkin.gloommaster.bd.entity.ScenarioGameStateBd
import com.rumpilstilstkin.gloommaster.bd.entity.ScenarioTranslationsBd
import com.rumpilstilstkin.gloommaster.bd.entity.TeamBd
import com.rumpilstilstkin.gloommaster.bd.entity.TeamCharacterClassBd
import com.rumpilstilstkin.gloommaster.bd.entity.TeamGoodBd
import com.rumpilstilstkin.gloommaster.bd.entity.TeamScenarioBd
import com.rumpilstilstkin.gloommaster.bd.migrations.ALL_MIGRATIONS
import com.rumpilstilstkin.gloommaster.bd.typeconverters.AchievementConverter
import com.rumpilstilstkin.gloommaster.bd.typeconverters.CardActionsTypeConverter
import com.rumpilstilstkin.gloommaster.bd.typeconverters.ListCharacterTaskItemTypeConverter
import com.rumpilstilstkin.gloommaster.bd.typeconverters.MonsterCardActionTypeConverter
import com.rumpilstilstkin.gloommaster.bd.typeconverters.MonsterStatTypeListConverter
import com.rumpilstilstkin.gloommaster.bd.typeconverters.ScenarioConverters
import com.rumpilstilstkin.gloommaster.bd.typeconverters.StringListTypeConverter

@TypeConverters(
    ListCharacterTaskItemTypeConverter::class,
    CardActionsTypeConverter::class,
    MonsterStatTypeListConverter::class,
    StringListTypeConverter::class,
    AchievementConverter::class,
    ScenarioConverters::class,
    MonsterCardActionTypeConverter::class,
)
@Database(
    entities = [
        TeamBd::class,
        CharacterBd::class,
        GameLevelInfoBd::class,
        ScenarioBd::class,
        ScenarioTranslationsBd::class,
        TeamScenarioBd::class,
        GoodBd::class,
        GoodTranslationsBd::class,
        CharacterGoodBd::class,
        CharacterPerkBd::class,
        PerkBd::class,
        PerkTranslationBd::class,
        PersonalQuestBd::class,
        PersonalQuestTranslationsBd::class,
        PersonalQuestTaskTranslationsBd::class,
        CharacterPersonalQuestBd::class,
        MonsterBd::class,
        MonsterTranslationsBd::class,
        MonsterStatsBd::class,
        MonsterAbilityCardBd::class,
        TeamCharacterClassBd::class,
        TeamGoodBd::class,
        AchievementBd::class,
        AchievementTranslateBd::class,
        ScenarioGameStateBd::class,
        LocationBd::class,
        LocationTranslateBd::class,
        MonsterTextStatsBd::class,
        MonsterAbilityCardTranslationBd::class,
    ],
    version = 5,
)
abstract class GlHelperDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

    abstract fun teamDao(): TeamDao

    abstract fun gameLevelInfoDao(): GameLevelInfoDao

    abstract fun scenarioDao(): ScenarioDao

    abstract fun teamScenarioDao(): TeamScenarioDao

    abstract fun characterGoodsDao(): CharacterGoodsDao

    abstract fun goodsDao(): GoodsDao

    abstract fun characterPerksDao(): CharacterPerksDao

    abstract fun perksDao(): PerksDao

    abstract fun personalQuestDao(): PersonalQuestDao

    abstract fun characterPersonalQuestDao(): CharacterPersonalQuestDao

    abstract fun monsterDao(): MonsterDao

    abstract fun teamCharacterClassDao(): TeamCharacterClassDao

    abstract fun teamGoodDao(): TeamGoodDao

    abstract fun achievementDao(): AchievementDao

    abstract fun scenarioGameStateDao(): ScenarioGameStateDao

    abstract fun locationsDao(): LocationsDao
}

fun createGlHelperDatabase(context: Context): GlHelperDatabase =
    Room
        .databaseBuilder(
            context,
            GlHelperDatabase::class.java,
            DATABASE_NAME,
        ).fallbackToDestructiveMigrationFrom(false, 1, 2, 3)
        .addMigrations(*ALL_MIGRATIONS)
        .build()

private const val DATABASE_NAME = "glHelperDatabase"
