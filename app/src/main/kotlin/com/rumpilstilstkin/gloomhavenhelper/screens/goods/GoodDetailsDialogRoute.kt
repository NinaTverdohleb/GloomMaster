package com.rumpilstilstkin.gloomhavenhelper.screens.goods

import androidx.compose.runtime.Composable
import com.rumpilstilstkin.gloomhavenhelper.screens.core.OverlayContract
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi

object GoodDetailsDialogContract : OverlayContract<GoodUi, Unit> {
    @Composable
    override fun Content(
        input: GoodUi,
        onDismissWithResult: (Unit?) -> Unit,
    ) {
        GoodDetailsDialogRoute(
            good = input,
            close = onDismissWithResult,
        )
    }
}

@Composable
fun GoodDetailsDialogRoute(
    good: GoodUi,
    close: (Unit) -> Unit,
) {
    GoodDetailsDialog(
        good = good,
        dismiss = { close(Unit) },
    )
}
