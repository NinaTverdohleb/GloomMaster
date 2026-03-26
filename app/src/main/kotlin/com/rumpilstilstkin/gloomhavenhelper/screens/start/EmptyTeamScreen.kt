package com.rumpilstilstkin.gloomhavenhelper.screens.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
internal fun EmptyTeamScreen(
    modifier: Modifier = Modifier,
    addTeam: () -> Unit
) = Column(
    modifier = modifier
        .fillMaxSize()
        .padding(32.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
) {
    Image(painter = painterResource(id = R.drawable.logo), contentDescription = "logo")
    Spacer(modifier = Modifier.height(48.dp))
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = addTeam
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Начать приключение",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Preview
@Composable
private fun EmptyTeamScreenPreview() {
    GloomhavenMasterTheme {
        EmptyTeamScreen(addTeam = {})
    }
}