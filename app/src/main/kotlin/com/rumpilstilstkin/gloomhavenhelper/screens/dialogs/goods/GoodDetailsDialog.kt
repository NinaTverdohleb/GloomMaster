package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.goods

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialogCustomActions
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoodDetailsDialog(
    imagePath: String,
    modifier: Modifier = Modifier,
    buttonText: String = "Добавть",
    isActionPositive: Boolean = true,
    dismiss: () -> Unit,
    confirm: () -> Unit = {},
) {

    GloomAlertDialog(
        modifier = modifier,
        content = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier.size(
                        width = 240.dp,
                        height = 350.dp
                    ),
                    model = imagePath,
                    contentDescription = null
                )
            }
        },
        onDismissRequest = dismiss,
        onConfirmRequest = if (isActionPositive) confirm else null,
        confirmText = buttonText,
        negativeText = buttonText,
        onNegativeRequest = if (isActionPositive) null else confirm,
        onNeutralRequest = null,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoodDetailsDialogCustomActions(
    imagePath: String,
    modifier: Modifier = Modifier,
    dismiss: () -> Unit,
    actions: @Composable () -> Unit,
) {
    GloomAlertDialogCustomActions(
        modifier = modifier,
        content = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier.size(
                        width = 240.dp,
                        height = 350.dp
                    ),
                    model = imagePath,
                    contentDescription = null
                )
            }
        },
        onDismissRequest = dismiss,
        actions = actions
    )
}

@Preview
@Composable
private fun GoodDetailsDialogPreview() {
    GloomhavenHalperTheme {
        GoodDetailsDialog(
            imagePath = "",
            dismiss = {}
        )
    }
}