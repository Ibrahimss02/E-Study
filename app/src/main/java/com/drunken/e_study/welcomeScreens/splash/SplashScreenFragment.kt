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
import com.drunken.e_study.database.Database
import com.drunken.e_study.mainScreens.MainActivity

class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireActivity().application
        val dataSource = Database.getInstance(application).userDatabaseDao
        val viewModelFactory = SplashScreenViewModelFactory(dataSource)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(SplashScreenViewModel::class.java)

        viewModel.user.observe(viewLifecycleOwner, {
            if (it != null){
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            } else {
                findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToWelcomeScreen())
            }
        })

        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }
}