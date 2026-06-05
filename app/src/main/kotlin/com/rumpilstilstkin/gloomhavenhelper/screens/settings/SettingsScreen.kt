package com.rumpilstilstkin.gloomhavenhelper.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortTeamInfoUi
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.ui.team.toImage
import com.rumpilstilstkin.gloomhavenhelper.ui.team.toLabel
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SettingsScreen(
    state: SettingsStateUi,
    back: () -> Unit,
    share: () -> Unit,
    settings: () -> Unit,
    selectTeam: (ShortTeamInfoUi) -> Unit,
    showAllTeam: () -> Unit,
    addTeam: () -> Unit,
    changeLanguage: () -> Unit,
    modifier: Modifier = Modifier,
) = Surface(
    modifier = modifier.fillMaxSize(),
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
        ) {
            IconButton(
                onClick = back,
                modifier = Modifier.align(Alignment.CenterEnd),
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White,
                )
            }
        }

        Box(modifier = Modifier.size(84.dp)) {
            Box(
                modifier =
                    Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.outlineVariant),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                )
            }
        }

        state.team?.teamName?.let {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = it,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row {
                    OutlinedButton(
                        onClick = share,
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    ) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }

                    Spacer(Modifier.width(32.dp))

                    OutlinedButton(
                        onClick = settings,
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    ) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        GloomCard(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Сменить команду",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(Modifier.height(8.dp))

            state.teams.take(2).forEach {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                ) {
                    TeamItem(
                        team = it,
                        selectTeam = selectTeam,
                    )
                }
            }

            if (state.teams.size > 2) {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable { },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .clickable { showAllTeam() }
                                .padding(16.dp),
                        text = "Показать все",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable { addTeam() }
                        .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add team",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp),
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Добавить команду",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        GloomCard(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clickable { changeLanguage() },
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Язык",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = state.language,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Unofficial fan app. Gloomhaven and all associated assets are trademarks of Cephalofair Games.",
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun TeamItem(
    team: ShortTeamInfoUi,
    modifier: Modifier = Modifier,
    selectTeam: (ShortTeamInfoUi) -> Unit,
) {
    val (_, tint) = team.level.toImage()
    val label = team.level.toLabel()

    Row(
        modifier = modifier.clickable { selectTeam(team) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray),
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = team.teamName,
        )
        Spacer(Modifier.weight(1f))
        Spacer(Modifier.width(8.dp))
        Box(
            modifier =
                Modifier
                    .border(
                        width = 1.dp,
                        color = tint,
                        shape = RoundedCornerShape(16.dp),
                    ).padding(horizontal = 10.dp, vertical = 2.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier.padding(4.dp),
                text = label,
                color = tint,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    GloomhavenMasterTheme {
        SettingsScreen(
            state =
                SettingsStateUi(
                    team = ShortTeamInfoUi.fixture(),
                    teams =
                        persistentListOf(
                            ShortTeamInfoUi.fixture(),
                            ShortTeamInfoUi.fixture(),
                            ShortTeamInfoUi.fixture(),
                            ShortTeamInfoUi.fixture(),
                        ),
                    language = "English",
                ),
            back = {},
            share = {},
            settings = {},
            selectTeam = {},
            showAllTeam = {},
            addTeam = {},
            changeLanguage = {},
        )
    }
}

@Preview
@Composable
private fun SettingsScreenEmptyTeamPreview() {
    GloomhavenMasterTheme {
        SettingsScreen(
            SettingsStateUi(
                team = null,
                teams = persistentListOf(),
                language = "English",
            ),
            back = {},
            share = {},
            settings = {},
            selectTeam = {},
            showAllTeam = {},
            addTeam = {},
            changeLanguage = {},
        )
    }
}
