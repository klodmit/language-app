package ru.languageapp.logic.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.languageapp.data.AuthRequest
import ru.languageapp.data.Question
import ru.languageapp.data.RegisterRequest
import ru.languageapp.data.RegisterResponse
import ru.languageapp.data.TokenRequest
import ru.languageapp.data.TokenResponse
import ru.languageapp.data.User
import ru.languageapp.data.UserInfo
import ru.languageapp.data.UserInfoRequest
import ru.languageapp.data.UserUpdateScoreRemote

interface MainApi {
    @POST("/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest ): RegisterResponse

    @POST("login")
    suspend fun loginUser(@Body authRequest: AuthRequest): User

    @POST("getUser")
    suspend fun getUserInfo(@Body userInfoRequest: UserInfoRequest): UserInfo

    @POST("validtoken")
    suspend fun checkAuthToken(@Body tokenRequest: TokenRequest): TokenResponse

    @POST("updateUserInfo")
    suspend fun updateUserInfo(@Body tokenRequest: TokenRequest): UserInfo

    @POST("updateUserScore")
    suspend fun updateUserScore(@Body updateUserScoreRemote: UserUpdateScoreRemote): UserInfo

    @GET("getTopThreeUsers")
    suspend fun getTopThreeUsers(): List<UserUpdateScoreRemote>

    @GET("getQuestion")
    suspend fun getQuestion(): Question
}