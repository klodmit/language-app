package ru.languageapp.logic

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.languageapp.data.AuthRequest
import ru.languageapp.data.User
import ru.languageapp.logic.api.MainApi

class AuthViewModel(private val mainApi: MainApi) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<Boolean>>()
    val loginResult: LiveData<Result<Boolean>> = _loginResult

    fun loginUser(login: String, password: String, context: Context) {
        viewModelScope.launch {
            try {
                val authRequest = AuthRequest(login, password)
                val loggedInUser = withContext(Dispatchers.IO) {
                    mainApi.loginUser(authRequest)
                }

                val success = loginAccepted(loggedInUser)
                if (success && loggedInUser != null) {
                    // Save token after successful login
                    val encryptManager = EncryptSharedPreferencesManager(context)
                    encryptManager.token = loggedInUser.token
                }

                _loginResult.postValue(Result.success(success))
            } catch (e: Exception) {
                _loginResult.postValue(Result.failure(e))
            }
        }
    }

    private fun loginAccepted(user: User?): Boolean {
        return user != null // Successful login if user is not null
    }
}
