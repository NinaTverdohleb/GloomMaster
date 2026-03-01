package com.rumpilstilstkin.gloomhavenhelper.bd.filler

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GoodsDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GoodBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.GoodType

object GoodsFiller {
    suspend fun fill_v1(
        goodsDao: GoodsDao
    ) {
        goodsDao.insertAll(
            GoodBd(
                number = 1,
                name = "Сапоги большого шага",
                type = GoodType.Foot.name,
                image = "item_1.webp",
                cost = 20
            ),
            GoodBd(
                number = 1,
                name = "Сапоги большого шага",
                type = GoodType.Foot.name,
                image = "item_1.webp",
                cost = 20
            ),
            GoodBd(
                number = 2,
                name = "Крылатые сапоги",
                type = GoodType.Foot.name,
                image = "item_2.webp",
                cost = 20
            ),
            GoodBd(
                number = 2,
                name = "Крылатые сапоги",
                type = GoodType.Foot.name,
                image = "item_2.webp",
                cost = 20
            ),
            GoodBd(
                number = 3,
                name = "Броня из шкур",
                type = GoodType.Body.name,
                image = "item_3.webp",
                cost = 10
            ),
            GoodBd(
                number = 3,
                name = "Броня из шкур",
                type = GoodType.Body.name,
                image = "item_3.webp",
                cost = 10
            ),
            GoodBd(
                number = 4,
                name = "Кожаная броня",
                type = GoodType.Body.name,
                image = "item_4.webp",
                cost = 20
            ),
            GoodBd(
                number = 4,
                name = "Кожаная броня",
                type = GoodType.Body.name,
                image = "item_4.webp",
                cost = 20
            ),
            GoodBd(
                number = 4,
                name = "Кожаная броня",
                type = GoodType.Body.name,
                image = "item_4.webp",
                cost = 20
            ),
            GoodBd(
                number = 5,
                name = "Плащ невидимка",
                type = GoodType.Body.name,
                image = "item_5.webp",
                cost = 20
            ),
            GoodBd(
                number = 5,
                name = "Плащ невидимка",
                type = GoodType.Body.name,
                image = "item_5.webp",
                cost = 20
            ),
            GoodBd(
                number = 6,
                name = "Очки орлиного взора",
                type = GoodType.Head.name,
                image = "item_6.webp",
                cost = 30
            ),
            GoodBd(
                number = 7,
                name = "Железный шлем",
                type = GoodType.Head.name,
                image = "item_7.webp",
                cost = 10
            ),
            GoodBd(
                number = 7,
                name = "Железный шлем",
                type = GoodType.Head.name,
                image = "item_7.webp",
                cost = 10
            ),
            GoodBd(
                number = 8,
                name = "Треугольный щит",
                type = GoodType.Arm.name,
                image = "item_8.webp",
                cost = 20
            ),
            GoodBd(
                number = 8,
                name = "Треугольный щит",
                type = GoodType.Arm.name,
                image = "item_8.webp",
                cost = 20
            ),
            GoodBd(
                number = 9,
                name = "Пронзающий лук",
                type = GoodType.DoubleArm.name,
                image = "item_9.webp",
                cost = 30
            ),
            GoodBd(
                number = 9,
                name = "Пронзающий лук",
                type = GoodType.DoubleArm.name,
                image = "item_9.webp",
                cost = 30
            ),
            GoodBd(
                number = 10,
                name = "Молот войны",
                type = GoodType.DoubleArm.name,
                image = "item_10.webp",
                cost = 30
            ),
            GoodBd(
                number = 10,
                name = "Молот войны",
                type = GoodType.DoubleArm.name,
                image = "item_10.webp",
                cost = 30
            ),
            GoodBd(
                number = 11,
                name = "Отравленный кинжал",
                type = GoodType.Arm.name,
                image = "item_11.webp",
                cost = 20
            ),
            GoodBd(
                number = 11,
                name = "Отравленный кинжал",
                type = GoodType.Arm.name,
                image = "item_11.webp",
                cost = 20
            ),
            GoodBd(
                number = 12,
                name = "Малое лечебное зелье",
                type = GoodType.SmallThing.name,
                image = "item_12.webp",
                cost = 10
            ),
            GoodBd(
                number = 12,
                name = "Малое лечебное зелье",
                type = GoodType.SmallThing.name,
                image = "item_12.webp",
                cost = 10
            ),
            GoodBd(
                number = 12,
                name = "Малое лечебное зелье",
                type = GoodType.SmallThing.name,
                image = "item_12.webp",
                cost = 10
            ),
            GoodBd(
                number = 12,
                name = "Малое лечебное зелье",
                type = GoodType.SmallThing.name,
                image = "item_12.webp",
                cost = 10
            ),
            GoodBd(
                number = 13,
                name = "Малое зелье выносливости",
                type = GoodType.SmallThing.name,
                image = "item_13.webp",
                cost = 10
            ),
            GoodBd(
                number = 13,
                name = "Малое зелье выносливости",
                type = GoodType.SmallThing.name,
                image = "item_13.webp",
                cost = 10
            ),
            GoodBd(
                number = 13,
                name = "Малое зелье выносливости",
                type = GoodType.SmallThing.name,
                image = "item_13.webp",
                cost = 10
            ),
            GoodBd(
                number = 13,
                name = "Малое зелье выносливости",
                type = GoodType.SmallThing.name,
                image = "item_13.webp",
                cost = 10
            ),
            GoodBd(
                number = 14,
                name = "Малое зелье силы",
                type = GoodType.SmallThing.name,
                image = "item_14.webp",
                cost = 10
            ),
            GoodBd(
                number = 14,
                name = "Малое зелье силы",
                type = GoodType.SmallThing.name,
                image = "item_14.webp",
                cost = 10
            ),
            GoodBd(
                number = 14,
                name = "Малое зелье силы",
                type = GoodType.SmallThing.name,
                image = "item_14.webp",
                cost = 10
            ),
            GoodBd(
                number = 14,
                name = "Малое зелье силы",
                type = GoodType.SmallThing.name,
                image = "item_14.webp",
                cost = 10
            ),
            GoodBd(
                number = 15,
                name = "Сапоги скорости",
                type = GoodType.Foot.name,
                image = "item_15.webp",
                cost = 30
            ),
            GoodBd(
                number = 15,
                name = "Сапоги скорости",
                type = GoodType.Foot.name,
                image = "item_15.webp",
                cost = 30
            ),
            GoodBd(
                number = 16,
                name = "Накидка с карманами",
                type = GoodType.Body.name,
                image = "item_16.webp",
                cost = 20
            ),
            GoodBd(
                number = 16,
                name = "Накидка с карманами",
                type = GoodType.Body.name,
                image = "item_16.webp",
                cost = 20
            ),
            GoodBd(
                number = 17,
                name = "Талисман Вдохновления",
                type = GoodType.Head.name,
                image = "item_17.webp",
                cost = 45
            ),
            GoodBd(
                number = 17,
                name = "Талисман Вдохновления",
                type = GoodType.Head.name,
                image = "item_17.webp",
                cost = 45
            ),
            GoodBd(
                number = 18,
                name = "Боевой топор",
                type = GoodType.Arm.name,
                image = "item_18.webp",
                cost = 20
            ),
            GoodBd(
                number = 18,
                name = "Боевой топор",
                type = GoodType.Arm.name,
                image = "item_18.webp",
                cost = 20
            ),
            GoodBd(
                number = 19,
                name = "Утяжеленная сеть",
                type = GoodType.DoubleArm.name,
                image = "item_19.webp",
                cost = 20
            ),
            GoodBd(
                number = 19,
                name = "Утяжеленная сеть",
                type = GoodType.DoubleArm.name,
                image = "item_19.webp",
                cost = 20
            ),
            GoodBd(
                number = 20,
                name = "Малое зелье маны",
                type = GoodType.SmallThing.name,
                image = "item_20.webp",
                cost = 10
            ),
            GoodBd(
                number = 20,
                name = "Малое зелье маны",
                type = GoodType.SmallThing.name,
                image = "item_20.webp",
                cost = 10
            ),
            GoodBd(
                number = 20,
                name = "Малое зелье маны",
                type = GoodType.SmallThing.name,
                image = "item_20.webp",
                cost = 10
            ),
            GoodBd(
                number = 20,
                name = "Малое зелье маны",
                type = GoodType.SmallThing.name,
                image = "item_20.webp",
                cost = 10
            ),
            GoodBd(
                number = 21,
                name = "Ослепляющая пыль",
                type = GoodType.SmallThing.name,
                image = "item_21.webp",
                cost = 20
            ),
            GoodBd(
                number = 21,
                name = "Ослепляющая пыль",
                type = GoodType.SmallThing.name,
                image = "item_21.webp",
                cost = 20
            ),
            GoodBd(
                number = 22,
                name = "Тяжелые поножи",
                type = GoodType.Foot.name,
                image = "item_22.webp",
                cost = 20
            ),
            GoodBd(
                number = 22,
                name = "Тяжелые поножи",
                type = GoodType.Foot.name,
                image = "item_22.webp",
                cost = 20
            ),
            GoodBd(
                number = 23,
                name = "Кольчуга",
                type = GoodType.Body.name,
                image = "item_23.webp",
                cost = 20
            ),
            GoodBd(
                number = 23,
                name = "Кольчуга",
                type = GoodType.Body.name,
                image = "item_23.webp",
                cost = 20
            ),
            GoodBd(
                number = 24,
                name = "Амулет жизни",
                type = GoodType.Head.name,
                image = "item_24.webp",
                cost = 20
            ),
            GoodBd(
                number = 24,
                name = "Амулет жизни",
                type = GoodType.Head.name,
                image = "item_24.webp",
                cost = 20
            ),
            GoodBd(
                number = 25,
                name = "Зазубренный меч",
                type = GoodType.Arm.name,
                image = "item_25.webp",
                cost = 30
            ),
            GoodBd(
                number = 25,
                name = "Зазубренный меч",
                type = GoodType.Arm.name,
                image = "item_25.webp",
                cost = 30
            ),
            GoodBd(
                number = 26,
                name = "Длинное копье",
                type = GoodType.DoubleArm.name,
                image = "item_26.webp",
                cost = 30
            ),
            GoodBd(
                number = 26,
                name = "Длинное копье",
                type = GoodType.DoubleArm.name,
                image = "item_26.webp",
                cost = 30
            ),
            GoodBd(
                number = 27,
                name = "Большое лечебное зелье",
                type = GoodType.SmallThing.name,
                image = "item_27.webp",
                cost = 30
            ),
            GoodBd(
                number = 27,
                name = "Большое лечебное зелье",
                type = GoodType.SmallThing.name,
                image = "item_27.webp",
                cost = 30
            ),
            GoodBd(
                number = 27,
                name = "Большое лечебное зелье",
                type = GoodType.SmallThing.name,
                image = "item_27.webp",
                cost = 30
            ),
            GoodBd(
                number = 27,
                name = "Большое лечебное зелье",
                type = GoodType.SmallThing.name,
                image = "item_27.webp",
                cost = 30
            ),
            GoodBd(
                number = 28,
                name = "Лунная серьга",
                type = GoodType.SmallThing.name,
                image = "item_28.webp",
                cost = 20
            ),
            GoodBd(
                number = 28,
                name = "Лунная серьга",
                type = GoodType.SmallThing.name,
                image = "item_28.webp",
                cost = 20
            ),
            GoodBd(
                number = 29,
                name = "Удобные башмаки",
                type = GoodType.Foot.name,
                image = "item_29.webp",
                cost = 30
            ),
            GoodBd(
                number = 29,
                name = "Удобные башмаки",
                type = GoodType.Foot.name,
                image = "item_29.webp",
                cost = 30
            ),
            GoodBd(
                number = 30,
                name = "Клепанная броня",
                type = GoodType.Body.name,
                image = "item_30.webp",
                cost = 30
            ),
            GoodBd(
                number = 30,
                name = "Клепанная броня",
                type = GoodType.Body.name,
                image = "item_30.webp",
                cost = 30
            ),
            GoodBd(
                number = 31,
                name = "Шлем ястреба",
                type = GoodType.Head.name,
                image = "item_31.webp",
                cost = 20
            ),
            GoodBd(
                number = 31,
                name = "Шлем ястреба",
                type = GoodType.Head.name,
                image = "item_31.webp",
                cost = 20
            ),
            GoodBd(
                number = 32,
                name = "Осадный щит",
                type = GoodType.Arm.name,
                image = "item_32.webp",
                cost = 40
            ),
            GoodBd(
                number = 32,
                name = "Осадный щит",
                type = GoodType.Arm.name,
                image = "item_32.webp",
                cost = 40
            ),
            GoodBd(
                number = 33,
                name = "Зажигательная бомба",
                type = GoodType.SmallThing.name,
                image = "item_33.webp",
                cost = 30
            ),
            GoodBd(
                number = 33,
                name = "Зажигательная бомба",
                type = GoodType.SmallThing.name,
                image = "item_33.webp",
                cost = 30
            ),
            GoodBd(
                number = 34,
                name = "Большое зелье выносливости",
                type = GoodType.SmallThing.name,
                image = "item_34.webp",
                cost = 30
            ),
            GoodBd(
                number = 34,
                name = "Большое зелье выносливости",
                type = GoodType.SmallThing.name,
                image = "item_34.webp",
                cost = 30
            ),
            GoodBd(
                number = 34,
                name = "Большое зелье выносливости",
                type = GoodType.SmallThing.name,
                image = "item_34.webp",
                cost = 30
            ),
            GoodBd(
                number = 34,
                name = "Большое зелье выносливости",
                type = GoodType.SmallThing.name,
                image = "item_34.webp",
                cost = 30
            ),
            GoodBd(
                number = 35,
                name = "Статуэтка сокола",
                type = GoodType.SmallThing.name,
                image = "item_35.webp",
                cost = 50
            ),
            GoodBd(
                number = 35,
                name = "Статуэтка сокола",
                type = GoodType.SmallThing.name,
                image = "item_35.webp",
                cost = 50
            ),
            GoodBd(
                number = 36,
                name = "Сапоги стремительности",
                type = GoodType.Foot.name,
                image = "item_36.webp",
                cost = 40
            ),
            GoodBd(
                number = 37,
                name = "Мантия воплощения",
                type = GoodType.Body.name,
                image = "item_37.webp",
                cost = 40
            ),
            GoodBd(
                number = 37,
                name = "Мантия воплощения",
                type = GoodType.Body.name,
                image = "item_37.webp",
                cost = 40
            ),
            GoodBd(
                number = 38,
                name = "Тяжелый шлем",
                type = GoodType.Head.name,
                image = "item_38.webp",
                cost = 30
            ),
            GoodBd(
                number = 38,
                name = "Тяжелый шлем",
                type = GoodType.Head.name,
                image = "item_38.webp",
                cost = 30
            ),
            GoodBd(
                number = 39,
                name = "Цепь с крюком",
                type = GoodType.DoubleArm.name,
                image = "item_39.webp",
                cost = 40
            ),
            GoodBd(
                number = 39,
                name = "Цепь с крюком",
                type = GoodType.DoubleArm.name,
                image = "item_39.webp",
                cost = 40
            ),
            GoodBd(
                number = 40,
                name = "Универсальный кинжал",
                type = GoodType.Arm.name,
                image = "item_40.webp",
                cost = 25
            ),
            GoodBd(
                number = 41,
                name = "Большое зелье силы",
                type = GoodType.SmallThing.name,
                image = "item_41.webp",
                cost = 40
            ),
            GoodBd(
                number = 41,
                name = "Большое зелье силы",
                type = GoodType.SmallThing.name,
                image = "item_41.webp",
                cost = 40
            ),
            GoodBd(
                number = 41,
                name = "Большое зелье силы",
                type = GoodType.SmallThing.name,
                image = "item_41.webp",
                cost = 40
            ),
            GoodBd(
                number = 41,
                name = "Большое зелье силы",
                type = GoodType.SmallThing.name,
                image = "item_41.webp",
                cost = 40
            ),
            GoodBd(
                number = 42,
                name = "Кольцо спешки",
                type = GoodType.SmallThing.name,
                image = "item_42.webp",
                cost = 30
            ),
            GoodBd(
                number = 42,
                name = "Кольцо спешки",
                type = GoodType.SmallThing.name,
                image = "item_42.webp",
                cost = 30
            ),
            GoodBd(
                number = 43,
                name = "Сапоги проворства",
                type = GoodType.Foot.name,
                image = "item_43.webp",
                cost = 75
            ),
            GoodBd(
                number = 43,
                name = "Сапоги проворства",
                type = GoodType.Foot.name,
                image = "item_43.webp",
                cost = 75
            ),
            GoodBd(
                number = 44,
                name = "Бригантина",
                type = GoodType.Body.name,
                image = "item_44.webp",
                cost = 35
            ),
            GoodBd(
                number = 44,
                name = "Бригантина",
                type = GoodType.Body.name,
                image = "item_44.webp",
                cost = 35
            ),
            GoodBd(
                number = 45,
                name = "Подвеска темных союзов",
                type = GoodType.Head.name,
                image = "item_45.webp",
                cost = 75
            ),
            GoodBd(
                number = 45,
                name = "Подвеска темных союзов",
                type = GoodType.Head.name,
                image = "item_45.webp",
                cost = 75
            ),
            GoodBd(
                number = 46,
                name = "Шипованный щит",
                type = GoodType.Arm.name,
                image = "item_46.webp",
                cost = 40
            ),
            GoodBd(
                number = 46,
                name = "Шипованный щит",
                type = GoodType.Arm.name,
                image = "item_46.webp",
                cost = 40
            ),
            GoodBd(
                number = 47,
                name = "Жатвенная коса",
                type = GoodType.DoubleArm.name,
                image = "item_47.webp",
                cost = 40
            ),
            GoodBd(
                number = 47,
                name = "Жатвенная коса",
                type = GoodType.DoubleArm.name,
                image = "item_47.webp",
                cost = 40
            ),
            GoodBd(
                number = 48,
                name = "Большое зелье маны",
                type = GoodType.SmallThing.name,
                image = "item_48.webp",
                cost = 30
            ),
            GoodBd(
                number = 48,
                name = "Большое зелье маны",
                type = GoodType.SmallThing.name,
                image = "item_48.webp",
                cost = 30
            ),
            GoodBd(
                number = 48,
                name = "Большое зелье маны",
                type = GoodType.SmallThing.name,
                image = "item_48.webp",
                cost = 30
            ),
            GoodBd(
                number = 48,
                name = "Большое зелье маны",
                type = GoodType.SmallThing.name,
                image = "item_48.webp",
                cost = 30
            ),
            GoodBd(
                number = 49,
                name = "Солнечная серьга",
                type = GoodType.SmallThing.name,
                image = "item_49.webp",
                cost = 40
            ),
            GoodBd(
                number = 49,
                name = "Солнечная серьга",
                type = GoodType.SmallThing.name,
                image = "item_49.webp",
                cost = 40
            ),
            GoodBd(
                number = 50,
                name = "Стальные сабатоны",
                type = GoodType.Foot.name,
                image = "item_50.webp",
                cost = 60
            ),
            GoodBd(
                number = 50,
                name = "Стальные сабатоны",
                type = GoodType.Foot.name,
                image = "item_50.webp",
                cost = 60
            ),
            GoodBd(
                number = 51,
                name = "Теневой доспех",
                type = GoodType.Body.name,
                image = "item_51.webp",
                cost = 30
            ),
            GoodBd(
                number = 51,
                name = "Теневой доспех",
                type = GoodType.Body.name,
                image = "item_51.webp",
                cost = 30
            ),
            GoodBd(
                number = 52,
                name = "Защитный амулет",
                type = GoodType.Head.name,
                image = "item_52.webp",
                cost = 60
            ),
            GoodBd(
                number = 52,
                name = "Защитный амулет",
                type = GoodType.Head.name,
                image = "item_52.webp",
                cost = 60
            ),
            GoodBd(
                number = 53,
                name = "Черный нож",
                type = GoodType.Arm.name,
                image = "item_53.webp",
                cost = 25
            ),
            GoodBd(
                number = 53,
                name = "Черный нож",
                type = GoodType.Arm.name,
                image = "item_53.webp",
                cost = 25
            ),
            GoodBd(
                number = 54,
                name = "Посох превосходства",
                type = GoodType.DoubleArm.name,
                image = "item_54.webp",
                cost = 60
            ),
            GoodBd(
                number = 54,
                name = "Посох превосходства",
                type = GoodType.DoubleArm.name,
                image = "item_54.webp",
                cost = 60
            ),
            GoodBd(
                number = 55,
                name = "Превосходное лечебное зелье",
                type = GoodType.SmallThing.name,
                image = "item_55.webp",
                cost = 50
            ),
            GoodBd(
                number = 55,
                name = "Превосходное лечебное зелье",
                type = GoodType.SmallThing.name,
                image = "item_55.webp",
                cost = 50
            ),
            GoodBd(
                number = 55,
                name = "Превосходное лечебное зелье",
                type = GoodType.SmallThing.name,
                image = "item_55.webp",
                cost = 50
            ),
            GoodBd(
                number = 55,
                name = "Превосходное лечебное зелье",
                type = GoodType.SmallThing.name,
                image = "item_55.webp",
                cost = 50
            ),
            GoodBd(
                number = 56,
                name = "Кольцо жестокости",
                type = GoodType.SmallThing.name,
                image = "item_56.webp",
                cost = 40
            ),
            GoodBd(
                number = 56,
                name = "Кольцо жестокости",
                type = GoodType.SmallThing.name,
                image = "item_56.webp",
                cost = 40
            ),
            GoodBd(
                number = 57,
                name = "Сандали безмятежности",
                type = GoodType.Foot.name,
                image = "item_57.webp",
                cost = 75
            ),
            GoodBd(
                number = 57,
                name = "Сандали безмятежности",
                type = GoodType.Foot.name,
                image = "item_57.webp",
                cost = 75
            ),
            GoodBd(
                number = 58,
                name = "Накидка прозрачности",
                type = GoodType.Body.name,
                image = "item_58.webp",
                cost = 75
            ),
            GoodBd(
                number = 58,
                name = "Накидка прозрачности",
                type = GoodType.Body.name,
                image = "item_58.webp",
                cost = 75
            ),
            GoodBd(
                number = 59,
                name = "Телескопические линзы",
                type = GoodType.Head.name,
                image = "item_59.webp",
                cost = 50
            ),
            GoodBd(
                number = 59,
                name = "Телескопические линзы",
                type = GoodType.Head.name,
                image = "item_59.webp",
                cost = 50
            ),
            GoodBd(
                number = 60,
                name = "Нестабильная взрывчатка",
                type = GoodType.Arm.name,
                image = "item_60.webp",
                cost = 45
            ),
            GoodBd(
                number = 60,
                name = "Нестабильная взрывчатка",
                type = GoodType.Arm.name,
                image = "item_60.webp",
                cost = 45
            ),
            GoodBd(
                number = 61,
                name = "Фигурный щит",
                type = GoodType.DoubleArm.name,
                image = "item_61.webp",
                cost = 60
            ),
            GoodBd(
                number = 61,
                name = "Фигурный щит",
                type = GoodType.DoubleArm.name,
                image = "item_61.webp",
                cost = 60
            ),
            GoodBd(
                number = 62,
                name = "Пыль обречения",
                type = GoodType.SmallThing.name,
                image = "item_62.webp",
                cost = 40
            ),
            GoodBd(
                number = 62,
                name = "Пыль обречения",
                type = GoodType.SmallThing.name,
                image = "item_62.webp",
                cost = 40
            ),
            GoodBd(
                number = 63,
                name = "Счастливый глаз",
                type = GoodType.SmallThing.name,
                image = "item_63.webp",
                cost = 60
            ),
            GoodBd(
                number = 63,
                name = "Счастливый глаз",
                type = GoodType.SmallThing.name,
                image = "item_63.webp",
                cost = 60
            ),
            GoodBd(
                number = 64,
                name = "Сапоги быстрого бега",
                type = GoodType.Foot.name,
                image = "item_64.webp",
                cost = 75
            ),
            GoodBd(
                number = 64,
                name = "Сапоги быстрого бега",
                type = GoodType.Foot.name,
                image = "item_64.webp",
                cost = 75
            ),
            GoodBd(
                number = 65,
                name = "Латы",
                type = GoodType.Body.name,
                image = "item_65.webp",
                cost = 50
            ),
            GoodBd(
                number = 65,
                name = "Латы",
                type = GoodType.Body.name,
                image = "item_65.webp",
                cost = 50
            ),
            GoodBd(
                number = 66,
                name = "Маска ужаса",
                type = GoodType.Head.name,
                image = "item_66.webp",
                cost = 50
            ),
            GoodBd(
                number = 66,
                name = "Маска ужаса",
                type = GoodType.Head.name,
                image = "item_66.webp",
                cost = 50
            ),
            GoodBd(
                number = 67,
                name = "Сбалансированный клинок",
                type = GoodType.Arm.name,
                image = "item_67.webp",
                cost = 60
            ),
            GoodBd(
                number = 67,
                name = "Сбалансированный клинок",
                type = GoodType.Arm.name,
                image = "item_67.webp",
                cost = 60
            ),
            GoodBd(
                number = 68,
                name = "Алебарда",
                type = GoodType.DoubleArm.name,
                image = "item_68.webp",
                cost = 75
            ),
            GoodBd(
                number = 68,
                name = "Алебарда",
                type = GoodType.DoubleArm.name,
                image = "item_68.webp",
                cost = 75
            ),
            GoodBd(
                number = 69,
                name = "Звездная серьга",
                type = GoodType.SmallThing.name,
                image = "item_69.webp",
                cost = 70
            ),
            GoodBd(
                number = 69,
                name = "Звездная серьга",
                type = GoodType.SmallThing.name,
                image = "item_69.webp",
                cost = 70
            ),
            GoodBd(
                number = 70,
                name = "Кольцо второго шанса",
                type = GoodType.SmallThing.name,
                image = "item_70.webp",
                cost = 75
            ),
            GoodBd(
                number = 70,
                name = "Кольцо второго шанса",
                type = GoodType.SmallThing.name,
                image = "item_70.webp",
                cost = 75
            ),
            GoodBd(
                number = 71,
                name = "Сапоги левитации",
                type = GoodType.Foot.name,
                image = "item_71.webp",
                cost = 50,
                isDrawing = true
            ),
            GoodBd(
                number = 71,
                name = "Сапоги левитации",
                type = GoodType.Foot.name,
                image = "item_71.webp",
                cost = 50
            ),
            GoodBd(
                number = 72,
                name = "Ботинки счастья",
                type = GoodType.Foot.name,
                image = "item_72.webp",
                cost = 50,
                isDrawing = true
            ),
            GoodBd(
                number = 72,
                name = "Ботинки счастья",
                type = GoodType.Foot.name,
                image = "item_72.webp",
                cost = 50
            ),
            GoodBd(
                number = 73,
                name = "Мерцающий плащ",
                type = GoodType.Body.name,
                image = "item_73.webp",
                cost = 50,
                isDrawing = true
            ),
            GoodBd(
                number = 73,
                name = "Мерцающий плащ",
                type = GoodType.Body.name,
                image = "item_73.webp",
                cost = 50
            ),
            GoodBd(
                number = 74,
                name = "Доспех лезвий",
                type = GoodType.Body.name,
                image = "item_74.webp",
                cost = 40,
                isDrawing = true
            ),
            GoodBd(
                number = 74,
                name = "Доспех лезвий",
                type = GoodType.Body.name,
                image = "item_74.webp",
                cost = 40
            ),
            GoodBd(
                number = 75,
                name = "Браслет стихий",
                type = GoodType.Head.name,
                image = "item_75.webp",
                cost = 25,
                isDrawing = true
            ),
            GoodBd(
                number = 75,
                name = "Браслет стихий",
                type = GoodType.Head.name,
                image = "item_75.webp",
                cost = 25
            ),
            GoodBd(
                number = 76,
                name = "Кольчужный капюшон",
                type = GoodType.Head.name,
                image = "item_76.webp",
                cost = 40,
                isDrawing = true
            ),
            GoodBd(
                number = 76,
                name = "Кольчужный капюшон",
                type = GoodType.Head.name,
                image = "item_76.webp",
                cost = 40
            ),
            GoodBd(
                number = 77,
                name = "Леденящий клинок",
                type = GoodType.Arm.name,
                image = "item_77.webp",
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 77,
                name = "Леденящий клинок",
                type = GoodType.Arm.name,
                image = "item_77.webp",
                cost = 30
            ),
            GoodBd(
                number = 78,
                name = "Штормовой клинок",
                type = GoodType.Arm.name,
                image = "item_78.webp",
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 78,
                name = "Штормовой клинок",
                type = GoodType.Arm.name,
                image = "item_78.webp",
                cost = 30
            ),
            GoodBd(
                number = 79,
                name = "Адский клинок",
                type = GoodType.Arm.name,
                image = "item_79.webp",
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 79,
                name = "Адский клинок",
                type = GoodType.Arm.name,
                image = "item_79.webp",
                cost = 30
            ),
            GoodBd(
                number = 80,
                name = "Дрожащий клинок",
                type = GoodType.Arm.name,
                image = "item_80.webp",
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 80,
                name = "Дрожащий клинок",
                type = GoodType.Arm.name,
                image = "item_80.webp",
                cost = 30
            ),
            GoodBd(
                number = 81,
                name = "Сияющий клинок",
                type = GoodType.Arm.name,
                image = "item_81.webp",
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 81,
                name = "Сияющий клинок",
                type = GoodType.Arm.name,
                image = "item_81.webp",
                cost = 30
            ),
            GoodBd(
                number = 82,
                name = "Ночной клинок",
                type = GoodType.Arm.name,
                image = "item_82.webp",
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 82,
                name = "Ночной клинок",
                type = GoodType.Arm.name,
                image = "item_82.webp",
                cost = 30
            ),
            GoodBd(
                number = 83,
                name = "Жезл мороза",
                type = GoodType.Arm.name,
                image = "item_83.webp",
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 83,
                name = "Жезл мороза",
                type = GoodType.Arm.name,
                image = "item_83.webp",
                cost = 30
            ),
            GoodBd(
                number = 84,
                name = "Жезл Штормов",
                type = GoodType.Arm.name,
                image = "item_84.webp",
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 84,
                name = "Жезл Штормов",
                type = GoodType.Arm.name,
                image = "item_84.webp",
                cost = 30
            ),
            GoodBd(
                number = 85,
                name = "Жезл Преисподней",
                type = GoodType.Arm.name,
                image = "item_85.webp",
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 85,
                name = "Жезл Преисподней",
                type = GoodType.Arm.name,
                image = "item_85.webp",
                cost = 30
            ),
            GoodBd(
                number = 86,
                name = "Жезл дрожи земли",
                type = GoodType.Arm.name,
                image = "item_86.webp",
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 86,
                name = "Жезл дрожи земли",
                type = GoodType.Arm.name,
                image = "item_86.webp",
                cost = 30
            ),
            GoodBd(
                number = 87,
                name = "Жезл сияния",
                type = GoodType.Arm.name,
                image = "item_87.webp",
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 87,
                name = "Жезл сияния",
                type = GoodType.Arm.name,
                image = "item_87.webp",
                cost = 30
            ),
            GoodBd(
                number = 88,
                name = "Жезл темноты",
                type = GoodType.Arm.name,
                image = "item_88.webp",
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 88,
                name = "Жезл темноты",
                type = GoodType.Arm.name,
                image = "item_88.webp",
                cost = 30
            ),
            GoodBd(
                number = 89,
                name = "Малое зелье исцеления",
                type = GoodType.SmallThing.name,
                image = "item_89.webp",
                cost = 10,
                isDrawing = true
            ),
            GoodBd(
                number = 89,
                name = "Малое зелье исцеления",
                type = GoodType.SmallThing.name,
                image = "item_89.webp",
                cost = 10
            ),
            GoodBd(
                number = 90,
                name = "Большое зелье исцеления",
                type = GoodType.SmallThing.name,
                image = "item_90.webp",
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 90,
                name = "Большое зелье исцеления",
                type = GoodType.SmallThing.name,
                image = "item_90.webp",
                cost = 30
            ),
            GoodBd(
                number = 91,
                name = "Стальное кольцо",
                type = GoodType.SmallThing.name,
                image = "item_91.webp",
                cost = 20,
                isDrawing = true
            ),
            GoodBd(
                number = 91,
                name = "Стальное кольцо",
                type = GoodType.SmallThing.name,
                image = "item_91.webp",
                cost = 20
            ),
            GoodBd(
                number = 92,
                name = "Кольцо подавления",
                type = GoodType.SmallThing.name,
                image = "item_92.webp",
                cost = 25,
                isDrawing = true
            ),
            GoodBd(
                number = 92,
                name = "Кольцо подавления",
                type = GoodType.SmallThing.name,
                image = "item_92.webp",
                cost = 25
            ),
            GoodBd(
                number = 93,
                name = "Свиток силы",
                type = GoodType.SmallThing.name,
                image = "item_93.webp",
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 93,
                name = "Свиток силы",
                type = GoodType.SmallThing.name,
                image = "item_93.webp",
                cost = 30
            ),
            GoodBd(
                number = 94,
                name = "Свиток лечения",
                type = GoodType.SmallThing.name,
                image = "item_94.webp",
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 94,
                name = "Свиток лечения",
                type = GoodType.SmallThing.name,
                image = "item_94.webp",
                cost = 30
            ),
            GoodBd(
                number = 95,
                name = "Свиток выносливости",
                type = GoodType.SmallThing.name,
                image = "item_95.webp",
                cost = 30,
                isDrawing = true
            ),
            GoodBd(
                number = 95,
                name = "Свиток выносливости",
                type = GoodType.SmallThing.name,
                image = "item_95.webp",
                cost = 30
            ),
            GoodBd(
                number = 96,
                name = "Реактивные сапоги",
                type = GoodType.Foot.name,
                image = "item_96.webp",
                cost = 80
            ),
            GoodBd(
                number = 96,
                name = "Реактивные сапоги",
                type = GoodType.Foot.name,
                image = "item_96.webp",
                cost = 80
            ),
            GoodBd(
                number = 97,
                name = "Портянки выносливости",
                type = GoodType.Foot.name,
                image = "item_97.webp",
                cost = 40
            ),
            GoodBd(
                number = 98,
                name = "Сапоги из чешуи дрейка",
                type = GoodType.Foot.name,
                image = "item_98.webp",
                cost = 50
            ),
            GoodBd(
                number = 99,
                name = "Лавовые ботфорты",
                type = GoodType.Foot.name,
                image = "item_99.webp",
                cost = 50
            ),
            GoodBd(
                number = 100,
                name = "Мантия призыва",
                type = GoodType.Body.name,
                image = "item_100.webp",
                cost = 40
            ),
            GoodBd(
                number = 101,
                name = "Вторая кожа",
                type = GoodType.Body.name,
                image = "item_101.webp",
                cost = 30
            ),
            GoodBd(
                number = 102,
                name = "Мантия самопожертвования",
                type = GoodType.Body.name,
                image = "item_102.webp",
                cost = 50
            ),
            GoodBd(
                number = 103,
                name = "Доспех из чешуи дрейка",
                type = GoodType.Body.name,
                image = "item_103.webp",
                cost = 50
            ),
            GoodBd(
                number = 104,
                name = "Паровой доспех",
                type = GoodType.Body.name,
                image = "item_104.webp",
                cost = 50
            ),
            GoodBd(
                number = 105,
                name = "Изьеденная молью шаль",
                type = GoodType.Body.name,
                image = "item_105.webp",
                cost = 10
            ),
            GoodBd(
                number = 106,
                name = "Ожерелье из зубов",
                type = GoodType.Head.name,
                image = "item_106.webp",
                cost = 40
            ),
            GoodBd(
                number = 107,
                name = "Рогатый шлем",
                type = GoodType.Head.name,
                image = "item_107.webp",
                cost = 30
            ),
            GoodBd(
                number = 107,
                name = "Рогатый шлем",
                type = GoodType.Head.name,
                image = "item_107.webp",
                cost = 30
            ),
            GoodBd(
                number = 108,
                name = "Шлем из чешуи дрейка",
                type = GoodType.Head.name,
                image = "item_108.webp",
                cost = 50
            ),
            GoodBd(
                number = 109,
                name = "Воровской капюшон",
                type = GoodType.Head.name,
                image = "item_109.webp",
                cost = 20
            ),
            GoodBd(
                number = 110,
                name = "Шлем гор",
                type = GoodType.Head.name,
                image = "item_110.webp",
                cost = 50
            ),
            GoodBd(
                number = 111,
                name = "Гребень волны",
                type = GoodType.Head.name,
                image = "item_111.webp",
                cost = 50
            ),
            GoodBd(
                number = 112,
                name = "Древний бур",
                type = GoodType.DoubleArm.name,
                image = "item_112.webp",
                cost = 30
            ),
            GoodBd(
                number = 112,
                name = "Древний бур",
                type = GoodType.DoubleArm.name,
                image = "item_112.webp",
                cost = 30
            ),
            GoodBd(
                number = 113,
                name = "Топор порчи черепов",
                type = GoodType.DoubleArm.name,
                image = "item_113.webp",
                cost = 50
            ),
            GoodBd(
                number = 114,
                name = "Посох Зорна",
                type = GoodType.DoubleArm.name,
                image = "item_114.webp",
                cost = 50
            ),
            GoodBd(
                number = 115,
                name = "Горный молот",
                type = GoodType.DoubleArm.name,
                image = "item_115.webp",
                cost = 50
            ),
            GoodBd(
                number = 116,
                name = "Воспламеняющийся меч",
                type = GoodType.Arm.name,
                image = "item_116.webp",
                cost = 20
            ),
            GoodBd(
                number = 116,
                name = "Воспламеняющийся меч",
                type = GoodType.Arm.name,
                image = "item_116.webp",
                cost = 20
            ),
            GoodBd(
                number = 117,
                name = "Кровавый топор",
                type = GoodType.Arm.name,
                image = "item_117.webp",
                cost = 40
            ),
            GoodBd(
                number = 117,
                name = "Кровавый топор",
                type = GoodType.Arm.name,
                image = "item_117.webp",
                cost = 40
            ),
            GoodBd(
                number = 118,
                name = "Посох стихий",
                type = GoodType.DoubleArm.name,
                image = "item_118.webp",
                cost = 50
            ),
            GoodBd(
                number = 118,
                name = "Посох стихий",
                type = GoodType.DoubleArm.name,
                image = "item_118.webp",
                cost = 50
            ),
            GoodBd(
                number = 119,
                name = "Череп ненависти",
                type = GoodType.Arm.name,
                image = "item_119.webp",
                cost = 50
            ),
            GoodBd(
                number = 120,
                name = "Посох призыва",
                type = GoodType.DoubleArm.name,
                image = "item_120.webp",
                cost = 60
            ),
            GoodBd(
                number = 121,
                name = "Сфера зари",
                type = GoodType.Arm.name,
                image = "item_121.webp",
                cost = 50
            ),
            GoodBd(
                number = 122,
                name = "Сфера сумерек",
                type = GoodType.Arm.name,
                image = "item_122.webp",
                cost = 50
            ),
            GoodBd(
                number = 123,
                name = "Кольцо черепов",
                type = GoodType.SmallThing.name,
                image = "item_123.webp",
                cost = 50
            ),
            GoodBd(
                number = 123,
                name = "Кольцо черепов",
                type = GoodType.SmallThing.name,
                image = "item_123.webp",
                cost = 50
            ),
            GoodBd(
                number = 124,
                name = "Компас обречения",
                type = GoodType.SmallThing.name,
                image = "item_124.webp",
                cost = 50
            ),
            GoodBd(
                number = 125,
                name = "Странный механизм",
                type = GoodType.SmallThing.name,
                image = "item_125.webp",
                cost = 50
            ),
            GoodBd(
                number = 126,
                name = "Управляемый паук",
                type = GoodType.SmallThing.name,
                image = "item_126.webp",
                cost = 40
            ),
            GoodBd(
                number = 127,
                name = "Гигантский управляемый паук",
                type = GoodType.SmallThing.name,
                image = "item_127.webp",
                cost = 60
            ),
            GoodBd(
                number = 128,
                name = "Черное кадило",
                type = GoodType.SmallThing.name,
                image = "item_128.webp",
                cost = 50
            ),
            GoodBd(
                number = 129,
                name = "Черная карта",
                type = GoodType.SmallThing.name,
                image = "item_129.webp",
                cost = 75
            ),
            GoodBd(
                number = 130,
                name = "Плетеное кольцо",
                type = GoodType.SmallThing.name,
                image = "item_130.webp",
                cost = 50
            ),
            GoodBd(
                number = 131,
                name = "Сердце предателя",
                type = GoodType.SmallThing.name,
                image = "item_131.webp",
                cost = 60
            ),
            GoodBd(
                number = 132,
                name = "Энергетическое ядро",
                type = GoodType.SmallThing.name,
                image = "item_132.webp",
                cost = 75
            ),
            GoodBd(
                number = 133,
                name = "Резонирующий кристал",
                type = GoodType.SmallThing.name,
                image = "item_133.webp",
                cost = 20
            ),
            GoodBd(
                number = 153,
                name = "Малое противоядие",
                type = GoodType.SmallThing.name,
                image = "item_153.webp",
                cost = 25
            ),
            GoodBd(
                number = 153,
                name = "Малое противоядие",
                type = GoodType.SmallThing.name,
                image = "item_153.webp",
                cost = 25
            ),
            GoodBd(
                number = 154,
                name = "Большое противоядие",
                type = GoodType.SmallThing.name,
                image = "item_154.webp",
                cost = 50
            ),
            GoodBd(
                number = 154,
                name = "Большое противоядие",
                type = GoodType.SmallThing.name,
                image = "item_154.webp",
                cost = 50
            ),
            GoodBd(
                number = 155,
                name = "Оберегающий доспех",
                type = GoodType.Body.name,
                image = "item_155.webp",
                cost = 75
            ),
            GoodBd(
                number = 156,
                name = "Клеймор стихий",
                type = GoodType.DoubleArm.name,
                image = "item_156.webp",
                cost = 50
            ),
            GoodBd(
                number = 156,
                name = "Клеймор стихий",
                type = GoodType.DoubleArm.name,
                image = "item_156.webp",
                cost = 50
            ),
            GoodBd(
                number = 157,
                name = "Древний лук",
                type = GoodType.DoubleArm.name,
                image = "item_157.webp",
                cost = 40
            ),
            GoodBd(
                number = 157,
                name = "Древний лук",
                type = GoodType.DoubleArm.name,
                image = "item_157.webp",
                cost = 40
            ),
            GoodBd(
                number = 158,
                name = "Заживляющие поножи",
                type = GoodType.Foot.name,
                image = "item_158.webp",
                cost = 35
            ),
            GoodBd(
                number = 158,
                name = "Заживляющие поножи",
                type = GoodType.Foot.name,
                image = "item_158.webp",
                cost = 35
            ),
            GoodBd(
                number = 159,
                name = "Свиток проворства",
                type = GoodType.SmallThing.name,
                image = "item_159.webp",
                cost = 30
            ),
            GoodBd(
                number = 159,
                name = "Свиток проворства",
                type = GoodType.SmallThing.name,
                image = "item_159.webp",
                cost = 30
            ),
            GoodBd(
                number = 160,
                name = "Кинжал карманника",
                type = GoodType.Arm.name,
                image = "item_160.webp",
                cost = 45
            ),
            GoodBd(
                number = 161,
                name = "Метательный топор",
                type = GoodType.Arm.name,
                image = "item_161.webp",
                cost = 35
            ),
            GoodBd(
                number = 161,
                name = "Метательный топор",
                type = GoodType.Arm.name,
                image = "item_161.webp",
                cost = 35
            ),
            GoodBd(
                number = 162,
                name = "Генераторо порталов",
                type = GoodType.SmallThing.name,
                image = "item_162.webp",
                cost = 50
            ),
            GoodBd(
                number = 163,
                name = "Кристальная тиара",
                type = GoodType.Head.name,
                image = "item_163.webp",
                cost = 75
            ),
            GoodBd(
                number = 164,
                name = "Чаша пророчества",
                type = GoodType.DoubleArm.name,
                image = "item_164.webp",
                cost = 55
            ),
            GoodBd(
                number = 165,
                name = "Кольцо дуальности",
                type = GoodType.SmallThing.name,
                image = "item_165.webp",
                cost = 50
            )
        )
    }
}