package com.drunken.e_study.welcomeScreens.userLogin

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.mainScreens.MainActivity
import com.drunken.e_study.R
import com.drunken.e_study.database.Database
import com.drunken.e_study.databinding.FragmentUserLoginBinding
import com.drunken.e_study.welcomeScreens.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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
                startActivity(Intent(requireContext(), MainActivity::class.java))
                viewModel.doneNavigating()
                requireActivity().finish()
            }
        })

        viewModel.showErrorSnackbar.observe(viewLifecycleOwner, {
            if (it.first){
                (activity as WelcomeActivity).showErrorSnackbar(it.second)
            }
        })

        viewModel.showProgressDialog.observe(viewLifecycleOwner, {
            if (it){
                (activity as WelcomeActivity).showProgressDialog("Signing you in")
            } else if (!it){
                (activity as WelcomeActivity).hideProgressDialog()
            }
        })

        binding.loginBackBtn.setOnClickListener(this)
        binding.signInBtn.setOnClickListener(this)



        return binding.root
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.login_back_btn -> {
                findNavController().navigateUp()
            }
            R.id.sign_in_btn -> {
                (activity as WelcomeActivity).showProgressDialog("Signing you in")
            }
        }
    }
}