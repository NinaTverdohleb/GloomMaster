package com.rumpilstilstkin.gloomhavenhelper.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.AchievementDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterGoodsDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterPerksDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterPersonalQuestDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GameLevelInfoDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GoodsDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.LocationsDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.MonsterDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.PerksDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.PersonalQuestDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.ScenarioDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.ScenarioGameStateDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamCharacterClassDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamGoodDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamScenarioDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.AchievementBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.AchievementTranslateBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterGoodBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPerkBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPersonalQuestBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GameLevelInfoBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GoodBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GoodTranslationsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.LocationBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.LocationTranslateBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterAbilityCardBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterAbilityCardTranslationBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterStatsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterTextStatsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterTranslationsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.PerkBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.PerkTranslationBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.PersonalQuestBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.PersonalQuestTaskTranslationsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.PersonalQuestTranslationsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.ScenarioBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.ScenarioGameStateBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.ScenarioTranslationsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamCharacterClassBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamGoodBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamScenarioBd
import com.rumpilstilstkin.gloomhavenhelper.bd.migrations.ALL_MIGRATIONS
import com.rumpilstilstkin.gloomhavenhelper.bd.typeconverters.AchievementConverter
import com.rumpilstilstkin.gloomhavenhelper.bd.typeconverters.CardActionsTypeConverter
import com.rumpilstilstkin.gloomhavenhelper.bd.typeconverters.ListCharacterTaskItemTypeConverter
import com.rumpilstilstkin.gloomhavenhelper.bd.typeconverters.MonsterCardActionTypeConverter
import com.rumpilstilstkin.gloomhavenhelper.bd.typeconverters.MonsterStatTypeListConverter
import com.rumpilstilstkin.gloomhavenhelper.bd.typeconverters.MonsterStatTypeSetConverter
import com.rumpilstilstkin.gloomhavenhelper.bd.typeconverters.ScenarioConverters
import com.rumpilstilstkin.gloomhavenhelper.bd.typeconverters.StringListTypeConverter

@TypeConverters(
    ListCharacterTaskItemTypeConverter::class,
    CardActionsTypeConverter::class,
    MonsterStatTypeListConverter::class,
    MonsterStatTypeSetConverter::class,
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
