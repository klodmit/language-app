package ru.languageapp.ui.loginscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import okhttp3.OkHttp
import org.json.JSONObject
import retrofit2.HttpException
import ru.languageapp.R
import ru.languageapp.RetrofitClient
import ru.languageapp.databinding.FragmentSignUp2Binding
import ru.languageapp.logic.RegisterViewModel
import ru.languageapp.logic.ViewModelFactory
import ru.languageapp.logic.api.MainApi

class SignUpFragmentSecond : Fragment() {

    private lateinit var binding: FragmentSignUp2Binding
    private val mainApi = RetrofitClient.retrofit.create(MainApi::class.java)
    private val registerViewModel: RegisterViewModel by activityViewModels { ViewModelFactory(RegisterViewModel::class.java, mainApi) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUp2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUp2Binding.bind(view)

        binding.registerButton.setOnClickListener {
            val password = binding.insertpass.text.toString()
            val confirmPassword = binding.confirmPass.text.toString()

            if (password == confirmPassword) {
                registerViewModel.registerUser(password)
            } else {
                Toast.makeText(context, "Пароли не совпадают", LENGTH_SHORT).show()
            }
        }

        // проверка регистрации
        registerViewModel.registerResult.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = { token ->
                    Toast.makeText(context, "Success", LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_SignUpFragmentSecond_to_LoginFragment)
                },
                onFailure = { error ->
                    if (error is HttpException && error.code() == 400) {
                        // Получаем тело ошибки из ответа сервера
                        val errorBody = error.response()?.errorBody()?.string() ?: "Unknown error"
                        Toast.makeText(context, errorBody, LENGTH_SHORT).show()
                    } else {
                        // Общая обработка других ошибок
                        val errorMessage = error.message ?: "Unknown error"
                        Toast.makeText(context, errorMessage, LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
}