package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json

import android.content.Context
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.MonsterStatsJson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JsonDataLoader @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    @PublishedApi
    internal val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        coerceInputValues = true
    }

    private val localeCache = LazyMapCache { key ->
        getFilesFromAssets(context, key)
    }

    fun getLocalesForPack(pack: String) = localeCache[pack]

    @PublishedApi
    internal fun readAsset(fileName: String, pack: String): String =
        context.assets
            .open("data/$pack/$fileName")
            .bufferedReader()
            .use { it.readText() }

    inline fun <reified T> loadDictionaryList(fileName: String, pack: String): List<T> =
        json.decodeFromString(readAsset(fileName, pack))

    fun loadMonsterStats(pack: String, type: String): List<MonsterStatsJson> =
        json.decodeFromString(readAsset("${pack}_${type}_stats.json", pack))
}

private fun getFilesFromAssets(context: Context, pack: String): List<String> {
    if (pack.isEmpty()) return emptyList()

    val assetManager = context.assets
    return try {
        assetManager.list(pack)?.filter { item ->
            assetManager.list("$pack/$item")?.isEmpty() == true
        }.orEmpty()
    } catch (e: IOException) {
        emptyList()
    }
}


private class LazyMapCache(
    private val valueGenerator: (String) -> List<String>
) {
    private val storage = ConcurrentHashMap<String, List<String>>()
    operator fun get(key: String): List<String> {
        return storage.computeIfAbsent(key, valueGenerator)
    }
}


