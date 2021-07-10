package com.drunken.e_study.welcomeScreens

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.mainScreens.MainActivity
import com.drunken.e_study.R
import com.drunken.e_study.databinding.FragmentUserLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserLoginFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentUserLoginBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserLoginBinding.inflate(layoutInflater)
        auth = Firebase.auth

        binding.loginBackBtn.setOnClickListener(this)
        binding.loginGoogleBtn.setOnClickListener(this)
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
                val email = binding.emailLoginEt.text.toString().trim { it <= ' ' }
                val password = binding.passwordLoginEt.text.toString()

                if (validateForm(password = password, email = email)){
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful){
                            startActivity(Intent(requireActivity(), MainActivity::class.java))
                            requireActivity().finish()
                        } else {
                            (activity as WelcomeActivity).showErrorSnackbar("Authentication Error")
                        }
                        (activity as WelcomeActivity).hideProgressDialog()
                    }
                }
            }
            R.id.login_google_btn ->{
                startActivity(Intent(requireActivity(), MainActivity::class.java))
                requireActivity().finish()
            }
        }
    }

    fun validateForm(password: String, email: String) : Boolean {
        return  when{
            TextUtils.isEmpty(password) -> {
                (activity as WelcomeActivity).showErrorSnackbar("Please enter a password")
                false
            }
            TextUtils.isEmpty(email) -> {
                (activity as WelcomeActivity).showErrorSnackbar("Please enter an email")
                false
            }
            else -> true
        }
    }
}