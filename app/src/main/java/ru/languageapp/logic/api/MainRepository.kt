package ru.languageapp.logic.api

import retrofit2.http.Body

import ru.languageapp.data.AuthRequest
import ru.languageapp.data.User

//class MainRepository {
//    private val api: MainApi by lazy { Retrofit.getClient().create(MainApi::class.java) }
//
//    suspend fun loginUser(): User {
//        return api.loginUser(
//            AuthRequest(
//                login = "",
//                password = ""
//            )
//        )
//    }
//
//}