package com.rumpilstilstkin.gloomhavenhelper.ui.goods

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.GloomhavenIcons
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.goods.Foot
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun GoodItem(
    good: GoodUi,
    modifier: Modifier = Modifier,
    active: Boolean = false,
    clickItem: (GoodUi) -> Unit,
) = GloomCard(
    modifier = modifier.clickable { clickItem(good) },
    active = active,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = modifier
                .size(52.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(8.dp),
                )
                .border(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.outline,
                    width = 1.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(34.dp),
                imageVector = good.typeImage,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "#${good.number} ${good.name}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${good.cost} Золотых",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
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
            clickItem = {}
        )
    }

}