package com.rumpilstilstkin.gloomhavenhelper.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
fun GloomBottomNavigationBar(
    items: List<NavItem>,
    selectedItem: NavItem,
    selectTab: (NavItem) -> Unit,
) {
    NavigationBar(
        windowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        items.forEach { item ->
            val title = stringResource(item.titleRes)
            NavigationBarItem(
                selected = selectedItem == item,
                onClick = { selectTab(item) },
                icon = {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(item.iconRes),
                        contentDescription = title
                    )
                },
                label = { Text(title.replaceFirstChar { it.uppercase() }) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.outline,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.secondary,
                    unselectedTextColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }
}

interface NavItem {
    @get:StringRes
    val titleRes: Int
    @get:DrawableRes
    val iconRes: Int
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun CampaignEmptyStatePreview() {
    GloomhavenMasterTheme {
        GloomBottomNavigationBar(
            items = SampleScreenTab.entries,
            selectedItem = SampleScreenTab.PROSPERITY,
            selectTab = {}
        )
    }
}

private enum class SampleScreenTab(
    override val titleRes: Int,
    override val iconRes: Int
) : NavItem {
    INFO(R.string.app_name, R.drawable.ic_fly),
    PROSPERITY(R.string.app_name, R.drawable.ic_fly),
}