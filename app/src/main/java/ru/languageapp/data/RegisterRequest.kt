package ru.languageapp.data

data class RegisterRequest(
    val login: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val points: Int
)
