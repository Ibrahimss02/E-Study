package com.drunken.e_study.ui.mainScreens.account

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.R
import com.drunken.e_study.database.Database
import com.drunken.e_study.databinding.FragmentAccountBinding
import com.drunken.e_study.ui.mainScreens.MainActivity
import com.drunken.e_study.ui.welcomeScreens.WelcomeActivity
import com.google.android.material.snackbar.Snackbar

class AccountFragment : Fragment() {

    private lateinit var binding : FragmentAccountBinding
    private lateinit var uri : Uri
    private lateinit var viewModel : AccountViewModel
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let{ uri ->
            this.uri = uri
            binding.ivProfile.setImageURI(uri)
            if (this::viewModel.isInitialized){
                viewModel.updateUserData(uri)
            }
        }
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
        binding = FragmentAccountBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val application = requireActivity().application
        val viewModelFactory = AccountViewModelFactory(Database.getInstance(application).userDatabaseDao, Database.getInstance(application).courseDatabaseDAO)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AccountViewModel::class.java)

        binding.viewModel = viewModel

        val adapter = AccountCourseAdapter(AccountCourseListener { courseId ->
            findNavController().navigate(AccountFragmentDirections.actionAccountFragmentToCourseDetailFragment2(courseId))
        })

        binding.rvAccount.adapter = adapter

        viewModel.notifyEmptyList.observe(viewLifecycleOwner, {
            if (it == true){
                (activity as MainActivity).showSnackbar("Kelas kamu masih kosong! Tambahkan yuk")
                viewModel.doneNotifyingEmpty()
            }
        })

        viewModel.user.observe(viewLifecycleOwner, {
            it?.let {
                viewModel.refreshUserData()
            }
        })

        viewModel.course.observe(viewLifecycleOwner, {
            val tv = binding.classInfo.findViewById<TextView>(R.id.tv_class_counter)
            if(!it.isNullOrEmpty()){
                binding.tvAccountFragment.visibility = View.VISIBLE
                tv.text = it.size.toString()
            } else {
                tv.text = "0"
                binding.tvAccountFragment.visibility = View.INVISIBLE
            }
        })

        viewModel.signOut.observe(viewLifecycleOwner, {
            if (it){
                val firstRun = requireActivity().getSharedPreferences("PREFERENCE",
                    Context.MODE_PRIVATE
                ).getBoolean("firstrun", true)
                if (!firstRun){
                    requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit().putBoolean("firstrun", true).apply()
                }
                viewModel.doneSigningOut()
                startActivity(Intent(requireContext(), WelcomeActivity::class.java))
                requireActivity().finish()
            }
        })

        viewModel.notifyUserUpdated.observe(viewLifecycleOwner, {
            if (it){
                Toast.makeText(context, "User Data Updated", Toast.LENGTH_SHORT).show()
                viewModel.refreshUserData()
            }
        })

        binding.imgProfileContainer.setOnClickListener {
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

        return binding.root
    }
}