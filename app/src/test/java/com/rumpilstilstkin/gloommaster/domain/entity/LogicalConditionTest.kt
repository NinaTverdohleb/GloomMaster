package com.rumpilstilstkin.gloommaster.domain.entity

import com.rumpilstilstkin.gloommaster.domain.entity.LogicalCondition
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class LogicalConditionTest {
    @Test
    fun `given empty condition when evaluate then returns true`() {
        // Given
        val condition = LogicalCondition("")

        // When
        val result = condition.evaluate(setOf("Any"))

        // Then
        expectThat(result).isTrue()
    }

    @Test
    fun `given blank condition when evaluate then returns true`() {
        // Given
        val condition = LogicalCondition("   ")

        // When
        val result = condition.evaluate(setOf("Any"))

        // Then
        expectThat(result).isTrue()
    }

    @Test
    fun `given single achievement token when evaluate with matching set then returns true`() {
        // Given
        val condition = LogicalCondition("Ancient Technology")

        // When
        val result = condition.evaluate(setOf("Ancient Technology"))

        // Then
        expectThat(result).isTrue()
    }

    @Test
    fun `given single achievement token when evaluate with missing set then returns false`() {
        // Given
        val condition = LogicalCondition("Ancient Technology")

        // When
        val result = condition.evaluate(setOf("Other"))

        // Then
        expectThat(result).isFalse()
    }

    @Test
    fun `given AND with both achievements present when evaluate then returns true`() {
        // Given
        val condition = LogicalCondition("A && B")

        // When
        val result = condition.evaluate(setOf("A", "B"))

        // Then
        expectThat(result).isTrue()
    }

    @Test
    fun `given AND with one missing when evaluate then returns false`() {
        // Given
        val condition = LogicalCondition("A && B")

        // When
        val result = condition.evaluate(setOf("A"))

        // Then
        expectThat(result).isFalse()
    }

    @Test
    fun `given OR with one present when evaluate then returns true`() {
        // Given
        val condition = LogicalCondition("A || B")

        // When / Then
        expectThat(condition.evaluate(setOf("A"))).isTrue()
        expectThat(condition.evaluate(setOf("B"))).isTrue()
    }

    @Test
    fun `given OR with neither present when evaluate then returns false`() {
        // Given
        val condition = LogicalCondition("A || B")

        // When
        val result = condition.evaluate(setOf("C"))

        // Then
        expectThat(result).isFalse()
    }

    @Test
    fun `given NOT when achievement is absent then returns true`() {
        // Given
        val condition = LogicalCondition("!A")

        // When
        val result = condition.evaluate(setOf("B"))

        // Then
        expectThat(result).isTrue()
    }

    @Test
    fun `given NOT when achievement is present then returns false`() {
        // Given
        val condition = LogicalCondition("!A")

        // When
        val result = condition.evaluate(setOf("A"))

        // Then
        expectThat(result).isFalse()
    }

    @Test
    fun `given NOT then AND precedence when evaluate then NOT binds tighter than AND`() {
        // Given
        val condition = LogicalCondition("!A && B")

        // When / Then — A absent, B present -> (!false) && true -> true
        expectThat(condition.evaluate(setOf("B"))).isTrue()
        // A present, B present -> (!true) && true -> false
        expectThat(condition.evaluate(setOf("A", "B"))).isFalse()
    }

    @Test
    fun `given AND then OR precedence when evaluate then AND binds tighter than OR`() {
        // Given — should be parsed as A || (B && C)
        val condition = LogicalCondition("A || B && C")

        // When / Then
        expectThat(condition.evaluate(setOf("A"))).isTrue()
        expectThat(condition.evaluate(setOf("B", "C"))).isTrue()
        expectThat(condition.evaluate(setOf("B"))).isFalse()
    }

    @Test
    fun `given parenthesized OR with AND when evaluate then parentheses override precedence`() {
        // Given
        val condition = LogicalCondition("(A || B) && C")

        // When / Then
        expectThat(condition.evaluate(setOf("A", "C"))).isTrue()
        expectThat(condition.evaluate(setOf("B", "C"))).isTrue()
        expectThat(condition.evaluate(setOf("A", "B"))).isFalse()
    }

    @Test
    fun `given nested parentheses when evaluate then inner expression resolves first`() {
        // Given — (A && (B || C))
        val condition = LogicalCondition("A && (B || C)")

        // When / Then
        expectThat(condition.evaluate(setOf("A", "B"))).isTrue()
        expectThat(condition.evaluate(setOf("A", "C"))).isTrue()
        expectThat(condition.evaluate(setOf("A"))).isFalse()
        expectThat(condition.evaluate(setOf("B", "C"))).isFalse()
    }

    @Test
    fun `given condition with missing closing paren when evaluate then unmatched opener is tolerated`() {
        // Given — parser drops the unmatched "(" but still emits operands/operators
        val condition = LogicalCondition("(A && B")

        // When
        val result = condition.evaluate(setOf("A", "B"))

        // Then
        expectThat(result).isTrue()
    }

    @Test
    fun `given complex Gloomhaven requirement when evaluate then RPN resolves correctly`() {
        // Given
        val condition =
            LogicalCondition("(City Rule: Militaristic || !The Merchant's Fleets) && Ancient Technology")

        // When / Then — (false || !false) && true -> true
        expectThat(condition.evaluate(setOf("Ancient Technology"))).isTrue()
        // (false || !true) && true -> false
        expectThat(condition.evaluate(setOf("Ancient Technology", "The Merchant's Fleets"))).isFalse()
    }

    @Test
    fun `given createWithDictionary when token has dictionary entry then condition uses substituted value`() {
        // Given
        val dictionary = mapOf("Ancient Technology" to "Древние технологии")

        // When
        val condition = LogicalCondition.createWithDictionary("Ancient Technology", dictionary)

        // Then
        expectThat(condition.evaluate(setOf("Древние технологии"))).isTrue()
        expectThat(condition.evaluate(setOf("Ancient Technology"))).isFalse()
    }

    @Test
    fun `given createWithDictionary when no entries match then condition is unchanged`() {
        // Given
        val dictionary = mapOf("Missing" to "Translated")

        // When
        val condition = LogicalCondition.createWithDictionary("A && B", dictionary)

        // Then
        expectThat(condition.evaluate(setOf("A", "B"))).isTrue()
    }

    @Test
    fun `given createWithDictionary when only some tokens match then substitution is partial`() {
        // Given
        val dictionary = mapOf("A" to "Alpha")

        // When
        val condition = LogicalCondition.createWithDictionary("A && B", dictionary)

        // Then
        expectThat(condition.evaluate(setOf("Alpha", "B"))).isTrue()
        expectThat(condition.evaluate(setOf("A", "B"))).isFalse()
    }

    @Test
    fun `given non-empty condition when isNotBlank then returns true`() {
        // Given
        val condition = LogicalCondition("A")

        // When / Then
        expectThat(condition.isNotBlank()).isTrue()
    }

    @Test
    fun `given empty condition when isNotBlank then returns false`() {
        // Given
        val condition = LogicalCondition("")

        // When / Then
        expectThat(condition.isNotBlank()).isFalse()
    }

    @Test
    fun `given blank condition when isNotBlank then returns false`() {
        // Given
        val condition = LogicalCondition("   ")

        // When / Then
        expectThat(condition.isNotBlank()).isFalse()
    }

    @Test
    fun `given empty condition when rpnQueue inspected then it is empty`() {
        // Given
        val condition = LogicalCondition("")

        // When / Then
        expectThat(condition.rpnQueue.size).isEqualTo(0)
    }
}
