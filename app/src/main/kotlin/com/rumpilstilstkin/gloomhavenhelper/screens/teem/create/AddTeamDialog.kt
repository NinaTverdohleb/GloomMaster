package com.rumpilstilstkin.gloomhavenhelper.screens.teem.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.GloomOutlinedTextField
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
internal fun AddTeamDialog(
    onAdd: (String) -> Unit,
    openFile: () -> Unit,
) {
    var teamName by rememberSaveable { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Text(
            text = stringResource(R.string.new_team_title),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
        )

        GloomOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = teamName,
            onValueChange = { teamName = it },
            label = stringResource(R.string.team_name_label),
        )

        GloomOutlineButton(
            text = stringResource(R.string.import_button),
            onClick = openFile,
            modifier = Modifier.fillMaxWidth(),
            isError = false,
            icon = AppIcon.Restore,
        )

        GloomOutlineButton(
            text = stringResource(R.string.add),
            onClick = { onAdd(teamName.trim()) },
            modifier = Modifier.fillMaxWidth(),
            isError = false,
            enabled = teamName.isNotBlank(),
            icon = AppIcon.Plus,
        )
    }
}

@Preview
@Composable
private fun AddTeamDialogPreview() {
    GloomhavenMasterTheme {
        AddTeamDialog(
            onAdd = {},
            openFile = {},
        )
    }
}
