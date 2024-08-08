package ru.languageapp.logic.api

import retrofit2.http.Body
import retrofit2.http.POST
import ru.languageapp.data.AuthRequest
import ru.languageapp.data.RegisterRequest
import ru.languageapp.data.RegisterResponse
import ru.languageapp.data.User
import ru.languageapp.data.UserInfo

interface MainApi {
    @POST("/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest ): RegisterResponse

    @POST("login")
    suspend fun loginUser(@Body authRequest: AuthRequest): User

    @POST("getUser")
    suspend fun getUserInfo(@Body userRequest: User): UserInfo

    @POST("tokeninfo")
    suspend fun checkAuthToken(@Body tokenRequest: AuthRequest): String
}