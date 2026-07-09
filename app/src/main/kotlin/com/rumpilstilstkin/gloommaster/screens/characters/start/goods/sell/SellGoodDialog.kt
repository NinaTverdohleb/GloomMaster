package com.rumpilstilstkin.gloommaster.screens.characters.start.goods.sell

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloommaster.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloommaster.designsystem.icons.NavigationIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme

@Composable
fun SellGoodDialog(
    name: String,
    sellPrice: Int,
    sell: () -> Unit,
    close: () -> Unit,
) {
    Column {
        Text(
            text = stringResource(R.string.sell_good_dialog_title),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text =
                stringResource(
                    R.string.sell_good_dialog_description,
                    name,
                    sellPrice,
                ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            GloomOutlineButton(
                text = stringResource(R.string.sell_button),
                onClick = sell,
                modifier = Modifier.weight(1f),
                icon = AppIcon.Buy,
            )

            GloomOutlineButton(
                text = stringResource(R.string.close),
                onClick = close,
                modifier = Modifier.weight(1f),
                icon = NavigationIcon.Close,
            )
        }
    }
}

@Preview
@Composable
private fun SellGoodDialogPreview() {
    GloomhavenMasterTheme {
        SellGoodDialog(
            name = "Boots of Speed",
            sellPrice = 10,
            sell = {},
            close = {},
        )
    }
}
