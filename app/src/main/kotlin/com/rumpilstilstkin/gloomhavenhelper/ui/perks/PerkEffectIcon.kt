package com.rumpilstilstkin.gloomhavenhelper.ui.perks

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.IconResCode
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.IconVectorCode
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme
import com.rumpilstilstkin.gloomhavenhelper.ui.toImage

@Composable
fun PerkEffectIcon(
    iconType: IconResCode,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = iconType.toImage()),
        contentDescription = "Icon",
        modifier = modifier
            .size(64.dp)
            .padding(2.dp)
    )
}

@Composable
fun PerkEffectIcon(
    iconType: IconVectorCode,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = iconType.toImage(),
        contentDescription = "Icon",
        tint = Color.Unspecified,
        modifier = modifier
            .size(64.dp)
            .padding(2.dp)
            .background(
                color = Color.White, // Background color of the circle
                shape = CircleShape
            )
            .border(
                width = 1.dp, // Border width
                color = Color.Black, // Border color
                shape = CircleShape
            )
            .padding(2.dp) // Optional padding to create space between the icon and the border
    )
}

@Preview
@Composable
private fun Sample() {
    GloomhavenHalperTheme {
        PerkEffectIcon(iconType = IconResCode.FROST)
    }

}

@Preview
@Composable
private fun Sample2() {
    GloomhavenHalperTheme {
        PerkEffectIcon(iconType = IconVectorCode.NEXT)
    }

}