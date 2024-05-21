package com.example.quizapplication.ui

import com.example.quizapplication.data.database.quiz.CachedDbQuiz

sealed class QuizState {
    data object Loading : QuizState()
    data class QuizLoaded(val quiz: CachedDbQuiz) : QuizState()
    data class AnswerSelected(val isCorrect: Boolean, val selectedAnswer: String, val correctAnswer: String) : QuizState()
    data class Error(val message: String) : QuizState()
    data object Completed: QuizState()
}
