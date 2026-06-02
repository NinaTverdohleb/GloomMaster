package com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest

import kotlinx.serialization.Serializable

@Serializable
enum class TaskType {
    Check,
    Count,
}
