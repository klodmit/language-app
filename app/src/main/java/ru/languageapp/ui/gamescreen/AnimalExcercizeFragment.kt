package ru.languageapp.ui.gamescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.languageapp.R

/**
 * A simple [Fragment] subclass.
 * Use the [AnimalExcercizeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AnimalExcercizeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_animal_excercize, container, false)
    }

}