package com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.goods.GoodDetailsDialog
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.GloomhavenIcons
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.goods.Foot
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun GoodItem(
    good: GoodUi,
    dialogButtonText: String = "Добавить",
    onActionClick: (GoodUi) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDetailsDialog by remember { mutableStateOf(false) }
    GoodDetailsDialog(
        goodNumber = good.number,
        showDialog = showDetailsDialog,
        onDismiss = { showDetailsDialog = false },
        onAction = {
            onActionClick(good)
            showDetailsDialog = false
        },
        buttonText = dialogButtonText,
        imagePath = good.imagePath
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { showDetailsDialog = true },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(34.dp),
                imageVector = good.typeImage,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "#" + good.number)
            Spacer(modifier = Modifier.width(16.dp))

            Text(
                modifier = Modifier,
                text = good.name,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Text(
            modifier = Modifier.padding(
                start = 16.dp
            ),
            style = MaterialTheme.typography.titleMedium,
            text = "${good.cost} G"
        )
    }
}

@Preview
@Composable
private fun GoodItemPreview() {
    GloomhavenHalperTheme {
        GoodItem(
            good = GoodUi(
                id = 1,
                number = 1,
                name = "Сапоги большого шага поешь этих сладких французких булок",
                typeImage = GloomhavenIcons.GoodTypes.Foot,
                cost = 20,
                image = ""
            ),
            onActionClick = {}
        )
    }

}