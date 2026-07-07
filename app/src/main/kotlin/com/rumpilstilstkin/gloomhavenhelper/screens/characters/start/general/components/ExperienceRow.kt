package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomOutlineFilledButtonIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.counter.GloomCounterFull
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.counter.GloomGloomCounterProgress
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.characters.start.CharacterGeneralTabTestTags

@Composable
fun ExperienceRow(
    experience: Int,
    nextLevelExperience: Int,
    modifier: Modifier = Modifier,
    levelRange: IntRange = 0..500,
    onLevelUp: () -> Unit,
    onExperienceChanged: (Int) -> Unit,
) = GloomCard(
    modifier = modifier,
) {
    val isCanLevelUp: Boolean = experience >= nextLevelExperience
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    style = MaterialTheme.typography.headlineSmall,
                    text = stringResource(R.string.experience_title),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            GloomCounterFull(
                modifier = Modifier.testTag(CharacterGeneralTabTestTags.EXPERIENCE_COUNTER),
                value = experience,
                intRange = levelRange,
                repeat = true,
                onValueChange = onExperienceChanged,
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        GloomGloomCounterProgress(
            value = experience,
            intRange = levelRange,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.next_level_format, nextLevelExperience),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )

            GloomOutlineFilledButtonIcon(
                modifier = Modifier.testTag(CharacterGeneralTabTestTags.LEVEL_UP),
                icon = AppIcon.Up,
                onClick = onLevelUp,
                enabled = isCanLevelUp,
            )
        }
    }
}

@Preview
@Composable
private fun ExperienceRowPreview() {
    GloomhavenMasterTheme {
        ExperienceRow(
            experience = 150,
            nextLevelExperience = 200,
            onLevelUp = {},
            onExperienceChanged = {},
        )
    }
}
