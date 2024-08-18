package ru.languageapp.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.languageapp.R
import ru.languageapp.databinding.FragmentFirstBinding
import androidx.activity.OnBackPressedCallback

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class OnboardingFragment : Fragment() {

private var _binding: FragmentFirstBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

      _binding = FragmentFirstBinding.inflate(inflater, container, false)
      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextButton.setOnClickListener {
            view.postDelayed({
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            },100)
        }

        binding.skip.setOnClickListener {
            view.postDelayed({
                findNavController().navigate(R.id.action_FirstFragment_to_ChooseLanguage)
            },100)
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })
    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}