package com.rumpilstilstkin.gloomhavenhelper.domain.entity

data class Good(
    val id: Int,
    val displayNumber: Int,
    val name: String,
    val type: GoodType,
    val cost: Int,
    val image: String,
    val characterGoodId: Int? = null,
    val pack: PackType
) {
    fun filterResult(goodType: GoodType?, search: String): Boolean {
        if (goodType != null && goodType != this.type) return false
        if (search.isBlank()) return true
        if (this.name.contains(search, ignoreCase = true)) return true

        val numberRegex = Regex("\\d+")
        val matchResult = numberRegex.find(search)
        val number = matchResult?.value?.toIntOrNull()

        return if (number != null) {
            this.displayNumber == number
        } else false
    }
}

enum class GoodType {
    Body,
    Head,
    Foot,
    Arm,
    DoubleArm,
    SmallThing
}
