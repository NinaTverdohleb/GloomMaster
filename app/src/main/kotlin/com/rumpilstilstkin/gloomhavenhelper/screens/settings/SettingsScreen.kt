package com.rumpilstilstkin.gloomhavenhelper.screens.settings

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortTeamInfoUi
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.ui.components.buttons.GloomOutlineFilledButtonIcon
import com.rumpilstilstkin.gloomhavenhelper.ui.components.items.GloomListItem
import com.rumpilstilstkin.gloomhavenhelper.ui.components.items.LeftItemIcon
import com.rumpilstilstkin.gloomhavenhelper.ui.components.items.RightItemText
import com.rumpilstilstkin.gloomhavenhelper.ui.components.toolbar.GloomToolbar
import com.rumpilstilstkin.gloomhavenhelper.ui.team.TeamItem
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.persistentListOf

private const val VISIBLE_TEAMS_LIMIT = 2

@Composable
fun SettingsScreen(
    state: SettingsStateUi,
    back: () -> Unit,
    share: () -> Unit,
    settings: () -> Unit,
    deleteCurrentTeam: () -> Unit,
    selectTeam: (ShortTeamInfoUi) -> Unit,
    showAllTeam: () -> Unit,
    addTeam: () -> Unit,
    changeLanguage: () -> Unit,
) = Scaffold(
    topBar = {
        GloomToolbar(
            title = stringResource(R.string.settings),
            back = back,
        )
    },
) { paddingValues ->
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 16.dp, end = 16.dp, top = 28.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AppLogo()

        state.team?.let { team ->
            Spacer(modifier = Modifier.height(16.dp))
            TeamSummary(
                teamName = team.teamName,
                share = share,
                openSettings = settings,
                delete = deleteCurrentTeam
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        TeamsCard(
            teams = state.teams,
            onSelectTeam = selectTeam,
            onShowAllTeams = showAllTeam,
            onAddTeam = addTeam,
        )

        Spacer(modifier = Modifier.height(16.dp))

        LanguageCard(
            language = state.language,
            onClick = changeLanguage,
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(R.string.settings_disclaimer),
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun AppLogo(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f))
                .border(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.secondary,
                    width = 1.dp,
                ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
        )
    }
}

@Composable
private fun TeamSummary(
    teamName: String,
    share: () -> Unit,
    openSettings: () -> Unit,
    delete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = teamName,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            GloomOutlineFilledButtonIcon(
                isError = true,
                icon = painterResource(R.drawable.ic_delete),
                onClick = delete
            )

            GloomOutlineFilledButtonIcon(
                icon = painterResource(R.drawable.ic_share),
                onClick = share
            )

            GloomOutlineFilledButtonIcon(
                icon = painterResource(R.drawable.ic_settings),
                onClick = openSettings
            )
        }
    }
}

@Composable
private fun TeamsCard(
    teams: List<ShortTeamInfoUi>,
    onSelectTeam: (ShortTeamInfoUi) -> Unit,
    onShowAllTeams: () -> Unit,
    onAddTeam: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GloomCard(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            teams.take(VISIBLE_TEAMS_LIMIT).forEach { team ->
                TeamItem(
                    team = team,
                    onClick = { onSelectTeam(team) }
                )
            }

            if (teams.size > VISIBLE_TEAMS_LIMIT) {
                GloomListItem(
                    onClick = onShowAllTeams,
                    title = stringResource(R.string.settings_show_all_teams),
                )
            }

            GloomListItem(
                onClick = onAddTeam,
                title = stringResource(R.string.settings_add_team),
                leftComponent = {
                    LeftItemIcon(
                        icon = painterResource(R.drawable.ic_plus)
                    )
                }
            )
        }
    }
}

@Composable
private fun LanguageCard(
    language: String,
    onClick: () -> Unit,
) {
    GloomCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GloomListItem(
                modifier = Modifier.fillMaxWidth(),
                onClick = onClick,
                title = stringResource(R.string.settings_language),
                rightComponent = {
                    RightItemText(text = language)
                }
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
            deleteCurrentTeam = {}
        )
    }
}

@Preview
@Composable
private fun SettingsScreenEmptyTeamPreview() {
    GloomhavenMasterTheme {
        SettingsScreen(
            state =
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
            deleteCurrentTeam = {}
        )
    }
}
