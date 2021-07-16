package com.drunken.e_study.welcomeScreens.userRegister

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.R
import com.drunken.e_study.database.Database
import com.drunken.e_study.databinding.FragmentUserRegisterBinding
import com.drunken.e_study.database.User
import com.drunken.e_study.mainScreens.MainActivity
import com.drunken.e_study.utils.FirestoreUtil
import com.drunken.e_study.welcomeScreens.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserRegisterFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentUserRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserRegisterBinding.inflate(layoutInflater)
        val application = requireActivity().application
        val viewModelFactory = UserRegisterViewModelFactory(Database.getInstance(application).userDatabaseDao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(UserRegisterViewModel::class.java)

        binding.viewModel = viewModel
        binding.punyaAkunTv.setOnClickListener(this)

        viewModel.showErrorSnackbar.observe(viewLifecycleOwner, {
            it?.let {
                if (it.first){
                    (activity as WelcomeActivity).showErrorSnackbar(it.second)
                    viewModel.snackbarShowed()
                }
            }
        })

        viewModel.showProgressDialog.observe(viewLifecycleOwner, {
            it?.let {
                if (it.first){
                    (activity as WelcomeActivity).showProgressDialog(it.second)
                    viewModel.dismissProgressDialog()
                }
                if(!it.first){
                    (activity as WelcomeActivity).hideProgressDialog()
                }
            }
        })

        viewModel.user.observe(viewLifecycleOwner, {
            it?.let {
                registerSuccess()
            }
        })



        return binding.root
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.punya_akun_tv -> {
                findNavController().navigate(UserRegisterFragmentDirections.actionUserRegisterFragmentToUserLoginFragment())
            }
        }
    }

    private fun registerSuccess(){
        (activity as WelcomeActivity).hideProgressDialog()
        startActivity(Intent(requireActivity(), MainActivity::class.java))
        requireActivity().finish()
    }
}