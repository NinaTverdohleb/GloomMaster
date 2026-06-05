package com.rumpilstilstkin.gloomhavenhelper.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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

private const val VISIBLE_TEAMS_LIMIT = 2

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
        SettingsHeader(onClose = back)

        AppLogo()

        state.team?.let { team ->
            Spacer(modifier = Modifier.height(16.dp))
            TeamSummary(
                teamName = team.teamName,
                onShare = share,
                onSettings = settings,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

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
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun SettingsHeader(
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
    ) {
        IconButton(
            onClick = onClose,
            modifier = Modifier.align(Alignment.CenterEnd),
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.close),
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
private fun AppLogo(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
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

@Composable
private fun TeamSummary(
    teamName: String,
    onShare: () -> Unit,
    onSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = teamName,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            CircularIconButton(
                icon = Icons.Default.Share,
                onClick = onShare,
            )
            CircularIconButton(
                icon = Icons.Default.Settings,
                onClick = onSettings,
            )
        }
    }
}

@Composable
private fun CircularIconButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors =
            ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurface,
            ),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
        )
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
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = stringResource(R.string.settings_change_team),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(Modifier.height(8.dp))

        teams.take(VISIBLE_TEAMS_LIMIT).forEach { team ->
            TeamItem(
                team = team,
                selectTeam = onSelectTeam,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
            )
        }

        if (teams.size > VISIBLE_TEAMS_LIMIT) {
            Text(
                text = stringResource(R.string.settings_show_all_teams),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable { onShowAllTeams() }
                        .padding(16.dp),
            )
        }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clickable { onAddTeam() }
                    .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(24.dp),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.settings_add_team),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
private fun LanguageCard(
    language: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GloomCard(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable { onClick() },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.settings_language),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = language,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun TeamItem(
    team: ShortTeamInfoUi,
    selectTeam: (ShortTeamInfoUi) -> Unit,
    modifier: Modifier = Modifier,
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
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_empty),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = team.teamName,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.weight(1f))
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
        )
    }
}
