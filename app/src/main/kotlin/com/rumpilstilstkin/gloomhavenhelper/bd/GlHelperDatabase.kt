package com.rumpilstilstkin.gloomhavenhelper.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterGoodsDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterPerksDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterPersonalQuestDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GameLevelInfoDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GoodsDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.MonsterDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.PerksDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.PersonalQuestDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.ScenarioDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamCharacterClassDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamGoodDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamScenarioDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterGoodBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPerkBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPersonalQuestBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GameLevelInfoBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GoodBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterAbilityCardBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterStatsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.PerkBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.PersonalQuestBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.ScenarioBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamCharacterClassBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamGoodBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamScenarioBd
import com.rumpilstilstkin.gloomhavenhelper.bd.migrations.ALL_MIGRATIONS
import com.rumpilstilstkin.gloomhavenhelper.bd.typeconverters.CardActionsTypeConverter
import com.rumpilstilstkin.gloomhavenhelper.bd.typeconverters.ListCharacterTaskItemTypeConverter
import com.rumpilstilstkin.gloomhavenhelper.bd.typeconverters.MonsterStatTypeConverter
import com.rumpilstilstkin.gloomhavenhelper.bd.typeconverters.StringListTypeConverter

@TypeConverters(
    ListCharacterTaskItemTypeConverter::class,
    CardActionsTypeConverter::class,
    MonsterStatTypeConverter::class,
    StringListTypeConverter::class,
)
@Database(
    entities = [
        TeamBd::class,
        CharacterBd::class,
        GameLevelInfoBd::class,
        ScenarioBd::class,
        TeamScenarioBd::class,
        GoodBd::class,
        CharacterGoodBd::class,
        CharacterPerkBd::class,
        PerkBd::class,
        PersonalQuestBd::class,
        CharacterPersonalQuestBd::class,
        MonsterBd::class,
        MonsterStatsBd::class,
        MonsterAbilityCardBd::class,
        TeamCharacterClassBd::class,
        TeamGoodBd::class,
    ],
    version = 1
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
}

fun createGlHelperDatabase(
    context: Context,
): GlHelperDatabase =
    Room.databaseBuilder(
        context,
        GlHelperDatabase::class.java,
        DATABASE_NAME
    )
        .addMigrations(*ALL_MIGRATIONS)
        .build()


private const val DATABASE_NAME = "glHelperDatabase"