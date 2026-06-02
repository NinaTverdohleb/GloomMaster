package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.IconCode.Companion.toIconCode
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCardAction
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.sampleDeck
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.GameIcon
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.GameIcon.Companion.toGameIcon
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.text.TextWithImagesByCode
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

private const val SQRT_3_OVER_2 = 0.8660254f

val HexagonShape =
    GenericShape { size, _ ->
        val cx = size.width / 2f
        val cy = size.height / 2f
        val r = minOf(cx, cy)
        val xOffset = r * SQRT_3_OVER_2
        val yOffset = r * 0.5f

        moveTo(cx, cy - r)
        lineTo(cx + xOffset, cy - yOffset)
        lineTo(cx + xOffset, cy + yOffset)
        lineTo(cx, cy + r)
        lineTo(cx - xOffset, cy + yOffset)
        lineTo(cx - xOffset, cy - yOffset)
        close()
    }

@Composable
fun MonsterCardView(
    monsterName: String,
    card: MonsterCard,
    modifier: Modifier = Modifier,
) {
    val cardShape = RoundedCornerShape(12.dp)
    val borderRed = Color(0xFF6B1A1A)

    Box(
        modifier =
            modifier
                .clip(cardShape)
                .border(2.dp, borderRed, cardShape)
                .background(
                    brush =
                        Brush.linearGradient(
                            colors =
                                listOf(
                                    Color(0xFF200404),
                                    Color(0xFF5C1212),
                                    Color(0xFF3D0808),
                                ),
                        ),
                ).padding(8.dp),
    ) {
        Column {
            MonsterCardHeaderView(
                weight = card.initiative,
                monsterName = monsterName,
            )

            Column(
                modifier =
                    Modifier
                        .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                card.actions.forEach {
                    ActionMonsterEffect(it)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        if (card.needsShuffle) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier =
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 10.dp, bottom = 6.dp)
                        .size(32.dp),
            )
        }
    }
}

@Composable
fun ActionMonsterEffect(
    item: MonsterCardAction,
    fontSize: TextUnit = 18.sp,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        item.startEffect?.toIconCode()?.toGameIcon()?.let {
            ActionEffectImage(it)
            Spacer(
                Modifier.width(16.dp),
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            val adaptiveFontSize =
                if (item.text.contains(Regex("\\p{L}"))) fontSize else fontSize.div(0.8)
            TextWithImagesByCode(
                text = item.text,
                fontSize = adaptiveFontSize,
                textAlign = TextAlign.Center,
            )

            item.subAction.forEach { subItem ->
                ActionMonsterEffect(
                    item = subItem,
                    fontSize = fontSize.div(1.4),
                )
            }
        }

        item.endEffect?.toIconCode()?.toGameIcon()?.let {
            Spacer(
                Modifier.width(16.dp),
            )
            ActionEffectImage(it)
        }
    }
}

@Composable
private fun ActionEffectImage(icon: GameIcon) {
    icon.color?.let {
        Icon(
            painter = painterResource(id = icon.imageRes),
            contentDescription = "Icon",
            tint = icon.color,
            modifier =
                Modifier
                    .padding(2.dp),
        )
    } ?: Image(
        painter = painterResource(id = icon.imageRes),
        contentDescription = "Icon",
        modifier =
            Modifier
                .padding(2.dp),
    )
}

@Composable
private fun MonsterCardHeaderView(
    weight: Int,
    monsterName: String,
) = Row(
    modifier =
        Modifier
            .fillMaxWidth()
            .background(
                brush =
                    Brush.verticalGradient(
                        colors = listOf(Color(0x88000000), Color.Transparent),
                    ),
            ).padding(horizontal = 12.dp, vertical = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center,
) {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        Spacer(
            modifier =
                Modifier
                    .size(56.dp)
                    .clip(HexagonShape)
                    .background(
                        brush =
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF4A4440), Color(0xFF2A2420)),
                                start = Offset(0f, 0f),
                                end = Offset(48f, 48f),
                            ),
                    ).border(1.5.dp, Color(0xFF6B5A50), HexagonShape),
        )
        Text(
            text = weight.toString(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
    Spacer(
        modifier = Modifier.width(16.dp),
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = monsterName,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(
            modifier = Modifier.height(16.dp),
        )
        HorizontalDivider()
    }
}

@Preview(
    heightDp = 3000,
)
@Composable
private fun MonsterCardViewSample() {
    GloomhavenMasterTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize(),
        ) {
            sampleDeck.forEach { card ->
                MonsterCardView(
                    monsterName = "Living bones",
                    card = card,
                    Modifier.size(width = 360.dp, height = 270.dp),
                )
                Spacer(
                    Modifier.height(8.dp),
                )
            }
        }
    }
}
