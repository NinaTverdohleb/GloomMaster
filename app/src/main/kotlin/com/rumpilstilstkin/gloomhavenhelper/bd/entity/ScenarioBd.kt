package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType

@Entity
data class ScenarioBd(
    @PrimaryKey val scenarioNumber: Int,
    val newScenarios: String = "",
    val requirements: String = "",
    val monsters: List<String> = emptyList(),
    val location: String = "",
    val pack: String,
)

@Entity(
    primaryKeys = ["scenarioNumber", "locale"],
    foreignKeys = [
        ForeignKey(
            entity = ScenarioBd::class,
            parentColumns = ["scenarioNumber"],
            childColumns = ["scenarioNumber"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ScenarioTranslationsBd(
    val scenarioNumber: Int,
    val locale: String,
    val name: String,
)

data class ScenarioWithNameBd(
    val scenarioNumber: Int,
    val newScenarios: String = "",
    val requirements: String = "",
    val monsters: List<String> = emptyList(),
    val locationName: String = "",
    val pack: String,
    val name: String,
)