package com.rumpilstilstkin.gloomhavenhelper.screens.characters.general

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme
import com.rumpilstilstkin.gloomhavenhelper.ui.view.NumberPicker

@Composable
fun CharacterGeneralTab(
    characterId: Int,
    modifier: Modifier = Modifier
) {
    val viewModel =
        hiltViewModel<CharacterGeneralTabViewModel, CharacterGeneralTabViewModel.Factory> { factory ->
            factory.create(characterId)
        }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CharacterGeneralTabContent(
        content = uiState,
        modifier = modifier,
        onAction = { viewModel.onAction(it) },
    )
}

@Composable
fun CharacterGeneralTabContent(
    content: CharacterGeneralTabState,
    modifier: Modifier = Modifier,
    onAction: (GeneralTabActions) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(12.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ExperienceRow(
            experience = content.experience,
            nextLevelExperience = content.nextLevel,
            onLevelUp = { onAction(GeneralTabActions.LevelUp) },
            onExperienceChanged = { onAction(GeneralTabActions.ExperienceChanged(it)) }
        )
        Spacer(modifier = Modifier.height(32.dp))

        GoldRow(
            goldCount = content.gold,
            isDonateAvailable = content.isDonateAvailable,
            onGoldChanged = { onAction(GeneralTabActions.GoldChanged(it)) },
            onDonate = { onAction(GeneralTabActions.Donate) }
        )

        Spacer(modifier = Modifier.height(32.dp))
        NoticeRow(
            checkMarkCount = content.checkMarks,
            notice = content.notes,
            onCheckedChange = { onAction(GeneralTabActions.CheckedChange(it)) },
            onNoticeChanged = { onAction(GeneralTabActions.NoticeChanged(it)) }
        )
    }
}

@Composable
fun NoticeRow(
    checkMarkCount: Int,
    notice: String,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit,
    onNoticeChanged: (String) -> Unit
) {

    // add characterDialog
    var showNoticeDialog by remember { mutableStateOf(false) }

    NoticeDialog(
        text = notice,
        showDialog = showNoticeDialog,
        onDismiss = { showNoticeDialog = false },
        onNoticeChanged = { text ->
            onNoticeChanged(text)
            showNoticeDialog = false
        }
    )

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = "Заметки",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))

        CheckMarks(checkMarkCount = checkMarkCount, onCheckedChange = onCheckedChange)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Текст",
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (notice.isNotEmpty()) {
            Text(text = notice)
            Spacer(modifier = Modifier.height(8.dp))
        }
        OutlinedButton(
            onClick = {
                showNoticeDialog = true
            },
        ) {
            Text(
                text = "Редактировать текст",
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun NoticeDialog(
    text: String,
    showDialog: Boolean,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onNoticeChanged: (String) -> Unit,
) {
    if (showDialog) {
        var newNotice by rememberSaveable { mutableStateOf(text) }

        AlertDialog(
            modifier = modifier,
            onDismissRequest = { onDismiss.invoke() },
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Заметки",
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Box {
                    OutlinedTextField(
                        modifier = Modifier.defaultMinSize(minHeight = 240.dp),
                        value = newNotice,
                        onValueChange = { newNotice = it },
                        label = { Text("Заметки") }
                    )
                }
            },
            confirmButton = {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onNoticeChanged.invoke(newNotice)
                    }
                ) {
                    Text("Сохранить")
                }
            }
        )
    }

}

@Composable
fun CheckMarks(
    checkMarkCount: Int,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit
) {
    val fullCount = checkMarkCount / 3
    val remainder = checkMarkCount % 3
    Column {
        for (i in 0..2)
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (j in 0.. 1) {
                    val t = i*2 + j
                    val count = if (t < fullCount) 3 else if (t == fullCount) remainder else 0
                    CheckMarkRow(
                        fillCount = count,
                        onCheckedChange = onCheckedChange
                    )
                    Spacer(modifier = Modifier.width(32.dp))
                }
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckMarkRow(
    fillCount: Int,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier.padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = ":",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
        )

        Spacer(modifier = Modifier.width(6.dp))
        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            for (i in 0..2) {
                Checkbox(
                    modifier = Modifier,
                    checked = i < fillCount,
                    onCheckedChange = { onCheckedChange(it) },
                    colors = CheckboxDefaults.colors().copy(
                        uncheckedBorderColor = MaterialTheme.colorScheme.primary,
                    )
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
        }

    }
}


@Composable
fun GoldRow(
    goldCount: Int,
    isDonateAvailable: Boolean,
    modifier: Modifier = Modifier,
    goldRange: IntRange = 0..1000000,
    onGoldChanged: (Int) -> Unit,
    onDonate: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Золото",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NumberPicker(
                value = goldCount,
                intRange = goldRange,
                onValueChange = onGoldChanged
            )

            Button(
                onClick = onDonate,
                enabled = isDonateAvailable
            ) {
                Text(
                    text = "Пожертвовать",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ExperienceRow(
    experience: Int,
    nextLevelExperience: Int,
    modifier: Modifier = Modifier,
    isCanLevelUp: Boolean = experience > nextLevelExperience,
    levelRange: IntRange = 0..500,
    onLevelUp: () -> Unit,
    onExperienceChanged: (Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {

        Text(
            text = "Опыт",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NumberPicker(
                value = experience,
                intRange = levelRange,
                onValueChange = onExperienceChanged
            )

            Button(
                onClick = onLevelUp,
                enabled = isCanLevelUp
            ) {
                Text(
                    text = "Повысить уровень",
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Следующий уровень: $nextLevelExperience")
    }
}

@Preview
@Composable
private fun Sample() {
    GloomhavenHalperTheme {
        CharacterGeneralTabContent(
            content = CharacterGeneralTabState(
                experience = 150,
                gold = 10,
                checkMarks = 15,
                hasTeam = false,
                teamName = null,
                nextLevel = 175,
                notes = "Некоторые заметки"
            ),
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun SampleNoticeDialog() {
    GloomhavenHalperTheme {
        NoticeDialog(
            text = "",
            showDialog = true,
            onDismiss = { /*TODO*/ },
            onNoticeChanged = {}
        )
    }
}

