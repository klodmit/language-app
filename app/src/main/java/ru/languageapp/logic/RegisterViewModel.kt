package ru.languageapp.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.languageapp.data.RegisterRequest
import ru.languageapp.logic.api.MainApi

class RegisterViewModel(private val mainApi: MainApi) : ViewModel() {

    private val _registerRequest = MutableLiveData<RegisterRequest>()
    val registerRequest: LiveData<RegisterRequest> = _registerRequest

    private val _registerResult = MutableLiveData<Result<String>>()
    val registerResult: LiveData<Result<String>> = _registerResult

    private var login: String? = null
    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null

    fun setUserInfo(login: String, firstName: String, lastName: String, email: String) {
        this.login = login
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
    }

    fun registerUser(password: String) {
        viewModelScope.launch {
            try {
                val request = RegisterRequest(
                    login = login ?: throw IllegalArgumentException("Login is null"),
                    password = password,
                    firstname = firstName ?: throw IllegalArgumentException("First name is null"),
                    lastname = lastName ?: throw IllegalArgumentException("Last name is null"),
                    email = email ?: throw IllegalArgumentException("Email is null"),
                    points = 0
                )
                val response = withContext(Dispatchers.IO) {
                    mainApi.registerUser(request)
                }
                // Получаем токен из ответа
                val token = response.token
                _registerResult.postValue(Result.success(token))
            } catch (e: Exception) {
                _registerResult.postValue(Result.failure(e))
            }
        }
    }

}
