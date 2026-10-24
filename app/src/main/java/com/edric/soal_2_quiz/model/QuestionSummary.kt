package com.edric.soal_2_quiz.model

data class QuestionSummary(
    val number: Int,
    val question : String,
    val answer : String,
    val correctAnswer : String,
    val isCorrect : Boolean
)
