package com.rumpilstilstkin.gloomhavenhelper.domain.entity

class LogicalCondition(
    val condition: String,
) {
    val rpnQueue: List<String> = parseToRpn(condition)

    fun evaluate(activeAchievements: Set<String>): Boolean {
        if (rpnQueue.isEmpty()) return true

        val stack = mutableListOf<Boolean>()
        for (token in rpnQueue) {
            when (token) {
                "!" -> {
                    val last = stack.removeAt(stack.size - 1)
                    stack.add(!last)
                }

                "&&" -> {
                    val r = stack.removeAt(stack.size - 1)
                    val l = stack.removeAt(stack.size - 1)
                    stack.add(l && r)
                }

                "||" -> {
                    val r = stack.removeAt(stack.size - 1)
                    val l = stack.removeAt(stack.size - 1)
                    stack.add(l || r)
                }

                else -> {
                    stack.add(token in activeAchievements)
                }
            }
        }
        return stack.firstOrNull() ?: true
    }

    private fun parseToRpn(condition: String): List<String> {
        val tokens =
            TOKEN_PATTERN
                .findAll(condition)
                .map { it.value.trim() }
                .filter { it.isNotEmpty() }
                .toList()

        val output = mutableListOf<String>()
        val operators = mutableListOf<String>()

        for (token in tokens) {
            when (token) {
                "(" -> {
                    operators.add(token)
                }

                ")" -> {
                    while (operators.isNotEmpty() && operators.last() != "(") {
                        output.add(operators.removeAt(operators.size - 1))
                    }
                    if (operators.isNotEmpty()) operators.removeAt(operators.size - 1)
                }

                in PRECEDENCE -> {
                    while (operators.isNotEmpty() && operators.last() != "(" &&
                        PRECEDENCE[operators.last()]!! >= PRECEDENCE[token]!!
                    ) {
                        output.add(operators.removeAt(operators.size - 1))
                    }
                    operators.add(token)
                }

                else -> {
                    output.add(token)
                }
            }
        }
        while (operators.isNotEmpty()) {
            output.add(operators.removeAt(operators.size - 1))
        }
        return output
    }

    fun isNotBlank() = condition.isNotBlank()

    companion object {
        private val PRECEDENCE = mapOf("!" to 3, "&&" to 2, "||" to 1)
        private val TOKEN_PATTERN = Regex("""(\|\||&&|!|\(|\)|[^!&|()]+)""")

        fun createWithDictionary(
            conditions: String,
            dictionary: Map<String, String>,
        ): LogicalCondition {
            val newConditions =
                TOKEN_PATTERN.replace(conditions) { matchResult ->
                    val rawToken = matchResult.value
                    val trimmedKey = rawToken.trim()

                    if (dictionary.containsKey(trimmedKey)) {
                        rawToken.replaceFirst(trimmedKey, dictionary.getValue(trimmedKey))
                    } else {
                        rawToken
                    }
                }
            return LogicalCondition(newConditions)
        }
    }
}
