package ru.languageapp.logic.api

import retrofit2.http.Body
import retrofit2.http.POST
import ru.languageapp.data.AuthRequest
import ru.languageapp.data.RegisterRequest
import ru.languageapp.data.User

interface MainApi {
    @POST("/registration")
    suspend fun registerUser(@Body registerRequest: RegisterRequest )

    @POST("login")
    suspend fun loginUser(@Body authRequest: AuthRequest): User
}