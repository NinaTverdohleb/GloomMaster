package com.rumpilstilstkin.gloomhavenhelper.designsystem.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun GloomBasicDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val dialogModifier =
        modifier
            .semantics { testTagsAsResourceId = true }
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .padding(24.dp)
    if (LocalInspectionMode.current) {
        Box(modifier = dialogModifier) {
            content()
        }
    } else {
        BasicAlertDialog(
            onDismissRequest = onDismissRequest,
            modifier = dialogModifier,
            content = content,
        )
    }
}

@Preview
@Composable
private fun GloomBasicDialogPreview() {
    GloomhavenMasterTheme {
        GloomBasicDialog(
            onDismissRequest = {},
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(MaterialTheme.colorScheme.surfaceContainerHighest),
            )
        }
    }
}
