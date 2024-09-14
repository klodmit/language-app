package ru.languageapp.data

data class Question(
    val questions_id: Int,
    val questions_title: String,
    val question: String,
    val ans_wrong_1: String,
    val ans_wrong_2: String,
    val ans_wrong_3: String,
    val ans_correct: String,
)
