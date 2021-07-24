package com.drunken.e_study.ui.mainScreens.payment.processPayment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.R
import com.drunken.e_study.database.Database
import com.drunken.e_study.databinding.FragmentProcessPaymentBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProcessPaymentFragment : Fragment() {

    private lateinit var binding : FragmentProcessPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProcessPaymentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val application = requireActivity().application
        val viewModelFactory = ProcessPaymentViewModelFactory(Database.getInstance(application).userDatabaseDao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProcessPaymentViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.paymentProcessingFinished.observe(viewLifecycleOwner, {
            if (it == true){
                binding.loadingContainer.visibility = View.GONE
            }
        })

        viewModel.onActionConfirm.observe(viewLifecycleOwner, {
            if (it == true){
                findNavController().navigate(ProcessPaymentFragmentDirections.actionProcessPaymentFragmentToHomeFragment())
                viewModel.fragmentNavigated()
            }
        })


        return binding.root
    }
}