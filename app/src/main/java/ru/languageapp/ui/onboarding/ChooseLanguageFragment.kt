package ru.languageapp.ui.onboarding

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.languageapp.R
import ru.languageapp.databinding.FragmentChooseLanguageBinding
import ru.languageapp.ui.loginscreen.Login
import java.util.Locale

class ChooseLanguageFragment : Fragment() {

    private var _binding: FragmentChooseLanguageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentChooseLanguageBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var selectedLanguageCode: String? = null

        val buttons = listOf(
            binding.englishButton,
            binding.russianButton,
            binding.chineseButton,
            binding.belarusButton,
            binding.kazakhButton
        )

        buttons.forEach { button ->
            button.setOnClickListener {
                buttons.forEach { btn ->
                    if (btn == button) {
                        changeButtonColor(btn)
                        selectedLanguageCode = getLanguageForButton(btn)
                    } else {
                        unChangeButtonColor(btn)
                    }
                }
            }
        }

        binding.chooseButton.setOnClickListener {
            if (selectedLanguageCode != null) {
                selectedLanguageCode?.let { languageCode ->
                    changeLanguage(languageCode)
                    val intent = Intent()
                    intent.setClass(requireActivity(), Login::class.java)
                    requireActivity().startActivity(intent)
                }
            } else {
                Toast.makeText(context, "Выберите язык", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun changeButtonColor(button: View) {
        button.setBackgroundResource(R.drawable.style_button_error)
    }

    private fun getLanguageForButton(button: TextView): String {
        return when (button) {
            binding.englishButton -> "en"
            binding.russianButton -> "ru"
            binding.chineseButton -> "zh"
            binding.belarusButton -> "br"
            binding.kazakhButton -> "kz"
            else -> "en"
        }
    }

    private fun changeLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        requireActivity().resources.updateConfiguration(
            config,
            requireActivity().resources.displayMetrics
        )
    }

    private fun unChangeButtonColor(button: View) {
        button.setBackgroundResource(R.drawable.style_button_language)
    }
}
