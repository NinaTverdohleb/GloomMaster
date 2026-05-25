package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.Index

/**
 * Normalized localized-text store. A row supplies the display [text] for one
 * ([entityType], [entityKey], [fieldName]) tuple in a single [locale]. Seeded for every
 * supported language at database fill time from per-locale dictionary assets.
 *
 * Keys are stable: scenarios use their number, deduplicated strings (locations) use their
 * canonical Russian value. The canonical content tables are never modified, so game logic
 * and saved progress are unaffected by translation.
 */
@Entity(
    primaryKeys = ["entityType", "entityKey", "fieldName", "locale"],
    indices = [Index(value = ["locale"])]
)
data class TranslationBd(
    val entityType: String,
    val entityKey: String,
    val fieldName: String,
    val locale: String,
    val text: String,
)
