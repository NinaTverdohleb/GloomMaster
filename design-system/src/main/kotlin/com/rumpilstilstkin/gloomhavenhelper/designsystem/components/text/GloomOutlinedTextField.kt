package com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun GloomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) = OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    modifier = modifier,
    textStyle = MaterialTheme.typography.bodyLarge,
    label = {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
        )
    },
    shape = RoundedCornerShape(12.dp),
    colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaterialTheme.colorScheme.outline,
        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
        focusedLabelColor = MaterialTheme.colorScheme.onSurface,
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
    ),
    singleLine = true
)

@Preview
@Composable
private fun GloomOutlinedTextFieldPreview() {
    GloomhavenMasterTheme {
        Box(modifier = Modifier.padding(40.dp)) {
            GloomOutlinedTextField(
                value = "",
                label = "label",
                onValueChange = {}
            )
        }
    }
}