package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.goods.delete

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.dialogs.ConfirmationDeleteDialog
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun DeleteGoodDialog(
    name: String,
    delete: () -> Unit,
    close: () -> Unit,
) {
    ConfirmationDeleteDialog(
        title = stringResource(R.string.delete_good_dialog_title),
        description =
            stringResource(
                R.string.delete_good_dialog_description,
                name,
            ),
        onDeleteConfirm = delete,
        onClose = close,
    )
}

@Preview
@Composable
private fun DeleteGoodDialogPreview() {
    GloomhavenMasterTheme {
        DeleteGoodDialog(
            name = "Boots of Speed",
            delete = {},
            close = {},
        )
    }
}
