package com.rumpilstilstkin.gloomhavenhelper.screens.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Good
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.GoodType
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.goods.Body
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.GloomhavenIcons
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.goods.Arm
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.goods.Foot
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.goods.Head
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.goods.Smallthing
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.goods.Twoarm

@Immutable
data class GoodUi(
    val id: Int,
    val number: Int,
    val name: String,
    val typeImage: ImageVector,
    val cost: Int,
    val characterGoodId: Int? = null,
    val image: String
) {
    val imagePath
        get() = "file:///android_asset/image/goods/$image"

    companion object {
        fun fixture(
            id: Int = 1,
            number: Int = 2
        ) = GoodUi(
            id = id,
            number = number,
            name = "Сапоги большого шага поешь этих сладких французких булок",
            typeImage = GloomhavenIcons.GoodTypes.Foot,
            cost = 20,
            image = ""
        )
    }
}

fun Good.toUi() = GoodUi(
    id = this.id,
    number = this.number,
    name = this.name,
    typeImage = this.type.toImage(),
    cost = this.cost,
    characterGoodId = this.characterGoodId,
    image = this.image
)

fun GoodType.toImage() = when (this) {
    GoodType.Body -> GloomhavenIcons.GoodTypes.Body
    GoodType.Head -> GloomhavenIcons.GoodTypes.Head
    GoodType.Foot -> GloomhavenIcons.GoodTypes.Foot
    GoodType.Arm -> GloomhavenIcons.GoodTypes.Arm
    GoodType.DoubleArm -> GloomhavenIcons.GoodTypes.Twoarm
    GoodType.SmallThing -> GloomhavenIcons.GoodTypes.Smallthing
}



