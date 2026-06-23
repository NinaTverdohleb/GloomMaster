package com.rumpilstilstkin.gloomhavenhelper.designsystem.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

interface GloomIcon{
    val resId: Int

    @Composable
    fun painter() = painterResource(resId)
}