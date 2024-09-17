package com.rumpilstilstkin.gloomhavenhelper.bd.filler

import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterClassDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GameLevelInfoDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.ScenarioDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterClassBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GameLevelInfoBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.ScenarioBd
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreens

object StartFill {
    suspend fun fillScenarios(
        scenarioDao: ScenarioDao
    ) {
        scenarioDao.insertAll(
            ScenarioBd(
                scenarioNumber = 1,
                name = "Черный курган",
                newScenarios = "2",
                teamAchievement = "Первые шаги",
            ),
            ScenarioBd(
                scenarioNumber = 2,
                name = "Подземное логово",
                newScenarios = "3, 4",
                requirements = "Получено достижение отряда Первые шаги"
            ),
            ScenarioBd(
                scenarioNumber = 3,
                name = "Лагерь иноксов",
                newScenarios = "8, 9",
                requirements = "Не получено общее достижение Сбежавшая торговка",
                teamAchievement = "Планы Джексеры",
            ),
            ScenarioBd(
                scenarioNumber = 4,
                name = "Склеп проклятых",
                newScenarios = "5, 6",
            ),
            ScenarioBd(
                scenarioNumber = 5,
                name = "Зловещий склеп",
                newScenarios = "10, 14, 19",
            ),
            ScenarioBd(
                scenarioNumber = 6,
                name = "Обветшалый склеп",
                newScenarios = "8",
                teamAchievement = "Планы Джексеры, Темная награда",
            ),
            ScenarioBd(
                scenarioNumber = 7,
                name = "Цветущий грот",
                newScenarios = "20",
                requirements = "Получены общие достижения Возможость улучшений и Сбежавшая торговка",
            ),
            ScenarioBd(
                scenarioNumber = 8,
                name = "Склад мрачной гавани",
                newScenarios = "7, 13, 14",
                globalAchievement = "Сбежавшая торговка",
                requirements = "Получено достижение отряда Планы Джексеры и не получено общее достижение Вторжение мертвецов",
            ),
            ScenarioBd(
                scenarioNumber = 9,
                name = "Алмазная шахта",
                newScenarios = "11, 12",
                globalAchievement = "Вторжение мертвецов",
                requirements = "Не получено общее достижение Сбежавшая торговка",
            ),
            ScenarioBd(
                scenarioNumber = 10,
                name = "Измерение силы стихий",
                newScenarios = "21, 22",
                teamAchievement = "Задание демона",
                requirements = "Не получено общее достижение Портал обезврежен",
            ),
            ScenarioBd(
                scenarioNumber = 11,
                name = "Площадь Мрачной гавани А",
                newScenarios = "16, 18",
                globalAchievement = "Городское правление: торговцы, Конец вторжения",
                requirements = "Не получено общее достижение Конец вторжения",
            ),
            ScenarioBd(
                scenarioNumber = 12,
                name = "Площадь Мрачной гавани Б",
                newScenarios = "16, 18, 28",
                globalAchievement = "Конец вторжения",
                requirements = "Не получено общее достижение Конец вторжения",
            ),
            ScenarioBd(
                scenarioNumber = 13,
                name = "Храм прорицателя",
                newScenarios = "15, 17, 20",
            ),
            ScenarioBd(
                scenarioNumber = 14,
                name = "Ледяная пещера",
                newScenarios = "",
                globalAchievement = "Возможость улучшений",
            ),
            ScenarioBd(
                scenarioNumber = 15,
                name = "Святилище силы",
            ),
            ScenarioBd(
                scenarioNumber = 16,
                name = "Горный перевал",
                newScenarios = "24, 25",
            ),
            ScenarioBd(
                scenarioNumber = 17,
                name = "Затерянный остров",
            ),
            ScenarioBd(
                scenarioNumber = 18,
                name = "Заброшенная канализация",
                newScenarios = "14, 23, 26, 43",
            ),
            ScenarioBd(
                scenarioNumber = 19,
                name = "Забытый склеп",
                newScenarios = "27",
                requirements = "Получено общее достижение Возможость улучшений",
                teamAchievement = "Кадило Каменолома",
            ),
            ScenarioBd(
                scenarioNumber = 20,
                name = "Прибежище некромантки",
                newScenarios = "16, 18, 28",
                requirements = "Получено общее достижение Сбежавшая торговка",
            ),
            ScenarioBd(
                scenarioNumber = 21,
                name = "Дьявольский трон",
                requirements = "Не получено общее достижение Портал обезврежен",
                globalAchievement = "Портал обезврежен, Артефакт: найден",
            ),
            ScenarioBd(
                scenarioNumber = 22,
                name = "Храм стихий",
                newScenarios = "31, 35, 36",
                requirements = "Получено достижение отряда Задание демона или По следам",
                globalAchievement = "Артефакт: найден",
            ),
            ScenarioBd(
                scenarioNumber = 23,
                name = "Глубокие руины",
                newScenarios = "26",
                globalAchievement = "Древняя технология",
                teamAchievement = "Путь через руины"
            ),
            ScenarioBd(
                scenarioNumber = 24,
                name = "Шепчущая галерея",
                newScenarios = "30, 32",
                teamAchievement = "Веление Голоса"
            ),
            ScenarioBd(
                scenarioNumber = 25,
                name = "Восхождение на Ледяной утес",
                newScenarios = "33, 34",
                teamAchievement = "Предложение дрейка"
            ),
            ScenarioBd(
                scenarioNumber = 26,
                name = "Древний резервуар",
                requirements = "Получено общее достижение Подводное дыхание или Путь через руины",
                newScenarios = "22",
                teamAchievement = "По следам"
            ),
            ScenarioBd(
                scenarioNumber = 27,
                name = "Зловещий портал",
                requirements = "Не получено общее достижение Артефакт: потерян и получено достижение отряда Кадило Каменолома",
                globalAchievement = "Портал обезврежен"
            ),
            ScenarioBd(
                scenarioNumber = 28,
                name = "Внешнее капище",
                requirements = "Получено общее достижение Темная награда",
                newScenarios = "29",
                teamAchievement = "Приглашение"
            ),
            ScenarioBd(
                scenarioNumber = 29,
                name = "Обитель Мрака",
                requirements = "Получено достижение отряда Приглашение",
                globalAchievement = "На грани тьмы"
            ),
            ScenarioBd(
                scenarioNumber = 30,
                name = "Святилище глубин",
                requirements = "Получено достижение отряда Веление Голоса",
                newScenarios = "42",
                teamAchievement = "Голос и скипитер"
            ),
            ScenarioBd(
                scenarioNumber = 31,
                name = "Ночное измерение",
                requirements = "Получено общее достижение Возможость улучшений и Артефакт: найден",
                newScenarios = "37, 38, 39, 43",
                globalAchievement = "Артефакт: очищен"
            ),
            ScenarioBd(
                scenarioNumber = 32,
                name = "Ветхая пуща",
                requirements = "Получено достижение отряда Веление Голоса",
                newScenarios = "33, 40",
            ),
            ScenarioBd(
                scenarioNumber = 33,
                name = "Оружейная саввасов",
                requirements = "Получено достижение отряда Веление Голоса или Предложение дрейка",
                teamAchievement = "Сокровище голоса, Сокровище дрейка"
            ),
            ScenarioBd(
                scenarioNumber = 34,
                name = "Выжженнная вершина",
                requirements = "Получено достижение отряда Предложение дрейка и не получено общее достижение Помощь дрейку",
                teamAchievement = "(-)Предложение дрейка",
                globalAchievement = "Убийство дракона"
            ),
            ScenarioBd(
                scenarioNumber = 35,
                name = "Бойницы Мрачной гавани А",
                requirements = "Получено достижение отряда Задание демона и не получено общее достижение Портал обезврежен",
                newScenarios = "45",
                teamAchievement = "(-)Задание демона",
                globalAchievement = "Городское правление: демоны, Артефакт: потерян"
            ),
            ScenarioBd(
                scenarioNumber = 36,
                name = "Бойницы Мрачной гавани Б",
                requirements = "Получено достижение отряда Задание демона и не получено общее достижение Портал обезврежен",
                teamAchievement = "(-)Задание демона",
                globalAchievement = "Портал обезврежен"
            ),
            ScenarioBd(
                scenarioNumber = 37,
                name = "Гиблая впадина",
                requirements = "Получено общее достижение Подводное дыхание",
                newScenarios = "47",
                teamAchievement = "Путь через впадину"
            ),
            ScenarioBd(
                scenarioNumber = 38,
                name = "Невольничьи клетки",
                newScenarios = "44, 48",
                teamAchievement = "Помощь Красношипа"
            ),
            ScenarioBd(
                scenarioNumber = 39,
                name = "Коварная пропасть",
                newScenarios = "15, 46",
                teamAchievement = "Путь через пропасть"
            ),
            ScenarioBd(
                scenarioNumber = 40,
                name = "Древние защитные механизмы",
                requirements = "Получены достижения отряда Веление Голоса и Сокровище голоса",
                newScenarios = "41",
                globalAchievement = "Древняя технология"
            ),
            ScenarioBd(
                scenarioNumber = 41,
                name = "Вековая гробница",
                requirements = "Получено достижение отряда Веление Голоса",
                globalAchievement = "Голос освобожден"
            ),
            ScenarioBd(
                scenarioNumber = 42,
                name = "Царство Голоса",
                requirements = "Получено достижение отряда Голос и скипитер и не получено общее достижение Голос освобожден",
                teamAchievement = "(-)Веление Голоса",
                globalAchievement = "Голос умолк"
            ),
            ScenarioBd(
                scenarioNumber = 43,
                name = "Гнездо дрейков",
                requirements = "Получено общее достижение Возможость улучшений",
                globalAchievement = "Подводное дыхание"
            ),
            ScenarioBd(
                scenarioNumber = 44,
                name = "Нападение племени",
                requirements = "Получено достижение отряда Помощь Красношипа",
            ),
            ScenarioBd(
                scenarioNumber = 45,
                name = "Привал подводных повстанцев",
                requirements = "Получено общее достижение Городское правление: демоны",
                newScenarios = "49, 50",
            ),
            ScenarioBd(
                scenarioNumber = 46,
                name = "Пик кошмара",
                requirements = "Получено достижение отряда Путь через пропасть",
                newScenarios = "51",
                globalAchievement = "Конец упадка"
            ),
            ScenarioBd(
                scenarioNumber = 47,
                name = "Логово невидящего ока",
                requirements = "Получено достижение отряда Путь через впадину",
                newScenarios = "51",
                globalAchievement = "Конец упадка"
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

    suspend fun fillGameLevelInfo(
        gameLevelInfoDao: GameLevelInfoDao
    ) {
        gameLevelInfoDao.insertAll(
            GameLevelInfoBd(
                level = 0,
                monsterLevel = 0,
                goldCount = 2,
                trapDamage = 2,
                experience = 4
            ),
            GameLevelInfoBd(
                level = 1,
                monsterLevel = 1,
                goldCount = 2,
                trapDamage = 3,
                experience = 6
            ),
            GameLevelInfoBd(
                level = 2,
                monsterLevel = 2,
                goldCount = 3,
                trapDamage = 4,
                experience = 8
            ),
            GameLevelInfoBd(
                level = 3,
                monsterLevel = 3,
                goldCount = 3,
                trapDamage = 5,
                experience = 10
            ),
            GameLevelInfoBd(
                level = 4,
                monsterLevel = 4,
                goldCount = 4,
                trapDamage = 6,
                experience = 12
            ),
            GameLevelInfoBd(
                level = 5,
                monsterLevel = 5,
                goldCount = 4,
                trapDamage = 7,
                experience = 14
            ),
            GameLevelInfoBd(
                level = 6,
                monsterLevel = 6,
                goldCount = 5,
                trapDamage = 8,
                experience = 16
            ),
            GameLevelInfoBd(
                level = 7,
                monsterLevel = 7,
                goldCount = 6,
                trapDamage = 9,
                experience = 18
            )
        )

    }

    suspend fun fillCharacterClasses(
        characterClassDao: CharacterClassDao
    ) {
        characterClassDao.insertAll(
            CharacterClassBd(name = "Инокс, дикарь", image = R.drawable.br),
            CharacterClassBd(name = "Вермлинг, повелитель зверей", image = R.drawable.bt),
            CharacterClassBd(name = "Саввас, пустотелый ", image = R.drawable.ch),
            CharacterClassBd(name = "Орхид, обрекающий", image = R.drawable.ds),
            CharacterClassBd(name = "Саввас, элементалист", image = R.drawable.el),
            CharacterClassBd(name = "Куатрил,воспевающая", image = R.drawable.ss),
            CharacterClassBd(name = "Человек, костоправ", image = R.drawable.sb),
            CharacterClassBd(name = "Жнец, предвестник чумы", image = R.drawable.ph),
            CharacterClassBd(name = "Куатрил, изобретатель", image = R.drawable.ti),
            CharacterClassBd(name = "Эстер, покров ночи", image = R.drawable.ns),
            CharacterClassBd(name = "Орхид, плетущая чары", image = R.drawable.sw),
            CharacterClassBd(name = "Эстер, призывающая", image = R.drawable.su),
            CharacterClassBd(name = "Валрат, хранящая солнце", image = R.drawable.sk),
            CharacterClassBd(name = "Вермлинг, крадущая разум", image = R.drawable.mt),
            CharacterClassBd(name = "Валрат, хранящая солнце", image = R.drawable.sk),
            CharacterClassBd(name = "Человек, плутовка", image = R.drawable.sc),
            CharacterClassBd(name = "Валрат, интендант", image = R.drawable.qm),
            CharacterClassBd(name = "Эстрер, прорицательница", image = R.drawable.dr),
        )
    }
}