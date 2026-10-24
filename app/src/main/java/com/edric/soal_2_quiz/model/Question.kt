package com.edric.soal_2_quiz.model

data class Question(
    val question: String,
    val correctAnswer: String,
    val wrongAnswerList: List<String>
)
