package com.rumpilstilstkin.gloomhavenhelper.designsystem.components.dialogs

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GloomBasicModalBottomSheet(
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(),
    content: @Composable BoxScope.() -> Unit,
) = ModalBottomSheet(
    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
    contentColor = MaterialTheme.colorScheme.onSurface,
    scrimColor = MaterialTheme.colorScheme.scrim.copy(alpha = 0.32f),
    tonalElevation = 0.dp,
    shape =
        MaterialTheme.shapes.extraLarge.copy(
            bottomStart = CornerSize(0.dp),
            bottomEnd = CornerSize(0.dp),
        ),
    dragHandle = {
        BottomSheetDefaults.DragHandle(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    },
    onDismissRequest = onDismissRequest,
    sheetState = sheetState,
    properties =
        ModalBottomSheetProperties(
            shouldDismissOnBackPress = true,
            isAppearanceLightStatusBars = false,
            isAppearanceLightNavigationBars = false,
        ),
    contentWindowInsets = { WindowInsets.statusBars.union(WindowInsets.navigationBars) },
    content = {
        Box(
            modifier = Modifier
                .semantics { testTagsAsResourceId = true }
                .padding(16.dp),
        ) {
            content()
        }
    },
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun GloomBasicModalBottomSheetPreview() {
    GloomhavenMasterTheme {
        GloomBasicModalBottomSheet(
            onDismissRequest = {},
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp),
            )
        }
    }
}
