package com.rumpilstilstkin.gloomhavenhelper.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class ResultExtensionsTest {

    @Test
    fun `mapLeft should transform failure exception`() {
        // Given
        val originalException = RuntimeException("Original error")
        val result = Result.failure<String>(originalException)
        
        // When
        val transformedResult = result.mapLeft { IOException("Transformed: ${it.message}") }
        
        // Then
        assertTrue(transformedResult.isFailure)
        val exception = transformedResult.exceptionOrNull()
        assertTrue(exception is IOException)
        assertEquals("Transformed: Original error", exception?.message)
    }
    
    @Test
    fun `mapLeft should not affect success result`() {
        // Given
        val result = Result.success("Success value")
        
        // When
        val transformedResult = result.mapLeft { IOException("This should not happen") }
        
        // Then
        assertTrue(transformedResult.isSuccess)
        assertEquals("Success value", transformedResult.getOrNull())
    }
    
    @Test
    fun `flatMap should transform success result`() {
        // Given
        val result = Result.success(5)
        
        // When
        val transformedResult = result.flatMap { value -> 
            Result.success("Number: $value") 
        }
        
        // Then
        assertTrue(transformedResult.isSuccess)
        assertEquals("Number: 5", transformedResult.getOrNull())
    }
    
    @Test
    fun `flatMap should propagate failure`() {
        // Given
        val exception = RuntimeException("Test error")
        val result = Result.failure<Int>(exception)
        
        // When
        val transformedResult = result.flatMap { value -> 
            Result.success("Number: $value") 
        }
        
        // Then
        assertTrue(transformedResult.isFailure)
        assertEquals(exception, transformedResult.exceptionOrNull())
    }
    
    @Test
    fun `mapSuccess should transform success value`() {
        // Given
        val result = Result.success(5)
        
        // When
        val transformedResult = result.mapSuccess { it * 2 }
        
        // Then
        assertTrue(transformedResult.isSuccess)
        assertEquals(10, transformedResult.getOrNull())
    }
    
    @Test
    fun `mapSuccess should not affect failure result`() {
        // Given
        val exception = RuntimeException("Test error")
        val result = Result.failure<Int>(exception)
        
        // When
        val transformedResult = result.mapSuccess { it * 2 }
        
        // Then
        assertTrue(transformedResult.isFailure)
        assertEquals(exception, transformedResult.exceptionOrNull())
    }
    
    @Test
    fun `recoverIf should recover from matching exception`() {
        // Given
        val result = Result.failure<Int>(IOException("Network error"))
        
        // When
        val recoveredResult = result.recoverIf(
            predicate = { it is IOException },
            recovery = { 42 }
        )
        
        // Then
        assertTrue(recoveredResult.isSuccess)
        assertEquals(42, recoveredResult.getOrNull())
    }
    
    @Test
    fun `recoverIf should not recover from non-matching exception`() {
        // Given
        val exception = RuntimeException("Test error")
        val result = Result.failure<Int>(exception)
        
        // When
        val recoveredResult = result.recoverIf(
            predicate = { it is IOException },
            recovery = { 42 }
        )
        
        // Then
        assertTrue(recoveredResult.isFailure)
        assertEquals(exception, recoveredResult.exceptionOrNull())
    }
    
    @Test
    fun `recoverIf should not affect success result`() {
        // Given
        val result = Result.success(10)
        
        // When
        val recoveredResult = result.recoverIf(
            predicate = { it is IOException },
            recovery = { 42 }
        )
        
        // Then
        assertTrue(recoveredResult.isSuccess)
        assertEquals(10, recoveredResult.getOrNull())
    }
    
    @Test
    fun `zip should combine two success results`() {
        // Given
        val result1 = Result.success(5)
        val result2 = Result.success("test")
        
        // When
        val combinedResult = result1.zip(result2) { num, str -> "$str: $num" }
        
        // Then
        assertTrue(combinedResult.isSuccess)
        assertEquals("test: 5", combinedResult.getOrNull())
    }
    
    @Test
    fun `zip should propagate first failure`() {
        // Given
        val exception = RuntimeException("First error")
        val result1 = Result.failure<Int>(exception)
        val result2 = Result.success("test")
        
        // When
        val combinedResult = result1.zip(result2) { num, str -> "$str: $num" }
        
        // Then
        assertTrue(combinedResult.isFailure)
        assertEquals(exception, combinedResult.exceptionOrNull())
    }
    
    @Test
    fun `zip should propagate second failure`() {
        // Given
        val exception = RuntimeException("Second error")
        val result1 = Result.success(5)
        val result2 = Result.failure<String>(exception)
        
        // When
        val combinedResult = result1.zip(result2) { num, str -> "$str: $num" }
        
        // Then
        assertTrue(combinedResult.isFailure)
        assertEquals(exception, combinedResult.exceptionOrNull())
    }
    
    @Test
    fun `toResult should convert non-null value to success result`() {
        // Given
        val value = "test"
        
        // When
        val result = value.toResult()
        
        // Then
        assertTrue(result.isSuccess)
        assertEquals("test", result.getOrNull())
    }
    
    @Test
    fun `toResult should convert null to failure result`() {
        // Given
        val value: String? = null
        
        // When
        val result = value.toResult()
        
        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is NullPointerException)
    }
    
    @Test
    fun `getOrNull should return value for success result`() {
        // Given
        val result = Result.success("test")
        
        // When
        val value = result.getOrNull()
        
        // Then
        assertEquals("test", value)
    }
    
    @Test
    fun `getOrNull should return null for failure result`() {
        // Given
        val result = Result.failure<String>(RuntimeException("Test error"))
        
        // When
        val value = result.getOrNull()
        
        // Then
        assertNull(value)
    }
}