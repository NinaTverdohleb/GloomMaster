package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.tabs.GloomTopNavigationBar
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.tabs.NavItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.toolbar.GloomToolbar
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.NavigationIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.CharacterGeneralTabRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.goods.CharacterItemsTabRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.perks.CharacterPerksTabRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI
import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.characters.start.CharacterDetailsTestTags
import com.rumpilstilstkin.gloomhavenhelper.ui.characters.CharacterHeaderItem
import kotlinx.coroutines.launch

@Composable
internal fun CharacterDetailsScreen(
    state: CharacterDetailsStateUi.Data,
    back: () -> Unit,
    showNameDialog: (CharacterUI) -> Unit,
    navController: NavHostController,
) {
    Scaffold(
        topBar = {
            GloomToolbar(
                title = stringResource(R.string.character_details_title),
                back = back,
            )
        },
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(paddingValues),
        ) {
            CharacterHeaderItem(
                modifier = Modifier.testTag(CharacterDetailsTestTags.HEADER),
                character = state.character,
                onClick = showNameDialog,
            )

            CharactersTabs(
                characterId = state.character.id,
                navController = navController,
            )
        }
    }
}

internal enum class CharacterDetailsTab(
    private val titleRes: Int,
    override val icon: NavigationIcon,
) : NavItem {
    GENERAL(R.string.tab_general, NavigationIcon.CharacterGeneral),
    STUFF(R.string.tab_items, NavigationIcon.CharacterGoods),
    SKILLS(R.string.tab_skills, NavigationIcon.CharacterPerks),
    ;

    @Composable
    override fun getTitle(): String = stringResource(titleRes)
}

@Composable
internal fun CharactersTabs(
    characterId: Int,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val tabs = CharacterDetailsTab.entries
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val selectedTab = tabs[pagerState.currentPage]
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier =
            modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
    ) {
        GloomTopNavigationBar(
            items = tabs,
            selectedItem = selectedTab,
            selectTab = { tab ->
                coroutineScope.launch {
                    pagerState.animateScrollToPage(tabs.indexOf(tab))
                }
            },
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            beyondViewportPageCount = tabs.size,
            key = { page -> tabs[page].icon },
        ) { page ->
            when (tabs[page]) {
                CharacterDetailsTab.GENERAL -> CharacterGeneralTabRoute(characterId, navController)
                CharacterDetailsTab.STUFF -> CharacterItemsTabRoute(characterId, navController)
                CharacterDetailsTab.SKILLS -> CharacterPerksTabRoute(characterId, navController)
            }
        }
    }
}

@Preview
@Composable
private fun CharacterDetailsScreenPreview() {
    GloomhavenMasterTheme {
        CharacterDetailsScreen(
            state =
                CharacterDetailsStateUi.Data(
                    character = CharacterUI.fixture(),
                ),
            back = {},
            showNameDialog = {},
            navController = rememberNavController(),
        )
    }
}
