package ru.languageapp.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.languageapp.logic.api.MainApi

class ViewModelFactory<T : ViewModel>(
    private val viewModelClass: Class<T>,
    private val mainApi: MainApi
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModelClass)) {
            try {
                @Suppress("UNCHECKED_CAST")
                return viewModelClass.getConstructor(MainApi::class.java).newInstance(mainApi) as T
            } catch (e: Exception) {
                throw RuntimeException("Cannot create an instance of $viewModelClass", e)
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}