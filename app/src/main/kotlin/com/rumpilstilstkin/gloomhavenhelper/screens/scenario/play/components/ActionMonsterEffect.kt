package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.image.TextWithImagesByCode
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.game.GameIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.game.GameIcon.Companion.toGameIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.game.IconCode.Companion.toIconCode
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCardAction
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ActionMonsterEffect(
    item: MonsterCardAction,
    style: TextStyle = MaterialTheme.typography.titleMedium,
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
            TextWithImagesByCode(
                style = style,
                text = item.text,
                textAlign = TextAlign.Center,
            )

            item.subAction.forEach { subItem ->
                ActionMonsterEffect(
                    item = subItem,
                    style = style.copy(fontSize = style.fontSize.div(1.4)),
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
    Icon(
        painter = icon.image.painter(),
        contentDescription = stringResource(icon.titleRes),
        tint = icon.color,
        modifier = Modifier.padding(2.dp),
    )
}

@Preview
@Composable
private fun ActionMonsterEffectPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ActionMonsterEffect(
                item =
                    MonsterCardAction(
                        text = "Attack #41 +1",
                        subAction =
                            persistentListOf(
                                MonsterCardAction(
                                    text = "Range #37 +1",
                                    subAction = persistentListOf(),
                                ),
                            ),
                    ),
            )

            ActionMonsterEffect(
                item =
                    MonsterCardAction(
                        text = "Attack #41 +1",
                        endEffect = "#31",
                        subAction =
                            persistentListOf(
                                MonsterCardAction(
                                    text = "Range #37 +1",
                                    subAction = persistentListOf(),
                                ),
                            ),
                    ),
            )
        }
    }
}
