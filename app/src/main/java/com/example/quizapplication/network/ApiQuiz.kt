package com.example.quizapplication.network

import kotlinx.serialization.Serializable

/**
 * Data class for the response from the API
 */
@Serializable
data class ApiQuiz (
    val type: String,
    val difficulty: String,
    val category: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)