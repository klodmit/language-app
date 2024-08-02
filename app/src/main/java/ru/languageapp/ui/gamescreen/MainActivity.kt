package ru.languageapp.ui.gamescreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.languageapp.databinding.ActivityLauncherBinding
import ru.languageapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}