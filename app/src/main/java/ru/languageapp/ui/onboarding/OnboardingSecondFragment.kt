package ru.languageapp.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.languageapp.R
import ru.languageapp.databinding.FragmentSecondBinding


class OnboardingSecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.moreButton.setOnClickListener {
            view.postDelayed({
            findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment)
            }, 100)
        }
        binding.skip.setOnClickListener {
            view.postDelayed({
                findNavController().navigate(R.id.action_SecondFragment_to_ChooseLanguage)
            }, 100)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}