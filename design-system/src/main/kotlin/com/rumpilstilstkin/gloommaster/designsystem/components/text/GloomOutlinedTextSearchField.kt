package com.rumpilstilstkin.gloommaster.designsystem.components.text

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme

@Composable
fun GloomOutlinedTextSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
) = OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    modifier = modifier,
    textStyle = MaterialTheme.typography.bodyLarge,
    placeholder = {
        Text(
            text = placeholder,
            style = MaterialTheme.typography.bodyLarge,
        )
    },
    leadingIcon = {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outlineVariant,
        )
    },
    shape = RoundedCornerShape(12.dp),
    colors =
        OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.outline,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedPlaceholderColor = MaterialTheme.colorScheme.outlineVariant,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.outlineVariant,
        ),
    singleLine = true,
)

@Preview
@Composable
private fun GloomOutlinedTextSearchFieldPreview() {
    GloomhavenMasterTheme {
        Box(modifier = Modifier.padding(40.dp)) {
            GloomOutlinedTextSearchField(
                value = "",
                placeholder = "label",
                onValueChange = {},
            )
        }
    }
}
