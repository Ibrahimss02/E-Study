package com.drunken.e_study.ui.mainScreens.home

import android.Manifest
import android.app.Application
import android.app.Dialog
import android.content.Context.MODE_PRIVATE
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.R
import com.drunken.e_study.database.Database
import com.drunken.e_study.databinding.FragmentHomeBinding
import com.drunken.e_study.utils.FirestoreUtil
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var dialog : Dialog
    private lateinit var dismissDialogBtn : Button
    private lateinit var application : Application

    private val requestInternetPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if(!isGranted){
            Toast.makeText(requireContext(), "Internet permission needed to use the app", Toast.LENGTH_SHORT).show()
        } else {
            /**
             * Refresh course data to cloud
             */
            if (this::application.isInitialized){
                lifecycleScope.launch {
                    FirestoreUtil().setupCourses(Database.getInstance(application).courseDatabaseDAO)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val firstRun = requireActivity().getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstrun", true)
        if (firstRun){
            checkInternetPermission()
            setupPrototypeDialog(requireActivity())
            requireActivity().getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("firstrun", false).apply()
        }
    }

    override fun onPause() {
        super.onPause()
        if (this::dialog.isInitialized){
            dialog.dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        application = requireActivity().application
        val viewModelFactory = HomeViewModelFactory(Database.getInstance(application).userDatabaseDao, Database.getInstance(application).courseDatabaseDAO)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.args.observe(viewLifecycleOwner, {
            if(it != null){
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToBrowseCourseFragment(
                    it.first, it.second
                ))
                viewModel.doneNavigating()
            }
        })

        binding.ivHomeAccount.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAccountFragment())
        }

        return binding.root
    }

    private fun checkInternetPermission() {
        when{
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.INTERNET
            ) == PackageManager.PERMISSION_GRANTED -> {
                /**
                 * Refresh course data to cloud
                 */
                if (this::application.isInitialized){
                    lifecycleScope.launch {
                        FirestoreUtil().setupCourses(Database.getInstance(application).courseDatabaseDAO)
                    }
                }
            }
            shouldShowRequestPermissionRationale(Manifest.permission.INTERNET) -> {
                Snackbar.make(requireActivity().findViewById(android.R.id.content), "Internet permission needed to use this app", Snackbar.LENGTH_SHORT).show()
            }
            else -> {
                requestInternetPermission.launch(Manifest.permission.INTERNET)
            }
        }
    }

    private fun setupPrototypeDialog(context : FragmentActivity) {
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.prototype_dialog)

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dismissDialogBtn = dialog.findViewById(R.id.prototype_dialog_btn)

        dismissDialogBtn.setOnClickListener {
            dialog.hide()
        }

        dialog.show()
    }
}