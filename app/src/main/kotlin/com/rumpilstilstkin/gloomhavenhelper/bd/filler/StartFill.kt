package com.rumpilstilstkin.gloomhavenhelper.bd.filler

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterClassDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GameLevelInfoDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GoodsDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.PerksDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.ScenarioDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterClassBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GameLevelInfoBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GoodBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.PerkBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.ScenarioBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.GoodType

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
            CharacterClassBd(name = "Инокс, дикарь", characterType = CharacterClassType.Brute.name),
            CharacterClassBd(
                name = "Вермлинг, повелитель зверей",
                characterType = CharacterClassType.BeastTyrant.name
            ),
            CharacterClassBd(
                name = "Саввас, пустотелый ",
                characterType = CharacterClassType.Cragheart.name
            ),
            CharacterClassBd(
                name = "Орхид, обрекающий",
                characterType = CharacterClassType.Doomstalker.name
            ),
            CharacterClassBd(
                name = "Саввас, элементалист",
                characterType = CharacterClassType.Elementalist.name
            ),
            CharacterClassBd(
                name = "Куатрил,воспевающая",
                characterType = CharacterClassType.Soothsinger.name
            ),
            CharacterClassBd(
                name = "Человек, костоправ",
                characterType = CharacterClassType.Sawbones.name
            ),
            CharacterClassBd(
                name = "Жнец, предвестник чумы",
                characterType = CharacterClassType.Plagueherald.name
            ),
            CharacterClassBd(
                name = "Куатрил, изобретатель",
                characterType = CharacterClassType.Tinkerer.name
            ),
            CharacterClassBd(
                name = "Эстер, покров ночи",
                characterType = CharacterClassType.Nightshroud.name
            ),
            CharacterClassBd(
                name = "Орхид, плетущая чары",
                characterType = CharacterClassType.Spellweaver.name
            ),
            CharacterClassBd(
                name = "Эстер, призывающая",
                characterType = CharacterClassType.Summoner.name
            ),
            CharacterClassBd(
                name = "Валрат, хранящая солнце",
                characterType = CharacterClassType.Sunkeeper.name
            ),
            CharacterClassBd(
                name = "Вермлинг, крадущая разум",
                characterType = CharacterClassType.Mindthief.name
            ),
            CharacterClassBd(
                name = "Человек, плутовка",
                characterType = CharacterClassType.Scoundrel.name
            ),
            CharacterClassBd(
                name = "Валрат, интендант",
                characterType = CharacterClassType.Quartermaster.name
            ),
            CharacterClassBd(
                name = "Эстрер, прорицательница",
                characterType = CharacterClassType.Diviner.name
            ),
        )
    }

    suspend fun fillGoods(
        goodsDao: GoodsDao
    ) {
        goodsDao.insertAll(
            GoodBd(
                number = 1,
                name = "Сапоги большого шага",
                type = GoodType.Foot.name,
                cost = 20
            ),
            GoodBd(
                number = 1,
                name = "Сапоги большого шага",
                type = GoodType.Foot.name,
                cost = 20
            ),
            GoodBd(
                number = 2,
                name = "Крылатые сапоги",
                type = GoodType.Foot.name,
                cost = 20
            ),
            GoodBd(
                number = 2,
                name = "Крылатые сапоги",
                type = GoodType.Foot.name,
                cost = 20
            ),
            GoodBd(
                number = 3,
                name = "Броня из шкур",
                type = GoodType.Body.name,
                cost = 10
            ),
            GoodBd(
                number = 3,
                name = "Броня из шкур",
                type = GoodType.Body.name,
                cost = 10
            ),
            GoodBd(
                number = 4,
                name = "Кожаная броня",
                type = GoodType.Body.name,
                cost = 20
            ),
            GoodBd(
                number = 4,
                name = "Кожаная броня",
                type = GoodType.Body.name,
                cost = 20
            ),
            GoodBd(
                number = 4,
                name = "Кожаная броня",
                type = GoodType.Body.name,
                cost = 20
            ),
            GoodBd(
                number = 5,
                name = "Плащ невидимка",
                type = GoodType.Body.name,
                cost = 20
            ),
            GoodBd(
                number = 5,
                name = "Плащ невидимка",
                type = GoodType.Body.name,
                cost = 20
            ),
            GoodBd(
                number = 6,
                name = "Очки орлиного взора",
                type = GoodType.Head.name,
                cost = 30
            ),
            GoodBd(
                number = 7,
                name = "Железный шлем",
                type = GoodType.Head.name,
                cost = 10
            ),
            GoodBd(
                number = 7,
                name = "Железный шлем",
                type = GoodType.Head.name,
                cost = 10
            ),
            GoodBd(
                number = 8,
                name = "Треугольный щит",
                type = GoodType.Arm.name,
                cost = 20
            ),
            GoodBd(
                number = 8,
                name = "Треугольный щит",
                type = GoodType.Arm.name,
                cost = 20
            ),
            GoodBd(
                number = 9,
                name = "Пронзающий лук",
                type = GoodType.DoubleArm.name,
                cost = 30
            ),
            GoodBd(
                number = 9,
                name = "Пронзающий лук",
                type = GoodType.DoubleArm.name,
                cost = 30
            ),
            GoodBd(
                number = 10,
                name = "Молот войны",
                type = GoodType.DoubleArm.name,
                cost = 30
            ),
            GoodBd(
                number = 10,
                name = "Молот войны",
                type = GoodType.DoubleArm.name,
                cost = 30
            ),
            GoodBd(
                number = 11,
                name = "Отравленный кинжал",
                type = GoodType.Arm.name,
                cost = 20
            ),
            GoodBd(
                number = 11,
                name = "Отравленный кинжал",
                type = GoodType.Arm.name,
                cost = 20
            ),
            GoodBd(
                number = 12,
                name = "Малое лечебное зелье",
                type = GoodType.SmallThing.name,
                cost = 10
            ),
            GoodBd(
                number = 12,
                name = "Малое лечебное зелье",
                type = GoodType.SmallThing.name,
                cost = 10
            ),
            GoodBd(
                number = 12,
                name = "Малое лечебное зелье",
                type = GoodType.SmallThing.name,
                cost = 10
            ),
            GoodBd(
                number = 12,
                name = "Малое лечебное зелье",
                type = GoodType.SmallThing.name,
                cost = 10
            ),
            GoodBd(
                number = 13,
                name = "Малое зелье выносливости",
                type = GoodType.SmallThing.name,
                cost = 10
            ),
            GoodBd(
                number = 13,
                name = "Малое зелье выносливости",
                type = GoodType.SmallThing.name,
                cost = 10
            ),
            GoodBd(
                number = 13,
                name = "Малое зелье выносливости",
                type = GoodType.SmallThing.name,
                cost = 10
            ),
            GoodBd(
                number = 13,
                name = "Малое зелье выносливости",
                type = GoodType.SmallThing.name,
                cost = 10
            ),
            GoodBd(
                number = 14,
                name = "Малое зелье силы",
                type = GoodType.SmallThing.name,
                cost = 10
            ),
            GoodBd(
                number = 14,
                name = "Малое зелье силы",
                type = GoodType.SmallThing.name,
                cost = 10
            ),
            GoodBd(
                number = 14,
                name = "Малое зелье силы",
                type = GoodType.SmallThing.name,
                cost = 10
            ),
            GoodBd(
                number = 14,
                name = "Малое зелье силы",
                type = GoodType.SmallThing.name,
                cost = 10
            ),
            GoodBd(
                number = 15,
                name = "Сапоги скорости",
                type = GoodType.Foot.name,
                cost = 30
            ),
            GoodBd(
                number = 15,
                name = "Сапоги скорости",
                type = GoodType.Foot.name,
                cost = 30
            ),
            GoodBd(
                number = 16,
                name = "Накидка с карманами",
                type = GoodType.Body.name,
                cost = 20
            ),
            GoodBd(
                number = 16,
                name = "Накидка с карманами",
                type = GoodType.Body.name,
                cost = 20
            ),
            GoodBd(
                number = 17,
                name = "Талисман Вдохновления",
                type = GoodType.Head.name,
                cost = 45
            ),
            GoodBd(
                number = 17,
                name = "Талисман Вдохновления",
                type = GoodType.Head.name,
                cost = 45
            ),
            GoodBd(
                number = 18,
                name = "Боевой топор",
                type = GoodType.Arm.name,
                cost = 20
            ),
            GoodBd(
                number = 18,
                name = "Боевой топор",
                type = GoodType.Arm.name,
                cost = 20
            ),
            GoodBd(
                number = 19,
                name = "Утяжеленная сеть",
                type = GoodType.DoubleArm.name,
                cost = 20
            ),
            GoodBd(
                number = 19,
                name = "Утяжеленная сеть",
                type = GoodType.DoubleArm.name,
                cost = 20
            ),
            GoodBd(
                number = 20,
                name = "Малое зелье маны",
                type = GoodType.SmallThing.name,
                cost = 10
            ),
            GoodBd(
                number = 20,
                name = "Малое зелье маны",
                type = GoodType.SmallThing.name,
                cost = 10
            ),
            GoodBd(
                number = 20,
                name = "Малое зелье маны",
                type = GoodType.SmallThing.name,
                cost = 10
            ),
            GoodBd(
                number = 20,
                name = "Малое зелье маны",
                type = GoodType.SmallThing.name,
                cost = 10
            ),
            GoodBd(
                number = 21,
                name = "Ослепляющая пыль",
                type = GoodType.SmallThing.name,
                cost = 20
            ),
            GoodBd(
                number = 21,
                name = "Ослепляющая пыль",
                type = GoodType.SmallThing.name,
                cost = 20
            ),
            GoodBd(
                number = 22,
                name = "Тяжелые поножи",
                type = GoodType.Foot.name,
                cost = 20
            ),
            GoodBd(
                number = 22,
                name = "Тяжелые поножи",
                type = GoodType.Foot.name,
                cost = 20
            ),
            GoodBd(
                number = 23,
                name = "Кольчуга",
                type = GoodType.Body.name,
                cost = 20
            ),
            GoodBd(
                number = 23,
                name = "Кольчуга",
                type = GoodType.Body.name,
                cost = 20
            ),
            GoodBd(
                number = 24,
                name = "Амулет жизни",
                type = GoodType.Head.name,
                cost = 20
            ),
            GoodBd(
                number = 24,
                name = "Амулет жизни",
                type = GoodType.Head.name,
                cost = 20
            ),
            GoodBd(
                number = 25,
                name = "Зазубренный меч",
                type = GoodType.Arm.name,
                cost = 30
            ),
            GoodBd(
                number = 25,
                name = "Зазубренный меч",
                type = GoodType.Arm.name,
                cost = 30
            ),
            GoodBd(
                number = 26,
                name = "Длинное копье",
                type = GoodType.DoubleArm.name,
                cost = 30
            ),
            GoodBd(
                number = 26,
                name = "Длинное копье",
                type = GoodType.DoubleArm.name,
                cost = 30
            ),
            GoodBd(
                number = 27,
                name = "Большое лечебное зелье",
                type = GoodType.SmallThing.name,
                cost = 30
            ),
            GoodBd(
                number = 27,
                name = "Большое лечебное зелье",
                type = GoodType.SmallThing.name,
                cost = 30
            ),
            GoodBd(
                number = 27,
                name = "Большое лечебное зелье",
                type = GoodType.SmallThing.name,
                cost = 30
            ),
            GoodBd(
                number = 27,
                name = "Большое лечебное зелье",
                type = GoodType.SmallThing.name,
                cost = 30
            ),
            GoodBd(
                number = 28,
                name = "Лунная серьга",
                type = GoodType.SmallThing.name,
                cost = 20
            ),
            GoodBd(
                number = 28,
                name = "Лунная серьга",
                type = GoodType.SmallThing.name,
                cost = 20
            ),
            GoodBd(
                number = 29,
                name = "Удобные башмаки",
                type = GoodType.Foot.name,
                cost = 30
            ),
            GoodBd(
                number = 29,
                name = "Удобные башмаки",
                type = GoodType.Foot.name,
                cost = 30
            ),
            GoodBd(
                number = 30,
                name = "Клепанная броня",
                type = GoodType.Body.name,
                cost = 30
            ),
            GoodBd(
                number = 30,
                name = "Клепанная броня",
                type = GoodType.Body.name,
                cost = 30
            ),
            GoodBd(
                number = 31,
                name = "Шлем ястреба",
                type = GoodType.Head.name,
                cost = 20
            ),
            GoodBd(
                number = 31,
                name = "Шлем ястреба",
                type = GoodType.Head.name,
                cost = 20
            ),
            GoodBd(
                number = 32,
                name = "Осадный щит",
                type = GoodType.Arm.name,
                cost = 40
            ),
            GoodBd(
                number = 32,
                name = "Осадный щит",
                type = GoodType.Arm.name,
                cost = 40
            ),
            GoodBd(
                number = 33,
                name = "Зажигательная бомба",
                type = GoodType.SmallThing.name,
                cost = 30
            ),
            GoodBd(
                number = 33,
                name = "Зажигательная бомба",
                type = GoodType.SmallThing.name,
                cost = 30
            ),
            GoodBd(
                number = 34,
                name = "Большое зелье выносливости",
                type = GoodType.SmallThing.name,
                cost = 30
            ),
            GoodBd(
                number = 34,
                name = "Большое зелье выносливости",
                type = GoodType.SmallThing.name,
                cost = 30
            ),
            GoodBd(
                number = 34,
                name = "Большое зелье выносливости",
                type = GoodType.SmallThing.name,
                cost = 30
            ),
            GoodBd(
                number = 34,
                name = "Большое зелье выносливости",
                type = GoodType.SmallThing.name,
                cost = 30
            ),
            GoodBd(
                number = 35,
                name = "Статуэтка сокола",
                type = GoodType.SmallThing.name,
                cost = 50
            ),
            GoodBd(
                number = 35,
                name = "Статуэтка сокола",
                type = GoodType.SmallThing.name,
                cost = 50
            ),
            GoodBd(
                number = 36,
                name = "Сапоги стремительности",
                type = GoodType.Foot.name,
                cost = 40
            ),
            GoodBd(
                number = 37,
                name = "Мантия воплощения",
                type = GoodType.Body.name,
                cost = 40
            ),
            GoodBd(
                number = 37,
                name = "Мантия воплощения",
                type = GoodType.Body.name,
                cost = 40
            ),
            GoodBd(
                number = 38,
                name = "Тяжелый шлем",
                type = GoodType.Head.name,
                cost = 30
            ),
            GoodBd(
                number = 38,
                name = "Тяжелый шлем",
                type = GoodType.Head.name,
                cost = 30
            ),
            GoodBd(
                number = 39,
                name = "Цепь с крюком",
                type = GoodType.DoubleArm.name,
                cost = 40
            ),
            GoodBd(
                number = 39,
                name = "Цепь с крюком",
                type = GoodType.DoubleArm.name,
                cost = 40
            ),
            GoodBd(
                number = 40,
                name = "Универсальный кинжал",
                type = GoodType.Arm.name,
                cost = 25
            ),
            GoodBd(
                number = 41,
                name = "Большое зелье силы",
                type = GoodType.SmallThing.name,
                cost = 40
            ),
            GoodBd(
                number = 41,
                name = "Большое зелье силы",
                type = GoodType.SmallThing.name,
                cost = 40
            ),
            GoodBd(
                number = 41,
                name = "Большое зелье силы",
                type = GoodType.SmallThing.name,
                cost = 40
            ),
            GoodBd(
                number = 41,
                name = "Большое зелье силы",
                type = GoodType.SmallThing.name,
                cost = 40
            ),
            GoodBd(
                number = 42,
                name = "Кольцо спешки",
                type = GoodType.SmallThing.name,
                cost = 30
            ),
            GoodBd(
                number = 42,
                name = "Кольцо спешки",
                type = GoodType.SmallThing.name,
                cost = 30
            ),
            GoodBd(
                number = 43,
                name = "Сапоги проворства",
                type = GoodType.Foot.name,
                cost = 75
            ),
            GoodBd(
                number = 43,
                name = "Сапоги проворства",
                type = GoodType.Foot.name,
                cost = 75
            ),
            GoodBd(
                number = 44,
                name = "Бригантина",
                type = GoodType.Body.name,
                cost = 35
            ),
            GoodBd(
                number = 44,
                name = "Бригантина",
                type = GoodType.Body.name,
                cost = 35
            ),
            GoodBd(
                number = 45,
                name = "Подвеска темных союзов",
                type = GoodType.Head.name,
                cost = 75
            ),
            GoodBd(
                number = 45,
                name = "Подвеска темных союзов",
                type = GoodType.Head.name,
                cost = 75
            ),
            GoodBd(
                number = 46,
                name = "Шипованный щит",
                type = GoodType.Arm.name,
                cost = 40
            ),
            GoodBd(
                number = 46,
                name = "Шипованный щит",
                type = GoodType.Arm.name,
                cost = 40
            ),
            GoodBd(
                number = 47,
                name = "Жатвенная коса",
                type = GoodType.DoubleArm.name,
                cost = 40
            ),
            GoodBd(
                number = 47,
                name = "Жатвенная коса",
                type = GoodType.DoubleArm.name,
                cost = 40
            ),
            GoodBd(
                number = 48,
                name = "Большое зелье маны",
                type = GoodType.SmallThing.name,
                cost = 30
            ),
            GoodBd(
                number = 48,
                name = "Большое зелье маны",
                type = GoodType.SmallThing.name,
                cost = 30
            ),
            GoodBd(
                number = 48,
                name = "Большое зелье маны",
                type = GoodType.SmallThing.name,
                cost = 30
            ),
            GoodBd(
                number = 48,
                name = "Большое зелье маны",
                type = GoodType.SmallThing.name,
                cost = 30
            ),
            GoodBd(
                number = 49,
                name = "Солнечная серьга",
                type = GoodType.SmallThing.name,
                cost = 40
            ),
            GoodBd(
                number = 49,
                name = "Солнечная серьга",
                type = GoodType.SmallThing.name,
                cost = 40
            ),
            GoodBd(
                number = 50,
                name = "Стальные сабатоны",
                type = GoodType.Foot.name,
                cost = 60
            ),
            GoodBd(
                number = 50,
                name = "Стальные сабатоны",
                type = GoodType.Foot.name,
                cost = 60
            ),
            GoodBd(
                number = 51,
                name = "Теневой доспех",
                type = GoodType.Body.name,
                cost = 30
            ),
            GoodBd(
                number = 51,
                name = "Теневой доспех",
                type = GoodType.Body.name,
                cost = 30
            ),
            GoodBd(
                number = 52,
                name = "Защитный амулет",
                type = GoodType.Head.name,
                cost = 60
            ),
            GoodBd(
                number = 52,
                name = "Защитный амулет",
                type = GoodType.Head.name,
                cost = 60
            ),
            GoodBd(
                number = 53,
                name = "Черный нож",
                type = GoodType.Arm.name,
                cost = 25
            ),
            GoodBd(
                number = 53,
                name = "Черный нож",
                type = GoodType.Arm.name,
                cost = 25
            ),
            GoodBd(
                number = 54,
                name = "Посох превосходства",
                type = GoodType.DoubleArm.name,
                cost = 60
            ),
            GoodBd(
                number = 54,
                name = "Посох превосходства",
                type = GoodType.DoubleArm.name,
                cost = 60
            ),
            GoodBd(
                number = 55,
                name = "Превосходное лечебное зелье",
                type = GoodType.SmallThing.name,
                cost = 50
            ),
            GoodBd(
                number = 55,
                name = "Превосходное лечебное зелье",
                type = GoodType.SmallThing.name,
                cost = 50
            ),
            GoodBd(
                number = 55,
                name = "Превосходное лечебное зелье",
                type = GoodType.SmallThing.name,
                cost = 50
            ),
            GoodBd(
                number = 55,
                name = "Превосходное лечебное зелье",
                type = GoodType.SmallThing.name,
                cost = 50
            ),
            GoodBd(
                number = 56,
                name = "Кольцо жестокости",
                type = GoodType.SmallThing.name,
                cost = 40
            ),
            GoodBd(
                number = 56,
                name = "Кольцо жестокости",
                type = GoodType.SmallThing.name,
                cost = 40
            ),
            GoodBd(
                number = 57,
                name = "Сандали безмятежности",
                type = GoodType.Foot.name,
                cost = 75
            ),
            GoodBd(
                number = 57,
                name = "Сандали безмятежности",
                type = GoodType.Foot.name,
                cost = 75
            ),
            GoodBd(
                number = 58,
                name = "Накидка прозрачности",
                type = GoodType.Body.name,
                cost = 75
            ),
            GoodBd(
                number = 58,
                name = "Накидка прозрачности",
                type = GoodType.Body.name,
                cost = 75
            ),
            GoodBd(
                number = 59,
                name = "Телескопические линзы",
                type = GoodType.Head.name,
                cost = 50
            ),
            GoodBd(
                number = 59,
                name = "Телескопические линзы",
                type = GoodType.Head.name,
                cost = 50
            ),
            GoodBd(
                number = 60,
                name = "Нестабильная взрывчатка",
                type = GoodType.Arm.name,
                cost = 45
            ),
            GoodBd(
                number = 60,
                name = "Нестабильная взрывчатка",
                type = GoodType.Arm.name,
                cost = 45
            ),
            GoodBd(
                number = 61,
                name = "Фигурный щит",
                type = GoodType.DoubleArm.name,
                cost = 60
            ),
            GoodBd(
                number = 61,
                name = "Фигурный щит",
                type = GoodType.DoubleArm.name,
                cost = 60
            ),
            GoodBd(
                number = 62,
                name = "Пыль обречения",
                type = GoodType.SmallThing.name,
                cost = 40
            ),
            GoodBd(
                number = 62,
                name = "Пыль обречения",
                type = GoodType.SmallThing.name,
                cost = 40
            ),
            GoodBd(
                number = 63,
                name = "Счастливый глаз",
                type = GoodType.SmallThing.name,
                cost = 60
            ),
            GoodBd(
                number = 63,
                name = "Счастливый глаз",
                type = GoodType.SmallThing.name,
                cost = 60
            ),
            GoodBd(
                number = 64,
                name = "Сапоги быстрого бега",
                type = GoodType.Foot.name,
                cost = 75
            ),
            GoodBd(
                number = 64,
                name = "Сапоги быстрого бега",
                type = GoodType.Foot.name,
                cost = 75
            ),
            GoodBd(
                number = 65,
                name = "Латы",
                type = GoodType.Body.name,
                cost = 50
            ),
            GoodBd(
                number = 65,
                name = "Латы",
                type = GoodType.Body.name,
                cost = 50
            ),
            GoodBd(
                number = 66,
                name = "Маска ужаса",
                type = GoodType.Head.name,
                cost = 50
            ),
            GoodBd(
                number = 66,
                name = "Маска ужаса",
                type = GoodType.Head.name,
                cost = 50
            ),
            GoodBd(
                number = 67,
                name = "Сбалансированный клинок",
                type = GoodType.Arm.name,
                cost = 60
            ),
            GoodBd(
                number = 67,
                name = "Сбалансированный клинок",
                type = GoodType.Arm.name,
                cost = 60
            ),
            GoodBd(
                number = 68,
                name = "Алебарда",
                type = GoodType.DoubleArm.name,
                cost = 75
            ),
            GoodBd(
                number = 68,
                name = "Алебарда",
                type = GoodType.DoubleArm.name,
                cost = 75
            ),
            GoodBd(
                number = 69,
                name = "Звездная серьга",
                type = GoodType.SmallThing.name,
                cost = 70
            ),
            GoodBd(
                number = 69,
                name = "Звездная серьга",
                type = GoodType.SmallThing.name,
                cost = 70
            ),
            GoodBd(
                number = 70,
                name = "Кольцо второго шанса",
                type = GoodType.SmallThing.name,
                cost = 75
            ),
            GoodBd(
                number = 70,
                name = "Кольцо второго шанса",
                type = GoodType.SmallThing.name,
                cost = 75
            ),
            GoodBd(
                number = 71,
                name = "Сапоги левитации",
                type = GoodType.Foot.name,
                cost = 50,
                isDrawing = true
            ),
            GoodBd(
                number = 71,
                name = "Сапоги левитации",
                type = GoodType.Foot.name,
                cost = 50
            ),
            GoodBd(
                number = 72,
                name = "Ботинки счастья",
                type = GoodType.Foot.name,
                cost = 50,
                isDrawing = true
            ),
            GoodBd(
                number = 72,
                name = "Ботинки счастья",
                type = GoodType.Foot.name,
                cost = 50
            ),
            GoodBd(
                number = 73,
                name = "Мерцающий плащ",
                type = GoodType.Body.name,
                cost = 50,
                isDrawing = true
            ),
            GoodBd(
                number = 73,
                name = "Мерцающий плащ",
                type = GoodType.Body.name,
                cost = 50
            ),
            GoodBd(
                number = 74,
                name = "Доспех лезвий",
                type = GoodType.Body.name,
                cost = 40,
                isDrawing = true
            ),
            GoodBd(
                number = 74,
                name = "Доспех лезвий",
                type = GoodType.Body.name,
                cost = 40
            ),
            GoodBd(
                number = 75,
                name = "Браслет стихий",
                type = GoodType.Head.name,
                cost = 25,
                isDrawing = true
            ),
            GoodBd(
                number = 75,
                name = "Браслет стихий",
                type = GoodType.Head.name,
                cost = 25
            ),
            GoodBd(
                number = 76,
                name = "Кольчужный капюшон",
                type = GoodType.Head.name,
                cost = 40,
                isDrawing = true
            ),
            GoodBd(
                number = 76,
                name = "Кольчужный капюшон",
                type = GoodType.Head.name,
                cost = 40
            ),
            GoodBd(
                number = 77,
                name = "Леденящий клинок",
                type = GoodType.Arm.name,
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 77,
                name = "Леденящий клинок",
                type = GoodType.Arm.name,
                cost = 30
            ),
            GoodBd(
                number = 78,
                name = "Штормовой клинок",
                type = GoodType.Arm.name,
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 78,
                name = "Штормовой клинок",
                type = GoodType.Arm.name,
                cost = 30
            ),
            GoodBd(
                number = 79,
                name = "Адский клинок",
                type = GoodType.Arm.name,
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 79,
                name = "Адский клинок",
                type = GoodType.Arm.name,
                cost = 30
            ),
            GoodBd(
                number = 80,
                name = "Дрожащий клинок",
                type = GoodType.Arm.name,
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 80,
                name = "Дрожащий клинок",
                type = GoodType.Arm.name,
                cost = 30
            ),
            GoodBd(
                number = 81,
                name = "Сияющий клинок",
                type = GoodType.Arm.name,
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 81,
                name = "Сияющий клинок",
                type = GoodType.Arm.name,
                cost = 30
            ),
            GoodBd(
                number = 82,
                name = "Ночной клинок",
                type = GoodType.Arm.name,
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 82,
                name = "Ночной клинок",
                type = GoodType.Arm.name,
                cost = 30
            ),
            GoodBd(
                number = 83,
                name = "Жезл мороза",
                type = GoodType.Arm.name,
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 83,
                name = "Жезл мороза",
                type = GoodType.Arm.name,
                cost = 30
            ),
            GoodBd(
                number = 84,
                name = "Жезл Штормов",
                type = GoodType.Arm.name,
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 84,
                name = "Жезл Штормов",
                type = GoodType.Arm.name,
                cost = 30
            ),
            GoodBd(
                number = 85,
                name = "Жезл Преисподней",
                type = GoodType.Arm.name,
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 85,
                name = "Жезл Преисподней",
                type = GoodType.Arm.name,
                cost = 30
            ),
            GoodBd(
                number = 86,
                name = "Жезл дрожи земли",
                type = GoodType.Arm.name,
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 86,
                name = "Жезл дрожи земли",
                type = GoodType.Arm.name,
                cost = 30
            ),
            GoodBd(
                number = 87,
                name = "Жезл сияния",
                type = GoodType.Arm.name,
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 87,
                name = "Жезл сияния",
                type = GoodType.Arm.name,
                cost = 30
            ),
            GoodBd(
                number = 88,
                name = "Жезл темноты",
                type = GoodType.Arm.name,
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 88,
                name = "Жезл темноты",
                type = GoodType.Arm.name,
                cost = 30
            ),
            GoodBd(
                number = 89,
                name = "Малое зелье исцеления",
                type = GoodType.SmallThing.name,
                cost = 10,
                isDrawing = true
            ),
            GoodBd(
                number = 89,
                name = "Малое зелье исцеления",
                type = GoodType.SmallThing.name,
                cost = 10
            ),
            GoodBd(
                number = 90,
                name = "Большое зелье исцеления",
                type = GoodType.SmallThing.name,
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 90,
                name = "Большое зелье исцеления",
                type = GoodType.SmallThing.name,
                cost = 30
            ),
            GoodBd(
                number = 91,
                name = "Стальное кольцо",
                type = GoodType.SmallThing.name,
                cost = 20,
                isDrawing = true
            ),
            GoodBd(
                number = 91,
                name = "Стальное кольцо",
                type = GoodType.SmallThing.name,
                cost = 20
            ),
            GoodBd(
                number = 92,
                name = "Кольцо подавления",
                type = GoodType.SmallThing.name,
                cost = 25,
                isDrawing = true
            ),
            GoodBd(
                number = 92,
                name = "Кольцо подавления",
                type = GoodType.SmallThing.name,
                cost = 25
            ),
            GoodBd(
                number = 93,
                name = "Свиток силы",
                type = GoodType.SmallThing.name,
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 93,
                name = "Свиток силы",
                type = GoodType.SmallThing.name,
                cost = 30
            ),
            GoodBd(
                number = 94,
                name = "Свиток лечения",
                type = GoodType.SmallThing.name,
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 94,
                name = "Свиток лечения",
                type = GoodType.SmallThing.name,
                cost = 30
            ),
            GoodBd(
                number = 95,
                name = "Свиток выносливости",
                type = GoodType.SmallThing.name,
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 95,
                name = "Свиток выносливости",
                type = GoodType.SmallThing.name,
                cost = 30
            ),
            GoodBd(
                number = 96,
                name = "Реактивные сапоги",
                type = GoodType.Foot.name,
                cost = 80
            ),
            GoodBd(
                number = 96,
                name = "Реактивные сапоги",
                type = GoodType.Foot.name,
                cost = 80
            ),
            GoodBd(
                number = 97,
                name = "Портянки выносливости",
                type = GoodType.Foot.name,
                cost = 40
            ),
            GoodBd(
                number = 98,
                name = "Сапоги из чешуи дрейка",
                type = GoodType.Foot.name,
                cost = 50
            ),
            GoodBd(
                number = 99,
                name = "Лавовые ботфорты",
                type = GoodType.Foot.name,
                cost = 50
            ),
            GoodBd(
                number = 100,
                name = "Мантия призыва",
                type = GoodType.Body.name,
                cost = 40
            ),
            GoodBd(
                number = 101,
                name = "Вторая кожа",
                type = GoodType.Body.name,
                cost = 30
            ),
            GoodBd(
                number = 102,
                name = "Мантия самопожертвования",
                type = GoodType.Body.name,
                cost = 50
            ),
            GoodBd(
                number = 103,
                name = "Доспех из чешуи дрейка",
                type = GoodType.Body.name,
                cost = 50
            ),
            GoodBd(
                number = 104,
                name = "Паровой доспех",
                type = GoodType.Body.name,
                cost = 50
            ),
            GoodBd(
                number = 105,
                name = "Изьеденная молью шаль",
                type = GoodType.Body.name,
                cost = 10
            ),
            GoodBd(
                number = 106,
                name = "Ожерелье из зубов",
                type = GoodType.Head.name,
                cost = 40
            ),
            GoodBd(
                number = 107,
                name = "Рогатый шлем",
                type = GoodType.Head.name,
                cost = 30
            ),
            GoodBd(
                number = 107,
                name = "Рогатый шлем",
                type = GoodType.Head.name,
                cost = 30
            ),
            GoodBd(
                number = 108,
                name = "Шлем из чешуи дрейка",
                type = GoodType.Head.name,
                cost = 50
            ),
            GoodBd(
                number = 109,
                name = "Воровской капюшон",
                type = GoodType.Head.name,
                cost = 20
            ),
            GoodBd(
                number = 110,
                name = "Шлем гор",
                type = GoodType.Head.name,
                cost = 50
            ),
            GoodBd(
                number = 111,
                name = "Гребень волны",
                type = GoodType.Head.name,
                cost = 50
            ),
            GoodBd(
                number = 112,
                name = "Древний бур",
                type = GoodType.DoubleArm.name,
                cost = 30
            ),
            GoodBd(
                number = 112,
                name = "Древний бур",
                type = GoodType.DoubleArm.name,
                cost = 30
            ),
            GoodBd(
                number = 113,
                name = "Топор порчи черепов",
                type = GoodType.DoubleArm.name,
                cost = 50
            ),
            GoodBd(
                number = 114,
                name = "Посох Зорна",
                type = GoodType.DoubleArm.name,
                cost = 50
            ),
            GoodBd(
                number = 115,
                name = "Горный молот",
                type = GoodType.DoubleArm.name,
                cost = 50
            ),
            GoodBd(
                number = 116,
                name = "Воспламеняющийся меч",
                type = GoodType.Arm.name,
                cost = 20
            ),
            GoodBd(
                number = 116,
                name = "Воспламеняющийся меч",
                type = GoodType.Arm.name,
                cost = 20
            ),
            GoodBd(
                number = 117,
                name = "Кровавый топор",
                type = GoodType.Arm.name,
                cost = 40
            ),
            GoodBd(
                number = 117,
                name = "Кровавый топор",
                type = GoodType.Arm.name,
                cost = 40
            ),
            GoodBd(
                number = 118,
                name = "Посох стихий",
                type = GoodType.DoubleArm.name,
                cost = 50
            ),
            GoodBd(
                number = 118,
                name = "Посох стихий",
                type = GoodType.DoubleArm.name,
                cost = 50
            ),
            GoodBd(
                number = 119,
                name = "Череп ненависти",
                type = GoodType.Arm.name,
                cost = 50
            ),
            GoodBd(
                number = 120,
                name = "Посох призыва",
                type = GoodType.DoubleArm.name,
                cost = 60
            ),
            GoodBd(
                number = 121,
                name = "Сфера зари",
                type = GoodType.Arm.name,
                cost = 50
            ),
            GoodBd(
                number = 122,
                name = "Сфера сумерек",
                type = GoodType.Arm.name,
                cost = 50
            ),
            GoodBd(
                number = 123,
                name = "Кольцо черепов",
                type = GoodType.SmallThing.name,
                cost = 50
            ),
            GoodBd(
                number = 123,
                name = "Кольцо черепов",
                type = GoodType.SmallThing.name,
                cost = 50
            ),
            GoodBd(
                number = 124,
                name = "Компас обречения",
                type = GoodType.SmallThing.name,
                cost = 50
            ),
            GoodBd(
                number = 125,
                name = "Странный механизм",
                type = GoodType.SmallThing.name,
                cost = 50
            ),
            GoodBd(
                number = 126,
                name = "Управляемый паук",
                type = GoodType.SmallThing.name,
                cost = 40
            ),
            GoodBd(
                number = 127,
                name = "Гигантский управляемый паук",
                type = GoodType.SmallThing.name,
                cost = 60
            ),
            GoodBd(
                number = 128,
                name = "Черное кадило",
                type = GoodType.SmallThing.name,
                cost = 50
            ),
            GoodBd(
                number = 129,
                name = "Черная карта",
                type = GoodType.SmallThing.name,
                cost = 75
            ),
            GoodBd(
                number = 130,
                name = "Плетеное кольцо",
                type = GoodType.SmallThing.name,
                cost = 50
            ),
            GoodBd(
                number = 131,
                name = "Сердце предателя",
                type = GoodType.SmallThing.name,
                cost = 60
            ),
            GoodBd(
                number = 132,
                name = "Энергетическое ядро",
                type = GoodType.SmallThing.name,
                cost = 75
            ),
            GoodBd(
                number = 133,
                name = "Резонирующий кристал",
                type = GoodType.SmallThing.name,
                cost = 20
            )
        )
    }

    suspend fun fillPerks(
        perksDao: PerksDao
    ) {
        addBrutePerks(perksDao)
        addMindthiefPerks(perksDao)
        addCragheartPerks(perksDao)
        addScoundrelPerks(perksDao)
        addSpellweaverPerks(perksDao)
        addTinkererPerks(perksDao)
    }

    private suspend fun addBrutePerks(
        perksDao: PerksDao
    ) {
        perksDao.insertAll(
            PerkBd(
                text = "Уберите 2 карты #01",
                characterType = CharacterClassType.Brute.name
            ),
            PerkBd(
                text = "Поменяйте 1 карту #01 на 1 карту #03",
                characterType = CharacterClassType.Brute.name
            ),
            PerkBd(
                text = "Добавьте 2 карты #03",
                characterType = CharacterClassType.Brute.name
            ),
            PerkBd(
                text = "Добавьте 2 карты #03",
                characterType = CharacterClassType.Brute.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #05",
                characterType = CharacterClassType.Brute.name
            ),
            PerkBd(
                text = "Добавьте 3 карты с эффектом \"#07 ОТТОЛКНУТЬ #08 1\"",
                characterType = CharacterClassType.Brute.name
            ),
            PerkBd(
                text = "Добавьте 3 карты с эффектом \"#07 ОТТОЛКНУТЬ #08 1\"",
                characterType = CharacterClassType.Brute.name
            ),
            PerkBd(
                text = "Добавьте 2 карты с эффектом \"#07 ПРОБОЙ #09 3\"",
                characterType = CharacterClassType.Brute.name
            ),
            PerkBd(
                text = "Добавьте 1 карту с эффектом \"#07 ОГЛУШЕНИЕ #10\"",
                characterType = CharacterClassType.Brute.name
            ),
            PerkBd(
                text = "Добавьте 1 карту с эффектом \"#07 ОГЛУШЕНИЕ #10\"",
                characterType = CharacterClassType.Brute.name
            ),
            PerkBd(
                text = "Добавьте 1 карту с эффектом \"#07 РАЗОРУЖЕНИЕ #11\" и 1 карту с эффектом \"#07 СМЯТЕНИЕ #12\"",
                characterType = CharacterClassType.Brute.name
            ),
            PerkBd(
                text = "Добавьте 1 карту с эффектом \"#07 ДОБАВИТЬ ЦЕЛЬ #13\"",
                characterType = CharacterClassType.Brute.name
            ),
            PerkBd(
                text = "Добавьте 1 карту с эффектом \"#07 ДОБАВИТЬ ЦЕЛЬ #13\"",
                characterType = CharacterClassType.Brute.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #03 с эффектом \"Щит 1 (на себя)\"",
                characterType = CharacterClassType.Brute.name
            ),
            PerkBd(
                text = "Игнорируйте отрицательные эффекты предметов и добавьте 1 карту #03",
                characterType = CharacterClassType.Brute.name
            )
        )
    }

    private suspend fun addCragheartPerks(
        perksDao: PerksDao
    ) {
        perksDao.insertAll(
            PerkBd(
                text = "Уберите 4 карты #15",
                characterType = CharacterClassType.Cragheart.name
            ),
            PerkBd(
                text = "Поменяйте 1 карту #01 на 1 карту #03",
                characterType = CharacterClassType.Cragheart.name
            ),
            PerkBd(
                text = "Поменяйте 1 карту #01 на 1 карту #03",
                characterType = CharacterClassType.Cragheart.name
            ),
            PerkBd(
                text = "Поменяйте 1 карту #01 на 1 карту #03",
                characterType = CharacterClassType.Cragheart.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #02 и 2 карты #04",
                characterType = CharacterClassType.Cragheart.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #03 с эффектом \"ПАРАЛИЧ #18\"",
                characterType = CharacterClassType.Cragheart.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #03 с эффектом \"ПАРАЛИЧ #18\"",
                characterType = CharacterClassType.Cragheart.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #04 с эффектом \"СМЯТЕНИЕ #12\"",
                characterType = CharacterClassType.Cragheart.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #04 с эффектом \"СМЯТЕНИЕ #12\"",
                characterType = CharacterClassType.Cragheart.name
            ),
            PerkBd(
                text = "Добавьте 2 карты эффектом \"#07 ОТТОЛКНУТЬ #08 2\"",
                characterType = CharacterClassType.Cragheart.name
            ),
            PerkBd(
                text = "Добавьте 2 карты #07 #23",
                characterType = CharacterClassType.Cragheart.name
            ),
            PerkBd(
                text = "Добавьте 2 карты #07 #23",
                characterType = CharacterClassType.Cragheart.name
            ),
            PerkBd(
                text = "Добавьте 2 карты #07 #21",
                characterType = CharacterClassType.Cragheart.name
            ),
            PerkBd(
                text = "Игнорируйте отрицательные эффекты предметов",
                characterType = CharacterClassType.Cragheart.name
            ),
            PerkBd(
                text = "Игнорируйте отрицательные эффекты сценариев",
                characterType = CharacterClassType.Cragheart.name
            )
        )
    }

    private suspend fun addScoundrelPerks(
        perksDao: PerksDao
    ) {
        perksDao.insertAll(
            PerkBd(
                text = "Уберите 2 карты #01",
                characterType = CharacterClassType.Scoundrel.name
            ),
            PerkBd(
                text = "Уберите 2 карты #01",
                characterType = CharacterClassType.Scoundrel.name
            ),
            PerkBd(
                text = "Уберите 4 карты #15",
                characterType = CharacterClassType.Scoundrel.name
            ),
            PerkBd(
                text = "Поменяйте 1 карту #02 на 1 карту #15",
                characterType = CharacterClassType.Scoundrel.name
            ),
            PerkBd(
                text = "Поменяйте 1 карту #01 на 1 карту #03",
                characterType = CharacterClassType.Scoundrel.name
            ),
            PerkBd(
                text = "Поменяйте 1 карту #15 на 1 карту #04",
                characterType = CharacterClassType.Scoundrel.name
            ),
            PerkBd(
                text = "Поменяйте 1 карту #15 на 1 карту #04",
                characterType = CharacterClassType.Scoundrel.name
            ),
            PerkBd(
                text = "Добавьте 2 карты #07 #03",
                characterType = CharacterClassType.Scoundrel.name
            ),
            PerkBd(
                text = "Добавьте 2 карты #07 #03",
                characterType = CharacterClassType.Scoundrel.name
            ),
            PerkBd(
                text = "Добавьте 2 карты с эффектом \"#07 ПРОБОЙ #09 3\"",
                characterType = CharacterClassType.Scoundrel.name
            ),
            PerkBd(
                text = "Добавьте 2 карты с эффектом \"#07 ОТРАВЛЕНИЕ #25\"",
                characterType = CharacterClassType.Scoundrel.name
            ),
            PerkBd(
                text = "Добавьте 2 карты с эффектом \"#07 ОТРАВЛЕНИЕ #25\"",
                characterType = CharacterClassType.Scoundrel.name
            ),
            PerkBd(
                text = "Добавьте 2 карты с эффектом \"#07 СМЯТЕНИЕ #12\"",
                characterType = CharacterClassType.Scoundrel.name
            ),
            PerkBd(
                text = "Добавьте 1 карту с эффектом \"#07 НЕВИДИМОСТЬ #24\"",
                characterType = CharacterClassType.Scoundrel.name
            ),
            PerkBd(
                text = "Игнорируйте отрицательные эффекты сценариев",
                characterType = CharacterClassType.Scoundrel.name
            )
        )
    }

    private suspend fun addMindthiefPerks(
        perksDao: PerksDao
    ) {
        perksDao.insertAll(
            PerkBd(
                text = "Уберите 2 карты #01",
                characterType = CharacterClassType.Mindthief.name
            ),
            PerkBd(
                text = "Уберите 2 карты #01",
                characterType = CharacterClassType.Mindthief.name
            ),
            PerkBd(
                text = "Уберите 4 карты #15",
                characterType = CharacterClassType.Mindthief.name
            ),
            PerkBd(
                text = "Поменяйте 2 карты #03 на 2 карты #04",
                characterType = CharacterClassType.Mindthief.name
            ),
            PerkBd(
                text = "Поменяйте 1 карту #02 на 1 карту #15",
                characterType = CharacterClassType.Mindthief.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #04 #16",
                characterType = CharacterClassType.Mindthief.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #04 #16",
                characterType = CharacterClassType.Mindthief.name
            ),
            PerkBd(
                text = "Добавьте 2 карты #07 #03",
                characterType = CharacterClassType.Mindthief.name
            ),
            PerkBd(
                text = "Добавьте 2 карты #07 #03",
                characterType = CharacterClassType.Mindthief.name
            ),
            PerkBd(
                text = "Добавьте 3 карты c эффектом \"#07 ПРИТЯНУТЬ #17 1\"",
                characterType = CharacterClassType.Mindthief.name
            ),
            PerkBd(
                text = "Добавьте 3 карты c эффектом \"#07 СМЯТЕНИЕ #12\"",
                characterType = CharacterClassType.Mindthief.name
            ),
            PerkBd(
                text = "Добавьте 2 карты c эффектом \"#07 ПАРАЛИЧ #18\"",
                characterType = CharacterClassType.Mindthief.name
            ),
            PerkBd(
                text = "Добавьте 1 карту c эффектом \"#07 ОГЛУШЕНИЕ #10\"",
                characterType = CharacterClassType.Mindthief.name
            ),
            PerkBd(
                text = "Добавьте 1 карту c эффектом \"#07 РАЗОРУЖЕНИЕ #11\" и 1 карту c эффектом \"#07 СМЯТЕНИЕ #12\"",
                characterType = CharacterClassType.Mindthief.name
            ),
            PerkBd(
                text = "Игнорируйте отрицательные эффекты сценариев",
                characterType = CharacterClassType.Mindthief.name
            ),
        )
    }

    private suspend fun addSpellweaverPerks(
        perksDao: PerksDao
    ) {
        perksDao.insertAll(
            PerkBd(
                text = "Уберите 4 карты #15",
                characterType = CharacterClassType.Spellweaver.name
            ),
            PerkBd(
                text = "Поменяйте 1 карту #01 на 1 карту #03",
                characterType = CharacterClassType.Spellweaver.name
            ),
            PerkBd(
                text = "Поменяйте 1 карту #01 на 1 карту #03",
                characterType = CharacterClassType.Spellweaver.name
            ),
            PerkBd(
                text = "Добавьте 2 карты #03",
                characterType = CharacterClassType.Spellweaver.name
            ),
            PerkBd(
                text = "Добавьте 2 карты #03",
                characterType = CharacterClassType.Spellweaver.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #15 с эффектом \"ОГЛУШЕНИЕ #10\"",
                characterType = CharacterClassType.Spellweaver.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #03 с эффектом \"РАНА #14\"",
                characterType = CharacterClassType.Spellweaver.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #03 с эффектом \"ПАРАЛИЧ #18\"",
                characterType = CharacterClassType.Spellweaver.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #03 с эффектом \"ПРОКЛЯТЬЕ #26\"",
                characterType = CharacterClassType.Spellweaver.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #04 #22",
                characterType = CharacterClassType.Spellweaver.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #04 #22",
                characterType = CharacterClassType.Spellweaver.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #04 #16",
                characterType = CharacterClassType.Spellweaver.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #04 #16",
                characterType = CharacterClassType.Spellweaver.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #07 #23 и 1 карту #07 #21",
                characterType = CharacterClassType.Spellweaver.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #07 #19 и 1 карту #07 #20",
                characterType = CharacterClassType.Spellweaver.name
            )
        )
    }

    private suspend fun addTinkererPerks(
        perksDao: PerksDao
    ) {
        perksDao.insertAll(
            PerkBd(
                text = "Уберите 2 карты #01",
                characterType = CharacterClassType.Tinkerer.name
            ),
            PerkBd(
                text = "Уберите 2 карты #01",
                characterType = CharacterClassType.Tinkerer.name
            ),
            PerkBd(
                text = "Поменяйте 1 карту #02 на 1 карту #15",
                characterType = CharacterClassType.Tinkerer.name
            ),
            PerkBd(
                text = "Добавьте 2 карты #03",
                characterType = CharacterClassType.Tinkerer.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #05",
                characterType = CharacterClassType.Tinkerer.name
            ),
            PerkBd(
                text = "Добавьте 2 карты #07 #22",
                characterType = CharacterClassType.Tinkerer.name
            ),
            PerkBd(
                text = "Добавьте 3 карты с эффектом \"#07 СМЯТЕНИЕ #12\"",
                characterType = CharacterClassType.Tinkerer.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #03 с эффектом \"РАНА #14\"",
                characterType = CharacterClassType.Tinkerer.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #03 с эффектом \"РАНА #14\"",
                characterType = CharacterClassType.Tinkerer.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #03 с эффектом \"ПАРАЛИЧ #18\"",
                characterType = CharacterClassType.Tinkerer.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #03 с эффектом \"ПАРАЛИЧ #18\"",
                characterType = CharacterClassType.Tinkerer.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #03 с эффектом \"Лечение 2\"",
                characterType = CharacterClassType.Tinkerer.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #03 с эффектом \"Лечение 2\"",
                characterType = CharacterClassType.Tinkerer.name
            ),
            PerkBd(
                text = "Добавьте 1 карту #15 с эффектом \"ДОБАВИТЬ ЦЕЛЬ #13\"",
                characterType = CharacterClassType.Tinkerer.name
            ),
            PerkBd(
                text = "Игнорируйте отрицательные эффекты сценариев",
                characterType = CharacterClassType.Tinkerer.name
            ),
        )
    }
}