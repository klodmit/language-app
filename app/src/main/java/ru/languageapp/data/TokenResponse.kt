package ru.languageapp.data

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("login") val login: String
)
