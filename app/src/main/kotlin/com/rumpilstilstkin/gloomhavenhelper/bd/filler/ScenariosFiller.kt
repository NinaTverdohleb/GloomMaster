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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
            ),
            ScenarioBd(
                scenarioNumber = 13,
                name = "Храм прорицателя",
                newScenarios = "15, 17, 20",
                monsters = listOf(
                    "Каменный голем",
                    "Пещерный медведь",
                    "Оживший дух",
                    "Шипящй дрейк",
                )
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
                )
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
                )
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
                )
            ),
            ScenarioBd(
                scenarioNumber = 17,
                name = "Затерянный остров",
                monsters = listOf(
                    "Вермлинг-разведчик",
                    "Вермлинг-шаман",
                    "Пещерный медведь",
                )
            ),
            ScenarioBd(
                scenarioNumber = 18,
                name = "Заброшенная канализация",
                newScenarios = "14, 23, 26, 43",
                monsters = listOf(
                    "Вермлинг-разведчик",
                    "Гигантская гадюка",
                    "Слизь",
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
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
                )
            ),
            ScenarioBd(
                scenarioNumber = 48,
                name = "Тенистая чаща",
                requirements = "Получено достижение отряда Помощь Красношипа",
                newScenarios = "51",
                globalAchievement = "Конец упадка"
            ),
            ScenarioBd(
                scenarioNumber = 49,
                name = "Лагерь повстанцев",
                requirements = "Получено общее достижение Городское правление: демоны",
                globalAchievement = "Уничтожение порядка"
            ),
            ScenarioBd(
                scenarioNumber = 50,
                name = "Призрачная крепость",
                requirements = "Получено общее достижение Городское правление: демоны и не получено общее достижение Уничтожение порядка",
                globalAchievement = "Городское правление: военные"
            ),
            ScenarioBd(
                scenarioNumber = 51,
                name = "Пустота",
                requirements = "Получено общее достижение Конец упадка",
                globalAchievement = "Конец эпохи Мрака"
            ),
            ScenarioBd(
                scenarioNumber = 52,
                name = "Жуткий погреб",
                requirements = "Личное задание Последователь зорна",
                newScenarios = "53",
            ),
            ScenarioBd(
                scenarioNumber = 53,
                name = "Подвал склепа",
                requirements = "Личное задание Последователь зорна",
                newScenarios = "54",
            ),
            ScenarioBd(
                scenarioNumber = 54,
                name = "Ледяной дворец",
                requirements = "Личное задание Последователь зорна, предмет Посох Зорна находится в инвентаре",
            ),
            ScenarioBd(
                scenarioNumber = 55,
                name = "Туманные заросли",
                requirements = "Личное задание Возвращение леса",
                newScenarios = "56",
            ),
            ScenarioBd(
                scenarioNumber = 56,
                name = "Разбойничья пуща",
                requirements = "Личное задание Возвращение леса",
            ),
            ScenarioBd(
                scenarioNumber = 57,
                name = "Расследование",
                requirements = "Личное задание Месть",
                newScenarios = "58",
            ),
            ScenarioBd(
                scenarioNumber = 58,
                name = "Кровавая хижина",
                requirements = "Личное задание Месть",
            ),
            ScenarioBd(
                scenarioNumber = 59,
                name = "Забытая роща",
                requirements = "Личное задание В Поисках лекарства",
                newScenarios = "60",
            ),
            ScenarioBd(
                scenarioNumber = 60,
                name = "Алхимическая лаборатория",
                requirements = "Личное задание В Поисках лекарства",
            ),
            ScenarioBd(
                scenarioNumber = 61,
                name = "Исчезающий маяк",
                requirements = "Личное задание Грехопадение",
                newScenarios = "62",
            ),
            ScenarioBd(
                scenarioNumber = 62,
                name = "Колодец душ",
                requirements = "Личное задание Грехопадение",
            ),
            ScenarioBd(
                scenarioNumber = 63,
                name = "Жерло вулкана",
            ),
            ScenarioBd(
                scenarioNumber = 64,
                name = "Подводная лагуна",
                requirements = "Получено общее достижение Подводное дыхание",
            ),
            ScenarioBd(
                scenarioNumber = 65,
                name = "Серная шахта",
                globalAchievement = "Древняя технология"
            ),
            ScenarioBd(
                scenarioNumber = 66,
                name = "Заводное побережье",
                globalAchievement = "Древняя технология"
            ),
            ScenarioBd(
                scenarioNumber = 67,
                name = "Мистическая библиотека",
                globalAchievement = "Древняя технология"
            ),
            ScenarioBd(
                scenarioNumber = 68,
                name = "Ядовитая трясина",
            ),
            ScenarioBd(
                scenarioNumber = 69,
                name = "Колодец несчастий",
            ),
            ScenarioBd(
                scenarioNumber = 70,
                name = "Прикованный остров",
            ),
            ScenarioBd(
                scenarioNumber = 71,
                name = "Подветренное нагорье",
            ),
            ScenarioBd(
                scenarioNumber = 72,
                name = "Скользкая роща",
            ),
            ScenarioBd(
                scenarioNumber = 73,
                name = "Гряда камнепадов",
            ),
            ScenarioBd(
                scenarioNumber = 74,
                name = "Торговый корабль",
                requirements = "Получено достижение отряда Морской конвой",
            ),
            ScenarioBd(
                scenarioNumber = 75,
                name = "Заросшее кладбище",
                requirements = "Получено достижение отряда Гробокопатели",
            ),
            ScenarioBd(
                scenarioNumber = 76,
                name = "Улей жнецов",
                requirements = "храбрость",
            ),
            ScenarioBd(
                scenarioNumber = 77,
                name = "Хранилище тайн",
            ),
            ScenarioBd(
                scenarioNumber = 78,
                name = "Темница жертвоприношений",
            ),
            ScenarioBd(
                scenarioNumber = 79,
                name = "Затерянный храм",
                requirements = "Получено достижение отряда Сомнительный помощник",
            ),
            ScenarioBd(
                scenarioNumber = 80,
                name = "Цитадель караула",
            ),
            ScenarioBd(
                scenarioNumber = 81,
                name = "Храм затмения",
            ),
            ScenarioBd(
                scenarioNumber = 82,
                name = "Пылающая гора",
            ),
            ScenarioBd(
                scenarioNumber = 83,
                name = "Обитель теней",
                requirements = "Получено достижение отряда Дурное знамение",
            ),
            ScenarioBd(
                scenarioNumber = 84,
                name = "Пещера кристалов",
                requirements = "Получено достижение отряда Дрожь земли",
            ),
            ScenarioBd(
                scenarioNumber = 85,
                name = "Храм солнца",
            ),
            ScenarioBd(
                scenarioNumber = 86,
                name = "Разграбленная деревня",
                newScenarios = "87",
                teamAchievement = "Источник отравы",
            ),
            ScenarioBd(
                scenarioNumber = 87,
                name = "Злосчастное побережье",
                requirements = "Получено достижение отряда Источник отравы",
            ),
            ScenarioBd(
                scenarioNumber = 88,
                name = "Водное измерение",
                requirements = "Получено достижение отряда Подводное дыхание и достижение отряда Посох воды",
            ),
            ScenarioBd(
                scenarioNumber = 89,
                name = "Убежище синдиката",
                requirements = "Получено достижение отряда Син-Ра",
            ),
            ScenarioBd(
                scenarioNumber = 90,
                name = "Демонический портал",
            ),
            ScenarioBd(
                scenarioNumber = 91,
                name = "Дикая схватка",
            ),
            ScenarioBd(
                scenarioNumber = 92,
                name = "Потасовка в переулке",
                requirements = "Получено достижение отряда Возвращение долга",
            ),
            ScenarioBd(
                scenarioNumber = 93,
                name = "Затонувшее судно",
                requirements = "Получено достижение отряда Карта сокровищ",
            ),
            ScenarioBd(
                scenarioNumber = 94,
                name = "Жилище вермлингов",
                newScenarios = "95",
                teamAchievement = "Путь через жилище",
            ),
            ScenarioBd(
                scenarioNumber = 95,
                name = "Час расплаты",
                requirements = "Получено достижение отряда Путь через жилище",
            ),
        )
    }
}