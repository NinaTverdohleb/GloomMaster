package com.rumpilstilstkin.gloomhavenhelper.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun GloomSwitch(
    selected: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
) =
    Switch(
        checked = selected,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(

        )
    )

@Preview
@Composable
private fun GloomSwitchPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            GloomSwitch(
                selected = false,
                onCheckedChange = null
            )

            GloomSwitch(
                selected = true,
                onCheckedChange = null
            )

        }
    }
}