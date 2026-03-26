package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
internal fun ScenarioStatsDialog(
    level: Int,
    exp: Int,
    gold: Int,
    trapDamage: Int,
    onDismiss: () -> Unit,
) {

    GloomAlertDialog(
        title = "Параметры прохождения сценария",
        onDismissRequest = onDismiss,
        onConfirmRequest = null,
    ) {
        StatItem(
            value = "$level - Уровень монстров",
            image = R.drawable.ic_level
        )

        StatItem(
            value = "$exp - Опыт за прохождение",
            image = R.drawable.ic_exp
        )

        StatItem(
            value = "$gold - Золотых за монету",
            image = R.drawable.ic_gold
        )

        StatItem(
            value = "$trapDamage - Дамага от повреждаюшей ловушки",
            image = R.drawable.ic_trap
        )
    }

}

@Preview
@Composable
private fun ScenarioStatsDialogPreview() {
    GloomhavenMasterTheme {
        ScenarioStatsDialog(
            level = 1,
            exp = 100,
            gold = 100,
            trapDamage = 10,
            onDismiss = { }
        )
    }
}