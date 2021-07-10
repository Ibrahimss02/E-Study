package com.drunken.e_study.welcomeScreens

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.mainScreens.MainActivity
import com.drunken.e_study.R
import com.drunken.e_study.databinding.FragmentUserRegisterBinding
import com.drunken.e_study.models.User
import com.drunken.e_study.utils.FirestoreUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserRegisterFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentUserRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserRegisterBinding.inflate(layoutInflater)
        auth = Firebase.auth

        binding.punyaAkunTv.setOnClickListener(this)
        binding.registerBtn.setOnClickListener(this)


        return binding.root
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.punya_akun_tv -> {
                findNavController().navigate(UserRegisterFragmentDirections.actionUserRegisterFragmentToUserLoginFragment())
            }
            R.id.register_btn -> {
                val email= binding.registerEmailEt.text.toString().trim{it <= ' '}
                val username= binding.registerUsernameEt.text.toString().trim { it <= ' ' }
                val password= binding.registerPasswordEt.text.toString()
                val confirmPassword = binding.registerConfirmPwEt.text.toString()

                if (validateForm(email, password, confirmPassword, username)){
                    (activity as WelcomeActivity).showProgressDialog("Validating your data")
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if(it.isSuccessful){
                            val firebaseUser = it.result!!.user!!
                            val user = User(firebaseUser.uid, username, email)
                            FirestoreUtil().registerUser(user, this)
                        } else {
                            Toast.makeText(activity, it.exception!!.message, Toast.LENGTH_SHORT).show()
                            (activity as WelcomeActivity).hideProgressDialog()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun registerSuccess(user : User){
        (activity as WelcomeActivity).hideProgressDialog()
        startActivity(Intent(requireActivity(), MainActivity::class.java))
        requireActivity().finish()
    }

    fun validateForm(email : String, password: String, confirmPassword : String, username: String) : Boolean {
        return  when{
            TextUtils.isEmpty(username) -> {
                (activity as WelcomeActivity).showErrorSnackbar("Please enter a username")
                false
            }
            TextUtils.isEmpty(email) -> {
                (activity as WelcomeActivity).showErrorSnackbar("Please input an email address")
                false
            }
            TextUtils.isEmpty(password) -> {
                (activity as WelcomeActivity).showErrorSnackbar("Please enter a password")
                false
            }
            TextUtils.isEmpty(confirmPassword) -> {
                (activity as WelcomeActivity).showErrorSnackbar("Please confirm your password")
                false
            }
            !TextUtils.equals(password, confirmPassword) -> {
                (activity as WelcomeActivity).showErrorSnackbar("Password did not match")
                false
            }
            else -> true
        }
    }


}