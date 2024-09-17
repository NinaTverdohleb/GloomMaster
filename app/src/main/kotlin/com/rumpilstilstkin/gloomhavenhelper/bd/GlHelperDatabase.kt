package com.rumpilstilstkin.gloomhavenhelper.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterClassDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GameLevelInfoDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.ScenarioDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamScenarioDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterClassBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GameLevelInfoBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.ScenarioBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamScenarioBd

@Database(
    entities = [
        TeamBd::class,
        CharacterClassBd::class,
        CharacterBd::class,
        GameLevelInfoBd::class,
        ScenarioBd::class,
        TeamScenarioBd::class,
    ],
    version = 1
)
abstract class GlHelperDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun characterClassDao(): CharacterClassDao
    abstract fun teamDao(): TeamDao
    abstract fun gameLevelInfoDao(): GameLevelInfoDao
    abstract fun scenarioDao(): ScenarioDao
    abstract fun teamScenarioDao(): TeamScenarioDao
}

fun createGlHelperDatabase(
    context: Context,
): GlHelperDatabase {
    val database = Room.databaseBuilder(
        context,
        GlHelperDatabase::class.java,
        DATABASE_NAME
    ).build()

    return database
}

private const val DATABASE_NAME = "glHelperDatabase"

