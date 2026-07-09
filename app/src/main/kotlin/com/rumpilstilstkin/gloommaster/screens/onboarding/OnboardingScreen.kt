package com.rumpilstilstkin.gloommaster.screens.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomButton
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.screens.scenario.play.components.PageIndicator
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    pages: ImmutableList<OnboardingPage>,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()
    val isLastPage by remember {
        derivedStateOf { pagerState.targetPage == pages.lastIndex }
    }

    BackHandler(onBack = onFinish)
    Scaffold(
        topBar = {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(
                    onClick = onFinish,
                ) {
                    Text(
                        style = MaterialTheme.typography.labelMedium,
                        text = stringResource(R.string.onboarding_skip),
                    )
                }
            }
        },
    ) { contentPaddings ->
        Column(
            modifier =
                modifier
                    .padding(contentPaddings)
                    .fillMaxSize()
                    .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HorizontalPager(
                state = pagerState,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
            ) { page ->
                OnboardingPageContent(
                    page = pages[page],
                    modifier = Modifier.fillMaxSize(),
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            PageIndicator(pageState = pagerState)
            Spacer(modifier = Modifier.height(24.dp))

            val scroll = {
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.targetPage + 1)
                }
                Unit
            }

            if (isLastPage) {
                GloomButton(
                    text = stringResource(R.string.onboarding_start),
                    onClick = onFinish,
                    modifier = Modifier.fillMaxWidth(),
                )
            } else {
                GloomButton(
                    text = stringResource(R.string.onboarding_next),
                    onClick = scroll,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun OnboardingPageContent(
    page: OnboardingPage,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
    ) {
        Image(
            painter = painterResource(page.imageRes),
            contentDescription = null,
            modifier =
                Modifier
                    .fillMaxWidth(0.85f)
                    .weight(1f)
                    .padding(bottom = 32.dp),
        )
        Text(
            text = stringResource(page.titleRes),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(page.bodyRes),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    GloomhavenMasterTheme {
        OnboardingScreen(
            pages = defaultOnboardingPages,
            onFinish = {},
        )
    }
}

@Preview(device = "spec:width=1280dp,height=800dp,orientation=landscape")
@Composable
private fun OnboardingScreenTabletPreview() {
    GloomhavenMasterTheme {
        OnboardingScreen(
            pages = defaultOnboardingPages,
            onFinish = {},
        )
    }
}
