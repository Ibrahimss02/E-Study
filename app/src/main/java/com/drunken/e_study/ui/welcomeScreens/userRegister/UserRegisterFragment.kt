package com.drunken.e_study.ui.welcomeScreens.userRegister

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.R
import com.drunken.e_study.database.Database
import com.drunken.e_study.databinding.FragmentUserRegisterBinding
import com.drunken.e_study.ui.mainScreens.MainActivity
import com.drunken.e_study.ui.welcomeScreens.WelcomeActivity
import com.google.android.material.snackbar.Snackbar

class UserRegisterFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentUserRegisterBinding
    private lateinit var uri : Uri
    private lateinit var viewModel : UserRegisterViewModel
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        this.uri = uri
        binding.ivUserRegister.setImageURI(uri)
        viewModel.imageUriString.value = uri.toString()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getContent.launch("image/*")
            } else {
                Toast.makeText(context, "Permission to access storage denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserRegisterBinding.inflate(layoutInflater)
        val application = requireActivity().application
        val viewModelFactory = UserRegisterViewModelFactory(Database.getInstance(application).userDatabaseDao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(UserRegisterViewModel::class.java)
        this.viewModel = viewModel
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

        viewModel.registerSuccess.observe(viewLifecycleOwner, {
            if (it){
                startActivity(Intent(requireContext(), MainActivity::class.java))
                viewModel.doneNavigating()
                requireActivity().finish()
            }
        })

        viewModel.pickImage.observe(viewLifecycleOwner, {
            if (it == true){
                when {
                    ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        getContent.launch("image/*")
                    }
                    shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                        Snackbar.make(requireActivity().findViewById(android.R.id.content), "Permission needed to read storage", Snackbar.LENGTH_SHORT).show()
                    }
                    else -> {
                        // You can directly ask for the permission.
                        // The registered ActivityResultCallback gets the result of this request.
                        requestPermissionLauncher.launch(
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                }
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
}