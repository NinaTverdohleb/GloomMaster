package com.rumpilstilstkin.gloommaster.screens.goods

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloommaster.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.screens.models.GoodUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoodDetailsDialog(
    good: GoodUi,
    dismiss: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            modifier =
                Modifier.size(
                    width = 240.dp,
                    height = 350.dp,
                ),
            model = good.imagePath,
            contentDescription = null,
        )
        Spacer(Modifier.height(24.dp))
        GloomOutlineButton(
            text = stringResource(R.string.ok),
            onClick = dismiss,
            modifier = Modifier.fillMaxWidth(),
            isError = false,
            icon = AppIcon.Check,
        )
    }
}

@Preview
@Composable
private fun GoodDetailsDialogPreview() {
    GloomhavenMasterTheme {
        GoodDetailsDialog(
            good = GoodUi.fixture(),
            dismiss = {},
        )
    }
}
