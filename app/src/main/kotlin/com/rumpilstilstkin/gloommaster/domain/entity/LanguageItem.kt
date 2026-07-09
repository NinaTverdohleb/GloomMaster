package com.rumpilstilstkin.gloommaster.domain.entity

data class LanguageItem(
    val languageTag: String? = null,
    val languageName: String,
    val selected: Boolean,
)
