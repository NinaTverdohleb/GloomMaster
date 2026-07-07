package com.rumpilstilstkin.gloomhavenhelper.designsystem.icons

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.res.painterResource

@Stable
interface GloomIcon {
    @get:DrawableRes
    val resId: Int

    @Composable
    fun painter() = painterResource(resId)
}
