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
                name = "Культист",
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

        val cityGuardId = monsterDao.insertMonster(
            MonsterBd(
                name = "Городской страж",
                deckName = "guard",
                isBoss = false,
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(cityGuardId, 0, false, 5, listOf(Action(MOVE, "2"), Action(ATTACK, "2"))),
            MonsterStatsBd(cityGuardId, 0, true, 6, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(SHIELD, "1"))),

            MonsterStatsBd(cityGuardId, 1, false, 5, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(SHIELD, "1"))),
            MonsterStatsBd(cityGuardId, 1, true, 6, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(SHIELD, "2"))),

            MonsterStatsBd(cityGuardId, 2, false, 7, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(SHIELD, "1"))),
            MonsterStatsBd(cityGuardId, 2, true, 9, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(SHIELD, "2"))),

            MonsterStatsBd(cityGuardId, 3, false, 8, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(SHIELD, "1"))),
            MonsterStatsBd(cityGuardId, 3, true, 9, listOf(Action(MOVE, "2"), Action(ATTACK, "4"), Action(SHIELD, "2"), Action(RETALIATE, "1"))),

            MonsterStatsBd(cityGuardId, 4, false, 9, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(SHIELD, "1"))),
            MonsterStatsBd(cityGuardId, 4, true, 10, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(SHIELD, "2"), Action(RETALIATE, "2"))),

            MonsterStatsBd(cityGuardId, 5, false, 10, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(SHIELD, "2"))),
            MonsterStatsBd(cityGuardId, 5, true, 12, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(SHIELD, "3"), Action(RETALIATE, "2"))),

            MonsterStatsBd(cityGuardId, 6, false, 11, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(SHIELD, "2"))),
            MonsterStatsBd(cityGuardId, 6, true, 13, listOf(Action(MOVE, "3"), Action(ATTACK, "5"), Action(SHIELD, "3"), Action(RETALIATE, "3"))),

            MonsterStatsBd(cityGuardId, 7, false, 13, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(SHIELD, "2"))),
            MonsterStatsBd(cityGuardId, 7, true, 14, listOf(Action(MOVE, "3"), Action(ATTACK, "6"), Action(SHIELD, "3"), Action(RETALIATE, "3"))),
        )

        val cityArcherId = monsterDao.insertMonster(
            MonsterBd(
                name = "Городской лучник",
                deckName = "archer",
                isBoss = false,
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(cityArcherId, 0, false, 4, listOf(Action(MOVE, "1"), Action(ATTACK, "2"), Action(RANGE, "3"))),
            MonsterStatsBd(cityArcherId, 0, true, 6, listOf(Action(MOVE, "1"), Action(ATTACK, "3"), Action(RANGE, "4"))),

            MonsterStatsBd(cityArcherId, 1, false, 5, listOf(Action(MOVE, "1"), Action(ATTACK, "2"), Action(RANGE, "4"))),
            MonsterStatsBd(cityArcherId, 1, true, 6, listOf(Action(MOVE, "1"), Action(ATTACK, "3"), Action(RANGE, "5"), Action(SHIELD, "1"), Action(PIERCE, "1"))),

            MonsterStatsBd(cityArcherId, 2, false, 6, listOf(Action(MOVE, "1"), Action(ATTACK, "3"), Action(RANGE, "4"))),
            MonsterStatsBd(cityArcherId, 2, true, 7, listOf(Action(MOVE, "1"), Action(ATTACK, "4"), Action(RANGE, "5"), Action(SHIELD, "1"), Action(PIERCE, "2"))),

            MonsterStatsBd(cityArcherId, 3, false, 6, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(SHIELD, "1"))),
            MonsterStatsBd(cityArcherId, 3, true, 8, listOf(Action(MOVE, "2"), Action(ATTACK, "4"), Action(RANGE, "5"), Action(SHIELD, "2"), Action(PIERCE, "2"))),

            MonsterStatsBd(cityArcherId, 4, false, 8, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(RANGE, "5"), Action(SHIELD, "1"))),
            MonsterStatsBd(cityArcherId, 4, true, 10, listOf(Action(MOVE, "2"), Action(ATTACK, "4"), Action(RANGE, "6"), Action(SHIELD, "2"), Action(PIERCE, "2"))),

            MonsterStatsBd(cityArcherId, 5, false, 9, listOf(Action(MOVE, "2"), Action(ATTACK, "4"), Action(RANGE, "5"), Action(SHIELD, "1"))),
            MonsterStatsBd(cityArcherId, 5, true, 11, listOf(Action(MOVE, "2"), Action(ATTACK, "5"), Action(RANGE, "6"), Action(SHIELD, "2"), Action(PIERCE, "3"))),

            MonsterStatsBd(cityArcherId, 6, false, 9, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "5"), Action(SHIELD, "2"))),
            MonsterStatsBd(cityArcherId, 6, true, 12, listOf(Action(MOVE, "3"), Action(ATTACK, "6"), Action(RANGE, "6"), Action(SHIELD, "2"), Action(PIERCE, "3"))),

            MonsterStatsBd(cityArcherId, 7, false, 10, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "6"), Action(SHIELD, "2"))),
            MonsterStatsBd(cityArcherId, 7, true, 13, listOf(Action(MOVE, "3"), Action(ATTACK, "6"), Action(RANGE, "7"), Action(SHIELD, "3"), Action(PIERCE, "3"))),
        )

        val guardCaptainId = monsterDao.insertMonster(
            MonsterBd(
                name = "Начальник стражи",
                deckName = "boss",
                isBoss = true,
                immunity = listOf(STUN, DISARM, WOUND, MUDDLE)
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(guardCaptainId, 0, false, 7, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Text("Способность 1: Лечит на 2 себя и всех союзников"), Text("Способность 2: Все союзники получают + 1 ко всем атакам на весь раунд. Атакует +1."))),

            MonsterStatsBd(guardCaptainId, 1, false, 9, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Text("Способность 1: Лечит на 2 себя и всех союзников"), Text("Способность 2: Все союзники получают + 1 ко всем атакам на весь раунд. Атакует +1."))),

            MonsterStatsBd(guardCaptainId, 2, false, 11, listOf(Action(MOVE, "2"), Action(ATTACK, "4"), Text("Способность 1: Лечит на 2 себя и всех союзников"), Text("Способность 2: Все союзники получают + 1 ко всем атакам на весь раунд. Атакует +1."))),

            MonsterStatsBd(guardCaptainId, 3, false, 14, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Text("Способность 1: Лечит на 2 себя и всех союзников"), Text("Способность 2: Все союзники получают + 1 ко всем атакам на весь раунд. Атакует +1."))),

            MonsterStatsBd(guardCaptainId, 4, false, 16, listOf(Action(MOVE, "3"), Action(ATTACK, "5"), Text("Способность 1: Лечит на 2 себя и всех союзников"), Text("Способность 2: Все союзники получают + 1 ко всем атакам на весь раунд. Атакует +1."))),

            MonsterStatsBd(guardCaptainId, 5, false, 20, listOf(Action(MOVE, "3"), Action(ATTACK, "5"), Text("Способность 1: Лечит на 2 себя и всех союзников"), Text("Способность 2: Все союзники получают + 1 ко всем атакам на весь раунд. Атакует +1."))),

            MonsterStatsBd(guardCaptainId, 6, false, 21, listOf(Action(MOVE, "4"), Action(ATTACK, "6"), Text("Способность 1: Лечит на 2 себя и всех союзников"), Text("Способность 2: Все союзники получают + 1 ко всем атакам на весь раунд. Атакует +1."))),

            MonsterStatsBd(guardCaptainId, 7, false, 25, listOf(Action(MOVE, "4"), Action(ATTACK, "6"), Text("Способность 1: Лечит на 2 себя и всех союзников"), Text("Способность 2: Все союзники получают + 1 ко всем атакам на весь раунд. Атакует +1."))),
        )

        val jekserahId = monsterDao.insertMonster(
            MonsterBd(
                name = "Джексера",
                deckName = "boss",
                isBoss = true,
                immunity = listOf(STUN, DISARM, CURSE, WOUND)
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(jekserahId, 0, false, 6, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Text("Способность 1: Прзывает Ожившие кости, аткует -1 всех соседних врагов"), Text("Способность 2: Призывает Оживгий труп, двигается -1, атакует +2"))),

            MonsterStatsBd(jekserahId, 1, false, 7, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Text("Способность 1: Прзывает Ожившие кости, аткует -1 всех соседних врагов"), Text("Способность 2: Призывает Оживгий труп, двигается -1, атакует +2"))),

            MonsterStatsBd(jekserahId, 2, false, 9, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Text("Способность 1: Прзывает Ожившие кости, аткует -1 всех соседних врагов"), Text("Способность 2: Призывает Оживгий труп, двигается -1, атакует +2"))),

            MonsterStatsBd(jekserahId, 3, false, 12, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Text("Способность 1: Прзывает Ожившие кости, аткует -1 всех соседних врагов"), Text("Способность 2: Призывает Оживгий труп, двигается -1, атакует +2"))),

            MonsterStatsBd(jekserahId, 4, false, 13, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Text("Способность 1: Прзывает Ожившие кости, аткует -1 всех соседних врагов"), Text("Способность 2: Призывает Оживгий труп, двигается -1, атакует +2"))),

            MonsterStatsBd(jekserahId, 5, false, 15, listOf(Action(MOVE, "5"), Action(ATTACK, "5"), Text("Способность 1: Прзывает Ожившие кости, аткует -1 всех соседних врагов"), Text("Способность 2: Призывает Оживгий труп, двигается -1, атакует +2"))),

            MonsterStatsBd(jekserahId, 6, false, 18, listOf(Action(MOVE, "5"), Action(ATTACK, "5"), Text("Способность 1: Прзывает Ожившие кости, аткует -1 всех соседних врагов"), Text("Способность 2: Призывает Оживгий труп, двигается -1, атакует +2"))),

            MonsterStatsBd(jekserahId, 7, false, 22, listOf(Action(MOVE, "5"), Action(ATTACK, "5"), Text("Способность 1: Прзывает Ожившие кости, аткует -1 всех соседних врагов"), Text("Способность 2: Призывает Оживгий труп, двигается -1, атакует +2"))),
        )

        val stoneGolemId = monsterDao.insertMonster(
            MonsterBd(
                name = "Каменный голем",
                deckName = "stone-golem",
                isBoss = false,
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(stoneGolemId, 0, false, 10, listOf(Action(MOVE, "1"), Action(ATTACK, "3"))),
            MonsterStatsBd(stoneGolemId, 0, true, 10, listOf(Action(MOVE, "2"), Action(ATTACK, "4"), Action(SHIELD, "1"))),

            MonsterStatsBd(stoneGolemId, 1, false, 10, listOf(Action(MOVE, "1"), Action(ATTACK, "3"), Action(SHIELD, "1"))),
            MonsterStatsBd(stoneGolemId, 1, true, 11, listOf(Action(MOVE, "2"), Action(ATTACK, "4"), Action(SHIELD, "2"))),

            MonsterStatsBd(stoneGolemId, 2, false, 11, listOf(Action(MOVE, "1"), Action(ATTACK, "4"), Action(SHIELD, "1"))),
            MonsterStatsBd(stoneGolemId, 2, true, 14, listOf(Action(MOVE, "2"), Action(ATTACK, "5"), Action(SHIELD, "2"))),

            MonsterStatsBd(stoneGolemId, 3, false, 11, listOf(Action(MOVE, "1"), Action(ATTACK, "4"), Action(SHIELD, "2"))),
            MonsterStatsBd(stoneGolemId, 3, true, 15, listOf(Action(MOVE, "2"), Action(ATTACK, "5"), Action(SHIELD, "3"))),

            MonsterStatsBd(stoneGolemId, 4, false, 12, listOf(Action(MOVE, "2"), Action(ATTACK, "4"), Action(SHIELD, "2"))),
            MonsterStatsBd(stoneGolemId, 4, true, 17, listOf(Action(MOVE, "2"), Action(ATTACK, "6"), Action(SHIELD, "3"))),

            MonsterStatsBd(stoneGolemId, 5, false, 13, listOf(Action(MOVE, "2"), Action(ATTACK, "5"), Action(SHIELD, "2"))),
            MonsterStatsBd(stoneGolemId, 5, true, 19, listOf(Action(MOVE, "3"), Action(ATTACK, "6"), Action(SHIELD, "3"))),

            MonsterStatsBd(stoneGolemId, 6, false, 16, listOf(Action(MOVE, "2"), Action(ATTACK, "5"), Action(SHIELD, "2"))),
            MonsterStatsBd(stoneGolemId, 6, true, 20, listOf(Action(MOVE, "3"), Action(ATTACK, "7"), Action(SHIELD, "3"))),

            MonsterStatsBd(stoneGolemId, 7, false, 16, listOf(Action(MOVE, "2"), Action(ATTACK, "5"), Action(SHIELD, "3"))),
            MonsterStatsBd(stoneGolemId, 7, true, 21, listOf(Action(MOVE, "3"), Action(ATTACK, "7"), Action(SHIELD, "4"))),
        )

        val spittingDrakeId = monsterDao.insertMonster(
            MonsterBd(
                name = "Шипящий дрейк",
                deckName = "spitting-drake",
                isBoss = false,
                fly = true,
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(spittingDrakeId, 0, false, 5, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "3"))),
            MonsterStatsBd(spittingDrakeId, 0, true, 8, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(RANGE, "4"))),

            MonsterStatsBd(spittingDrakeId, 1, false, 6, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "3"))),
            MonsterStatsBd(spittingDrakeId, 1, true, 9, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(MUDDLE, ""))),

            MonsterStatsBd(spittingDrakeId, 2, false, 8, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "3"), Action(MUDDLE, ""))),
            MonsterStatsBd(spittingDrakeId, 2, true, 10, listOf(Action(MOVE, "3"), Action(ATTACK, "5"), Action(RANGE, "4"), Action(MUDDLE, ""))),

            MonsterStatsBd(spittingDrakeId, 3, false, 8, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(MUDDLE, ""))),
            MonsterStatsBd(spittingDrakeId, 3, true, 12, listOf(Action(MOVE, "3"), Action(ATTACK, "5"), Action(RANGE, "5"), Action(MUDDLE, ""))),

            MonsterStatsBd(spittingDrakeId, 4, false, 9, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(MUDDLE, ""))),
            MonsterStatsBd(spittingDrakeId, 4, true, 14, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(RANGE, "5"), Action(MUDDLE, ""))),

            MonsterStatsBd(spittingDrakeId, 5, false, 12, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(MUDDLE, ""))),
            MonsterStatsBd(spittingDrakeId, 5, true, 16, listOf(Action(MOVE, "4"), Action(ATTACK, "6"), Action(RANGE, "5"), Action(MUDDLE, ""))),

            MonsterStatsBd(spittingDrakeId, 6, false, 13, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(RANGE, "4"), Action(MUDDLE, ""))),
            MonsterStatsBd(spittingDrakeId, 6, true, 19, listOf(Action(MOVE, "4"), Action(ATTACK, "6"), Action(RANGE, "5"), Action(MUDDLE, ""))),

            MonsterStatsBd(spittingDrakeId, 7, false, 16, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(RANGE, "4"), Action(MUDDLE, ""))),
            MonsterStatsBd(spittingDrakeId, 7, true, 21, listOf(Action(MOVE, "4"), Action(ATTACK, "7"), Action(RANGE, "5"), Action(MUDDLE, ""))),
        )

        val savvasIcestormId = monsterDao.insertMonster(
            MonsterBd(
                name = "Саввас Ледяной шторм",
                deckName = "savvas-icestorm",
                isBoss = false,
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(savvasIcestormId, 0, false, 7, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(RANGE, "3"), Action(PIERCE, "3"))),
            MonsterStatsBd(savvasIcestormId, 0, true, 12, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(PIERCE, "3"))),

            MonsterStatsBd(savvasIcestormId, 1, false, 10, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(RANGE, "4"), Action(PIERCE, "3"))),
            MonsterStatsBd(savvasIcestormId, 1, true, 12, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(RANGE, "5"), Action(PIERCE, "3"), Action(SHIELD, "1"))),

            MonsterStatsBd(savvasIcestormId, 2, false, 12, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(RANGE, "4"), Action(PIERCE, "3"))),
            MonsterStatsBd(savvasIcestormId, 2, true, 15, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "5"), Action(PIERCE, "3"), Action(SHIELD, "1"))),

            MonsterStatsBd(savvasIcestormId, 3, false, 12, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(PIERCE, "3"), Action(SHIELD, "1"))),
            MonsterStatsBd(savvasIcestormId, 3, true, 18, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "6"), Action(PIERCE, "3"), Action(SHIELD, "1"))),

            MonsterStatsBd(savvasIcestormId, 4, false, 14, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "5"), Action(PIERCE, "3"), Action(SHIELD, "1"))),
            MonsterStatsBd(savvasIcestormId, 4, true, 19, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(RANGE, "6"), Action(PIERCE, "3"), Action(SHIELD, "2"))),

            MonsterStatsBd(savvasIcestormId, 5, false, 16, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "5"), Action(PIERCE, "3"), Action(SHIELD, "1"))),
            MonsterStatsBd(savvasIcestormId, 5, true, 21, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(RANGE, "6"), Action(PIERCE, "3"), Action(SHIELD, "2"))),

            MonsterStatsBd(savvasIcestormId, 6, false, 16, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "5"), Action(PIERCE, "3"), Action(SHIELD, "2"))),
            MonsterStatsBd(savvasIcestormId, 6, true, 23, listOf(Action(MOVE, "4"), Action(ATTACK, "6"), Action(RANGE, "6"), Action(PIERCE, "3"), Action(SHIELD, "2"))),

            MonsterStatsBd(savvasIcestormId, 7, false, 17, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(RANGE, "6"), Action(PIERCE, "3"), Action(SHIELD, "2"))),
            MonsterStatsBd(savvasIcestormId, 7, true, 24, listOf(Action(MOVE, "4"), Action(ATTACK, "6"), Action(RANGE, "6"), Action(PIERCE, "3"), Action(SHIELD, "3"))),
        )

        val harrowerInfesterId = monsterDao.insertMonster(
            MonsterBd(
                name = "Жнец заразитель",
                deckName = "harrower-infester",
                isBoss = false,
                fly = true,
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(harrowerInfesterId, 0, false, 6, listOf(Action(MOVE, "2"), Action(ATTACK, "2"))),
            MonsterStatsBd(harrowerInfesterId, 0, true, 12, listOf(Action(MOVE, "2"), Action(ATTACK, "2"))),

            MonsterStatsBd(harrowerInfesterId, 1, false, 7, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(RETALIATE, "1"))),
            MonsterStatsBd(harrowerInfesterId, 1, true, 12, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(RETALIATE, "2"))),

            MonsterStatsBd(harrowerInfesterId, 2, false, 8, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(RETALIATE, "2"))),
            MonsterStatsBd(harrowerInfesterId, 2, true, 14, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(RETALIATE, "2"))),

            MonsterStatsBd(harrowerInfesterId, 3, false, 10, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(RETALIATE, "2"))),
            MonsterStatsBd(harrowerInfesterId, 3, true, 17, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RETALIATE, "3"))),

            MonsterStatsBd(harrowerInfesterId, 4, false, 12, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RETALIATE, "2"))),
            MonsterStatsBd(harrowerInfesterId, 4, true, 19, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RETALIATE, "3"))),

            MonsterStatsBd(harrowerInfesterId, 5, false, 12, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RETALIATE, "3"))),
            MonsterStatsBd(harrowerInfesterId, 5, true, 21, listOf(Action(MOVE, "3"), Action(ATTACK, "5"), Action(RETALIATE, "3"))),

            MonsterStatsBd(harrowerInfesterId, 6, false, 15, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RETALIATE, "3"))),
            MonsterStatsBd(harrowerInfesterId, 6, true, 22, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(RETALIATE, "4"))),

            MonsterStatsBd(harrowerInfesterId, 7, false, 17, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RETALIATE, "4"))),
            MonsterStatsBd(harrowerInfesterId, 7, true, 26, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(RETALIATE, "4"))),
        )

        val vermlingShamanId = monsterDao.insertMonster(
            MonsterBd(
                name = "Вермлинг-шаман",
                deckName = "shaman",
                isBoss = false,
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(vermlingShamanId, 0, false, 2, listOf(Action(MOVE, "2"), Action(ATTACK, "1"), Action(RANGE, "3"), Action(SHIELD, "2"))),
            MonsterStatsBd(vermlingShamanId, 0, true, 3, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(RANGE, "3"), Action(SHIELD, "2"))),

            MonsterStatsBd(vermlingShamanId, 1, false, 2, listOf(Action(MOVE, "2"), Action(ATTACK, "1"), Action(RANGE, "3"), Action(SHIELD, "3"))),
            MonsterStatsBd(vermlingShamanId, 1, true, 3, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(RANGE, "3"), Action(SHIELD, "3"))),

            MonsterStatsBd(vermlingShamanId, 2, false, 3, listOf(Action(MOVE, "2"), Action(ATTACK, "1"), Action(RANGE, "4"), Action(SHIELD, "3"))),
            MonsterStatsBd(vermlingShamanId, 2, true, 4, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(RANGE, "4"), Action(SHIELD, "3"))),

            MonsterStatsBd(vermlingShamanId, 3, false, 3, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(RANGE, "4"), Action(SHIELD, "3"))),
            MonsterStatsBd(vermlingShamanId, 3, true, 5, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(SHIELD, "3"))),

            MonsterStatsBd(vermlingShamanId, 4, false, 3, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(RANGE, "4"), Action(SHIELD, "3"), Action(MUDDLE, ""))),
            MonsterStatsBd(vermlingShamanId, 4, true, 5, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(SHIELD, "4"), Action(MUDDLE, ""))),

            MonsterStatsBd(vermlingShamanId, 5, false, 4, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(SHIELD, "3"), Action(MUDDLE, ""))),
            MonsterStatsBd(vermlingShamanId, 5, true, 6, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "4"), Action(MUDDLE, ""))),

            MonsterStatsBd(vermlingShamanId, 6, false, 5, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "3"), Action(MUDDLE, ""))),
            MonsterStatsBd(vermlingShamanId, 6, true, 6, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "5"), Action(MUDDLE, ""))),

            MonsterStatsBd(vermlingShamanId, 7, false, 7, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "3"), Action(MUDDLE, ""))),
            MonsterStatsBd(vermlingShamanId, 7, true, 8, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "5"), Action(MUDDLE, ""))),
        )

        val giantViperId = monsterDao.insertMonster(
            MonsterBd(
                name = "Гигантская гадюка",
                deckName = "giant-viper",
                isBoss = false,
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(giantViperId, 0, false, 2, listOf(Action(MOVE, "2"), Action(ATTACK, "1"), Action(POISON, ""))),
            MonsterStatsBd(giantViperId, 0, true, 3, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(POISON, ""))),

            MonsterStatsBd(giantViperId, 1, false, 3, listOf(Action(MOVE, "2"), Action(ATTACK, "1"), Action(POISON, ""))),
            MonsterStatsBd(giantViperId, 1, true, 5, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(POISON, ""))),

            MonsterStatsBd(giantViperId, 2, false, 4, listOf(Action(MOVE, "3"), Action(ATTACK, "1"), Action(POISON, ""))),
            MonsterStatsBd(giantViperId, 2, true, 7, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(POISON, ""))),

            MonsterStatsBd(giantViperId, 3, false, 4, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(POISON, ""))),
            MonsterStatsBd(giantViperId, 3, true, 8, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(POISON, ""))),

            MonsterStatsBd(giantViperId, 4, false, 6, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(POISON, ""))),
            MonsterStatsBd(giantViperId, 4, true, 11, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(POISON, ""))),

            MonsterStatsBd(giantViperId, 5, false, 7, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(POISON, ""))),
            MonsterStatsBd(giantViperId, 5, true, 13, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Action(POISON, ""))),

            MonsterStatsBd(giantViperId, 6, false, 8, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Action(POISON, ""))),
            MonsterStatsBd(giantViperId, 6, true, 14, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(POISON, ""))),

            MonsterStatsBd(giantViperId, 7, false, 10, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Action(POISON, ""))),
            MonsterStatsBd(giantViperId, 7, true, 17, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(POISON, ""))),
        )

        val oozeId = monsterDao.insertMonster(
            MonsterBd(
                name = "Слизь",
                deckName = "ooze",
                isBoss = false,
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(oozeId, 0, false, 4, listOf(Action(MOVE, "1"), Action(ATTACK, "2"), Action(RANGE, "2"))),
            MonsterStatsBd(oozeId, 0, true, 8, listOf(Action(MOVE, "1"), Action(ATTACK, "2"), Action(RANGE, "3"))),

            MonsterStatsBd(oozeId, 1, false, 5, listOf(Action(MOVE, "1"), Action(ATTACK, "2"), Action(RANGE, "2"), Action(SHIELD, "1"))),
            MonsterStatsBd(oozeId, 1, true, 9, listOf(Action(MOVE, "1"), Action(ATTACK, "2"), Action(RANGE, "3"), Action(SHIELD, "1"))),

            MonsterStatsBd(oozeId, 2, false, 7, listOf(Action(MOVE, "1"), Action(ATTACK, "2"), Action(RANGE, "3"), Action(SHIELD, "1"))),
            MonsterStatsBd(oozeId, 2, true, 11, listOf(Action(MOVE, "1"), Action(ATTACK, "3"), Action(RANGE, "3"), Action(SHIELD, "1"))),

            MonsterStatsBd(oozeId, 3, false, 8, listOf(Action(MOVE, "1"), Action(ATTACK, "3"), Action(RANGE, "3"), Action(SHIELD, "1"))),
            MonsterStatsBd(oozeId, 3, true, 11, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(SHIELD, "1"), Action(POISON, ""))),

            MonsterStatsBd(oozeId, 4, false, 9, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(RANGE, "3"), Action(SHIELD, "1"))),
            MonsterStatsBd(oozeId, 4, true, 13, listOf(Action(MOVE, "2"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "1"), Action(POISON, ""))),

            MonsterStatsBd(oozeId, 5, false, 10, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(RANGE, "3"), Action(SHIELD, "1"), Action(POISON, ""))),
            MonsterStatsBd(oozeId, 5, true, 15, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "1"), Action(POISON, ""))),

            MonsterStatsBd(oozeId, 6, false, 12, listOf(Action(MOVE, "2"), Action(ATTACK, "4"), Action(RANGE, "3"), Action(SHIELD, "1"), Action(POISON, ""))),
            MonsterStatsBd(oozeId, 6, true, 16, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(RANGE, "4"), Action(SHIELD, "2"), Action(POISON, ""))),

            MonsterStatsBd(oozeId, 7, false, 14, listOf(Action(MOVE, "2"), Action(ATTACK, "4"), Action(RANGE, "3"), Action(SHIELD, "1"), Action(POISON, ""))),
            MonsterStatsBd(oozeId, 7, true, 18, listOf(Action(MOVE, "3"), Action(ATTACK, "5"), Action(RANGE, "4"), Action(SHIELD, "2"), Action(POISON, ""))),
        )

        val archdemonId = monsterDao.insertMonster(
            MonsterBd(
                name = "Архидемон",
                deckName = "boss",
                isBoss = true,
                immunity = listOf(STUN, IMMOBILIZE, DISARM, MUDDLE, POISON, WOUND)
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(archdemonId, 0, false, 8, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Text("Способность 1: Алтарь двигается, призывает демона, движение +2, атака -1"), Text("Способность 2: Алтарь двигается, призывает демона, движение +2, атака -1"))),

            MonsterStatsBd(archdemonId, 1, false, 9, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Text("Способность 1: Алтарь двигается, призывает демона, движение +2, атака -1"), Text("Способность 2: Алтарь двигается, призывает демона, движение +2, атака -1"))),

            MonsterStatsBd(archdemonId, 2, false, 10, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Text("Способность 1: Алтарь двигается, призывает демона, движение +2, атака -1"), Text("Способность 2: Алтарь двигается, призывает демона, движение +2, атака -1"))),

            MonsterStatsBd(archdemonId, 3, false, 12, listOf(Action(MOVE, "4"), Action(ATTACK, "6"), Text("Способность 1: Алтарь двигается, призывает демона, движение +2, атака -1"), Text("Способность 2: Алтарь двигается, призывает демона, движение +2, атака -1"))),

            MonsterStatsBd(archdemonId, 4, false, 14, listOf(Action(MOVE, "5"), Action(ATTACK, "6"), Text("Способность 1: Алтарь двигается, призывает демона, движение +2, атака -1"), Text("Способность 2: Алтарь двигается, призывает демона, движение +2, атака -1"))),

            MonsterStatsBd(archdemonId, 5, false, 16, listOf(Action(MOVE, "5"), Action(ATTACK, "7"), Text("Способность 1: Алтарь двигается, призывает демона, движение +2, атака -1"), Text("Способность 2: Алтарь двигается, призывает демона, движение +2, атака -1"))),

            MonsterStatsBd(archdemonId, 6, false, 20, listOf(Action(MOVE, "5"), Action(ATTACK, "7"), Text("Способность 1: Алтарь двигается, призывает демона, движение +2, атака -1"), Text("Способность 2: Алтарь двигается, призывает демона, движение +2, атака -1"))),

            MonsterStatsBd(archdemonId, 7, false, 22, listOf(Action(MOVE, "5"), Action(ATTACK, "8"), Text("Способность 1: Алтарь двигается, призывает демона, движение +2, атака -1"), Text("Способность 2: Алтарь двигается, призывает демона, движение +2, атака -1"))),
        )

        val ancientArtilleryId = monsterDao.insertMonster(
            MonsterBd(
                name = "Древняя пушка",
                deckName = "ancient-artillery",
                isBoss = false
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(ancientArtilleryId, 0, false, 4, listOf(Action(ATTACK, "2"), Action(RANGE, "4"))),
            MonsterStatsBd(ancientArtilleryId, 0, true, 7, listOf(Action(ATTACK, "3"), Action(RANGE, "5"))),

            MonsterStatsBd(ancientArtilleryId, 1, false, 6, listOf(Action(ATTACK, "2"), Action(RANGE, "4"))),
            MonsterStatsBd(ancientArtilleryId, 1, true, 9, listOf(Action(ATTACK, "3"), Action(RANGE, "5"))),

            MonsterStatsBd(ancientArtilleryId, 2, false, 7, listOf(Action(ATTACK, "2"), Action(RANGE, "5"))),
            MonsterStatsBd(ancientArtilleryId, 2, true, 11, listOf(Action(ATTACK, "3"), Action(RANGE, "6"))),

            MonsterStatsBd(ancientArtilleryId, 3, false, 8, listOf(Action(ATTACK, "3"), Action(RANGE, "5"))),
            MonsterStatsBd(ancientArtilleryId, 3, true, 13, listOf(Action(ATTACK, "4"), Action(RANGE, "6"))),

            MonsterStatsBd(ancientArtilleryId, 4, false, 9, listOf(Action(ATTACK, "4"), Action(RANGE, "5"))),
            MonsterStatsBd(ancientArtilleryId, 4, true, 13, listOf(Action(ATTACK, "4"), Action(RANGE, "6"), Action(TARGET, "2"))),

            MonsterStatsBd(ancientArtilleryId, 5, false, 11, listOf(Action(ATTACK, "4"), Action(RANGE, "6"))),
            MonsterStatsBd(ancientArtilleryId, 5, true, 15, listOf(Action(ATTACK, "4"), Action(RANGE, "7"), Action(TARGET, "2"))),

            MonsterStatsBd(ancientArtilleryId, 6, false, 14, listOf(Action(ATTACK, "4"), Action(RANGE, "6"))),
            MonsterStatsBd(ancientArtilleryId, 6, true, 16, listOf(Action(ATTACK, "5"), Action(RANGE, "7"), Action(TARGET, "2"))),

            MonsterStatsBd(ancientArtilleryId, 7, false, 16, listOf(Action(ATTACK, "4"), Action(RANGE, "7"))),
            MonsterStatsBd(ancientArtilleryId, 7, true, 20, listOf(Action(ATTACK, "5"), Action(RANGE, "7"), Action(TARGET, "2"))),
        )

        val rendingDrakeId = monsterDao.insertMonster(
            MonsterBd(
                name = "Когтистый дрейк",
                deckName = "rending-drake",
                isBoss = false,
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(rendingDrakeId, 0, false, 5, listOf(Action(MOVE, "3"), Action(ATTACK, "3"))),
            MonsterStatsBd(rendingDrakeId, 0, true, 7, listOf(Action(MOVE, "4"), Action(ATTACK, "4"))),

            MonsterStatsBd(rendingDrakeId, 1, false, 6, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(WOUND, ""))),
            MonsterStatsBd(rendingDrakeId, 1, true, 7, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(WOUND, ""))),

            MonsterStatsBd(rendingDrakeId, 2, false, 7, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Action(WOUND, ""))),
            MonsterStatsBd(rendingDrakeId, 2, true, 9, listOf(Action(MOVE, "5"), Action(ATTACK, "5"), Action(WOUND, ""))),

            MonsterStatsBd(rendingDrakeId, 3, false, 7, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(WOUND, ""))),
            MonsterStatsBd(rendingDrakeId, 3, true, 10, listOf(Action(MOVE, "5"), Action(ATTACK, "6"), Action(WOUND, ""))),

            MonsterStatsBd(rendingDrakeId, 4, false, 9, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(WOUND, ""))),
            MonsterStatsBd(rendingDrakeId, 4, true, 11, listOf(Action(MOVE, "6"), Action(ATTACK, "6"), Action(WOUND, ""))),

            MonsterStatsBd(rendingDrakeId, 5, false, 10, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(WOUND, ""))),
            MonsterStatsBd(rendingDrakeId, 5, true, 14, listOf(Action(MOVE, "6"), Action(ATTACK, "6"), Action(WOUND, ""))),

            MonsterStatsBd(rendingDrakeId, 6, false, 11, listOf(Action(MOVE, "5"), Action(ATTACK, "5"), Action(WOUND, ""))),
            MonsterStatsBd(rendingDrakeId, 6, true, 15, listOf(Action(MOVE, "6"), Action(ATTACK, "7"), Action(WOUND, ""))),

            MonsterStatsBd(rendingDrakeId, 7, false, 14, listOf(Action(MOVE, "5"), Action(ATTACK, "5"), Action(WOUND, ""))),
            MonsterStatsBd(rendingDrakeId, 7, true, 18, listOf(Action(MOVE, "6"), Action(ATTACK, "7"), Action(WOUND, ""))),
        )

        val blackImpId = monsterDao.insertMonster(
            MonsterBd(
                name = "Черный бес",
                deckName = "imp",
                isBoss = false,
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(blackImpId, 0, false, 3, listOf(Action(MOVE, "1"), Action(ATTACK, "1"), Action(RANGE, "3"))),
            MonsterStatsBd(blackImpId, 0, true, 4, listOf(Action(MOVE, "1"), Action(ATTACK, "2"), Action(RANGE, "3"), Action(POISON, ""))),

            MonsterStatsBd(blackImpId, 1, false, 4, listOf(Action(MOVE, "1"), Action(ATTACK, "1"), Action(RANGE, "3"), Action(POISON, ""))),
            MonsterStatsBd(blackImpId, 1, true, 6, listOf(Action(MOVE, "1"), Action(ATTACK, "2"), Action(RANGE, "3"), Action(POISON, ""))),

            MonsterStatsBd(blackImpId, 2, false, 5, listOf(Action(MOVE, "1"), Action(ATTACK, "1"), Action(RANGE, "4"), Action(POISON, ""))),
            MonsterStatsBd(blackImpId, 2, true, 8, listOf(Action(MOVE, "1"), Action(ATTACK, "2"), Action(RANGE, "4"), Action(POISON, ""))),

            MonsterStatsBd(blackImpId, 3, false, 5, listOf(Action(MOVE, "1"), Action(ATTACK, "2"), Action(RANGE, "4"), Action(POISON, ""))),
            MonsterStatsBd(blackImpId, 3, true, 8, listOf(Action(MOVE, "1"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(POISON, ""), Text("Атаки по бесу проходят с помехой"))),

            MonsterStatsBd(blackImpId, 4, false, 7, listOf(Action(MOVE, "1"), Action(ATTACK, "2"), Action(RANGE, "4"), Action(POISON, ""))),
            MonsterStatsBd(blackImpId, 4, true, 11, listOf(Action(MOVE, "1"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(POISON, ""), Text("Атаки по бесу проходят с помехой"))),

            MonsterStatsBd(blackImpId, 5, false, 9, listOf(Action(MOVE, "1"), Action(ATTACK, "2"), Action(RANGE, "4"), Action(POISON, ""))),
            MonsterStatsBd(blackImpId, 5, true, 12, listOf(Action(MOVE, "1"), Action(ATTACK, "3"), Action(RANGE, "5"), Action(POISON, ""), Text("Атаки по бесу проходят с помехой"))),

            MonsterStatsBd(blackImpId, 6, false, 10, listOf(Action(MOVE, "1"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(POISON, ""))),
            MonsterStatsBd(blackImpId, 6, true, 14, listOf(Action(MOVE, "1"), Action(ATTACK, "4"), Action(RANGE, "5"), Action(POISON, ""), Text("Атаки по бесу проходят с помехой"))),

            MonsterStatsBd(blackImpId, 7, false, 12, listOf(Action(MOVE, "1"), Action(ATTACK, "3"), Action(RANGE, "4"), Action(POISON, ""))),
            MonsterStatsBd(blackImpId, 7, true, 17, listOf(Action(MOVE, "1"), Action(ATTACK, "4"), Action(RANGE, "5"), Action(POISON, ""), Text("Атаки по бесу проходят с помехой"))),
        )

        val deepTerrorId = monsterDao.insertMonster(
            MonsterBd(
                name = "Невыносимый ужас",
                deckName = "deep-terror",
                isBoss = false,
                immunity = listOf(STUN, DISARM, IMMOBILIZE, MUDDLE)
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(deepTerrorId, 0, false, 3, listOf(Action(ATTACK, "2"))),
            MonsterStatsBd(deepTerrorId, 0, true, 5, listOf(Action(ATTACK, "3"))),

            MonsterStatsBd(deepTerrorId, 1, false, 4, listOf(Action(ATTACK, "2"), Action(RETALIATE, "1"))),
            MonsterStatsBd(deepTerrorId, 1, true, 6, listOf(Action(ATTACK, "3"), Action(RETALIATE, "1"))),

            MonsterStatsBd(deepTerrorId, 2, false, 4, listOf(Action(ATTACK, "3"), Action(RETALIATE, "1"))),
            MonsterStatsBd(deepTerrorId, 2, true, 7, listOf(Action(ATTACK, "4"), Action(RETALIATE, "1"))),

            MonsterStatsBd(deepTerrorId, 3, false, 5, listOf(Action(ATTACK, "3"), Action(RETALIATE, "2"))),
            MonsterStatsBd(deepTerrorId, 3, true, 8, listOf(Action(ATTACK, "4"), Action(RETALIATE, "2"))),

            MonsterStatsBd(deepTerrorId, 4, false, 6, listOf(Action(ATTACK, "4"), Action(RETALIATE, "2"))),
            MonsterStatsBd(deepTerrorId, 4, true, 9, listOf(Action(ATTACK, "5"), Action(RETALIATE, "2"))),

            MonsterStatsBd(deepTerrorId, 5, false, 7, listOf(Action(ATTACK, "4"), Action(RETALIATE, "3"))),
            MonsterStatsBd(deepTerrorId, 5, true, 11, listOf(Action(ATTACK, "5"), Action(RETALIATE, "3"))),

            MonsterStatsBd(deepTerrorId, 6, false, 8, listOf(Action(ATTACK, "5"), Action(RETALIATE, "3"))),
            MonsterStatsBd(deepTerrorId, 6, true, 13, listOf(Action(ATTACK, "6"), Action(RETALIATE, "3"))),

            MonsterStatsBd(deepTerrorId, 7, false, 9, listOf(Action(ATTACK, "5"), Action(RETALIATE, "4"))),
            MonsterStatsBd(deepTerrorId, 7, true, 15, listOf(Action(ATTACK, "6"), Action(RETALIATE, "4"))),
        )

        val lurkerId = monsterDao.insertMonster(
            MonsterBd(
                name = "Скрытень",
                deckName = "lurker",
                isBoss = false,
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(lurkerId, 0, false, 5, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(TARGET, "2"))),
            MonsterStatsBd(lurkerId, 0, true, 7, listOf(Action(MOVE, "2"), Action(ATTACK, "3"), Action(TARGET, "2"), Action(SHIELD, "1"))),

            MonsterStatsBd(lurkerId, 1, false, 7, listOf(Action(MOVE, "2"), Action(ATTACK, "2"), Action(TARGET, "2"), Action(PIERCE, "1"))),
            MonsterStatsBd(lurkerId, 1, true, 9, listOf(Action(MOVE, "2"), Action(ATTACK, "3"),  Action(TARGET, "2"), Action(PIERCE, "1"), Action(SHIELD, "1"))),

            MonsterStatsBd(lurkerId, 2, false, 9, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(TARGET, "2"), Action(PIERCE, "1"))),
            MonsterStatsBd(lurkerId, 2, true, 12, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(TARGET, "2"), Action(PIERCE, "2"), Action(SHIELD, "1"))),

            MonsterStatsBd(lurkerId, 3, false, 10, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(TARGET, "2"), Action(PIERCE, "2"))),
            MonsterStatsBd(lurkerId, 3, true, 14, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(TARGET, "2"), Action(PIERCE, "2"), Action(SHIELD, "1"))),

            MonsterStatsBd(lurkerId, 4, false, 10, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(TARGET, "2"), Action(PIERCE, "2"), Action(SHIELD, "1"))),
            MonsterStatsBd(lurkerId, 4, true, 14, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(TARGET, "2"), Action(PIERCE, "3"), Action(SHIELD, "2"))),

            MonsterStatsBd(lurkerId, 5, false, 11, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(TARGET, "2"), Action(PIERCE, "2"), Action(SHIELD, "1"))),
            MonsterStatsBd(lurkerId, 5, true, 15, listOf(Action(MOVE, "3"), Action(ATTACK, "5"), Action(TARGET, "2"), Action(PIERCE, "3"), Action(SHIELD, "2"))),

            MonsterStatsBd(lurkerId, 6, false, 12, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(TARGET, "2"), Action(PIERCE, "3"), Action(SHIELD, "1"))),
            MonsterStatsBd(lurkerId, 6, true, 16, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(TARGET, "2"), Action(PIERCE, "4"), Action(SHIELD, "2"))),

            MonsterStatsBd(lurkerId, 7, false, 14, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(TARGET, "2"), Action(PIERCE, "3"), Action(SHIELD, "1"))),
            MonsterStatsBd(lurkerId, 7, true, 18, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(TARGET, "2"), Action(PIERCE, "4"), Action(SHIELD, "2"))),
        )

        val savvasLavaflowId = monsterDao.insertMonster(
            MonsterBd(
                name = "Саввас Поток лавы",
                deckName = "savvas-lavaflow",
                isBoss = false,
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(savvasLavaflowId, 0, false, 8, listOf(Action(MOVE, "3"), Action(ATTACK, "2"))),
            MonsterStatsBd(savvasLavaflowId, 0, true, 13, listOf(Action(MOVE, "3"), Action(ATTACK, "3"))),

            MonsterStatsBd(savvasLavaflowId, 1, false, 9, listOf(Action(MOVE, "3"), Action(ATTACK, "2"), Action(POISON, ""))),
            MonsterStatsBd(savvasLavaflowId, 1, true, 15, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(WOUND, ""))),

            MonsterStatsBd(savvasLavaflowId, 2, false, 11, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(POISON, ""))),
            MonsterStatsBd(savvasLavaflowId, 2, true, 18, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(POISON, ""), Action(WOUND, ""))),

            MonsterStatsBd(savvasLavaflowId, 3, false, 14, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Action(POISON, ""))),
            MonsterStatsBd(savvasLavaflowId, 3, true, 21, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(POISON, ""), Action(WOUND, ""))),

            MonsterStatsBd(savvasLavaflowId, 4, false, 16, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(POISON, ""))),
            MonsterStatsBd(savvasLavaflowId, 4, true, 24, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(POISON, ""), Action(WOUND, ""))),

            MonsterStatsBd(savvasLavaflowId, 5, false, 18, listOf(Action(MOVE, "3"), Action(ATTACK, "4"), Action(POISON, ""), Action(WOUND, ""))),
            MonsterStatsBd(savvasLavaflowId, 5, true, 27, listOf(Action(MOVE, "4"), Action(ATTACK, "5"), Action(POISON, ""), Action(WOUND, ""))),

            MonsterStatsBd(savvasLavaflowId, 6, false, 20, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(POISON, ""), Action(WOUND, ""))),
            MonsterStatsBd(savvasLavaflowId, 6, true, 30, listOf(Action(MOVE, "4"), Action(ATTACK, "6"), Action(POISON, ""), Action(WOUND, ""))),

            MonsterStatsBd(savvasLavaflowId, 7, false, 24, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Action(POISON, ""), Action(WOUND, ""))),
            MonsterStatsBd(savvasLavaflowId, 7, true, 35, listOf(Action(MOVE, "4"), Action(ATTACK, "6"), Action(POISON, ""), Action(WOUND, ""))),
        )

        val drakePatriarchId = monsterDao.insertMonster(
            MonsterBd(
                name = "Дрейк-патриарх",
                deckName = "boss",
                isBoss = true,
                immunity = listOf(STUN, IMMOBILIZE, DISARM, PUSH, PULL, POISON, WOUND)
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(drakePatriarchId, 0, false, 11, listOf(Action(ATTACK, "3"), Text("Способность 1: Атака +0 #38"), Text("Способность 2: Двишается и призывает 2 зефира"))),

            MonsterStatsBd(drakePatriarchId, 1, false, 12, listOf(Action(ATTACK, "4"), Text("Способность 1: Атака +0 #38"), Text("Способность 2: Двишается и призывает 2 зефира"))),

            MonsterStatsBd(drakePatriarchId, 2, false, 15, listOf(Action(ATTACK, "4"), Text("Способность 1: Атака +0 #38"), Text("Способность 2: Двишается и призывает 2 зефира"))),

            MonsterStatsBd(drakePatriarchId, 3, false, 16, listOf( Action(ATTACK, "5"), Text("Способность 1: Атака +0 #38"), Text("Способность 2: Двишается и призывает 2 зефира"))),

            MonsterStatsBd(drakePatriarchId, 4, false, 20, listOf(Action(ATTACK, "5"), Text("Способность 1: Атака +0 #38"), Text("Способность 2: Двишается и призывает 2 зефира"))),

            MonsterStatsBd(drakePatriarchId, 5, false, 22, listOf(Action(ATTACK, "6"), Text("Способность 1: Атака +0 #38"), Text("Способность 2: Двишается и призывает 2 зефира"))),

            MonsterStatsBd(drakePatriarchId, 6, false, 27, listOf(Action(ATTACK, "6"), Text("Способность 1: Атака +0 #38"), Text("Способность 2: Двишается и призывает 2 зефира"))),

            MonsterStatsBd(drakePatriarchId, 7, false, 29, listOf(Action(ATTACK, "7"), Text("Способность 1: Атака +0 #38"), Text("Способность 2: Двишается и призывает 2 зефира"))),
        )

        val wingedHorrorId = monsterDao.insertMonster(
            MonsterBd(
                name = "Крылатый ужас",
                deckName = "boss",
                isBoss = true,
                immunity = listOf(STUN, DISARM, CURSE, MUDDLE, POISON)
            )
        ).toInt()
        monsterDao.insertAllStats(
            MonsterStatsBd(wingedHorrorId, 0, false, 6, listOf(Action(MOVE, "3"), Action(ATTACK, "3"), Text("Способность 1: Атакует -1 все рядомстоящие враги, Атакует +0 дальность 3, Вылупляются яйца"), Text("Способность 2: Призывает КП яиц, двигается -1, атакует +0"))),

            MonsterStatsBd(wingedHorrorId, 1, false, 7, listOf(Action(MOVE, "4"), Action(ATTACK, "3"), Text("Способность 1: Атакует -1 все рядомстоящие враги, Атакует +0 дальность 3, Вылупляются яйца"), Text("Способность 2: Призывает КП яиц, двигается -1, атакует +0"))),

            MonsterStatsBd(wingedHorrorId, 2, false, 8, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Text("Способность 1: Атакует -1 все рядомстоящие враги, Атакует +0 дальность 3, Вылупляются яйца"), Text("Способность 2: Призывает КП яиц, двигается -1, атакует +0"))),

            MonsterStatsBd(wingedHorrorId, 3, false, 10, listOf(Action(MOVE, "4"), Action(ATTACK, "4"), Text("Способность 1: Атакует -1 все рядомстоящие враги, Атакует +0 дальность 3, Вылупляются яйца"), Text("Способность 2: Призывает КП яиц, двигается -1, атакует +0"))),

            MonsterStatsBd(wingedHorrorId, 4, false, 12, listOf(Action(MOVE, "5"), Action(ATTACK, "4"), Text("Способность 1: Атакует -1 все рядомстоящие враги, Атакует +0 дальность 3, Вылупляются яйца"), Text("Способность 2: Призывает КП яиц, двигается -1, атакует +0"))),

            MonsterStatsBd(wingedHorrorId, 5, false, 14, listOf(Action(MOVE, "5"), Action(ATTACK, "5"), Text("Способность 1: Атакует -1 все рядомстоящие враги, Атакует +0 дальность 3, Вылупляются яйца"), Text("Способность 2: Призывает КП яиц, двигается -1, атакует +0"))),

            MonsterStatsBd(wingedHorrorId, 6, false, 17, listOf(Action(MOVE, "5"), Action(ATTACK, "5"), Text("Способность 1: Атакует -1 все рядомстоящие враги, Атакует +0 дальность 3, Вылупляются яйца"), Text("Способность 2: Призывает КП яиц, двигается -1, атакует +0"))),

            MonsterStatsBd(wingedHorrorId, 7, false, 20, listOf(Action(MOVE, "5"), Action(ATTACK, "5"), Text("Способность 1: Атакует -1 все рядомстоящие враги, Атакует +0 дальность 3, Вылупляются яйца"), Text("Способность 2: Призывает КП яиц, двигается -1, атакует +0"))),
        )

        val sightlessEyeId = monsterDao.insertMonster(
            MonsterBd(
                name = "Невидящее око",
                deckName = "boss",
                isBoss = true,
                immunity = listOf(STUN, PUSH, PULL, DISARM, CURSE, MUDDLE)
            )
        ).toInt()
        monsterDao.run {
            insertAllStats(
                MonsterStatsBd(sightlessEyeId, 0, false, 7, listOf( Action(ATTACK, "5"), Action(RANGE, "3"), Text("Способность 1: Призывает Невыносимый ужас, атакует -3 #39"), Text("Способность 2: Призывает Невыносимый ужас, атакует -2 #40"))),

                MonsterStatsBd(sightlessEyeId, 1, false, 8, listOf(Action(ATTACK, "6"), Action(RANGE, "3"),Text("Способность 1: Призывает Невыносимый ужас, атакует -3 #39"), Text("Способность 2: Призывает Невыносимый ужас, атакует -2 #40"))),

                MonsterStatsBd(sightlessEyeId, 2, false, 10, listOf(Action(ATTACK, "6"),Action(RANGE, "3"), Text("Способность 1: Призывает Невыносимый ужас, атакует -3 #39"), Text("Способность 2: Призывает Невыносимый ужас, атакует -2 #40"))),

                MonsterStatsBd(sightlessEyeId, 3, false, 11, listOf(Action(ATTACK, "7"),Action(RANGE, "3"), Text("Способность 1: Призывает Невыносимый ужас, атакует -3 #39"), Text("Способность 2: Призывает Невыносимый ужас, атакует -2 #40"))),

                MonsterStatsBd(sightlessEyeId, 4, false, 14, listOf(Action(ATTACK, "7"), Action(RANGE, "3"),Text("Способность 1: Призывает Невыносимый ужас, атакует -3 #39"), Text("Способность 2: Призывает Невыносимый ужас, атакует -2 #40"))),

                MonsterStatsBd(sightlessEyeId, 5, false, 25, listOf(Action(ATTACK, "8"),Action(RANGE, "3"), Text("Способность 1: Призывает Невыносимый ужас, атакует -3 #39"), Text("Способность 2: Призывает Невыносимый ужас, атакует -2 #40"))),

                MonsterStatsBd(sightlessEyeId, 6, false, 18, listOf(Action(ATTACK, "8"),Action(RANGE, "3"), Text("Способность 1: Призывает Невыносимый ужас, атакует -3 #39"), Text("Способность 2: Призывает Невыносимый ужас, атакует -2 #40"))),

                MonsterStatsBd(sightlessEyeId, 7, false, 20, listOf(Action(ATTACK, "9"), Action(RANGE, "3"),Text("Способность 1: Призывает Невыносимый ужас, атакует -3 #39"), Text("Способность 2: Призывает Невыносимый ужас, атакует -2 #40"))),
            )
        }
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

        // stone-golem deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "stone-golem", imageName = "ic_deck_ma_sg_1.webp"),
            MonsterAbilityCardBd(deckName = "stone-golem", imageName = "ic_deck_ma_sg_2.webp"),
            MonsterAbilityCardBd(deckName = "stone-golem", imageName = "ic_deck_ma_sg_3.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "stone-golem", imageName = "ic_deck_ma_sg_4.webp"),
            MonsterAbilityCardBd(deckName = "stone-golem", imageName = "ic_deck_ma_sg_5.webp"),
            MonsterAbilityCardBd(deckName = "stone-golem", imageName = "ic_deck_ma_sg_6.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "stone-golem", imageName = "ic_deck_ma_sg_7.webp"),
            MonsterAbilityCardBd(deckName = "stone-golem", imageName = "ic_deck_ma_sg_8.webp"),
        )

        // spitting-drake deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "spitting-drake", imageName = "ic_deck_ma_spd_1.webp"),
            MonsterAbilityCardBd(deckName = "spitting-drake", imageName = "ic_deck_ma_spd_2.webp"),
            MonsterAbilityCardBd(deckName = "spitting-drake", imageName = "ic_deck_ma_spd_3.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "spitting-drake", imageName = "ic_deck_ma_spd_4.webp"),
            MonsterAbilityCardBd(deckName = "spitting-drake", imageName = "ic_deck_ma_spd_5.webp"),
            MonsterAbilityCardBd(deckName = "spitting-drake", imageName = "ic_deck_ma_spd_6.webp"),
            MonsterAbilityCardBd(deckName = "spitting-drake", imageName = "ic_deck_ma_spd_7.webp"),
            MonsterAbilityCardBd(deckName = "spitting-drake", imageName = "ic_deck_ma_spd_8.webp", needsShuffle = true),
        )

        // savvas-icestorm deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "savvas-icestorm", imageName = "ic_deck_ma_si_1.webp"),
            MonsterAbilityCardBd(deckName = "savvas-icestorm", imageName = "ic_deck_ma_si_2.webp"),
            MonsterAbilityCardBd(deckName = "savvas-icestorm", imageName = "ic_deck_ma_si_3.webp"),
            MonsterAbilityCardBd(deckName = "savvas-icestorm", imageName = "ic_deck_ma_si_4.webp"),
            MonsterAbilityCardBd(deckName = "savvas-icestorm", imageName = "ic_deck_ma_si_5.webp"),
            MonsterAbilityCardBd(deckName = "savvas-icestorm", imageName = "ic_deck_ma_si_6.webp"),
            MonsterAbilityCardBd(deckName = "savvas-icestorm", imageName = "ic_deck_ma_si_7.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "savvas-icestorm", imageName = "ic_deck_ma_si_8.webp", needsShuffle = true),
        )

        // harrower-infester deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "harrower-infester", imageName = "ic_deck_ma_hi_1.webp"),
            MonsterAbilityCardBd(deckName = "harrower-infester", imageName = "ic_deck_ma_hi_2.webp"),
            MonsterAbilityCardBd(deckName = "harrower-infester", imageName = "ic_deck_ma_hi_3.webp"),
            MonsterAbilityCardBd(deckName = "harrower-infester", imageName = "ic_deck_ma_hi_4.webp"),
            MonsterAbilityCardBd(deckName = "harrower-infester", imageName = "ic_deck_ma_hi_5.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "harrower-infester", imageName = "ic_deck_ma_hi_6.webp"),
            MonsterAbilityCardBd(deckName = "harrower-infester", imageName = "ic_deck_ma_hi_7.webp"),
            MonsterAbilityCardBd(deckName = "harrower-infester", imageName = "ic_deck_ma_hi_8.webp", needsShuffle = true),
        )

        // giant-viper deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "giant-viper", imageName = "ic_deck_ma_gv_1.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "giant-viper", imageName = "ic_deck_ma_gv_2.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "giant-viper", imageName = "ic_deck_ma_gv_3.webp"),
            MonsterAbilityCardBd(deckName = "giant-viper", imageName = "ic_deck_ma_gv_4.webp"),
            MonsterAbilityCardBd(deckName = "giant-viper", imageName = "ic_deck_ma_gv_5.webp"),
            MonsterAbilityCardBd(deckName = "giant-viper", imageName = "ic_deck_ma_gv_6.webp"),
            MonsterAbilityCardBd(deckName = "giant-viper", imageName = "ic_deck_ma_gv_7.webp"),
            MonsterAbilityCardBd(deckName = "giant-viper", imageName = "ic_deck_ma_gv_8.webp"),
        )

        // ooze deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "ooze", imageName = "ic_deck_ma_oo_1.webp"),
            MonsterAbilityCardBd(deckName = "ooze", imageName = "ic_deck_ma_oo_2.webp"),
            MonsterAbilityCardBd(deckName = "ooze", imageName = "ic_deck_ma_oo_3.webp"),
            MonsterAbilityCardBd(deckName = "ooze", imageName = "ic_deck_ma_oo_4.webp"),
            MonsterAbilityCardBd(deckName = "ooze", imageName = "ic_deck_ma_oo_5.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "ooze", imageName = "ic_deck_ma_oo_6.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "ooze", imageName = "ic_deck_ma_oo_7.webp"),
            MonsterAbilityCardBd(deckName = "ooze", imageName = "ic_deck_ma_oo_8.webp"),
        )

        // ancient-artillery deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "ancient-artillery", imageName = "ic_deck_ma_aa_1.webp"),
            MonsterAbilityCardBd(deckName = "ancient-artillery", imageName = "ic_deck_ma_aa_2.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "ancient-artillery", imageName = "ic_deck_ma_aa_3.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "ancient-artillery", imageName = "ic_deck_ma_aa_4.webp"),
            MonsterAbilityCardBd(deckName = "ancient-artillery", imageName = "ic_deck_ma_aa_5.webp"),
            MonsterAbilityCardBd(deckName = "ancient-artillery", imageName = "ic_deck_ma_aa_6.webp"),
            MonsterAbilityCardBd(deckName = "ancient-artillery", imageName = "ic_deck_ma_aa_7.webp"),
            MonsterAbilityCardBd(deckName = "ancient-artillery", imageName = "ic_deck_ma_aa_8.webp"),
        )

        // rending-drake deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "rending-drake", imageName = "ic_deck_ma_rd_1.webp"),
            MonsterAbilityCardBd(deckName = "rending-drake", imageName = "ic_deck_ma_rd_2.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "rending-drake", imageName = "ic_deck_ma_rd_3.webp"),
            MonsterAbilityCardBd(deckName = "rending-drake", imageName = "ic_deck_ma_rd_4.webp"),
            MonsterAbilityCardBd(deckName = "rending-drake", imageName = "ic_deck_ma_rd_5.webp"),
            MonsterAbilityCardBd(deckName = "rending-drake", imageName = "ic_deck_ma_rd_6.webp"),
            MonsterAbilityCardBd(deckName = "rending-drake", imageName = "ic_deck_ma_rd_7.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "rending-drake", imageName = "ic_deck_ma_rd_8.webp"),
        )

        // deep-terror deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "deep-terror", imageName = "ic_deck_ma_dt_1.webp"),
            MonsterAbilityCardBd(deckName = "deep-terror", imageName = "ic_deck_ma_dt_2.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "deep-terror", imageName = "ic_deck_ma_dt_3.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "deep-terror", imageName = "ic_deck_ma_dt_4.webp"),
            MonsterAbilityCardBd(deckName = "deep-terror", imageName = "ic_deck_ma_dt_5.webp"),
            MonsterAbilityCardBd(deckName = "deep-terror", imageName = "ic_deck_ma_dt_6.webp"),
            MonsterAbilityCardBd(deckName = "deep-terror", imageName = "ic_deck_ma_dt_7.webp"),
            MonsterAbilityCardBd(deckName = "deep-terror", imageName = "ic_deck_ma_dt_8.webp"),
        )

        // lurker deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "lurker", imageName = "ic_deck_ma_lu_1.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "lurker", imageName = "ic_deck_ma_lu_2.webp"),
            MonsterAbilityCardBd(deckName = "lurker", imageName = "ic_deck_ma_lu_3.webp"),
            MonsterAbilityCardBd(deckName = "lurker", imageName = "ic_deck_ma_lu_4.webp"),
            MonsterAbilityCardBd(deckName = "lurker", imageName = "ic_deck_ma_lu_5.webp"),
            MonsterAbilityCardBd(deckName = "lurker", imageName = "ic_deck_ma_lu_6.webp"),
            MonsterAbilityCardBd(deckName = "lurker", imageName = "ic_deck_ma_lu_7.webp"),
            MonsterAbilityCardBd(deckName = "lurker", imageName = "ic_deck_ma_lu_8.webp", needsShuffle = true),
        )

        // savvas-lavaflow deck
        monsterDao.insertCards(
            MonsterAbilityCardBd(deckName = "savvas-lavaflow", imageName = "ic_deck_ma_sl_1.webp"),
            MonsterAbilityCardBd(deckName = "savvas-lavaflow", imageName = "ic_deck_ma_sl_2.webp"),
            MonsterAbilityCardBd(deckName = "savvas-lavaflow", imageName = "ic_deck_ma_sl_3.webp"),
            MonsterAbilityCardBd(deckName = "savvas-lavaflow", imageName = "ic_deck_ma_sl_4.webp", needsShuffle = true),
            MonsterAbilityCardBd(deckName = "savvas-lavaflow", imageName = "ic_deck_ma_sl_5.webp"),
            MonsterAbilityCardBd(deckName = "savvas-lavaflow", imageName = "ic_deck_ma_sl_6.webp"),
            MonsterAbilityCardBd(deckName = "savvas-lavaflow", imageName = "ic_deck_ma_sl_7.webp"),
            MonsterAbilityCardBd(deckName = "savvas-lavaflow", imageName = "ic_deck_ma_sl_8.webp", needsShuffle = true),
        )
    }
}
