package ru.languageapp.ui.loginscreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.languageapp.ui.gamescreen.MainActivity

import ru.languageapp.data.AuthRequest
import ru.languageapp.data.User
import ru.languageapp.databinding.FragmentLoginBinding
import ru.languageapp.logic.api.MainApi


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun loginAccepted(user: User?): Boolean {
        return user != null // Успешная авторизация, если пользователь не null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://languageapp-klodmit.amvera.io/").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val mainApi = retrofit.create(MainApi::class.java)

        binding.login.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val authRequest = AuthRequest(
                        login = binding.insertaddress.text.toString(),
                        password = binding.insertpass.text.toString()
                    )

                    val loggedInUser = withContext(Dispatchers.IO) {
                        mainApi.loginUser(authRequest)
                    }

                    if (loginAccepted(loggedInUser)) {
                        val intent = Intent(requireActivity(), MainActivity::class.java)
                        requireActivity().startActivity(intent)
                    } else {
                        // Обработка неудачной авторизации
                        println("Ошибка авторизации: неправильный логин или пароль")
                    }
                } catch (e: Exception) {
                    // Обработка ошибки авторизации
                    println("Ошибка авторизации: ${e.message}")
                }
            }
        }
    }
}