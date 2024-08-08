package ru.languageapp.ui.loginscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.languageapp.R
import ru.languageapp.RetrofitClient
import ru.languageapp.databinding.FragmentSignUpBinding
import ru.languageapp.logic.RegisterViewModel
import ru.languageapp.logic.ViewModelFactory
import ru.languageapp.logic.api.MainApi

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val mainApi = RetrofitClient.retrofit.create(MainApi::class.java)
    private val registerViewModel: RegisterViewModel by activityViewModels { ViewModelFactory(RegisterViewModel::class.java, mainApi) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)

        binding.continueButton.setOnClickListener {
            val login = binding.insertLogin.text.toString()
            val firstName = binding.insertName.text.toString()
            val lastName = binding.lastName.text.toString()
            val email = binding.insertAddress.text.toString()

            if(registerViewModel.setUserInfo(login, firstName, lastName, email) != null){
                findNavController().navigate(R.id.action_SignUpFragment_to_SignUpSecondFragment)
            }

        }
    }
}
