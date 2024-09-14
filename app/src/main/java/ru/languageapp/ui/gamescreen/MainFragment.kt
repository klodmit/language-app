package ru.languageapp.ui.gamescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.languageapp.R
import ru.languageapp.RetrofitClient
import ru.languageapp.databinding.FragmentLoginBinding
import ru.languageapp.databinding.FragmentMainBinding
import ru.languageapp.logic.EncryptSharedPreferencesManager
import ru.languageapp.logic.api.MainApi

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val mainApi = RetrofitClient.retrofit.create(MainApi::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        getTopUsers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })

        val encryptManager = EncryptSharedPreferencesManager(requireContext())

        binding.textView61.text = "Hello, ${encryptManager.userFirstName}"


        binding.wordPractice.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_wordPracticeFragment)
        }
    }

    private fun getTopUsers() {
        lifecycleScope.launch {
            val topUsers = mainApi.getTopThreeUsers()
            binding.user1.text = topUsers[0].login
            binding.user2.text = topUsers[1].login
            binding.user3.text = topUsers[2].login
            binding.user1Points.text = topUsers[0].points.toString()
            binding.user2Points.text = topUsers[1].points.toString()
            binding.user3Points.text = topUsers[2].points.toString()

        }
    }

}