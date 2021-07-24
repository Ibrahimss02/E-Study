package com.drunken.e_study.ui.mainScreens.payment.confirmPayment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.database.Database
import com.drunken.e_study.databinding.FragmentConfirmPaymentBinding

class ConfirmPaymentFragment : Fragment() {

    private lateinit var binding : FragmentConfirmPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfirmPaymentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val args = ConfirmPaymentFragmentArgs.fromBundle(requireArguments())

        val application = requireActivity().application
        val dataSource = Database.getInstance(application)
        val viewModelFactory = ConfirmPaymentViewModelFactory(dataSource.userDatabaseDao, dataSource.courseDatabaseDAO, args.metode)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ConfirmPaymentViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.navigateToCourseDetail.observe(viewLifecycleOwner, {
            it?.let { course ->
                findNavController().navigate(ConfirmPaymentFragmentDirections.actionConfirmPaymentFragmentToCourseDetailFragment(course))
                viewModel.onCourseNavigated()
            }
        })

        viewModel.navigateToMethodChooser.observe(viewLifecycleOwner, {
            if (it == true){
                findNavController().navigate(ConfirmPaymentFragmentDirections.actionConfirmPaymentFragmentToPaymentMethodFragment())
                viewModel.onMethodChooserNavigated()
            }
        })

        viewModel.navigateToPaymentInfo.observe(viewLifecycleOwner, {
            if (it == true){
                if (viewModel.arguments.value != null){
                    findNavController().navigate(ConfirmPaymentFragmentDirections.actionConfirmPaymentFragmentToPaymentInfoFragment(viewModel.arguments.value!!, viewModel.totalPriceString.value!!))
                    viewModel.onPaymentInfoNavigated()
                } else {
                    Toast.makeText(requireContext(), "Metode pembayaran belum ditentukan", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.navigateUpBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        val adapter = ConfirmPaymentAdapter(CourseListener {
            viewModel.onCourseItemClicked(it)
        })

        binding.paymentRv.adapter = adapter


        return binding.root
    }
}