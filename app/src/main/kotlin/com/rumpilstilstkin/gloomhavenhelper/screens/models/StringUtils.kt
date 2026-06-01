package com.rumpilstilstkin.gloomhavenhelper.screens.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LogicalCondition

@Composable
fun LogicalCondition.toHumanReadable(): String {
    if (rpnQueue.isEmpty()) return stringResource(R.string.no_conditions)

    val stack = mutableListOf<String>()
    val obtainedPrefix = stringResource(R.string.achievement_obtained_format, "").trim()
    val andOp = stringResource(R.string.logical_and)
    val orOp = stringResource(R.string.logical_or)

    for (token in rpnQueue) {
        when (token) {
            "!" -> {
                val operand = stack.removeAt(stack.size - 1)
                if (operand.startsWith(obtainedPrefix)) {
                    val name = operand.removePrefix(obtainedPrefix).trim()
                    stack.add(stringResource(R.string.achievement_not_obtained_format, name))
                } else {
                    stack.add(stringResource(R.string.achievement_not_obtained_format, operand))
                }
            }
            "&&" -> {
                val r = stack.removeAt(stack.size - 1)
                val l = stack.removeAt(stack.size - 1)
                stack.add("($l $andOp $r)")
            }
            "||" -> {
                val r = stack.removeAt(stack.size - 1)
                val l = stack.removeAt(stack.size - 1)
                stack.add("($l $orOp $r)")
            }
            else -> {
                stack.add(stringResource(R.string.achievement_obtained_format, token))
            }
        }
    }

    return stack.firstOrNull()?.removeSurrounding("(", ")") ?: ""
}
