package ru.languageapp.ui.gamescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.languageapp.R
import ru.languageapp.RetrofitClient
import ru.languageapp.data.UserUpdateScoreRemote
import ru.languageapp.databinding.FragmentWordPracticeBinding
import ru.languageapp.logic.EncryptSharedPreferencesManager
import ru.languageapp.logic.api.MainApi

class WordPracticeFragment : Fragment() {
    private lateinit var binding: FragmentWordPracticeBinding
    private val mainApi = RetrofitClient.retrofit.create(MainApi::class.java)
    private var answer = ""
    private var isAnswerChecked = false  // Флаг для отслеживания состояния
    private var ans = ""  // Переменная для хранения выбранного ответа
    private var prevbtn: View? = null  // Предыдущая нажатая кнопка

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWordPracticeBinding.inflate(inflater, container, false)
        updateUI()
        return binding.root
    }

    private fun updateUI() {
        lifecycleScope.launch {
            val question = mainApi.getQuestion()
            binding.questionTitle.text = question.questions_title
            binding.question.text = question.question
            val answers = listOf(
                question.ans_wrong_1,
                question.ans_wrong_2,
                question.ans_wrong_3,
                question.ans_correct
            ).shuffled()
            answer = question.ans_correct

            // Устанавливаем текст ответов
            binding.ans1.text = answers[0]
            binding.ans2.text = answers[1]
            binding.ans3.text = answers[2]
            binding.ans4.text = answers[3]

            // Сбрасываем цвета кнопок
            resetButtonColors()

            // Сбрасываем состояние кнопки
            isAnswerChecked = false
            binding.checkButton.text = "Check"

            // Сбрасываем выбранный ответ
            ans = ""
            prevbtn = null
        }
    }

    private fun resetButtonColors() {
        // Сбрасываем цвета всех кнопок
        val buttons = listOf(
            binding.ans1,
            binding.ans2,
            binding.ans3,
            binding.ans4
        )
        buttons.forEach { button ->
            unChangeButtonColor(button)
        }
    }

    private fun changeButtonColor(button: View) {
        button.setBackgroundResource(R.drawable.style_button)
    }

    private fun unChangeButtonColor(button: View) {
        button.setBackgroundResource(R.drawable.style_word_button)
    }

    private fun answerFailed(button: View) {
        button.setBackgroundResource(R.drawable.style_button_error)
    }

    private fun answerCorrect(button: View) {
        button.setBackgroundResource(R.drawable.style_button_green)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                }
            })

        val encryptManager = EncryptSharedPreferencesManager(requireContext())
        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_wordPracticeFragment_to_mainFragment)
        }

        val buttons = listOf(
            binding.ans1,
            binding.ans2,
            binding.ans3,
            binding.ans4
        )

        buttons.forEach { button ->
            button.setOnClickListener {
                buttons.forEach { btn ->
                    if (btn == button) {
                        changeButtonColor(btn)
                        ans = button.text.toString()
                        prevbtn = button
                    } else {
                        unChangeButtonColor(btn)
                    }
                }
            }
        }

        binding.checkButton.setOnClickListener {
            // Проверяем, выбран ли ответ
            if (ans == "") {
                // Если нет, показываем Toast и не меняем состояние кнопок
                Toast.makeText(context, "Выберите ответ", Toast.LENGTH_SHORT).show()
            } else {
                // Если ответ выбран, проверяем правильность
                if (!isAnswerChecked) {
                    if (ans == answer) {
                        lifecycleScope.launch {
                            encryptManager.userPoints += 1
                            mainApi.updateUserScore(UserUpdateScoreRemote(encryptManager.userLogin, encryptManager.userPoints))
                        }
                        prevbtn?.let { answerCorrect(it) }
                    } else {
                        prevbtn?.let { answerFailed(it) }
                        // Подсвечиваем правильный ответ зелёным
                        buttons.find { it.text == answer }?.let { answerCorrect(it) }
                    }

                    // Меняем состояние флага и текст кнопки
                    isAnswerChecked = true
                    binding.checkButton.text = "Next"
                } else {
                    // Если нажали "Next", обновляем UI
                    updateUI()
                }
            }
        }
    }
}
