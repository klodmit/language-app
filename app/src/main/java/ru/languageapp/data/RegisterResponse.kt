package ru.languageapp.data

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("token") val token: String
)