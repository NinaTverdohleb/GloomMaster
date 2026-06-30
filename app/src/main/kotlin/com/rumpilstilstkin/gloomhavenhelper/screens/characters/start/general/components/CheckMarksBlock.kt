package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.GloomHeader
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.characters.start.CharacterGeneralTabTestTags

@Composable
fun CheckMarksBlock(
    checkMarkCount: Int,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        GloomHeader(
            stringResource(R.string.notes_title),
        )

        CheckMarks(
            modifier = Modifier.fillMaxWidth(),
            checkedCount = checkMarkCount,
            onClick = onCheckedChange,
        )
    }
}

@Composable
fun CheckMarks(
    checkedCount: Int,
    modifier: Modifier = Modifier,
    onClick: (Boolean) -> Unit,
    perksPerGroup: Int = 3,
    groupsPerRow: Int = 3,
    total: Int = 18,
) {
    GloomCard(modifier = modifier) {
        FlowRow(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            maxItemsInEachRow = groupsPerRow,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            (0 until total).chunked(perksPerGroup).forEach { group ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    group.forEach { index ->
                        val isChecked = index < checkedCount
                        CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
                            Checkbox(
                                modifier =
                                    Modifier
                                        .padding(2.dp)
                                        .testTag(CharacterGeneralTabTestTags.CHECK_MARK),
                                checked = isChecked,
                                onCheckedChange = { onClick(!isChecked) },
                                colors =
                                    CheckboxDefaults.colors(
                                        checkedColor = MaterialTheme.colorScheme.primary,
                                        checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                                        uncheckedColor = MaterialTheme.colorScheme.outline,
                                    ),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CheckMarksBlockPreview() {
    GloomhavenMasterTheme {
        CheckMarksBlock(
            checkMarkCount = 9,
            onCheckedChange = {},
        )
    }
}
