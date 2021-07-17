package com.drunken.e_study.ui.welcomeScreens.userLogin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.ui.mainScreens.MainActivity
import com.drunken.e_study.R
import com.drunken.e_study.database.Database
import com.drunken.e_study.databinding.FragmentUserLoginBinding
import com.drunken.e_study.ui.welcomeScreens.WelcomeActivity

class UserLoginFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentUserLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserLoginBinding.inflate(layoutInflater)

        val application = requireActivity().application
        val viewModelFactory = UserLoginViewModelFactory(Database.getInstance(application).userDatabaseDao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(UserLoginViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.loginSuccess.observe(viewLifecycleOwner, {
            if (it){
                viewModel.doneNavigating()
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }
        })

        viewModel.showErrorSnackbar.observe(viewLifecycleOwner, {
            if (it.first){
                (activity as WelcomeActivity).showErrorSnackbar(it.second)
            }
        })

        viewModel.showProgressDialog.observe(viewLifecycleOwner, {
            if (it == true){
                (activity as WelcomeActivity).showProgressDialog("Signing you in")
            }
        })

        binding.loginBackBtn.setOnClickListener(this)



        return binding.root
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.login_back_btn -> {
                findNavController().navigateUp()
            }
        }
    }
}