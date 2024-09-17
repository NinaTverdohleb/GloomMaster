package com.rumpilstilstkin.gloomhavenhelper.di

import android.content.Context
import android.content.SharedPreferences
import com.rumpilstilstkin.gloomhavenhelper.bd.GlHelperDatabase
import com.rumpilstilstkin.gloomhavenhelper.bd.createGlHelperDatabase
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterClassDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GameLevelInfoDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GlHelperModule {

    @Provides
    fun providesSharedPreference(
        @ApplicationContext context: Context
    ): SharedPreferences =
        context.getSharedPreferences(
            "GlHelperPreferences", Context.MODE_PRIVATE
        )

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context,
    ) = createGlHelperDatabase(app)

    @Singleton
    @Provides
    fun provideCharacterDao(db: GlHelperDatabase): CharacterDao = db.characterDao()

    @Singleton
    @Provides
    fun provideTeamDao(db: GlHelperDatabase): TeamDao = db.teamDao()

    @Singleton
    @Provides
    fun provideCharacterClassDao(db: GlHelperDatabase): CharacterClassDao = db.characterClassDao()

    @Singleton
    @Provides
    fun provideGameLevelInfoDao(db: GlHelperDatabase): GameLevelInfoDao = db.gameLevelInfoDao()

    @Singleton
    @Provides
    fun provideScenarioDao(db: GlHelperDatabase) = db.scenarioDao()

    @Singleton
    @Provides
    fun provideTeamScenarioDao(db: GlHelperDatabase) = db.teamScenarioDao()
}