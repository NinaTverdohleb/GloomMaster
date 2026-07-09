package com.rumpilstilstkin.gloommaster.screens.start.team.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.GloomCard
import com.rumpilstilstkin.gloommaster.designsystem.components.text.GloomHeader
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.testtags.screens.start.team.component.TeamReputationTestTags

@Composable
internal fun TeamReputation(
    reputation: Int,
    discount: Int,
    modifier: Modifier = Modifier,
    updateReputation: (Int) -> Unit,
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(20.dp),
) {
    GloomHeader(
        stringResource(R.string.reputation),
    )
    GloomCard {
        LevelWithCounterView(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .testTag(TeamReputationTestTags.COUNTER),
            label = stringResource(R.string.shop_discount),
            level = discount.toString(),
            showSign = true,
            range = IntRange(-20, 20),
            counterValue = reputation,
        ) { newValue ->
            updateReputation(newValue)
        }
    }
}

@Preview
@Composable
private fun TeamReputationSample() {
    GloomhavenMasterTheme {
        TeamReputation(
            reputation = 2,
            discount = -1,
            updateReputation = {},
        )
    }
}
