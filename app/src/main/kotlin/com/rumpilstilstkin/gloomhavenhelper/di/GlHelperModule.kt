package com.rumpilstilstkin.gloomhavenhelper.di

import android.content.Context
import android.content.SharedPreferences
import com.rumpilstilstkin.gloomhavenhelper.bd.GlHelperDatabase
import com.rumpilstilstkin.gloomhavenhelper.bd.createGlHelperDatabase
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

    @Singleton
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
    fun provideGameLevelInfoDao(db: GlHelperDatabase): GameLevelInfoDao = db.gameLevelInfoDao()

    @Singleton
    @Provides
    fun provideScenarioDao(db: GlHelperDatabase) = db.scenarioDao()

    @Singleton
    @Provides
    fun provideTeamScenarioDao(db: GlHelperDatabase) = db.teamScenarioDao()

    @Singleton
    @Provides
    fun provideCharacterGoods(db: GlHelperDatabase) = db.characterGoodsDao()

    @Singleton
    @Provides
    fun provideGoodsDao(db: GlHelperDatabase) = db.goodsDao()

    @Singleton
    @Provides
    fun providePerksDao(db: GlHelperDatabase) = db.perksDao()

    @Singleton
    @Provides
    fun provideCharacterPerksDao(db: GlHelperDatabase) = db.characterPerksDao()

    @Singleton
    @Provides
    fun providePersonalQuestDao(db: GlHelperDatabase) = db.personalQuestDao()

    @Singleton
    @Provides
    fun provideCharacterPersonalQuestDao(db: GlHelperDatabase) = db.characterPersonalQuestDao()

    @Singleton
    @Provides
    fun provideMonsterDao(db: GlHelperDatabase) = db.monsterDao()

    @Singleton
    @Provides
    fun provideTeamCharacterClassDao(db: GlHelperDatabase) = db.teamCharacterClassDao()

    @Singleton
    @Provides
    fun provideTeamGoodDao(db: GlHelperDatabase) = db.teamGoodDao()
}