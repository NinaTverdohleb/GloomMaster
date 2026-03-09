package com.rumpilstilstkin.gloomhavenhelper.screens.characters.quests.freeselect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterTaskItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.QuestReward
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.quests.components.PersonalQuestItem
import com.rumpilstilstkin.gloomhavenhelper.screens.models.PersonalQuestUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.QuestTaskPhaseUI
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SearchQuestScreen(
    characterId: Int,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val viewModel =
        hiltViewModel<SearchQuestViewModel, SearchQuestViewModel.Factory> { factory ->
            factory.create(characterId)
        }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val navigationEvents by viewModel.navigationEvents.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(navigationEvents) {
        navigationEvents?.let { event ->
            GlHelperEventHelper.event(
                event = event,
                navController = navController
            )
        }
    }

    SearchQuestView(
        state = uiState,
        modifier = modifier,
        onAction = viewModel::onAction,
    )
}

@Composable
private fun SearchQuestView(
    state: SearchQuestState,
    modifier: Modifier = Modifier,
    onAction: (SearchQuestActions) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            value = state.searchText,
            onValueChange = { onAction(SearchQuestActions.SearchTextChange(it)) },
            label = { Text("Название или номер") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                cursorColor = MaterialTheme.colorScheme.primary,
            )
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        LazyColumn(
            modifier = Modifier.weight(1f),
        ) {
            items(state.quests) { quest ->
                PersonalQuestItem(
                    modifier = Modifier
                        .padding(8.dp)
                        .animateItem(),
                    quest = quest,
                    chooseQuest = { onAction(SearchQuestActions.ChooseQuest(quest.id)) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchQuestPreview() {
    GloomhavenHalperTheme {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            SearchQuestView(
                state = SearchQuestState(
                    quests = persistentListOf(
                        PersonalQuestUI(
                            id = "511",
                            title = "Искатель Ксорна",
                            description = "Еше с детства ты слышал зов Ксорна. Когда-то его почитали как бога, но его паства давно уничтожена. Но ты можешь слышать его зов. Ты отправтлся в Глумхевен по его приказу. Ты найдешь нго останки и освободишь его. Случившееся однажды повторится снова.",
                            reward = QuestReward(
                                classType = CharacterClassType.Plagueherald,
                            ),
                            phases = persistentListOf(
                                QuestTaskPhaseUI(
                                    priority = 0,
                                    tasks = persistentListOf(
                                        CharacterTaskItem.Count(
                                            priority = 0,
                                            text = "Пройдите три сценария с названием Склеп",
                                            count = 3,
                                            currentCount = 0,
                                            id = 1
                                        )
                                    )
                                ),
                                QuestTaskPhaseUI(
                                    priority = 1,
                                    tasks = persistentListOf(
                                        CharacterTaskItem.Check(
                                            priority = 1,
                                            text = "Откройте и пройдите полностью сенарий \"Жуткий погреб\"",
                                            id = 2
                                        )
                                    )
                                )
                            )
                        ),
                        PersonalQuestUI(
                            id = "512",
                            title = "Искатель Ксорна",
                            description = "Еше с детства ты слышал зов Ксорна. Когда-то его почитали как бога, но его паства давно уничтожена. Но ты можешь слышать его зов. Ты отправтлся в Глумхевен по его приказу. Ты найдешь нго останки и освободишь его. Случившееся однажды повторится снова.",
                            reward = QuestReward(
                                classType = CharacterClassType.Plagueherald,
                            ),
                            phases = persistentListOf(
                                QuestTaskPhaseUI(
                                    priority = 0,
                                    tasks = persistentListOf(
                                        CharacterTaskItem.Count(
                                            priority = 0,
                                            text = "Пройдите три сценария с названием Склеп",
                                            count = 3,
                                            currentCount = 0,
                                            id = 1
                                        )
                                    )
                                ),
                                QuestTaskPhaseUI(
                                    priority = 1,
                                    tasks = persistentListOf(
                                        CharacterTaskItem.Check(
                                            priority = 1,
                                            text = "Откройте и пройдите полностью сенарий \"Жуткий погреб\"",
                                            id = 2
                                        )
                                    )
                                )
                            )
                        )
                    )
                ),
                onAction = {},
            )
        }
    }
}

