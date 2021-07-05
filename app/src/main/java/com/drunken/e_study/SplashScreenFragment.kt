package com.drunken.e_study

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        lifecycleScope.launch {
            delay(3000L)
            this@SplashScreenFragment.findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToWelcomeScreen())
        }

        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }
}