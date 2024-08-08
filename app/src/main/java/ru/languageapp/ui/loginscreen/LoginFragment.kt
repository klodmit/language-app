package ru.languageapp.ui.loginscreen

import ru.languageapp.logic.AuthViewModel
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ru.languageapp.R
import ru.languageapp.RetrofitClient
import ru.languageapp.ui.gamescreen.MainActivity

import ru.languageapp.databinding.FragmentLoginBinding
import ru.languageapp.logic.ViewModelFactory
import ru.languageapp.logic.api.MainApi


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val mainApi = RetrofitClient.retrofit.create(MainApi::class.java)
    private val authViewModel: AuthViewModel by viewModels { ViewModelFactory(AuthViewModel::class.java, mainApi) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        binding.login.setOnClickListener {
            val login = binding.insertLogin.text.toString()
            val password = binding.insertpass.text.toString()
            authViewModel.loginUser(login, password)
        }
        authViewModel.loginResult.observe(viewLifecycleOwner, Observer { result ->
            result.onSuccess { success ->
                if (success) {
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    requireActivity().startActivity(intent)
                } else {
                    // Обработка неудачной авторизации
                    println("Ошибка авторизации: неправильный логин или пароль")
                }
            }.onFailure { exception ->
                // Обработка ошибки авторизации
                println("Ошибка авторизации: ${exception.message}")
            }
        })
        binding.signup.setOnClickListener{
            view.postDelayed({
                findNavController().navigate(R.id.action_LoginFragment_to_SignUpFragment)
            },100)
        }
        binding.back.setOnClickListener{
            view.postDelayed({
                activity?.onBackPressedDispatcher?.onBackPressed()
            },100)
        }
    }
}