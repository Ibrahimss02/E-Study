package com.drunken.e_study.ui.mainScreens.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.R
import com.drunken.e_study.database.Database
import com.drunken.e_study.databinding.FragmentConfirmPaymentBinding
import com.drunken.e_study.ui.mainScreens.cart.CartFragmentDirections
import com.drunken.e_study.ui.mainScreens.cart.CourseListener

class ConfirmPaymentFragment : Fragment() {

    private lateinit var binding : FragmentConfirmPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfirmPaymentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val application = requireActivity().application
        val dataSource = Database.getInstance(application)
        val viewModelFactory = ConfirmPaymentViewModelFactory(dataSource.userDatabaseDao, dataSource.courseDatabaseDAO)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ConfirmPaymentViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.navigateToCourseDetail.observe(viewLifecycleOwner, {
            it?.let { course ->
                findNavController().navigate(ConfirmPaymentFragmentDirections.actionConfirmPaymentFragmentToCourseDetailFragment(course))
                viewModel.onCourseNavigated()
            }
        })

        val adapter = ConfirmPaymentAdapter(CourseListener {
            viewModel.onCourseItemClicked(it)
        })

        binding.paymentRv.adapter = adapter


        return binding.root
    }
}