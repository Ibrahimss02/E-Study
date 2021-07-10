package com.drunken.e_study.welcomeScreens.splash

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.R
import com.drunken.e_study.mainScreens.MainActivity

class SplashScreenFragment : Fragment() {

    private val viewModel : SplashScreenViewModel by lazy {
        ViewModelProvider(this).get(SplashScreenViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.navigateToHome.observe(viewLifecycleOwner, {
            it?.let {
                if (it){
                    findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToWelcomeScreen())
                    viewModel.doneNavigating()
                } else if (!it) {
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                    viewModel.doneNavigating()
                }
            }
        })

        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }
}