package com.rumpilstilstkin.gloomhavenhelper.bd.filler

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.ScenarioDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.ScenarioBd

object ScenariosFiller {
    suspend fun fill_v1(
        scenarioDao: ScenarioDao
    ) {
        scenarioDao.insertAll(
            ScenarioBd(
                scenarioNumber = 1,
                name = "Черный курган",
                newScenarios = "2",
                teamAchievement = "Первые шаги",
                monsters = listOf(
                    "Разбойник-страж",
                    "Разбойник-лучница",
                    "Ожившие кости"
                ),
                location = "Мертволесье"
            ),
            ScenarioBd(
                scenarioNumber = 2,
                name = "Подземное логово",
                newScenarios = "3, 4",
                requirements = "Получено достижение отряда Первые шаги",
                monsters = listOf(
                    "Разбойник-лучница",
                    "Ожившие кости",
                    "Оживший труп",
                    "Главарь разбойников"
                ),
                location = "Мертволесье"
            ),
            ScenarioBd(
                scenarioNumber = 3,
                name = "Лагерь иноксов",
                newScenarios = "8, 9",
                requirements = "Не получено общее достижение Сбежавшая торговка",
                teamAchievement = "Планы Джексеры",
                monsters = listOf(
                    "Инокс-стражница",
                    "Инокс-лучник",
                    "Инокс-шаман",
                ),
                location = "Кинжальный лес"
            ),
            ScenarioBd(
                scenarioNumber = 4,
                name = "Склеп проклятых",
                newScenarios = "5, 6",
                monsters = listOf(
                    "Разбойник-лучница",
                    "Ожившие кости",
                    "Культист",
                    "Земляной демон",
                    "Воздушный демон"
                ),
                location = "Тихая река"
            ),
            ScenarioBd(
                scenarioNumber = 5,
                name = "Зловещий склеп",
                newScenarios = "10, 14, 19",
                monsters = listOf(
                    "Ожившие кости",
                    "Культист",
                    "Ночной демон",
                    "Огненный демон",
                    "Морозный демон",
                ),
                location = "Медные хребты"
            ),
            ScenarioBd(
                scenarioNumber = 6,
                name = "Обветшалый склеп",
                newScenarios = "8",
                teamAchievement = "Планы Джексеры, Темная награда",
                monsters = listOf(
                    "Оживший дух",
                    "Оживший труп",
                    "Ожившие кости",
                ),
                location = "Тихая река"
            ),
            ScenarioBd(
                scenarioNumber = 7,
                name = "Цветущий грот",
                newScenarios = "20",
                requirements = "Получены общие достижения Возможость улучшений и Сбежавшая торговка",
                monsters = listOf(
                    "Лесной бес",
                    "Пещерный медведь",
                    "Инокс-шаман",
                    "Земляной демон",
                ),
                location = "Медные хребты"
            ),
            ScenarioBd(
                scenarioNumber = 8,
                name = "Склад мрачной гавани",
                newScenarios = "7, 13, 14",
                globalAchievement = "Сбежавшая торговка",
                requirements = "Получено достижение отряда Планы Джексеры и не получено общее достижение Вторжение мертвецов",
                monsters = listOf(
                    "Ожившие кости",
                    "Оживший труп",
                    "Инокс-телохранитель - 1",
                    "Инокс-телохранитель - 2",
                ),
                location = "Мрачная гавань"
            ),
            ScenarioBd(
                scenarioNumber = 9,
                name = "Алмазная шахта",
                newScenarios = "11, 12",
                globalAchievement = "Вторжение мертвецов",
                requirements = "Не получено общее достижение Сбежавшая торговка",
                monsters = listOf(
                    "Гончая",
                    "Вермлинг-разведчик",
                    "Жестокий надзиратель",
                ),
                location = "Сторожевые горы"
            ),
            ScenarioBd(
                scenarioNumber = 10,
                name = "Измерение силы стихий",
                newScenarios = "21, 22",
                teamAchievement = "Задание демона",
                requirements = "Не получено общее достижение Портал обезврежен",
                monsters = listOf(
                    "Огненный демон",
                    "Земляной демон",
                    "Солнечный демон",
                ),
                location = "Медные хребты"
            ),
            ScenarioBd(
                scenarioNumber = 11,
                name = "Площадь Мрачной гавани А",
                newScenarios = "16, 18",
                globalAchievement = "Городское правление: торговцы, Конец вторжения",
                requirements = "Не получено общее достижение Конец вторжения",
                monsters = listOf(
                    "Ожившие кости",
                    "Оживший труп",
                    "Городской страж",
                    "Городской лучник",
                    "Начальник стражи"
                ),
                location = "Мрачная гавань"
            ),
            ScenarioBd(
                scenarioNumber = 12,
                name = "Площадь Мрачной гавани Б",
                newScenarios = "16, 18, 28",
                globalAchievement = "Конец вторжения",
                requirements = "Не получено общее достижение Конец вторжения",
                monsters = listOf(
                    "Ожившие кости",
                    "Оживший труп",
                    "Культист",
                    "Городской страж",
                    "Городской лучник",
                    "Джексера"
                ),
                location = "Мрачная гавань"
            ),
            ScenarioBd(
                scenarioNumber = 13,
                name = "Храм прорицателя",
                newScenarios = "15, 17, 20",
                monsters = listOf(
                    "Каменный голем",
                    "Пещерный медведь",
                    "Оживший дух",
                    "Шипящий дрейк",
                ),
                location = "Сторожевые горы"
            ),
            ScenarioBd(
                scenarioNumber = 14,
                name = "Ледяная пещера",
                newScenarios = "",
                globalAchievement = "Возможость улучшений",
                monsters = listOf(
                    "Гончая",
                    "Оживший дух",
                    "Морозный демон",
                ),
                location = "Медные хребты"
            ),
            ScenarioBd(
                scenarioNumber = 15,
                name = "Святилище силы",
                monsters = listOf(
                    "Каменный голем",
                    "Саввас Ледяной шторм",
                    "Воздушный демон",
                    "Морозный демон",
                    "Жнец заразитель",
                ),
                location = "Медные хребты"
            ),
            ScenarioBd(
                scenarioNumber = 16,
                name = "Горный перевал",
                newScenarios = "24, 25",
                monsters = listOf(
                    "Земляной демон",
                    "Воздушный демон",
                    "Инокс-стражница",
                    "Инокс-лучник",
                ),
                location = "Медные хребты"
            ),
            ScenarioBd(
                scenarioNumber = 17,
                name = "Затерянный остров",
                monsters = listOf(
                    "Вермлинг-разведчик",
                    "Вермлинг-шаман",
                    "Пещерный медведь",
                ),
                location = "Туманное море"
            ),
            ScenarioBd(
                scenarioNumber = 18,
                name = "Заброшенная канализация",
                newScenarios = "14, 23, 26, 43",
                monsters = listOf(
                    "Вермлинг-разведчик",
                    "Гигантская гадюка",
                    "Слизь",
                ),
                location = "Мрачная гавань"
            ),
            ScenarioBd(
                scenarioNumber = 19,
                name = "Забытый склеп",
                newScenarios = "27",
                requirements = "Получено общее достижение Возможость улучшений",
                teamAchievement = "Кадило Каменолома",
                monsters = listOf(
                    "Культист",
                    "Ожившие кости",
                    "Оживший дух",
                    "Оживший труп",
                ),
                location = "Вечное болото"
            ),
            ScenarioBd(
                scenarioNumber = 20,
                name = "Прибежище некромантки",
                newScenarios = "16, 18, 28",
                requirements = "Получено общее достижение Сбежавшая торговка",
                monsters = listOf(
                    "Культист",
                    "Ожившие кости",
                    "Ночной демон",
                    "Оживший труп",
                    "Джексера",
                ),
                location = "Мертволесье"
            ),
            ScenarioBd(
                scenarioNumber = 21,
                name = "Дьявольский трон",
                requirements = "Не получено общее достижение Портал обезврежен",
                globalAchievement = "Портал обезврежен, Артефакт: найден",
                monsters = listOf(
                    "Солнечный демон",
                    "Морозный демон",
                    "Ночной демон",
                    "Воздушный демон",
                    "Земляной демон",
                    "Огненный демон",
                    "Архидемон"
                ),
                location = "Медные хребты"
            ),
            ScenarioBd(
                scenarioNumber = 22,
                name = "Храм стихий",
                newScenarios = "31, 35, 36",
                requirements = "Получено достижение отряда Задание демона или По следам",
                globalAchievement = "Артефакт: найден",
                monsters = listOf(
                    "Ожившие кости",
                    "Культист",
                    "Морозный демон",
                    "Воздушный демон",
                    "Земляной демон",
                    "Огненный демон",
                ),
                location = "Река Змеиный поцелуй"
            ),
            ScenarioBd(
                scenarioNumber = 23,
                name = "Глубокие руины",
                newScenarios = "26",
                globalAchievement = "Древняя технология",
                teamAchievement = "Путь через руины",
                monsters = listOf(
                    "Каменный голем",
                    "Древняя пушка",
                    "Ожившие кости",
                    "Оживший дух",
                ),
                location = "Мрачная гавань"
            ),
            ScenarioBd(
                scenarioNumber = 24,
                name = "Шепчущая галерея",
                newScenarios = "30, 32",
                teamAchievement = "Веление Голоса",
                monsters = listOf(
                    "Когтистый дрейк",
                    "Слизь",
                    "Оживший дух",
                ),
                location = "Медные хребты"
            ),
            ScenarioBd(
                scenarioNumber = 25,
                name = "Восхождение на Ледяной утес",
                newScenarios = "33, 34",
                teamAchievement = "Предложение дрейка",
                monsters = listOf(
                    "Когтистый дрейк",
                    "Шипящий дрейк",
                    "Гончая",
                ),
                location = "Медные хребты"
            ),
            ScenarioBd(
                scenarioNumber = 26,
                name = "Древний резервуар",
                requirements = "Получено общее достижение Подводное дыхание или Путь через руины",
                newScenarios = "22",
                teamAchievement = "По следам",
                monsters = listOf(
                    "Оживший труп",
                    "Слизь",
                    "Ночной демон",
                    "Черный бес"
                ),
                location = "Мрачная гавань"
            ),
            ScenarioBd(
                scenarioNumber = 27,
                name = "Зловещий портал",
                requirements = "Не получено общее достижение Артефакт: потерян и получено достижение отряда Кадило Каменолома",
                globalAchievement = "Портал обезврежен",
                monsters = listOf(
                    "Ночной демон",
                    "Воздушный демон",
                    "Морозный демон",
                    "Солнечный демон",
                    "Земляной демон",
                    "Огненный демон"
                ),
                location = "Медные хребты"
            ),
            ScenarioBd(
                scenarioNumber = 28,
                name = "Внешнее капище",
                requirements = "Получено общее достижение Темная награда",
                newScenarios = "29",
                teamAchievement = "Приглашение",
                monsters = listOf(
                    "Ночной демон",
                    "Оживший труп",
                    "Ожившие кости",
                    "Солнечный демон",
                    "Культист",
                ),
                location = "Кинжальный лес"
            ),
            ScenarioBd(
                scenarioNumber = 29,
                name = "Обитель Мрака",
                requirements = "Получено достижение отряда Приглашение",
                globalAchievement = "На грани тьмы",
                monsters = listOf(
                    "Ожившие кости",
                    "Оживший дух",
                    "Оживший труп",
                    "Черный бес",
                ),
                location = "Кинжальный лес"
            ),
            ScenarioBd(
                scenarioNumber = 30,
                name = "Святилище глубин",
                requirements = "Получено достижение отряда Веление Голоса",
                newScenarios = "42",
                teamAchievement = "Голос и скипитер",
                monsters = listOf(
                    "Слизь",
                    "Скрытень",
                    "Невыносимый ужас",
                ),
                location = "Туманное море"
            ),
            ScenarioBd(
                scenarioNumber = 31,
                name = "Ночное измерение",
                requirements = "Получено общее достижение Возможость улучшений и Артефакт: найден",
                newScenarios = "37, 38, 39, 43",
                globalAchievement = "Артефакт: очищен",
                monsters = listOf(
                    "Черный бес",
                    "Ночной демон",
                    "Невыносимый ужас",
                ),
                location = "Мрачная гавань"
            ),
            ScenarioBd(
                scenarioNumber = 32,
                name = "Ветхая пуща",
                requirements = "Получено достижение отряда Веление Голоса",
                newScenarios = "33, 40",
                monsters = listOf(
                    "Жнец заразитель",
                    "Гигантская гадюка",
                    "Невыносимый ужас",
                    "Черный бес",
                ),
                location = "Вечное болото"
            ),
            ScenarioBd(
                scenarioNumber = 33,
                name = "Оружейная саввасов",
                requirements = "Получено достижение отряда Веление Голоса или Предложение дрейка",
                teamAchievement = "Сокровище голоса, Сокровище дрейка",
                monsters = listOf(
                    "Саввас Ледяной шторм",
                    "Саввас Поток лавы",
                    "Воздушный демон",
                    "Морозный демон",
                    "Огненный демон",
                    "Земляной демон",
                ),
                location = "Медные хребты"
            ),
            ScenarioBd(
                scenarioNumber = 34,
                name = "Выжженнная вершина",
                requirements = "Получено достижение отряда Предложение дрейка и не получено общее достижение Помощь дрейку",
                teamAchievement = "(-)Предложение дрейка",
                globalAchievement = "Убийство дракона",
                monsters = listOf(
                    "Когтистый дрейк",
                    "Шипящий дрейк",
                    "Дрейк-патриарх",
                ),
                location = "Медные хребты"
            ),
            ScenarioBd(
                scenarioNumber = 35,
                name = "Бойницы Мрачной гавани А",
                requirements = "Получено достижение отряда Задание демона и не получено общее достижение Портал обезврежен",
                newScenarios = "45",
                teamAchievement = "(-)Задание демона",
                globalAchievement = "Городское правление: демоны, Артефакт: потерян",
                monsters = listOf(
                    "Городской страж",
                    "Городской лучник",
                    "Воздушный демон",
                    "Морозный демон",
                    "Огненный демон",
                    "Земляной демон",
                    "Начальник стражи"
                ),
                location = "Мрачная гавань"
            ),
            ScenarioBd(
                scenarioNumber = 36,
                name = "Бойницы Мрачной гавани Б",
                requirements = "Получено достижение отряда Задание демона и не получено общее достижение Портал обезврежен",
                teamAchievement = "(-)Задание демона",
                globalAchievement = "Портал обезврежен",
                monsters = listOf(
                    "Городской лучник",
                    "Воздушный демон",
                    "Морозный демон",
                    "Огненный демон",
                    "Земляной демон",
                    "Архидемон"
                ),
                location = "Мрачная гавань"
            ),
            ScenarioBd(
                scenarioNumber = 37,
                name = "Гиблая впадина",
                requirements = "Получено общее достижение Подводное дыхание",
                newScenarios = "47",
                teamAchievement = "Путь через впадину",
                monsters = listOf(
                    "Скрытень",
                    "Невыносимый ужас",
                    "Жнец заразитель",
                ),
                location = "Туманное море"
            ),
            ScenarioBd(
                scenarioNumber = 38,
                name = "Невольничьи клетки",
                newScenarios = "44, 48",
                teamAchievement = "Помощь Красношипа",
                monsters = listOf(
                    "Инокс-стражница",
                    "Инокс-лучник",
                    "Инокс-шаман",
                    "Каменный голем"
                ),
                location = "Кинжальный лес"
            ),
            ScenarioBd(
                scenarioNumber = 39,
                name = "Коварная пропасть",
                newScenarios = "15, 46",
                teamAchievement = "Путь через пропасть",
                monsters = listOf(
                    "Пещерный медведь",
                    "Морозный демон",
                    "Шипящий дрейк",
                    "Культист",
                    "Ожившие кости"
                ),
                location = "Медные хребты"
            ),
            ScenarioBd(
                scenarioNumber = 40,
                name = "Древние защитные механизмы",
                requirements = "Получены достижения отряда Веление Голоса и Сокровище голоса",
                newScenarios = "41",
                globalAchievement = "Древняя технология",
                monsters = listOf(
                    "Пещерный медведь",
                    "Оживший труп",
                    "Лесной бес",
                    "Каменный голем",
                    "Огненный демон"
                ),
                location = "Мертволесье"
            ),
            ScenarioBd(
                scenarioNumber = 41,
                name = "Вековая гробница",
                requirements = "Получено достижение отряда Веление Голоса",
                globalAchievement = "Голос освобожден",
                monsters = listOf(
                    "Древняя пушка",
                    "Оживший труп",
                    "Оживший дух",
                    "Каменный голем",
                ),
                location = "Мертволесье"
            ),
            ScenarioBd(
                scenarioNumber = 42,
                name = "Царство Голоса",
                requirements = "Получено достижение отряда Голос и скипитер и не получено общее достижение Голос освобожден",
                teamAchievement = "(-)Веление Голоса",
                globalAchievement = "Голос умолк",
                monsters = listOf(
                    "Воздушный демон",
                    "Ночной демон",
                    "Оживший дух",
                ),
                location = "Медные хребты"
            ),
            ScenarioBd(
                scenarioNumber = 43,
                name = "Гнездо дрейков",
                requirements = "Получено общее достижение Возможость улучшений",
                globalAchievement = "Подводное дыхание",
                monsters = listOf(
                    "Когтистый дрейк",
                    "Шипящий дрейк",
                    "Огненный демон",
                ),
                location = "Кинжальный лес"
            ),
            ScenarioBd(
                scenarioNumber = 44,
                name = "Нападение племени",
                requirements = "Получено достижение отряда Помощь Красношипа",
                monsters = listOf(
                    "Инокс-стражница",
                    "Инокс-лучник",
                    "Инокс-шаман",
                    "Гончая"
                ),
                location = "Кинжальный лес"
            ),
            ScenarioBd(
                scenarioNumber = 45,
                name = "Привал подводных повстанцев",
                requirements = "Получено общее достижение Городское правление: демоны",
                newScenarios = "49, 50",
                monsters = listOf(
                    "Городской страж",
                    "Городской лучник",
                    "Гончая",
                ),
                location = "Вечное болото"
            ),
            ScenarioBd(
                scenarioNumber = 46,
                name = "Пик кошмара",
                requirements = "Получено достижение отряда Путь через пропасть",
                newScenarios = "51",
                globalAchievement = "Конец упадка",
                monsters = listOf(
                    "Ночной демон",
                    "Морозный демон",
                    "Воздушный демон",
                    "Саввас Ледяной шторм",
                    "Крылатый ужас"
                ),
                location = "Медные хребты"
            ),
            ScenarioBd(
                scenarioNumber = 47,
                name = "Логово невидящего ока",
                requirements = "Получено достижение отряда Путь через впадину",
                newScenarios = "51",
                globalAchievement = "Конец упадка",
                monsters = listOf(
                    "Скрытень",
                    "Невыносимый ужас",
                    "Невидящее око",
                    "Жнец заразитель"
                ),
                location = "Туманное море"
            ),
            ScenarioBd(
                scenarioNumber = 48,
                name = "Тенистая чаща",
                requirements = "Получено достижение отряда Помощь Красношипа",
                newScenarios = "51",
                globalAchievement = "Конец упадка",
                monsters = listOf(
                    "Лесной бес",
                    "Земляной демон",
                    "Жнец заразитель",
                    "Темный всадник",
                ),
                location = "Кинжальный лес"
            ),
            ScenarioBd(
                scenarioNumber = 49,
                name = "Лагерь повстанцев",
                requirements = "Получено общее достижение Городское правление: демоны",
                globalAchievement = "Уничтожение порядка",
                monsters = listOf(
                    "Гигантская гадюка",
                    "Городской страж",
                    "Городской лучник",
                    "Древняя пушка"
                ),
                location = "Вечное болото"
            ),
            ScenarioBd(
                scenarioNumber = 50,
                name = "Призрачная крепость",
                requirements = "Получено общее достижение Городское правление: демоны и не получено общее достижение Уничтожение порядка",
                globalAchievement = "Городское правление: военные",
                monsters = listOf(
                    "Ночной демон",
                    "Солнечный демон",
                    "Земляной демон",
                    "Воздушный демон"
                ),
                location = "Мрачная гавань"
            ),
            ScenarioBd(
                scenarioNumber = 51,
                name = "Пустота",
                requirements = "Получено общее достижение Конец упадка",
                globalAchievement = "Конец эпохи Мрака",
                monsters = listOf(
                    "Мрак",
                ),
                location = "Мрачная гавань"
            ),
            ScenarioBd(
                scenarioNumber = 52,
                name = "Жуткий погреб",
                requirements = "Личное задание Последователь зорна",
                newScenarios = "53",
                monsters = listOf(
                    "Шипящий дрейк",
                    "Слизь",
                    "Вермлинг-разведчик",
                    "Вермлинг-шаман",
                    "Оживший труп"
                ),
                location = "Мрачная гавань"
            ),
            ScenarioBd(
                scenarioNumber = 53,
                name = "Подвал склепа",
                requirements = "Личное задание Последователь зорна",
                newScenarios = "54",
                monsters = listOf(
                    "Слизь",
                    "Оживший труп",
                    "Оживший дух",
                    "Ожившие кости",
                    "Гигантская гадюка"
                ),
                location = "Тихая река"
            ),
            ScenarioBd(
                scenarioNumber = 54,
                name = "Ледяной дворец",
                requirements = "Личное задание Последователь зорна, предмет Посох Зорна находится в инвентаре",
                monsters = listOf(
                    "Пещерный медведь",
                    "Оживший дух",
                    "Морозный демон",
                    "Жнец заразитель",
                ),
                location = "Медные хребты"
            ),
            ScenarioBd(
                scenarioNumber = 55,
                name = "Туманные заросли",
                requirements = "Личное задание Возвращение леса",
                newScenarios = "56",
                location = "Кинжальный лес"
            ),
            ScenarioBd(
                scenarioNumber = 56,
                name = "Разбойничья пуща",
                requirements = "Личное задание Возвращение леса",
                monsters = listOf(
                    "Гончая",
                    "Разбойник-лучница",
                    "Когтистый дрейк",
                    "Разбойник-страж",
                ),
                location = "Кинжальный лес"
            ),
            ScenarioBd(
                scenarioNumber = 57,
                name = "Расследование",
                requirements = "Личное задание Месть",
                newScenarios = "58",
                monsters = listOf(
                    "Гончая",
                    "Городской страж",
                    "Городской лучник",
                    "Жнец заразитель",
                ),
                location = "Мрачная гавань"
            ),
            ScenarioBd(
                scenarioNumber = 58,
                name = "Кровавая хижина",
                requirements = "Личное задание Месть",
                monsters = listOf(
                    "Земляной демон",
                    "Городской страж",
                    "Черный бес",
                    "Жнец заразитель",
                ),
                location = "Мрачная гавань"
            ),
            ScenarioBd(
                scenarioNumber = 59,
                name = "Забытая роща",
                requirements = "Личное задание В Поисках лекарства",
                newScenarios = "60",
                monsters = listOf(
                    "Гончая",
                    "Пещерный медведь",
                    "Лесной бес",
                ),
                location = "Кинжальный лес"
            ),
            ScenarioBd(
                scenarioNumber = 60,
                name = "Алхимическая лаборатория",
                requirements = "Личное задание В Поисках лекарства",
                monsters = listOf(
                    "Гончая",
                    "Гигантская гадюка",
                    "Слизь",
                    "Когтистый дрейк",
                    "Шипящий дрейк",
                ),
                location = "Мрачная гавань"
            ),
            ScenarioBd(
                scenarioNumber = 61,
                name = "Исчезающий маяк",
                requirements = "Личное задание Грехопадение",
                newScenarios = "62",
                monsters = listOf(
                    "Слизь",
                    "Гигантская гадюка",
                    "Морозный демон",
                    "Огненный демон",
                ),
                location = "Вечное болото"
            ),
            ScenarioBd(
                scenarioNumber = 62,
                name = "Колодец душ",
                requirements = "Личное задание Грехопадение",
                monsters = listOf(
                    "Оживший дух",
                    "Ожившие кости",
                ),
                location = "Вечное болото"
            ),
            ScenarioBd(
                scenarioNumber = 63,
                name = "Жерло вулкана",
                monsters = listOf(
                    "Вермлинг-разведчик",
                    "Огненный демон",
                    "Инокс-стражница",
                    "Инокс-лучник",
                ),
                location = "Сторожевые горы"
            ),
            ScenarioBd(
                scenarioNumber = 64,
                name = "Подводная лагуна",
                requirements = "Получено общее достижение Подводное дыхание",
                monsters = listOf(
                    "Слизь",
                    "Лесной бес",
                    "Когтистый дрейк",
                ),
                location = "Туманное море"
            ),
            ScenarioBd(
                scenarioNumber = 65,
                name = "Серная шахта",
                globalAchievement = "Древняя технология",
                monsters = listOf(
                    "Вермлинг-разведчик",
                    "Гончая",
                    "Инокс-шаман",
                ),
                location = "Сторожевые горы"
            ),
            ScenarioBd(
                scenarioNumber = 66,
                name = "Заводное побережье",
                globalAchievement = "Древняя технология",
                monsters = listOf(
                    "Слизь",
                    "Древняя пушка",
                    "Каменный голем",
                    "Оживший дух"
                ),
                location = "Мертволесье"
            ),
            ScenarioBd(
                scenarioNumber = 67,
                name = "Мистическая библиотека",
                globalAchievement = "Древняя технология",
                monsters = listOf(
                    "Лесной бес",
                    "Пещерный медведь",
                    "Каменный голем",
                ),
                location = "Восточный тракт"
            ),
            ScenarioBd(
                scenarioNumber = 68,
                name = "Ядовитая трясина",
                monsters = listOf(
                    "Когтистый дрейк",
                    "Черный бес",
                    "Гигантская гадюка",
                    "Оживший труп",
                ),
                location = "Вечное болото"
            ),
            ScenarioBd(
                scenarioNumber = 69,
                name = "Колодец несчастий",
                monsters = listOf(
                    "Вермлинг-разведчик",
                    "Вермлинг-шаман",
                    "Лесной бес",
                    "Каменный голем",
                    "Оживший дух"
                ),
                location = "Каменистый тракт"
            ),
            ScenarioBd(
                scenarioNumber = 70,
                name = "Прикованный остров",
                monsters = listOf(
                    "Ночной демон",
                    "Воздушный демон",
                    "Оживший дух"
                ),
                location = "Туманное море"
            ),
            ScenarioBd(
                scenarioNumber = 71,
                name = "Подветренное нагорье",
                monsters = listOf(
                    "Шипящий дрейк",
                    "Воздушный демон",
                    "Солнечный демон"
                ),
                location = "Река Змеиный поцелуй"
            ),
            ScenarioBd(
                scenarioNumber = 72,
                name = "Скользкая роща",
                monsters = listOf(
                    "Слизь",
                    "Гигантская гадюка",
                    "Лесной бес",
                ),
                location = "Мертволесье"
            ),
            ScenarioBd(
                scenarioNumber = 73,
                name = "Гряда камнепадов",
                monsters = listOf(
                    "Древняя пушка",
                    "Инокс-шаман",
                    "Инокс-стражница",
                    "Инокс-лучник",
                    "Гончая",
                ),
                location = "Сторожевые горы"
            ),
            ScenarioBd(
                scenarioNumber = 74,
                name = "Торговый корабль",
                requirements = "Получено достижение отряда Морской конвой",
                monsters = listOf(
                    "Разбойник-страж",
                    "Разбойник-лучница",
                    "Скрытень",
                    "Невыносимый ужас",
                ),
                location = "Туманное море"
            ),
            ScenarioBd(
                scenarioNumber = 75,
                name = "Заросшее кладбище",
                requirements = "Получено достижение отряда Гробокопатели",
                monsters = listOf(
                    "Оживший дух",
                    "Оживший труп",
                    "Ожившие кости",
                ),
                location = "Мертволесье"
            ),
            ScenarioBd(
                scenarioNumber = 76,
                name = "Улей жнецов",
                requirements = "храбрость",
                monsters = listOf(
                    "Гигантская гадюка",
                    "Ночной демон",
                    "Ожившие кости",
                    "Жнец заразитель"
                ),
                location = "Сторожевые горы"
            ),
            ScenarioBd(
                scenarioNumber = 77,
                name = "Хранилище тайн",
                monsters = listOf(
                    "Гончая",
                    "Городской страж",
                    "Городской лучник",
                    "Каменный голем",
                ),
                location = "Мрачная гавань"
            ),
            ScenarioBd(
                scenarioNumber = 78,
                name = "Темница жертвоприношений",
                monsters = listOf(
                    "Разбойник-страж",
                    "Разбойник-лучница",
                    "Культист",
                    "Ожившие кости",
                    "Черный бес"
                ),
                location = "Мрачная гавань"
            ),
            ScenarioBd(
                scenarioNumber = 79,
                name = "Затерянный храм",
                requirements = "Получено достижение отряда Сомнительный помощник",
                monsters = listOf(
                    "Каменный голем",
                    "Гигантская гадюка",
                    "Предатель",
                ),
                location = "Вечное болото"
            ),
            ScenarioBd(
                scenarioNumber = 80,
                name = "Цитадель караула",
                monsters = listOf(
                    "Гончая",
                    "Городской страж",
                    "Городской лучник",
                    "Древняя пушка",
                ),
                location = "Сторожевые горы"
            ),
            ScenarioBd(
                scenarioNumber = 81,
                name = "Храм затмения",
                monsters = listOf(
                    "Ночной демон",
                    "Солнечный демон",
                    "Каменный голем",
                    "Древняя пушка",
                    "Бесцветный"
                ),
                location = "Кинжальный лес"
            ),
            ScenarioBd(
                scenarioNumber = 82,
                name = "Пылающая гора",
                monsters = listOf(
                    "Земляной демон",
                    "Огненный демон",
                    "Каменный голем",
                ),
                location = "Сторожевые горы"
            ),
            ScenarioBd(
                scenarioNumber = 83,
                name = "Обитель теней",
                requirements = "Получено достижение отряда Дурное знамение",
                monsters = listOf(
                    "Гончая",
                    "Культист",
                    "Ожившие кости",
                    "Оживший дух",
                    "Огненный демон",
                ),
                location = "Мрачная гавань"
            ),
            ScenarioBd(
                scenarioNumber = 84,
                name = "Пещера кристалов",
                requirements = "Получено достижение отряда Дрожь земли",
                monsters = listOf(
                    "Земляной демон",
                    "Огненный демон",
                    "Морозный демон",
                ),
                location = "Медные хребты"
            ),
            ScenarioBd(
                scenarioNumber = 85,
                name = "Храм солнца",
                monsters = listOf(
                    "Ночной демон",
                    "Солнечный демон",
                    "Гончая",
                    "Черный бес"
                ),
                location = "Сторожевые горы"
            ),
            ScenarioBd(
                scenarioNumber = 86,
                name = "Разграбленная деревня",
                newScenarios = "87",
                teamAchievement = "Источник отравы",
                monsters = listOf(
                    "Пещерный медведь",
                    "Вермлинг-шаман",
                    "Вермлинг-разведчик",
                    "Скрытень"
                ),
                location = "Мрачная гавань"
            ),
            ScenarioBd(
                scenarioNumber = 87,
                name = "Злосчастное побережье",
                requirements = "Получено достижение отряда Источник отравы",
                monsters = listOf(
                    "Скрытень",
                    "Невыносимый ужас",
                    "Слизь",
                    "Черный бес"
                ),
                location = "Бухта торговцев"
            ),
            ScenarioBd(
                scenarioNumber = 88,
                name = "Водное измерение",
                requirements = "Получено достижение отряда Подводное дыхание и достижение отряда Посох воды",
                monsters = listOf(
                    "Морозный демон",
                    "Слизь",
                    "Скрытень",
                ),
                location = "Мрачная гавань"
            ),
            ScenarioBd(
                scenarioNumber = 89,
                name = "Убежище синдиката",
                requirements = "Получено достижение отряда Син-Ра",
                monsters = listOf(
                    "Разбойник-страж",
                    "Разбойник-лучница",
                    "Культист",
                    "Гигантская гадюка",
                ),
                location = "Мрачная гавань"
            ),
            ScenarioBd(
                scenarioNumber = 90,
                name = "Демонический портал",
                monsters = listOf(
                    "Земляной демон",
                    "Воздушный демон",
                    "Ночной демон",
                    "Оживший дух"
                ),
                location = "Река Змеиный поцелуй"
            ),
            ScenarioBd(
                scenarioNumber = 91,
                name = "Дикая схватка",
                monsters = listOf(
                    "Разбойник-страж",
                    "Разбойник-лучница",
                    "Пещерный медведь",
                    "Оживший дух",
                    "Гончая",
                ),
                location = "Кинжальный лес"
            ),
            ScenarioBd(
                scenarioNumber = 92,
                name = "Потасовка в переулке",
                requirements = "Получено достижение отряда Возвращение долга",
                monsters = listOf(
                    "Разбойник-страж",
                    "Разбойник-лучница",
                    "Городской страж",
                    "Городской лучник",
                    "Земляной демон",
                    "Огненный демон",
                    "Инокс-стражница",
                    "Саввас Поток лавы"
                ),
                location = "Мрачная гавань"
            ),
            ScenarioBd(
                scenarioNumber = 93,
                name = "Затонувшее судно",
                requirements = "Получено достижение отряда Карта сокровищ",
                monsters = listOf(
                    "Скрытень",
                    "Морозный демон",
                    "Оживший дух",
                ),
                location = "Туманное море"
            ),
            ScenarioBd(
                scenarioNumber = 94,
                name = "Жилище вермлингов",
                newScenarios = "95",
                teamAchievement = "Путь через жилище",
                monsters = listOf(
                    "Пещерный медведь",
                    "Гончая",
                    "Вермлинг-разведчик",
                    "Вермлинг-шаман",
                ),
                location = "Мертволесье"
            ),
            ScenarioBd(
                scenarioNumber = 95,
                name = "Час расплаты",
                requirements = "Получено достижение отряда Путь через жилище",
                monsters = listOf(
                    "Земляной демон",
                    "Огненный демон",
                    "Невыносимый ужас",
                    "Саввас Поток лавы"
                ),
                location = "Мертволесье"
            ),
        )
    }
}