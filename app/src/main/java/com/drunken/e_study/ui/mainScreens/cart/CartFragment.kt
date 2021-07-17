package com.drunken.e_study.ui.mainScreens.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.database.Database
import com.drunken.e_study.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private lateinit var binding : FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val application = requireActivity().application
        val viewModelFactory = CartViewModelFactory(Database.getInstance(application).userDatabaseDao, Database.getInstance(application).courseDatabaseDAO)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(CartViewModel::class.java)

        binding.viewModel = viewModel

        val adapter = CartAdapter(CourseListener {
            viewModel.onCourseItemClicked(it)
        })
        binding.cartRv.adapter = adapter

        viewModel.navigateToCourseDetail.observe(viewLifecycleOwner, {
            it?.let { course ->
                findNavController().navigate(CartFragmentDirections.actionCartFragmentToCourseDetailFragment(course))
                viewModel.onCourseNavigated()
            }
        })

        viewModel.course.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()){
                binding.emptyCartInfoContainer.visibility = View.GONE
                binding.proceedButton.visibility = View.VISIBLE
            }
        })

        viewModel.user.observe(viewLifecycleOwner, {
            it?.let {
                viewModel.refreshCourse()
            }
        })

        viewModel.proceedToPayment.observe(viewLifecycleOwner, {
            if (it == true){
                findNavController().navigate(CartFragmentDirections.actionCartFragmentToConfirmPaymentFragment())
                viewModel.onProceedNavigated()
            }
        })


        return binding.root
    }
}