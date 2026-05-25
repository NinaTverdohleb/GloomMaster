package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.teams

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
fun AddTeamDialog(
    onDismiss: () -> Unit,
    onAdd: (String) -> Unit,
    openFile: () -> Unit,
) {
    var teamName by rememberSaveable { mutableStateOf("") }

    GloomAlertDialog(
        title = stringResource(R.string.new_team_title),
        titleIcon = Icons.Default.Groups,
        confirmText = stringResource(R.string.add),
        neutralText = stringResource(R.string.import_button),
        confirmEnabled = teamName.isNotBlank(),
        onDismissRequest = onDismiss,
        onConfirmRequest = {
            onAdd(teamName.trim())
            onDismiss()
        },
        onNeutralRequest = {
            openFile()
            onDismiss()
        }
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = teamName,
            onValueChange = { teamName = it },
            label = { Text(stringResource(R.string.team_name_label)) },
            singleLine = true,
        )
    }
}

@Preview
@Composable
private fun AddTeamDialogPreview() {
    GloomhavenMasterTheme {
        AddTeamDialog(
            onDismiss = {},
            onAdd = {},
            openFile = {}
        )
    }
}