package ru.languageapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.languageapp.data.TokenRequest
import ru.languageapp.data.UserInfoRequest
import ru.languageapp.databinding.FragmentSplashBinding
import ru.languageapp.logic.EncryptSharedPreferencesManager
import ru.languageapp.logic.api.MainApi
import ru.languageapp.ui.gamescreen.MainActivity

class SplashFragment : Fragment() {
    private val mainApi = RetrofitClient.retrofit.create(MainApi::class.java)
    private var _binding: FragmentSplashBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Запускаем проверку токена
        checkAuthToken()
    }

    private fun checkAuthToken() {
        val encryptManager = EncryptSharedPreferencesManager(requireContext())
        val token = encryptManager.token

        // Если токен пустой, сразу отправляем на экран авторизации
        if (token.isEmpty()) {
            navigateToFirstFragment()
        } else {
            // Проверяем токен на сервере
            lifecycleScope.launch {
                try {
                    val response = withContext(Dispatchers.IO) {
                        mainApi.checkAuthToken(TokenRequest(token))

                    }

                    // Обрабатываем результат
                    if (response.login.isNotEmpty()) {
                        Log.d("FF", "Ответ от API: $response")
                        // Токен валиден, продолжаем
                        Log.d("FF","Токен валиден")
                        val result = mainApi.getUserInfo(UserInfoRequest(response.login))
                        Log.d("FF", "Ответ от API: $result")

                        encryptManager.userLogin = result.login
                        encryptManager.userFirstName = result.firstname
                        encryptManager.userLastName = result.lastname
                        encryptManager.userEmail = result.email
                        encryptManager.userPoints = result.points

                        navigateToMainScreen()
                    } else {
                        // Токен не валиден, перенаправляем на авторизацию
                        navigateToFirstFragment()
                    }
                } catch (e: Exception) {
                    Log.e("FF", "Ошибка: ${e.message}")
                    // В случае ошибки перенаправляем на экран логина
                    navigateToFirstFragment()
                }
            }
        }
    }

    private fun navigateToMainScreen() {
        // Запускаем MainActivity и завершаем SplashActivity
        val intent = Intent(requireActivity(), MainActivity::class.java)
        Handler().postDelayed({
        startActivity(intent)
        requireActivity().finish()},2000) // Закрываем текущую активность (Splash)
    }

    private fun navigateToFirstFragment() {
        // Используем Handler для задержки перед навигацией, чтобы избежать конфликтов
        Handler().postDelayed({
            findNavController().navigate(R.id.action_SplashFragment_to_FirstFragment)
        }, 3000) // Небольшая задержка 100 мс
    }

    private fun isConnectedToInternet(): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo?.isConnected ?: false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
