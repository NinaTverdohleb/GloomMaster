package com.rumpilstilstkin.gloomhavenhelper.screens.models

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.GloomIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.GoodIcon
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Good
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.GoodType

@Immutable
data class GoodUi(
    val goodId: Int,
    val number: Int,
    val name: String,
    val icon: GloomIcon,
    val cost: Int,
    val characterGoodId: Int? = null,
    val image: String,
) {
    val imagePath
        get() = "file:///android_asset/image/goods/$image"

    companion object {
        fun fixture(id: Int = 2) =
            GoodUi(
                goodId = id,
                number = 14,
                name = "Seven-league boots, feast on these sweet French brioches",
                icon = GoodIcon.Foot,
                cost = 20,
                image = "",
            )
    }
}

fun Good.toUi() =
    GoodUi(
        goodId = this.id,
        number = this.displayNumber,
        name = this.name,
        icon = this.type.toImage(),
        cost = this.cost,
        characterGoodId = this.characterGoodId,
        image = this.image,
    )

fun GoodType.toImage() =
    when (this) {
        GoodType.Body -> GoodIcon.Body
        GoodType.Head -> GoodIcon.Head
        GoodType.Foot -> GoodIcon.Foot
        GoodType.Arm -> GoodIcon.Arm
        GoodType.DoubleArm -> GoodIcon.TwoArm
        GoodType.SmallThing -> GoodIcon.SmallThing
    }
