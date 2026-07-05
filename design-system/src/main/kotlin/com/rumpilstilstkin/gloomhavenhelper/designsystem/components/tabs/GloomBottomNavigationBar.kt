package com.rumpilstilstkin.gloomhavenhelper.designsystem.components.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.NavigationIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.testtags.components.NavigationBarTestTags

@Composable
fun GloomTopNavigationBar(
    items: List<NavItem>,
    selectedItem: NavItem,
    selectTab: (NavItem) -> Unit,
) {
    val selectedIndex = items.indexOf(selectedItem)
    PrimaryTabRow(
        selectedTabIndex = selectedIndex,
        modifier =
            Modifier
                .fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(selectedIndex, matchContentSize = false),
                width = Dp.Unspecified,
                height = 1.dp,
            )
        },
    ) {
        items.forEachIndexed { index, item ->
            val title = item.getTitle()
            LeadingIconTab(
                modifier = Modifier.testTag(NavigationBarTestTags.tabTop(index)),
                selected = selectedItem == item,
                onClick = { selectTab(item) },
                text = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelMedium,
                    )
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = item.icon.painter(),
                        contentDescription = title,
                    )
                },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
fun GloomBottomNavigationBar(
    items: List<NavItem>,
    selectedItem: NavItem,
    selectTab: (NavItem) -> Unit,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        items.forEachIndexed { index, item ->
            val title = item.getTitle()
            NavigationBarItem(
                modifier = Modifier.testTag(NavigationBarTestTags.tabBottom(index)),
                selected = selectedItem == item,
                onClick = { selectTab(item) },
                icon = {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = item.icon.painter(),
                        contentDescription = title,
                    )
                },
                label = {
                    Text(
                        style = MaterialTheme.typography.labelMedium,
                        text = title.replaceFirstChar { it.uppercase() },
                    )
                },
                colors =
                    NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = Color.Transparent,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
            )
        }
    }
}

interface NavItem {
    val icon: NavigationIcon

    @Composable
    fun getTitle(): String
}

@Preview
@Composable
fun CampaignEmptyStatePreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GloomBottomNavigationBar(
                items = SampleScreenTab.entries,
                selectedItem = SampleScreenTab.PROSPERITY,
                selectTab = {},
            )

            GloomTopNavigationBar(
                items = SampleScreenTab.entries,
                selectedItem = SampleScreenTab.PROSPERITY,
                selectTab = {},
            )
        }
    }
}

private enum class SampleScreenTab(
    private val title: String,
    override val icon: NavigationIcon,
) : NavItem {
    INFO("Tab 1", NavigationIcon.Team),
    PROSPERITY("Tab 1", NavigationIcon.Team),
    ;

    @Composable
    override fun getTitle(): String = title
}
