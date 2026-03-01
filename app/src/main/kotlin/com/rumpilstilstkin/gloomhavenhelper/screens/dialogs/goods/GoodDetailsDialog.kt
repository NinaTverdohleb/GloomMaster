package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.goods

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoodDetailsDialog(
    goodNumber: Int,
    showDialog: Boolean,
    imagePath: String,
    modifier: Modifier = Modifier,
    buttonText: String = "Добавть",
    isAction: Boolean = true,
    onDismiss: () -> Unit = {},
    onAction: (Int) -> Unit = {},
) {
    if (!showDialog) return

    BasicAlertDialog(
        modifier = modifier,
        content = {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black
                )

            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    AsyncImage(
                        modifier = Modifier.size(
                            width = 240.dp,
                            height = 350.dp
                        ),
                        model = imagePath,
                        contentDescription = null
                    )
                    if(isAction) {
                        Button(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .width(240.dp),
                            onClick = { onAction(goodNumber) }
                        ) {
                            Text(buttonText)
                        }
                    }
                }
            }
        },
        onDismissRequest = { onDismiss() },
    )
}

@Preview
@Composable
private fun GoodDetailsDialogPreview() {
    GloomhavenHalperTheme {
        GoodDetailsDialog(
            goodNumber = 19,
            showDialog = true,
            imagePath = ""
        )
    }
}