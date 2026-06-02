package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.character

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
fun DeleteCharacterDialog(
    onDismiss: () -> Unit,
    delete: () -> Unit,
    retire: () -> Unit,
    canRetire: Boolean,
) {
    GloomAlertDialog(
        title = stringResource(R.string.delete_character_title),
        onDismissRequest = onDismiss,
        onConfirmRequest = null,
        onNeutralRequest = if (canRetire) retire else null,
        onNegativeRequest = delete,
        neutralText = stringResource(R.string.retire),
        negativeText = stringResource(R.string.delete),
        content = {
            Text(
                text = stringResource(R.string.delete_warning),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
    )
}

@Preview
@Composable
private fun DeleteCharacterDialogPreview() {
    GloomhavenMasterTheme {
        DeleteCharacterDialog(
            onDismiss = {},
            delete = {},
            retire = {},
            canRetire = true,
        )
    }
}
