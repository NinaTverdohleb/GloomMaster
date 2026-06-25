package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun PageIndicator(
    pageState: PagerState,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(pageState.pageCount) { iteration ->
            val color =
                if (pageState.currentPage == iteration) {
                    MaterialTheme.colorScheme.surfaceTint
                } else {
                    MaterialTheme.colorScheme.surfaceBright
                }

            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(8.dp),
            )
        }
    }
}

@Preview
@Composable
private fun PageIndicatorPreview() {
    GloomhavenMasterTheme {
        PageIndicator(
            pageState = PagerState ( pageCount = { 4 } )
        )
    }
}