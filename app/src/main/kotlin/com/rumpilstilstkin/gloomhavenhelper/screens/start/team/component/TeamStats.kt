package com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomVariantCard
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme
import com.rumpilstilstkin.gloomhavenhelper.ui.components.NumberPicker
import com.rumpilstilstkin.gloomhavenhelper.ui.components.NumberPickerProgress
import java.util.Locale

@Composable
internal fun TeamStats(
    reputation: Int,
    prosperity: Int,
    discount: Int,
    modifier: Modifier = Modifier,
    onReputationClick: (Int) -> Unit = {},
    onProsperityClick: (Int) -> Unit = {}
) = Column(
    modifier = modifier
) {
    GloomCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                style = MaterialTheme.typography.labelLarge,
                text = "РЕПУТАЦИЯ",
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            NumberPicker(
                value = reputation,
                showSign = true,
                intRange = IntRange(-20, 20)
            ) { newValue ->
                onReputationClick(newValue)
            }
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            GloomVariantCard {
                Row(
                    modifier = Modifier.padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_shop),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )
                    Text(
                        style = MaterialTheme.typography.labelLarge,
                        text = "Скидка в магазине:"
                    )
                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )
                    Text(
                        style = MaterialTheme.typography.titleLarge,
                        text = String.format(Locale.getDefault(), "%+d", discount),
                        color = MaterialTheme.colorScheme.primary
                    )

                }
            }
        }
    }

    Spacer(
        modifier = Modifier.height(16.dp)
    )

    GloomCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    style = MaterialTheme.typography.labelLarge,
                    text = "ПРОЦВЕТАНИЕ",
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    style = MaterialTheme.typography.labelMedium,
                    text = "Уровень: $prosperity"
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            NumberPickerProgress(
                value = reputation,
                showSign = false,
                intRange = IntRange(0, 9)
            ) { newValue ->
                onProsperityClick(newValue)
            }
        }
    }
}

@Preview
@Composable
private fun ContentSample() {
    GloomhavenHalperTheme {
        TeamStats(
            reputation = 2,
            prosperity = 3,
            discount = -1
        )
    }
}

