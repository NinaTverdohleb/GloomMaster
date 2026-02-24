package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ActionType
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CardLine
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterAbilityCard
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.Background
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.SurfaceVariant

@Composable
internal fun MonsterAbilityCardItem(
    card: MonsterAbilityCard,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.5f),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 1. Header (Инициатива и Название)
            HeaderRow(card.initiative, card.name)

            // 2. Body (Список действий)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    card.lines.forEach { line ->
                        CardLineItem(line)
                        val subLine = line.subLine
                        if (subLine != null) {
                            CardLineItem(subLine, true)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }

            // 3. Footer (ID, Шафл)
            FooterRow(card.id, card.needsShuffle)
        }
    }
}

@Composable
private fun HeaderRow(initiative: Int, name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
            .padding(end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Блок инициативы
        Box(
            modifier = Modifier
                .background(
                    color = Color.Black,
                    shape = CutCornerShape(bottomEnd = 16.dp)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initiative.toString().padStart(2, '0'),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CardLineItem(line: CardLine, isSubLine: Boolean = false) {
    val fontSize = if(isSubLine) 14.sp else 22.sp
    val iconSize = if(isSubLine) 18.dp else 24.dp
    when (line) {
        is CardLine.Action -> {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = line.type.title, fontSize = fontSize)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = painterResource(id = line.type.iconRes),
                    contentDescription = "Shuffle Deck",
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = line.modifier, fontSize = fontSize)
            }
        }

        is CardLine.Text -> {
            Text(
                text = line.content,
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }

        is CardLine.MixedText -> {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = line.prefix, color = Color.White, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = painterResource(id = line.iconRes),
                    contentDescription = "Shuffle Deck",
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = line.suffix, color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Composable
private fun FooterRow(
    id: String,
    needsShuffle: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // ID карточки (слева)
        Text(
            text = id,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 12.sp
        )


        // Иконка шафла (справа)
        if (needsShuffle) {
            Icon(
                painter = painterResource(id = R.drawable.ic_shuffle),
                contentDescription = "Shuffle Deck",
                tint = MaterialTheme.colorScheme.onSurface
            )
        } else {
            Spacer(modifier = Modifier.size(24.dp))
        }
    }
}

@Preview
@Composable
private fun MonsterAbilityCardItemPreview() {
    GloomhavenHalperTheme {
        MonsterAbilityCardItem(
            card = MonsterAbilityCard(
                id = "524",
                name = "Разбойник страж",
                initiative = 15,
                lines = listOf(
                    CardLine.Action(ActionType.MOVE, "-1"),
                    CardLine.Action(
                        ActionType.STRENGTH,
                        "",
                        CardLine.Text("Себя")
                    ),
                ),
                needsShuffle = true
            )
        )
    }
}