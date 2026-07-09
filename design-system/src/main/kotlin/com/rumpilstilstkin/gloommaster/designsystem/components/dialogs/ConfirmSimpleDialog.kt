package com.rumpilstilstkin.gloommaster.designsystem.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.designsystem.R
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloommaster.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloommaster.designsystem.icons.NavigationIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationDeleteDialog(
    title: String,
    description: String,
    onDeleteConfirm: () -> Unit,
    onClose: () -> Unit,
) {
    Column {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = description,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            // Кнопка Delete
            GloomOutlineButton(
                text = stringResource(R.string.design_system_delete),
                onClick = onDeleteConfirm,
                modifier =
                    Modifier
                        .weight(1f),
                isError = true,
                icon = AppIcon.Delete,
            )

            GloomOutlineButton(
                text = stringResource(R.string.design_system_close),
                onClick = onClose,
                modifier = Modifier.weight(1f),
                isError = false,
                icon = NavigationIcon.Close,
            )
        }
    }
}

@Preview
@Composable
private fun ConfirmationDeleteDialogPreview() {
    GloomhavenMasterTheme {
        ConfirmationDeleteDialog(
            title = "Delete something",
            description = "Are you sure?",
            onDeleteConfirm = {},
            onClose = {},
        )
    }
}
