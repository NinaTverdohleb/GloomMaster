package com.rumpilstilstkin.gloomhavenhelper.bd.filler

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.MonsterDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterAbilityCardBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterStatsBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterAction.Action
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterAction.Text
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType.*

object MonstersFiller {
    suspend fun fill_v1(monsterDao: MonsterDao) {
        fillMonsters(monsterDao)
        fillAbilityDecks(monsterDao)
    }

    private suspend fun fillMonsters(monsterDao: MonsterDao) {
        val bossId = monsterDao.insertMonster(
            MonsterBd(
                name = "Главарь разбойников",
                deckName = "boss",
                isBoss = true,
                immunity = listOf(STUN, IMMOBILIZE, CURSE)
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(bossId, 0, false, 8, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Text("Перемещается к двери и открывает комнату"), Text("Призывает Ожившие кости"))),
            MonsterStatsBd(bossId, 1, false, 10, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Text("Перемещается к двери и открывает комнату"), Text("Призывает Ожившие кости"))),
            MonsterStatsBd(bossId, 2, false, 12, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Text("Перемещается к двери и открывает комнату"), Text("Призывает Ожившие кости"))),
            MonsterStatsBd(bossId, 3, false, 13, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Text("Перемещается к двери и открывает комнату"), Text("Призывает Ожившие кости"))),
            MonsterStatsBd(bossId, 4, false, 15, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Text("Перемещается к двери и открывает комнату"), Text("Призывает Ожившие кости"))),
            MonsterStatsBd(bossId, 5, false, 16, listOf(Action(MOVE, "5"), Action(ATTACK, "5"), Text("Перемещается к двери и открывает комнату"), Text("Призывает Ожившие кости"))),
            MonsterStatsBd(bossId, 6, false, 19, listOf(Action(MOVE, "5"), Action(ATTACK, "5"), Text("Перемещается к двери и открывает комнату"), Text("Призывает Ожившие кости"))),
            MonsterStatsBd(bossId, 7, false, 23, listOf(Action(MOVE, "5"), Action(ATTACK, "5"), Text("Перемещается к двери и открывает комнату"), Text("Призывает Ожившие кости"))),
        )

        val livingBonesId = monsterDao.insertMonster(
            MonsterBd(
                name = "Ожившие кости",
                deckName = "living-bones",
                isBoss = false)

        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(livingBonesId, 0, false, 5, listOf(Action(MOVE, "2"), Action(ATTACK, "1"), Action(TARGET, "2"))),
            MonsterStatsBd(livingBonesId, 0, true, 6, listOf(Action(MOVE, "4"), Action(ATTACK, "2"), Action(TARGET, "2"))),
            MonsterStatsBd(livingBonesId, 1, false, 5, listOf(Action(MOVE, "3"), Action(ATTACK, "1"), Action(TARGET, "2"), Action(SHIELD, "1"))),
            MonsterStatsBd(livingBonesId, 1, true, 6, listOf(Action(MOVE, "4"), Action(ATTACK, "2"), Action(TARGET, "3"), Action(SHIELD, "1"))),
            MonsterStatsBd(livingBonesId, 2, false, 5, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(TARGET, "2"), Action(SHIELD, "1"))),
            MonsterStatsBd(livingBonesId, 2, true, 7, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Action(TARGET, "3"), Action(SHIELD, "1"))),
            MonsterStatsBd(livingBonesId, 3, false, 7, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(TARGET, "2"), Action(SHIELD, "1"))),
            MonsterStatsBd(livingBonesId, 3, true, 10, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Action(TARGET, "3"), Action(SHIELD, "1"))),
            MonsterStatsBd(livingBonesId, 4, false, 7, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(TARGET, "2"), Action(SHIELD, "1"))),
            MonsterStatsBd(livingBonesId, 4, true, 11, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(TARGET, "3"), Action(SHIELD, "1"))),
            MonsterStatsBd(livingBonesId, 5, false, 9, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(TARGET, "2"), Action(SHIELD, "1"))),
            MonsterStatsBd(livingBonesId, 5, true, 11, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(TARGET, "3"), Action(SHIELD, "2"))),
            MonsterStatsBd(livingBonesId, 6, false, 10, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Action(TARGET, "2"), Action(SHIELD, "1"))),
            MonsterStatsBd(livingBonesId, 6, true, 11, listOf(Action(MOVE, "6"), Action(ATTACK, "4"), Action(TARGET, "3"), Action(SHIELD, "2"))),
            MonsterStatsBd(livingBonesId, 7, false, 13, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Action(TARGET, "2"), Action(SHIELD, "1"))),
            MonsterStatsBd(livingBonesId, 7, true, 14, listOf(Action(MOVE, "6"), Action(ATTACK, "4"), Action(TARGET, "3"), Action(SHIELD, "2"))),
        )

        val guardId = monsterDao.insertMonster(
            MonsterBd(
                name = "Разбойник-страж",
                deckName = "guard",
                isBoss = false
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(guardId, 0, false, 5, listOf(Action(MOVE, "2"), Action(ATTACK, "2"))),
            MonsterStatsBd(guardId, 0, true, 9, listOf(Action(MOVE, "2"), Action(ATTACK, "3"))),
            MonsterStatsBd(guardId, 1, false, 6, listOf(Action(MOVE, "3"), Action(ATTACK, "2"))),
            MonsterStatsBd(guardId, 1, true, 9, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(SHIELD, "1"))),
            MonsterStatsBd(guardId, 2, false, 6, listOf(Action(MOVE, "3"), Action(ATTACK, "3"))),
            MonsterStatsBd(guardId, 2, true, 10, listOf(Action(MOVE, "2"), Action(ATTACK, "4"), Action(SHIELD, "1"))),
            MonsterStatsBd(guardId, 3, false, 9, listOf(Action(MOVE, "3"), Action(ATTACK, "3"))),
            MonsterStatsBd(guardId, 3, true, 10, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(SHIELD, "2"))),
            MonsterStatsBd(guardId, 4, false, 10, listOf(Action(MOVE, "4"), Action(ATTACK, "3"))),
            MonsterStatsBd(guardId, 4, true, 11, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(SHIELD, "2"), Action(MUDDLE, ""))),
            MonsterStatsBd(guardId, 5, false, 11, listOf(Action(MOVE, "4"), Action(ATTACK, "4"))),
            MonsterStatsBd(guardId, 5, true, 12, listOf(Action(MOVE, "3"), Action(ATTACK, "5"), Action(SHIELD, "2"), Action(MUDDLE, ""))),
            MonsterStatsBd(guardId, 6, false, 14, listOf(Action(MOVE, "4"), Action(ATTACK, "4"))),
            MonsterStatsBd(guardId, 6, true, 14, listOf(Action(MOVE, "3"), Action(ATTACK, "5"), Action(SHIELD, "2"), Action(MUDDLE, ""))),
            MonsterStatsBd(guardId, 7, false, 16, listOf(Action(MOVE, "5"), Action(ATTACK, "4"))),
            MonsterStatsBd(guardId, 7, true, 14, listOf(Action(MOVE, "3"), Action(ATTACK, "5"), Action(SHIELD, "3"), Action(MUDDLE, ""))),
        )

        val archerId = monsterDao.insertMonster(
            MonsterBd(
                name = "Разбойник-лучница",
                deckName = "archer",
                isBoss = false
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(archerId, 0, false, 4, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(RANGE, "3"))),
            MonsterStatsBd(archerId, 0, true, 6, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(RANGE, "3"))),
            MonsterStatsBd(archerId, 1, false, 5, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(RANGE, "4"))),
            MonsterStatsBd(archerId, 1, true, 7, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(RANGE, "5"))),
            MonsterStatsBd(archerId, 2, false, 6, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(RANGE, "4"))),
            MonsterStatsBd(archerId, 2, true, 9, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "5"))),
            MonsterStatsBd(archerId, 3, false, 6, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "4"))),
            MonsterStatsBd(archerId, 3, true, 10, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "5"))),
            MonsterStatsBd(archerId, 4, false, 8, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "5"))),
            MonsterStatsBd(archerId, 4, true, 10, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "6"), Action(POISON, ""))),
            MonsterStatsBd(archerId, 5, false, 10, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "5"))),
            MonsterStatsBd(archerId, 5, true, 12, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(RANGE, "6"), Action(POISON, ""))),
            MonsterStatsBd(archerId, 6, false, 10, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "5"))),
            MonsterStatsBd(archerId, 6, true, 13, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(RANGE, "6"), Action(POISON, ""))),
            MonsterStatsBd(archerId, 7, false, 13, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "5"))),
            MonsterStatsBd(archerId, 7, true, 17, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(RANGE, "6"), Action(POISON, ""))),
        )

        val livingCorpseId = monsterDao.insertMonster(
            MonsterBd(
                name = "Оживший труп",
                deckName = "living-corpse",
                isBoss = false
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(livingCorpseId, 0, false, 5, listOf(Action(MOVE, "1"), Action(ATTACK, "3"))),
            MonsterStatsBd(livingCorpseId, 0, true, 10, listOf(Action(MOVE, "1"), Action(ATTACK, "3"))),
            MonsterStatsBd(livingCorpseId, 1, false, 7, listOf(Action(MOVE, "1"), Action(ATTACK, "3"))),
            MonsterStatsBd(livingCorpseId, 1, true, 10, listOf(Action(MOVE, "1"), Action(ATTACK, "4"))),
            MonsterStatsBd(livingCorpseId, 2, false, 9, listOf(Action(MOVE, "1"), Action(ATTACK, "3"))),
            MonsterStatsBd(livingCorpseId, 2, true, 13, listOf(Action(MOVE, "1"), Action(ATTACK, "4"))),
            MonsterStatsBd(livingCorpseId, 3, false, 10, listOf(Action(MOVE, "1"), Action(ATTACK, "4"))),
            MonsterStatsBd(livingCorpseId, 3, true, 13, listOf(Action(MOVE, "2"), Action(ATTACK, "5"))),
            MonsterStatsBd(livingCorpseId, 4, false, 11, listOf(Action(MOVE, "2"), Action(ATTACK, "4"))),
            MonsterStatsBd(livingCorpseId, 4, true, 15, listOf(Action(MOVE, "2"), Action(ATTACK, "5"), Action(POISON, ""))),
            MonsterStatsBd(livingCorpseId, 5, false, 13, listOf(Action(MOVE, "2"), Action(ATTACK, "4"))),
            MonsterStatsBd(livingCorpseId, 5, true, 17, listOf(Action(MOVE, "2"), Action(ATTACK, "6"), Action(POISON, ""))),
            MonsterStatsBd(livingCorpseId, 6, false, 14, listOf(Action(MOVE, "2"), Action(ATTACK, "4"), Action(POISON, ""))),
            MonsterStatsBd(livingCorpseId, 6, true, 21, listOf(Action(MOVE, "2"), Action(ATTACK, "6"), Action(POISON, ""))),
            MonsterStatsBd(livingCorpseId, 7, false, 15, listOf(Action(MOVE, "2"), Action(ATTACK, "5"), Action(POISON, ""))),
            MonsterStatsBd(livingCorpseId, 7, true, 25, listOf(Action(MOVE, "2"), Action(ATTACK, "6"), Action(POISON, ""))),
        )

        val inoxGuardId = monsterDao.insertMonster(
            MonsterBd(
                name = "Инокс-стражница",
                deckName = "guard",
                isBoss = false
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(inoxGuardId, 0, false, 5, listOf(Action(MOVE, "2"), Action(ATTACK, "2"))),
            MonsterStatsBd(inoxGuardId, 0, true, 9, listOf(Action(MOVE, "1"), Action(ATTACK, "3"), Action(RETALIATE, "1"))),

            MonsterStatsBd(inoxGuardId, 1, false, 8, listOf(Action(MOVE, "2"), Action(ATTACK, "2"))),
            MonsterStatsBd(inoxGuardId, 1, true, 10, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(RETALIATE, "2"))),

            MonsterStatsBd(inoxGuardId, 2, false, 9, listOf(Action(MOVE, "2"), Action(ATTACK, "3"))),
            MonsterStatsBd(inoxGuardId, 2, true, 12, listOf(Action(MOVE, "2"), Action(ATTACK, "4"), Action(RETALIATE, "2"))),

            MonsterStatsBd(inoxGuardId, 3, false, 11, listOf(Action(MOVE, "3"), Action(ATTACK, "3"))),
            MonsterStatsBd(inoxGuardId, 3, true, 15, listOf(Action(MOVE, "2"), Action(ATTACK, "4"), Action(RETALIATE, "3"))),

            MonsterStatsBd(inoxGuardId, 4, false, 12, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RETALIATE, "1"))),
            MonsterStatsBd(inoxGuardId, 4, true, 17, listOf(Action(MOVE, "2"), Action(ATTACK, "5"), Action(RETALIATE, "3"))),

            MonsterStatsBd(inoxGuardId, 5, false, 13, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RETALIATE, "1"))),
            MonsterStatsBd(inoxGuardId, 5, true, 19, listOf(Action(MOVE, "2"), Action(ATTACK, "5"), Action(RETALIATE, "4"))),

            MonsterStatsBd(inoxGuardId, 6, false, 16, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RETALIATE, "1"))),
            MonsterStatsBd(inoxGuardId, 6, true, 21, listOf(Action(MOVE, "3"), Action(ATTACK, "5"), Action(RETALIATE, "4"))),

            MonsterStatsBd(inoxGuardId, 7, false, 19, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RETALIATE, "2"))),
            MonsterStatsBd(inoxGuardId, 7, true, 23, listOf(Action(MOVE, "3"), Action(ATTACK, "6"), Action(RETALIATE, "4"))),
        )

        val inoxArcherId = monsterDao.insertMonster(
            MonsterBd(
                name = "Инокс-лучник",
                deckName = "archer",
                isBoss = false
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(inoxArcherId, 0, false, 5, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(RANGE, "2"))),
            MonsterStatsBd(inoxArcherId, 0, true, 7, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(RANGE, "3"))),

            MonsterStatsBd(inoxArcherId, 1, false, 6, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(RANGE, "3"))),
            MonsterStatsBd(inoxArcherId, 1, true, 8, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(RANGE, "4"))),

            MonsterStatsBd(inoxArcherId, 2, false, 8, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(RANGE, "3"))),
            MonsterStatsBd(inoxArcherId, 2, true, 11, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(RANGE, "4"))),

            MonsterStatsBd(inoxArcherId, 3, false, 9, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(RANGE, "3"))),
            MonsterStatsBd(inoxArcherId, 3, true, 13, listOf(Action(MOVE, "2"), Action(ATTACK, "4"), Action(RANGE, "4"))),

            MonsterStatsBd(inoxArcherId, 4, false, 10, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "3"))),
            MonsterStatsBd(inoxArcherId, 4, true, 14, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(WOUND, ""))),

            MonsterStatsBd(inoxArcherId, 5, false, 12, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "4"))),
            MonsterStatsBd(inoxArcherId, 5, true, 17, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "5"), Action(WOUND, ""))),

            MonsterStatsBd(inoxArcherId, 6, false, 12, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(WOUND, ""))),
            MonsterStatsBd(inoxArcherId, 6, true, 19, listOf(Action(MOVE, "3"), Action(ATTACK, "5"), Action(RANGE, "5"), Action(WOUND, ""))),

            MonsterStatsBd(inoxArcherId, 7, false, 15, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(WOUND, ""))),
            MonsterStatsBd(inoxArcherId, 7, true, 23, listOf(Action(MOVE, "3"), Action(ATTACK, "5"), Action(RANGE, "5"), Action(WOUND, ""))),
        )

        val inoxShamanId = monsterDao.insertMonster(
            MonsterBd(
                name = "Инокс-шаман",
                deckName = "shaman",
                isBoss = false
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(inoxShamanId, 0, false, 4, listOf(Action(MOVE, "1"), Action(ATTACK, "2"), Action(RANGE, "3"))),
            MonsterStatsBd(inoxShamanId, 0, true, 6, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(RANGE, "3"))),

            MonsterStatsBd(inoxShamanId, 1, false, 6, listOf(Action(MOVE, "1"), Action(ATTACK, "2"), Action(RANGE, "3"))),
            MonsterStatsBd(inoxShamanId, 1, true, 9, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(RANGE, "3"))),

            MonsterStatsBd(inoxShamanId, 2, false, 7, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(RANGE, "3"))),
            MonsterStatsBd(inoxShamanId, 2, true, 11, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "3"))),

            MonsterStatsBd(inoxShamanId, 3, false, 9, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(RANGE, "4"))),
            MonsterStatsBd(inoxShamanId, 3, true, 14, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "4"))),

            MonsterStatsBd(inoxShamanId, 4, false, 10, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(RANGE, "4"))),
            MonsterStatsBd(inoxShamanId, 4, true, 16, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "4"))),

            MonsterStatsBd(inoxShamanId, 5, false, 13, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(RANGE, "4"))),
            MonsterStatsBd(inoxShamanId, 5, true, 20, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "4"))),

            MonsterStatsBd(inoxShamanId, 6, false, 15, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "4"))),
            MonsterStatsBd(inoxShamanId, 6, true, 24, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(RANGE, "4"))),

            MonsterStatsBd(inoxShamanId, 7, false, 16, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "4"))),
            MonsterStatsBd(inoxShamanId, 7, true, 27, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(RANGE, "4"))),
        )

        val cultistId = monsterDao.insertMonster(
            MonsterBd(
                name = "Кульист",
                deckName = "cultist",
                isBoss = false
            )

        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(cultistId, 0, false, 4, listOf(Action(MOVE, "2"), Action(ATTACK, "1"))),
            MonsterStatsBd(cultistId, 0, true, 7, listOf(Action(MOVE, "2"), Action(ATTACK, "2"))),

            MonsterStatsBd(cultistId, 1, false, 5, listOf(Action(MOVE, "2"), Action(ATTACK, "1"))),
            MonsterStatsBd(cultistId, 1, true, 9, listOf(Action(MOVE, "2"), Action(ATTACK, "2"))),

            MonsterStatsBd(cultistId, 2, false, 7, listOf(Action(MOVE, "2"), Action(ATTACK, "1"))),
            MonsterStatsBd(cultistId, 2, true, 12, listOf(Action(MOVE, "2"), Action(ATTACK, "2"))),

            MonsterStatsBd(cultistId, 3, false, 9, listOf(Action(MOVE, "3"), Action(ATTACK, "1"))),
            MonsterStatsBd(cultistId, 3, true, 13, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(CURSE, ""))),

            MonsterStatsBd(cultistId, 4, false, 10, listOf(Action(MOVE, "3"), Action(ATTACK, "2"))),
            MonsterStatsBd(cultistId, 4, true, 15, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(CURSE, ""))),

            MonsterStatsBd(cultistId, 5, false, 11, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(CURSE, ""))),
            MonsterStatsBd(cultistId, 5, true, 18, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(CURSE, ""))),

            MonsterStatsBd(cultistId, 6, false, 14, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(CURSE, ""))),
            MonsterStatsBd(cultistId, 6, true, 22, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(CURSE, ""))),

            MonsterStatsBd(cultistId, 7, false, 15, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(CURSE, ""))),
            MonsterStatsBd(cultistId, 7, true, 25, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(CURSE, ""))),
        )

        val earthDemonId = monsterDao.insertMonster(
            MonsterBd(
                name = "Земляной демон",
                deckName = "earth-demon",
                isBoss = false
            )

        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(earthDemonId, 0, false, 7, listOf(Action(MOVE, "1"), Action(ATTACK, "3"))),
            MonsterStatsBd(earthDemonId, 0, true, 10, listOf(Action(MOVE, "2"), Action(ATTACK, "4"))),

            MonsterStatsBd(earthDemonId, 1, false, 9, listOf(Action(MOVE, "1"), Action(ATTACK, "3"))),
            MonsterStatsBd(earthDemonId, 1, true, 13, listOf(Action(MOVE, "2"), Action(ATTACK, "4"))),

            MonsterStatsBd(earthDemonId, 2, false, 12, listOf(Action(MOVE, "1"), Action(ATTACK, "3"))),
            MonsterStatsBd(earthDemonId, 2, true, 18, listOf(Action(MOVE, "2"), Action(ATTACK, "4"))),

            MonsterStatsBd(earthDemonId, 3, false, 13, listOf(Action(MOVE, "2"), Action(ATTACK, "3"))),
            MonsterStatsBd(earthDemonId, 3, true, 20, listOf(Action(MOVE, "2"), Action(ATTACK, "4"), Action(IMMOBILIZE, ""))),

            MonsterStatsBd(earthDemonId, 4, false, 15, listOf(Action(MOVE, "2"), Action(ATTACK, "4"))),
            MonsterStatsBd(earthDemonId, 4, true, 21, listOf(Action(MOVE, "3"), Action(ATTACK, "5"), Action(IMMOBILIZE, ""))),

            MonsterStatsBd(earthDemonId, 5, false, 17, listOf(Action(MOVE, "2"), Action(ATTACK, "4"), Action(IMMOBILIZE, ""))),
            MonsterStatsBd(earthDemonId, 5, true, 25, listOf(Action(MOVE, "3"), Action(ATTACK, "5"), Action(IMMOBILIZE, ""))),

            MonsterStatsBd(earthDemonId, 6, false, 20, listOf(Action(MOVE, "2"), Action(ATTACK, "4"), Action(IMMOBILIZE, ""))),
            MonsterStatsBd(earthDemonId, 6, true, 27, listOf(Action(MOVE, "3"), Action(ATTACK, "6"), Action(IMMOBILIZE, ""))),

            MonsterStatsBd(earthDemonId, 7, false, 22, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(IMMOBILIZE, ""))),
            MonsterStatsBd(earthDemonId, 7, true, 32, listOf(Action(MOVE, "3"), Action(ATTACK, "6"), Action(IMMOBILIZE, ""))),
        )


        val airDemonId = monsterDao.insertMonster(
            MonsterBd(
                name = "Воздушный демон",
                deckName = "wind-demon",
                isBoss = false,
                fly = true,
            )

        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(airDemonId, 0, false, 3, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(RANGE, "3"), Action(SHIELD, "1"))),
            MonsterStatsBd(airDemonId, 0, true, 5, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(SHIELD, "1"))),

            MonsterStatsBd(airDemonId, 1, false, 3, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(RANGE, "3"), Action(SHIELD, "2"))),
            MonsterStatsBd(airDemonId, 1, true, 5, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(SHIELD, "2"))),

            MonsterStatsBd(airDemonId, 2, false, 4, listOf(Action(MOVE, "4"), Action(ATTACK, "2"), Action(RANGE, "3"), Action(SHIELD, "2"))),
            MonsterStatsBd(airDemonId, 2, true, 7, listOf(Action(MOVE, "5"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(SHIELD, "2"))),

            MonsterStatsBd(airDemonId, 3, false, 5, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Action(RANGE, "3"), Action(SHIELD, "2"))),
            MonsterStatsBd(airDemonId, 3, true, 8, listOf(Action(MOVE, "5"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "2"))),

            MonsterStatsBd(airDemonId, 4, false, 7, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Action(RANGE, "3"), Action(SHIELD, "2"))),
            MonsterStatsBd(airDemonId, 4, true, 8, listOf(Action(MOVE, "5"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "2"), Action(DISARM, ""))),

            MonsterStatsBd(airDemonId, 5, false, 9, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(SHIELD, "2"))),
            MonsterStatsBd(airDemonId, 5, true, 11, listOf(Action(MOVE, "5"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "2"), Action(DISARM, ""))),

            MonsterStatsBd(airDemonId, 6, false, 10, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(SHIELD, "3"))),
            MonsterStatsBd(airDemonId, 6, true, 12, listOf(Action(MOVE, "5"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "3"), Action(DISARM, ""))),

            MonsterStatsBd(airDemonId, 7, false, 11, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "3"))),
            MonsterStatsBd(airDemonId, 7, true, 13, listOf(Action(MOVE, "5"), Action(ATTACK, "5"), Action(RANGE, "4"), Action(SHIELD, "3"), Action(DISARM, ""))),
        )

        val livingSpiritId = monsterDao.insertMonster(
            MonsterBd(
                name = "Оживший дух",
                deckName = "living-spirit",
                isBoss = false,
                fly = true,
            )

        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(livingSpiritId, 0, false, 2, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(RANGE, "2"), Action(SHIELD, "1"))),
            MonsterStatsBd(livingSpiritId, 0, true, 3, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "3"), Action(SHIELD, "2"))),

            MonsterStatsBd(livingSpiritId, 1, false, 2, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(RANGE, "2"), Action(SHIELD, "2"))),
            MonsterStatsBd(livingSpiritId, 1, true, 3, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "3"), Action(SHIELD, "3"))),

            MonsterStatsBd(livingSpiritId, 2, false, 2, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(RANGE, "3"), Action(SHIELD, "2"))),
            MonsterStatsBd(livingSpiritId, 2, true, 3, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(SHIELD, "3"))),

            MonsterStatsBd(livingSpiritId, 3, false, 3, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "3"), Action(SHIELD, "2"))),
            MonsterStatsBd(livingSpiritId, 3, true, 4, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "3"))),

            MonsterStatsBd(livingSpiritId, 4, false, 3, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "3"), Action(SHIELD, "3"))),
            MonsterStatsBd(livingSpiritId, 4, true, 4, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "4"))),

            MonsterStatsBd(livingSpiritId, 5, false, 4, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(SHIELD, "3"))),
            MonsterStatsBd(livingSpiritId, 5, true, 6, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "4"))),

            MonsterStatsBd(livingSpiritId, 6, false, 4, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "3"))),
            MonsterStatsBd(livingSpiritId, 6, true, 7, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(RANGE, "5"), Action(SHIELD, "4"))),

            MonsterStatsBd(livingSpiritId, 7, false, 6, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "3"))),
            MonsterStatsBd(livingSpiritId, 7, true, 9, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(RANGE, "5"), Action(SHIELD, "4"))),
        )

        val nightDemonId = monsterDao.insertMonster(
            MonsterBd(
                name = "Ночной демон",
                deckName = "night-demon",
                isBoss = false,
            )

        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(nightDemonId, 0, false, 3, listOf(Text("Атаки по демону проходят с помехой"), Action(MOVE, "3"), Action(ATTACK, "3"))),
            MonsterStatsBd(nightDemonId, 0, true, 5, listOf(Text("Атаки по демону проходят с помехой"), Action(MOVE, "4"), Action(ATTACK, "4"))),

            MonsterStatsBd(nightDemonId, 1, false, 5, listOf(Text("Атаки по демону проходят с помехой"), Action(MOVE, "3"), Action(ATTACK, "3"))),
            MonsterStatsBd(nightDemonId, 1, true, 8, listOf(Text("Атаки по демону проходят с помехой"), Action(MOVE, "4"), Action(ATTACK, "4"))),

            MonsterStatsBd(nightDemonId, 2, false, 6, listOf(Text("Атаки по демону проходят с помехой"), Action(MOVE, "3"), Action(ATTACK, "4"))),
            MonsterStatsBd(nightDemonId, 2, true, 11, listOf(Text("Атаки по демону проходят с помехой"), Action(MOVE, "4"), Action(ATTACK, "4"))),

            MonsterStatsBd(nightDemonId, 3, false, 7, listOf(Text("Атаки по демону проходят с помехой"), Action(MOVE, "3"), Action(ATTACK, "4"))),
            MonsterStatsBd(nightDemonId, 3, true, 13, listOf(Text("Атаки по демону проходят с помехой"), Action(MOVE, "4"), Action(ATTACK, "5"))),

            MonsterStatsBd(nightDemonId, 4, false, 7, listOf(Text("Атаки по демону проходят с помехой"), Action(MOVE, "4"), Action(ATTACK, "3"), Action(RANGE, "3"), Action(SHIELD, "2"))),
            MonsterStatsBd(nightDemonId, 4, true, 8, listOf(Text("Атаки по демону проходят с помехой"), Action(MOVE, "4"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "2"), Action(DISARM, ""))),

            MonsterStatsBd(nightDemonId, 5, false, 9, listOf(Text("Атаки по демону проходят с помехой"), Action(MOVE, "4"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(SHIELD, "2"))),
            MonsterStatsBd(nightDemonId, 5, true, 11, listOf(Text("Атаки по демону проходят с помехой"), Action(MOVE, "5"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "2"), Action(DISARM, ""))),

            MonsterStatsBd(nightDemonId, 6, false, 10, listOf(Text("Атаки по демону проходят с помехой"), Action(MOVE, "4"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(SHIELD, "3"))),
            MonsterStatsBd(nightDemonId, 6, true, 12, listOf(Text("Атаки по демону проходят с помехой"), Action(MOVE, "5"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "3"), Action(DISARM, ""))),

            MonsterStatsBd(nightDemonId, 7, false, 11, listOf(Text("Атаки по демону проходят с помехой"), Action(MOVE, "4"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "3"))),
            MonsterStatsBd(nightDemonId, 7, true, 13, listOf(Text("Атаки по демону проходят с помехой"), Action(MOVE, "5"), Action(ATTACK, "5"), Action(RANGE, "4"), Action(SHIELD, "3"), Action(DISARM, ""))),
        )
        val flameDemonId = monsterDao.insertMonster(
            MonsterBd(
                name = "Огненный демон",
                deckName = "flame-demon",
                isBoss = false,
                fly = true,
            )

        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(flameDemonId, 0, false, 2, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(RANGE, "3"), Action(SHIELD, "2"))),
            MonsterStatsBd(flameDemonId, 0, true, 3, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(RANGE, "3"), Action(SHIELD, "3"))),

            MonsterStatsBd(flameDemonId, 1, false, 2, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(RANGE, "3"), Action(SHIELD, "3"))),
            MonsterStatsBd(flameDemonId, 1, true, 3, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(RANGE, "4"), Action(SHIELD, "4"), Text("Ответный удар 2 с дальностью 2"))),

            MonsterStatsBd(flameDemonId, 2, false, 3, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "3"), Action(SHIELD, "3"))),
            MonsterStatsBd(flameDemonId, 2, true, 4, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(SHIELD, "4"), Text("Ответный удар 3 с дальностью 2"))),

            MonsterStatsBd(flameDemonId, 3, false, 3, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(SHIELD, "3"), Text("Ответный удар 2 с дальностью 2"))),
            MonsterStatsBd(flameDemonId, 3, true, 5, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "5"), Action(SHIELD, "4"), Text("Ответный удар 3 с дальностью 3"))),

            MonsterStatsBd(flameDemonId, 4, false, 3, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(SHIELD, "3"), Text("Ответный удар 3 с дальностью 2"))),
            MonsterStatsBd(flameDemonId, 4, true, 5, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(RANGE, "5"), Action(SHIELD, "4"), Text("Ответный удар 4 с дальностью 3"))),

            MonsterStatsBd(flameDemonId, 5, false, 4, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(SHIELD, "4"), Text("Ответный удар 3 с дальностью 2"))),
            MonsterStatsBd(flameDemonId, 5, true, 6, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(RANGE, "5"), Action(SHIELD, "5"), Text("Ответный удар 4 с дальностью 3"))),

            MonsterStatsBd(flameDemonId, 6, false, 4, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "4"), Text("Ответный удар 4 с дальностью 2"))),
            MonsterStatsBd(flameDemonId, 6, true, 7, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(RANGE, "5"), Action(SHIELD, "5"), Text("Ответный удар 5 с дальностью 3"))),

            MonsterStatsBd(flameDemonId, 7, false, 5, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(RANGE, "5"), Action(SHIELD, "4"), Text("Ответный удар 4 с дальностью 3"))),
            MonsterStatsBd(flameDemonId, 7, true, 8, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(RANGE, "6"), Action(SHIELD, "5"), Text("Ответный удар 5 с дальностью 4"))),
        )

        val frostDemonId = monsterDao.insertMonster(
            MonsterBd(
                name = "Морозный демон",
                deckName = "frost-demon",
                isBoss = false,
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(frostDemonId, 0, false, 5, listOf(Action(MOVE, "2"), Action(ATTACK, "3"))),
            MonsterStatsBd(frostDemonId, 0, true, 10, listOf(Action(MOVE, "3"), Action(ATTACK, "3"))),

            MonsterStatsBd(frostDemonId, 1, false, 6, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(RETALIATE, "1"))),
            MonsterStatsBd(frostDemonId, 1, true, 10, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RETALIATE, "2"))),

            MonsterStatsBd(frostDemonId, 2, false, 7, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RETALIATE, "2"))),
            MonsterStatsBd(frostDemonId, 2, true, 12, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(RETALIATE, "2"))),

            MonsterStatsBd(frostDemonId, 3, false, 8, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RETALIATE, "2"))),
            MonsterStatsBd(frostDemonId, 3, true, 14, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(RETALIATE, "3"))),

            MonsterStatsBd(frostDemonId, 4, false, 10, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RETALIATE, "2"))),
            MonsterStatsBd(frostDemonId, 4, true, 18, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(RETALIATE, "3"))),

            MonsterStatsBd(frostDemonId, 5, false, 11, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RETALIATE, "3"))),
            MonsterStatsBd(frostDemonId, 5, true, 20, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(RETALIATE, "3"))),

            MonsterStatsBd(frostDemonId, 6, false, 12, listOf(Action(MOVE, "3"), Action(ATTACK, "5"), Action(RETALIATE, "3"))),
            MonsterStatsBd(frostDemonId, 6, true, 22, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(RETALIATE, "4"))),

            MonsterStatsBd(frostDemonId, 7, false, 14, listOf(Action(MOVE, "3"), Action(ATTACK, "5"), Action(RETALIATE, "3"))),
            MonsterStatsBd(frostDemonId, 7, true, 25, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(RETALIATE, "4"))),
        )

        val impId = monsterDao.insertMonster(
            MonsterBd(
                name = "Лесной бес",
                deckName = "imp",
                isBoss = false,
                fly = true,
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(impId, 0, false, 1, listOf(Action(MOVE, "3"), Action(ATTACK, "1"), Action(RANGE, "3"), Action(SHIELD, "1"))),
            MonsterStatsBd(impId, 0, true, 4, listOf(Action(MOVE, "3"), Action(ATTACK, "1"), Action(RANGE, "3"), Action(SHIELD, "1"))),

            MonsterStatsBd(impId, 1, false, 2, listOf(Action(MOVE, "3"), Action(ATTACK, "1"), Action(RANGE, "3"), Action(SHIELD, "1"))),
            MonsterStatsBd(impId, 1, true, 5, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(RANGE, "3"), Action(SHIELD, "1"))),

            MonsterStatsBd(impId, 2, false, 2, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(RANGE, "3"), Action(SHIELD, "1"))),
            MonsterStatsBd(impId, 2, true, 6, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(RANGE, "3"), Action(SHIELD, "1"), Action(CURSE, ""))),

            MonsterStatsBd(impId, 3, false, 3, listOf(Action(MOVE, "4"), Action(ATTACK, "2"), Action(RANGE, "4"), Action(SHIELD, "1"))),
            MonsterStatsBd(impId, 3, true, 7, listOf(Action(MOVE, "4"), Action(ATTACK, "2"), Action(RANGE, "4"), Action(SHIELD, "1"), Action(CURSE, ""))),

            MonsterStatsBd(impId, 4, false, 3, listOf(Action(MOVE, "4"), Action(ATTACK, "2"), Action(RANGE, "4"), Action(SHIELD, "2"))),
            MonsterStatsBd(impId, 4, true, 7, listOf(Action(MOVE, "4"), Action(ATTACK, "2"), Action(RANGE, "4"), Action(SHIELD, "2"), Action(CURSE, ""))),

            MonsterStatsBd(impId, 5, false, 4, listOf(Action(MOVE, "4"), Action(ATTACK, "2"), Action(RANGE, "4"), Action(SHIELD, "2"), Action(CURSE, ""))),
            MonsterStatsBd(impId, 5, true, 8, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(SHIELD, "2"), Action(CURSE, ""))),

            MonsterStatsBd(impId, 6, false, 4, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(SHIELD, "2"), Action(CURSE, ""))),
            MonsterStatsBd(impId, 6, true, 9, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "2"), Action(CURSE, ""))),

            MonsterStatsBd(impId, 7, false, 6, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(SHIELD, "2"), Action(CURSE, ""))),
            MonsterStatsBd(impId, 7, true, 11, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "2"), Action(CURSE, ""))),
        )

        val caveBearId = monsterDao.insertMonster(
            MonsterBd(
                name = "Пещерный медведь",
                deckName = "cave-bear",
                isBoss = false,
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(caveBearId, 0, false, 7, listOf(Action(MOVE, "3"), Action(ATTACK, "3"))),
            MonsterStatsBd(caveBearId, 0, true, 11, listOf(Action(MOVE, "3"), Action(ATTACK, "4"))),

            MonsterStatsBd(caveBearId, 1, false, 9, listOf(Action(MOVE, "3"), Action(ATTACK, "3"))),
            MonsterStatsBd(caveBearId, 1, true, 14, listOf(Action(MOVE, "3"), Action(ATTACK, "4"))),

            MonsterStatsBd(caveBearId, 2, false, 11, listOf(Action(MOVE, "4"), Action(ATTACK, "3"))),
            MonsterStatsBd(caveBearId, 2, true, 17, listOf(Action(MOVE, "4"), Action(ATTACK, "4"))),

            MonsterStatsBd(caveBearId, 3, false, 13, listOf(Action(MOVE, "4"), Action(ATTACK, "4"))),
            MonsterStatsBd(caveBearId, 3, true, 20, listOf(Action(MOVE, "4"), Action(ATTACK, "5"))),

            MonsterStatsBd(caveBearId, 4, false, 16, listOf(Action(MOVE, "4"), Action(ATTACK, "4"))),
            MonsterStatsBd(caveBearId, 4, true, 21, listOf(Action(MOVE, "5"), Action(ATTACK, "5"), Action(WOUND, ""))),

            MonsterStatsBd(caveBearId, 5, false, 17, listOf(Action(MOVE, "5"), Action(ATTACK, "4"), Action(WOUND, ""))),
            MonsterStatsBd(caveBearId, 5, true, 24, listOf(Action(MOVE, "5"), Action(ATTACK, "6"), Action(WOUND, ""))),

            MonsterStatsBd(caveBearId, 6, false, 19, listOf(Action(MOVE, "5"), Action(ATTACK, "5"), Action(WOUND, ""))),
            MonsterStatsBd(caveBearId, 6, true, 28, listOf(Action(MOVE, "5"), Action(ATTACK, "7"), Action(WOUND, ""))),

            MonsterStatsBd(caveBearId, 7, false, 22, listOf(Action(MOVE, "5"), Action(ATTACK, "5"), Action(WOUND, ""))),
            MonsterStatsBd(caveBearId, 7, true, 33, listOf(Action(MOVE, "5"), Action(ATTACK, "7"), Action(WOUND, ""))),
        )

        val inoxBodyGuardId1 = monsterDao.insertMonster(
            MonsterBd(
                name = "Инокс-телохранитель - 1",
                deckName = "boss",
                isBoss = true,
                immunity = listOf(DISARM, MUDDLE, POISON, STUN)
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(inoxBodyGuardId1, 0, false, 6, listOf(Action(MOVE, "2"), Action(ATTACK, "С"), Text("Способность 1: Движение -1, Атака -1 по площади #37"), Text("Способность 2: Движение +0, Атака +0, Ответный удар 4"))),

            MonsterStatsBd(inoxBodyGuardId1, 1, false, 7, listOf(Action(MOVE, "2"), Action(ATTACK, "1+С"), Text("Способность 1: Движение -1, Атака -1 по площади #37"), Text("Способность 2: Движение +0, Атака +0, Ответный удар 4"))),

            MonsterStatsBd(inoxBodyGuardId1, 2, false, 9, listOf(Action(MOVE, "2"), Action(ATTACK, "1+С"), Text("Способность 1: Движение -1, Атака -1 по площади #37"), Text("Способность 2: Движение +0, Атака +0, Ответный удар 4"))),

            MonsterStatsBd(inoxBodyGuardId1, 3, false, 10, listOf(Action(MOVE, "3"), Action(ATTACK, "2+С"), Text("Способность 1: Движение -1, Атака -1 по площади #37"), Text("Способность 2: Движение +0, Атака +0, Ответный удар 4"))),

            MonsterStatsBd(inoxBodyGuardId1, 4, false, 11, listOf(Action(MOVE, "3"), Action(ATTACK, "2+С"), Text("Способность 1: Движение -1, Атака -1 по площади #37"), Text("Способность 2: Движение +0, Атака +0, Ответный удар 4"))),

            MonsterStatsBd(inoxBodyGuardId1, 5, false, 13, listOf(Action(MOVE, "3"), Action(ATTACK, "3+С"), Text("Способность 1: Движение -1, Атака -1 по площади #37"), Text("Способность 2: Движение +0, Атака +0, Ответный удар 4"))),

            MonsterStatsBd(inoxBodyGuardId1, 6, false, 15, listOf(Action(MOVE, "4"), Action(ATTACK, "3+С"), Text("Способность 1: Движение -1, Атака -1 по площади #37"), Text("Способность 2: Движение +0, Атака +0, Ответный удар 4"))),

            MonsterStatsBd(inoxBodyGuardId1, 7, false, 27, listOf(Action(MOVE, "4"), Action(ATTACK, "4+С"), Text("Способность 1: Движение -1, Атака -1 по площади #37"), Text("Способность 2: Движение +0, Атака +0, Ответный удар 4"))),
        )

        val inoxBodyGuardId2 = monsterDao.insertMonster(
            MonsterBd(
                name = "Инокс-телохранитель - 2",
                deckName = "boss",
                isBoss = true,
                immunity = listOf(DISARM, MUDDLE, POISON, STUN)
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(inoxBodyGuardId2, 0, false, 6, listOf(Action(MOVE, "2"), Action(ATTACK, "С"), Text("Способность 1: Движение -1, Атака -1 по площади #37"), Text("Способность 2: Движение +0, Атака +0, Ответный удар 4"))),

            MonsterStatsBd(inoxBodyGuardId2, 1, false, 7, listOf(Action(MOVE, "2"), Action(ATTACK, "1+С"), Text("Способность 1: Движение -1, Атака -1 по площади #37"), Text("Способность 2: Движение +0, Атака +0, Ответный удар 4"))),

            MonsterStatsBd(inoxBodyGuardId2, 2, false, 9, listOf(Action(MOVE, "2"), Action(ATTACK, "1+С"), Text("Способность 1: Движение -1, Атака -1 по площади #37"), Text("Способность 2: Движение +0, Атака +0, Ответный удар 4"))),

            MonsterStatsBd(inoxBodyGuardId2, 3, false, 10, listOf(Action(MOVE, "3"), Action(ATTACK, "2+С"), Text("Способность 1: Движение -1, Атака -1 по площади #37"), Text("Способность 2: Движение +0, Атака +0, Ответный удар 4"))),

            MonsterStatsBd(inoxBodyGuardId2, 4, false, 11, listOf(Action(MOVE, "3"), Action(ATTACK, "2+С"), Text("Способность 1: Движение -1, Атака -1 по площади #37"), Text("Способность 2: Движение +0, Атака +0, Ответный удар 4"))),

            MonsterStatsBd(inoxBodyGuardId2, 5, false, 13, listOf(Action(MOVE, "3"), Action(ATTACK, "3+С"), Text("Способность 1: Движение -1, Атака -1 по площади #37"), Text("Способность 2: Движение +0, Атака +0, Ответный удар 4"))),

            MonsterStatsBd(inoxBodyGuardId2, 6, false, 15, listOf(Action(MOVE, "4"), Action(ATTACK, "3+С"), Text("Способность 1: Движение -1, Атака -1 по площади #37"), Text("Способность 2: Движение +0, Атака +0, Ответный удар 4"))),

            MonsterStatsBd(inoxBodyGuardId2, 7, false, 27, listOf(Action(MOVE, "4"), Action(ATTACK, "4+С"), Text("Способность 1: Движение -1, Атака -1 по площади #37"), Text("Способность 2: Движение +0, Атака +0, Ответный удар 4"))),
        )

        val houndId = monsterDao.insertMonster(
            MonsterBd(
                name = "Гончая",
                deckName = "hound",
                isBoss = false,
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(houndId, 0, false, 4, listOf(Action(MOVE, "3"), Action(ATTACK, "2"))),
            MonsterStatsBd(houndId, 0, true, 6, listOf(Action(MOVE, "5"), Action(ATTACK, "2"))),

            MonsterStatsBd(houndId, 1, false, 4, listOf(Action(MOVE, "4"), Action(ATTACK, "2"), Action(RETALIATE, "1"))),
            MonsterStatsBd(houndId, 1, true, 6, listOf(Action(MOVE, "5"), Action(ATTACK, "3"), Action(RETALIATE, "1"))),

            MonsterStatsBd(houndId, 2, false, 6, listOf(Action(MOVE, "4"), Action(ATTACK, "2"), Action(RETALIATE, "1"))),
            MonsterStatsBd(houndId, 2, true, 7, listOf(Action(MOVE, "5"), Action(ATTACK, "3"), Action(RETALIATE, "2"))),

            MonsterStatsBd(houndId, 3, false, 8, listOf(Action(MOVE, "4"), Action(ATTACK, "2"), Action(RETALIATE, "1"))),
            MonsterStatsBd(houndId, 3, true, 8, listOf(Action(MOVE, "5"), Action(ATTACK, "4"), Action(RETALIATE, "2"))),

            MonsterStatsBd(houndId, 4, false, 8, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Action(RETALIATE, "1"))),
            MonsterStatsBd(houndId, 4, true, 11, listOf(Action(MOVE, "5"), Action(ATTACK, "4"), Action(RETALIATE, "2"))),

            MonsterStatsBd(houndId, 5, false, 9, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Action(RETALIATE, "2"))),
            MonsterStatsBd(houndId, 5, true, 12, listOf(Action(MOVE, "5"), Action(ATTACK, "4"), Action(RETALIATE, "3"))),

            MonsterStatsBd(houndId, 6, false, 11, listOf(Action(MOVE, "5"), Action(ATTACK, "3"), Action(RETALIATE, "2"))),
            MonsterStatsBd(houndId, 6, true, 15, listOf(Action(MOVE, "6"), Action(ATTACK, "4"), Action(RETALIATE, "3"))),

            MonsterStatsBd(houndId, 7, false, 15, listOf(Action(MOVE, "5"), Action(ATTACK, "3"), Action(RETALIATE, "2"))),
            MonsterStatsBd(houndId, 7, true, 15, listOf(Action(MOVE, "6"), Action(ATTACK, "5"), Action(RETALIATE, "4"))),
        )

        val vermlingScoutId = monsterDao.insertMonster(
            MonsterBd(
                name = "Вермлинг-разведчик",
                deckName = "scout",
                isBoss = false,
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(vermlingScoutId, 0, false, 2, listOf(Action(MOVE, "3"), Action(ATTACK, "1"))),
            MonsterStatsBd(vermlingScoutId, 0, true, 4, listOf(Action(MOVE, "3"), Action(ATTACK, "2"))),

            MonsterStatsBd(vermlingScoutId, 1, false, 3, listOf(Action(MOVE, "3"), Action(ATTACK, "1"))),
            MonsterStatsBd(vermlingScoutId, 1, true, 5, listOf(Action(MOVE, "3"), Action(ATTACK, "2"))),

            MonsterStatsBd(vermlingScoutId, 2, false, 3, listOf(Action(MOVE, "3"), Action(ATTACK, "2"))),
            MonsterStatsBd(vermlingScoutId, 2, true, 5, listOf(Action(MOVE, "4"), Action(ATTACK, "3"))),

            MonsterStatsBd(vermlingScoutId, 3, false, 5, listOf(Action(MOVE, "3"), Action(ATTACK, "2"))),
            MonsterStatsBd(vermlingScoutId, 3, true, 7, listOf(Action(MOVE, "4"), Action(ATTACK, "3"))),

            MonsterStatsBd(vermlingScoutId, 4, false, 6, listOf(Action(MOVE, "3"), Action(ATTACK, "3"))),
            MonsterStatsBd(vermlingScoutId, 4, true, 8, listOf(Action(MOVE, "4"), Action(ATTACK, "4"))),

            MonsterStatsBd(vermlingScoutId, 5, false, 8, listOf(Action(MOVE, "3"), Action(ATTACK, "3"))),
            MonsterStatsBd(vermlingScoutId, 5, true, 11, listOf(Action(MOVE, "4"), Action(ATTACK, "4"))),

            MonsterStatsBd(vermlingScoutId, 6, false, 9, listOf(Action(MOVE, "4"), Action(ATTACK, "3"))),
            MonsterStatsBd(vermlingScoutId, 6, true, 12, listOf(Action(MOVE, "5"), Action(ATTACK, "4"))),

            MonsterStatsBd(vermlingScoutId, 7, false, 11, listOf(Action(MOVE, "4"), Action(ATTACK, "3"))),
            MonsterStatsBd(vermlingScoutId, 7, true, 15, listOf(Action(MOVE, "5"), Action(ATTACK, "4"))),
        )

        val cruelOverseerId = monsterDao.insertMonster(
            MonsterBd(
                name = "Жестокий надзиратель",
                deckName = "boss",
                isBoss = true,
                immunity = listOf(STUN, WOUND, DISARM, CURSE)
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(cruelOverseerId, 0, false, 6, listOf(Action(MOVE, "2"), Action(ATTACK, "V"), Text("Способность 1: все разведчики ходят еще раз"), Text("Способность 2: Призывает вермлинга разведчика"), Text("V = количество разведчиков на карте"))),

            MonsterStatsBd(cruelOverseerId, 1, false, 8, listOf(Action(MOVE, "2"), Action(ATTACK, "V"), Text("Способность 1: все разведчики ходят еще раз"), Text("Способность 2: Призывает вермлинга разведчика"), Text("V = количество разведчиков на карте"))),

            MonsterStatsBd(cruelOverseerId, 2, false, 9, listOf(Action(MOVE, "3"), Action(ATTACK, "V"), Text("Способность 1: все разведчики ходят еще раз"), Text("Способность 2: Призывает вермлинга разведчика"), Text("V = количество разведчиков на карте"))),

            MonsterStatsBd(cruelOverseerId, 3, false, 11, listOf(Action(MOVE, "3"), Action(ATTACK, "V"), Text("Способность 1: все разведчики ходят еще раз"), Text("Способность 2: Призывает вермлинга разведчика"), Text("V = количество разведчиков на карте"))),

            MonsterStatsBd(cruelOverseerId, 4, false, 12, listOf(Action(MOVE, "4"), Action(ATTACK, "V"), Text("Способность 1: все разведчики ходят еще раз"), Text("Способность 2: Призывает вермлинга разведчика"), Text("V = количество разведчиков на карте"))),

            MonsterStatsBd(cruelOverseerId, 5, false, 14, listOf(Action(MOVE, "4"), Action(ATTACK, "V"), Text("Способность 1: все разведчики ходят еще раз"), Text("Способность 2: Призывает вермлинга разведчика"), Text("V = количество разведчиков на карте"))),

            MonsterStatsBd(cruelOverseerId, 6, false, 16, listOf(Action(MOVE, "4"), Action(ATTACK, "V"), Text("Способность 1: все разведчики ходят еще раз"), Text("Способность 2: Призывает вермлинга разведчика"), Text("V = количество разведчиков на карте"))),

            MonsterStatsBd(cruelOverseerId, 7, false, 19, listOf(Action(MOVE, "4"), Action(ATTACK, "V"), Text("Способность 1: все разведчики ходят еще раз"), Text("Способность 2: Призывает вермлинга разведчика"), Text("V = количество разведчиков на карте"))),
        )

        val sunDemonId = monsterDao.insertMonster(
            MonsterBd(
                name = "Солнечный демон",
                deckName = "sun-demon",
                isBoss = false,
                fly = true,
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(sunDemonId, 0, false, 5, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(SHIELD, "1"), Text("Атакует с преимуществом"))),
            MonsterStatsBd(sunDemonId, 0, true, 9, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(SHIELD, "1"), Text("Атакует с преимуществом"))),

            MonsterStatsBd(sunDemonId, 1, false, 7, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(SHIELD, "1"), Text("Атакует с преимуществом"))),
            MonsterStatsBd(sunDemonId, 1, true, 12, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(SHIELD, "1"), Text("Атакует с преимуществом"))),

            MonsterStatsBd(sunDemonId, 2, false, 9, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(SHIELD, "1"), Text("Атакует с преимуществом"))),
            MonsterStatsBd(sunDemonId, 2, true, 13, listOf(Action(MOVE, "2"), Action(ATTACK, "4"), Action(SHIELD, "1"), Text("Атакует с преимуществом"))),

            MonsterStatsBd(sunDemonId, 3, false, 10, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(SHIELD, "1"), Text("Атакует с преимуществом"))),
            MonsterStatsBd(sunDemonId, 3, true, 15, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(SHIELD, "1"), Text("Атакует с преимуществом"))),

            MonsterStatsBd(sunDemonId, 4, false, 11, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(SHIELD, "1"), Text("Атакует с преимуществом"))),
            MonsterStatsBd(sunDemonId, 4, true, 16, listOf(Action(MOVE, "3"), Action(ATTACK, "5"), Action(SHIELD, "1"), Text("Атакует с преимуществом"))),

            MonsterStatsBd(sunDemonId, 5, false, 11, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(SHIELD, "2"), Text("Атакует с преимуществом"))),
            MonsterStatsBd(sunDemonId, 5, true, 16, listOf(Action(MOVE, "3"), Action(ATTACK, "5"), Action(SHIELD, "2"), Text("Атакует с преимуществом"))),

            MonsterStatsBd(sunDemonId, 6, false, 12, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(SHIELD, "2"), Text("Атакует с преимуществом"))),
            MonsterStatsBd(sunDemonId, 6, true, 18, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(SHIELD, "2"), Text("Атакует с преимуществом"))),

            MonsterStatsBd(sunDemonId, 7, false, 15, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(SHIELD, "2"), Text("Атакует с преимуществом"))),
            MonsterStatsBd(sunDemonId, 7, true, 22, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(SHIELD, "2"), Text("Атакует с преимуществом"))),
        )
    }

    private suspend fun fillAbilityDecks(monsterDao: MonsterDao) {
        // Boss deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "boss", imageName = "ic_deck_ma_bo_1.webp"),
            MonsterAbilityCardBd(deckName = "boss", imageName = "ic_deck_ma_bo_2.webp"),
            MonsterAbilityCardBd(deckName = "boss", imageName = "ic_deck_ma_bo_3.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "boss", imageName = "ic_deck_ma_bo_4.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "boss", imageName = "ic_deck_ma_bo_5.webp"),
            MonsterAbilityCardBd(deckName = "boss", imageName = "ic_deck_ma_bo_6.webp"),
            MonsterAbilityCardBd(deckName = "boss", imageName = "ic_deck_ma_bo_7.webp"),
            MonsterAbilityCardBd(deckName = "boss", imageName = "ic_deck_ma_bo_8.webp"),
        )

        // living-bones deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "living-bones", imageName = "ic_deck_ma_lb_1.webp"),
            MonsterAbilityCardBd(deckName = "living-bones", imageName = "ic_deck_ma_lb_2.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "living-bones", imageName = "ic_deck_ma_lb_3.webp"),
            MonsterAbilityCardBd(deckName = "living-bones", imageName = "ic_deck_ma_lb_4.webp"),
            MonsterAbilityCardBd(deckName = "living-bones", imageName = "ic_deck_ma_lb_5.webp"),
            MonsterAbilityCardBd(deckName = "living-bones", imageName = "ic_deck_ma_lb_6.webp"),
            MonsterAbilityCardBd(deckName = "living-bones", imageName = "ic_deck_ma_lb_7.webp"),
            MonsterAbilityCardBd(deckName = "living-bones", imageName = "ic_deck_ma_lb_8.webp", needsShuffle = true),
        )

        // guard deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "guard", imageName = "ic_deck_ma_gu_1.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "guard", imageName = "ic_deck_ma_gu_2.webp"),
            MonsterAbilityCardBd(deckName = "guard", imageName = "ic_deck_ma_gu_3.webp"),
            MonsterAbilityCardBd(deckName = "guard", imageName = "ic_deck_ma_gu_4.webp"),
            MonsterAbilityCardBd(deckName = "guard", imageName = "ic_deck_ma_gu_5.webp"),
            MonsterAbilityCardBd(deckName = "guard", imageName = "ic_deck_ma_gu_6.webp"),
            MonsterAbilityCardBd(deckName = "guard", imageName = "ic_deck_ma_gu_7.webp"),
            MonsterAbilityCardBd(deckName = "guard", imageName = "ic_deck_ma_gu_8.webp", needsShuffle = true),
        )

        // archer deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "archer", imageName = "ic_deck_ma_ar_1.webp"),
            MonsterAbilityCardBd(deckName = "archer", imageName = "ic_deck_ma_ar_2.webp"),
            MonsterAbilityCardBd(deckName = "archer", imageName = "ic_deck_ma_ar_3.webp"),
            MonsterAbilityCardBd(deckName = "archer", imageName = "ic_deck_ma_ar_4.webp"),
            MonsterAbilityCardBd(deckName = "archer", imageName = "ic_deck_ma_ar_5.webp"),
            MonsterAbilityCardBd(deckName = "archer", imageName = "ic_deck_ma_ar_6.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "archer", imageName = "ic_deck_ma_ar_7.webp"),
            MonsterAbilityCardBd(deckName = "archer", imageName = "ic_deck_ma_ar_8.webp", needsShuffle = true),
        )

        // living-corpse deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "living-corpse", imageName = "ic_deck_ma_lc_1.webp"),
            MonsterAbilityCardBd(deckName = "living-corpse", imageName = "ic_deck_ma_lc_2.webp"),
            MonsterAbilityCardBd(deckName = "living-corpse", imageName = "ic_deck_ma_lc_3.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "living-corpse", imageName = "ic_deck_ma_lc_4.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "living-corpse", imageName = "ic_deck_ma_lc_5.webp"),
            MonsterAbilityCardBd(deckName = "living-corpse", imageName = "ic_deck_ma_lc_6.webp"),
            MonsterAbilityCardBd(deckName = "living-corpse", imageName = "ic_deck_ma_lc_7.webp"),
            MonsterAbilityCardBd(deckName = "living-corpse", imageName = "ic_deck_ma_lc_8.webp"),
        )

        // shaman deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "shaman", imageName = "ic_deck_ma_sh_1.webp"),
            MonsterAbilityCardBd(deckName = "shaman", imageName = "ic_deck_ma_sh_2.webp"),
            MonsterAbilityCardBd(deckName = "shaman", imageName = "ic_deck_ma_sh_3.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "shaman", imageName = "ic_deck_ma_sh_4.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "shaman", imageName = "ic_deck_ma_sh_5.webp"),
            MonsterAbilityCardBd(deckName = "shaman", imageName = "ic_deck_ma_sh_6.webp"),
            MonsterAbilityCardBd(deckName = "shaman", imageName = "ic_deck_ma_sh_7.webp"),
            MonsterAbilityCardBd(deckName = "shaman", imageName = "ic_deck_ma_sh_8.webp"),
        )

        // cultist deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "cultist", imageName = "ic_deck_ma_cu_1.webp"),
            MonsterAbilityCardBd(deckName = "cultist", imageName = "ic_deck_ma_cu_2.webp"),
            MonsterAbilityCardBd(deckName = "cultist", imageName = "ic_deck_ma_cu_3.webp"),
            MonsterAbilityCardBd(deckName = "cultist", imageName = "ic_deck_ma_cu_4.webp"),
            MonsterAbilityCardBd(deckName = "cultist", imageName = "ic_deck_ma_cu_5.webp"),
            MonsterAbilityCardBd(deckName = "cultist", imageName = "ic_deck_ma_cu_6.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "cultist", imageName = "ic_deck_ma_cu_7.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "cultist", imageName = "ic_deck_ma_cu_8.webp"),
        )

        // earth-demon deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "earth-demon", imageName = "ic_deck_ma_ed_1.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "earth-demon", imageName = "ic_deck_ma_ed_2.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "earth-demon", imageName = "ic_deck_ma_ed_3.webp"),
            MonsterAbilityCardBd(deckName = "earth-demon", imageName = "ic_deck_ma_ed_4.webp"),
            MonsterAbilityCardBd(deckName = "earth-demon", imageName = "ic_deck_ma_ed_5.webp"),
            MonsterAbilityCardBd(deckName = "earth-demon", imageName = "ic_deck_ma_ed_6.webp"),
            MonsterAbilityCardBd(deckName = "earth-demon", imageName = "ic_deck_ma_ed_7.webp"),
            MonsterAbilityCardBd(deckName = "earth-demon", imageName = "ic_deck_ma_ed_8.webp"),
        )

        // wind-demon deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "wind-demon", imageName = "ic_deck_ma_wd_1.webp"),
            MonsterAbilityCardBd(deckName = "wind-demon", imageName = "ic_deck_ma_wd_2.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "wind-demon", imageName = "ic_deck_ma_wd_3.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "wind-demon", imageName = "ic_deck_ma_wd_4.webp"),
            MonsterAbilityCardBd(deckName = "wind-demon", imageName = "ic_deck_ma_wd_5.webp"),
            MonsterAbilityCardBd(deckName = "wind-demon", imageName = "ic_deck_ma_wd_6.webp"),
            MonsterAbilityCardBd(deckName = "wind-demon", imageName = "ic_deck_ma_wd_7.webp"),
            MonsterAbilityCardBd(deckName = "wind-demon", imageName = "ic_deck_ma_wd_8.webp"),
        )

        // living-spirit deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "living-spirit", imageName = "ic_deck_ma_ls_1.webp"),
            MonsterAbilityCardBd(deckName = "living-spirit", imageName = "ic_deck_ma_ls_2.webp"),
            MonsterAbilityCardBd(deckName = "living-spirit", imageName = "ic_deck_ma_ls_3.webp"),
            MonsterAbilityCardBd(deckName = "living-spirit", imageName = "ic_deck_ma_ls_4.webp"),
            MonsterAbilityCardBd(deckName = "living-spirit", imageName = "ic_deck_ma_ls_5.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "living-spirit", imageName = "ic_deck_ma_ls_6.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "living-spirit", imageName = "ic_deck_ma_ls_7.webp"),
            MonsterAbilityCardBd(deckName = "living-spirit", imageName = "ic_deck_ma_ls_8.webp"),
        )

        // night-demon deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "night-demon", imageName = "ic_deck_ma_nd_1.webp"),
            MonsterAbilityCardBd(deckName = "night-demon", imageName = "ic_deck_ma_nd_2.webp"),
            MonsterAbilityCardBd(deckName = "night-demon", imageName = "ic_deck_ma_nd_3.webp"),
            MonsterAbilityCardBd(deckName = "night-demon", imageName = "ic_deck_ma_nd_4.webp"),
            MonsterAbilityCardBd(deckName = "night-demon", imageName = "ic_deck_ma_nd_5.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "night-demon", imageName = "ic_deck_ma_nd_6.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "night-demon", imageName = "ic_deck_ma_nd_7.webp"),
            MonsterAbilityCardBd(deckName = "night-demon", imageName = "ic_deck_ma_nd_8.webp"),
        )

        // flame-demon deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "flame-demon", imageName = "ic_deck_ma_fld_1.webp"),
            MonsterAbilityCardBd(deckName = "flame-demon", imageName = "ic_deck_ma_fld_2.webp"),
            MonsterAbilityCardBd(deckName = "flame-demon", imageName = "ic_deck_ma_fld_3.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "flame-demon", imageName = "ic_deck_ma_fld_4.webp"),
            MonsterAbilityCardBd(deckName = "flame-demon", imageName = "ic_deck_ma_fld_5.webp"),
            MonsterAbilityCardBd(deckName = "flame-demon", imageName = "ic_deck_ma_fld_6.webp"),
            MonsterAbilityCardBd(deckName = "flame-demon", imageName = "ic_deck_ma_fld_7.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "flame-demon", imageName = "ic_deck_ma_fld_8.webp"),
        )

        // frost-demon deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "frost-demon", imageName = "ic_deck_ma_frd_1.webp"),
            MonsterAbilityCardBd(deckName = "frost-demon", imageName = "ic_deck_ma_frd_2.webp"),
            MonsterAbilityCardBd(deckName = "frost-demon", imageName = "ic_deck_ma_frd_3.webp"),
            MonsterAbilityCardBd(deckName = "frost-demon", imageName = "ic_deck_ma_frd_4.webp"),
            MonsterAbilityCardBd(deckName = "frost-demon", imageName = "ic_deck_ma_frd_5.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "frost-demon", imageName = "ic_deck_ma_frd_6.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "frost-demon", imageName = "ic_deck_ma_frd_7.webp"),
            MonsterAbilityCardBd(deckName = "frost-demon", imageName = "ic_deck_ma_frd_8.webp"),
        )

        // imp deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "imp", imageName = "ic_deck_ma_im_1.webp"),
            MonsterAbilityCardBd(deckName = "imp", imageName = "ic_deck_ma_im_2.webp"),
            MonsterAbilityCardBd(deckName = "imp", imageName = "ic_deck_ma_im_3.webp"),
            MonsterAbilityCardBd(deckName = "imp", imageName = "ic_deck_ma_im_4.webp"),
            MonsterAbilityCardBd(deckName = "imp", imageName = "ic_deck_ma_im_5.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "imp", imageName = "ic_deck_ma_im_6.webp"),
            MonsterAbilityCardBd(deckName = "imp", imageName = "ic_deck_ma_im_7.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "imp", imageName = "ic_deck_ma_im_8.webp"),
        )

        // cave-bear deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "cave-bear", imageName = "ic_deck_ma_cb_1.webp"),
            MonsterAbilityCardBd(deckName = "cave-bear", imageName = "ic_deck_ma_cb_2.webp"),
            MonsterAbilityCardBd(deckName = "cave-bear", imageName = "ic_deck_ma_cb_3.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "cave-bear", imageName = "ic_deck_ma_cb_4.webp"),
            MonsterAbilityCardBd(deckName = "cave-bear", imageName = "ic_deck_ma_cb_5.webp"),
            MonsterAbilityCardBd(deckName = "cave-bear", imageName = "ic_deck_ma_cb_6.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "cave-bear", imageName = "ic_deck_ma_cb_7.webp"),
            MonsterAbilityCardBd(deckName = "cave-bear", imageName = "ic_deck_ma_cb_8.webp"),
        )

        // hound deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "hound", imageName = "ic_deck_ma_ho_1.webp"),
            MonsterAbilityCardBd(deckName = "hound", imageName = "ic_deck_ma_ho_2.webp"),
            MonsterAbilityCardBd(deckName = "hound", imageName = "ic_deck_ma_ho_3.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "hound", imageName = "ic_deck_ma_ho_4.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "hound", imageName = "ic_deck_ma_ho_5.webp"),
            MonsterAbilityCardBd(deckName = "hound", imageName = "ic_deck_ma_ho_6.webp"),
            MonsterAbilityCardBd(deckName = "hound", imageName = "ic_deck_ma_ho_7.webp"),
            MonsterAbilityCardBd(deckName = "hound", imageName = "ic_deck_ma_ho_8.webp"),
        )

        // scout deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "scout", imageName = "ic_deck_ma_sc_1.webp"),
            MonsterAbilityCardBd(deckName = "scout", imageName = "ic_deck_ma_sc_2.webp"),
            MonsterAbilityCardBd(deckName = "scout", imageName = "ic_deck_ma_sc_3.webp"),
            MonsterAbilityCardBd(deckName = "scout", imageName = "ic_deck_ma_sc_4.webp"),
            MonsterAbilityCardBd(deckName = "scout", imageName = "ic_deck_ma_sc_5.webp"),
            MonsterAbilityCardBd(deckName = "scout", imageName = "ic_deck_ma_sc_6.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "scout", imageName = "ic_deck_ma_sc_7.webp"),
            MonsterAbilityCardBd(deckName = "scout", imageName = "ic_deck_ma_sc_8.webp", needsShuffle = true),
        )

        // sun-demon deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "sun-demon", imageName = "ic_deck_ma_sud_1.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "sun-demon", imageName = "ic_deck_ma_sud_2.webp"),
            MonsterAbilityCardBd(deckName = "sun-demon", imageName = "ic_deck_ma_sud_3.webp"),
            MonsterAbilityCardBd(deckName = "sun-demon", imageName = "ic_deck_ma_sud_4.webp"),
            MonsterAbilityCardBd(deckName = "sun-demon", imageName = "ic_deck_ma_sud_5.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "sun-demon", imageName = "ic_deck_ma_sud_6.webp"),
            MonsterAbilityCardBd(deckName = "sun-demon", imageName = "ic_deck_ma_sud_7.webp"),
            MonsterAbilityCardBd(deckName = "sun-demon", imageName = "ic_deck_ma_sud_8.webp"),
        )
    }
}
