package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
fun MonsterCardHeader(
    name: String,
    isFly: Boolean,
    modifier: Modifier = Modifier,
    delete: () -> Unit,
    onAddUnit: (() -> Unit)? = null
) = Row(
    modifier = modifier
        .fillMaxWidth()
        .height(64.dp)
        .padding(vertical = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
) {
    Row(
        modifier = Modifier.weight(1f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
        )

        if (isFly) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                modifier = Modifier.size(26.dp),
                painter = painterResource(id = R.drawable.ic_fly),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
    if (onAddUnit != null) {

        OutlinedButton(
            onClick = onAddUnit,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.secondary,
            ),
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp),
        ) {
            Text(
                text = "+ Добавить врага",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }

        Spacer(Modifier.width(8.dp))
    }
    IconButton(
        onClick = delete,
        modifier = Modifier.size(32.dp),
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(24.dp),
        )
    }
}

@Preview
@Composable
private fun MonsterCardHeaderPreview() {
    GloomhavenMasterTheme {
        MonsterCardHeader(
            delete = {},
            name = "Разбойник страж",
            isFly = true,
            onAddUnit = {}
        )
    }
}