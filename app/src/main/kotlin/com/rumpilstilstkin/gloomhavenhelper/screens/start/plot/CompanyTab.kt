package com.rumpilstilstkin.gloomhavenhelper.screens.start.plot

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun CompanyTab(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    EmptyCompanyTab() {}

}


@Composable
fun EmptyCompanyTab(
    modifier: Modifier = Modifier,
    startGame: () -> Unit
) {
    Column(
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
            onClick = {
                startGame.invoke()
            }) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Начать приключение",
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun ContentCompanyTab(modifier: Modifier = Modifier) {

}


@Preview
@Composable
private fun EmptySample() {
    GloomhavenHalperTheme {
        EmptyCompanyTab() {}
    }
}

@Preview
@Composable
private fun ContentSample() {
    GloomhavenHalperTheme {
        ContentCompanyTab()
    }
}