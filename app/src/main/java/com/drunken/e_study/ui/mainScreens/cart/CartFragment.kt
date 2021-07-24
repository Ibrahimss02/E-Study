package com.drunken.e_study.ui.mainScreens.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        val adapter = CartAdapter(CourseListener({ courseId ->
            viewModel.onCourseItemClicked(courseId)
        }, { course ->
            viewModel.onCourseDeleteAction(course)
        }))
        binding.cartRv.adapter = adapter

        viewModel.navigateToCourseDetail.observe(viewLifecycleOwner, {
            it?.let { course ->
                findNavController().navigate(CartFragmentDirections.actionCartFragmentToCourseDetailFragment(course))
                viewModel.onCourseNavigated()
            }
        })

        viewModel.notifyItemRemoved.observe(viewLifecycleOwner, {
            if (it != null){
                Toast.makeText(requireContext(), "$it dihapus dari daftar", Toast.LENGTH_SHORT).show()
                viewModel.notified()
                adapter.notifyDataSetChanged()
            }
            if (viewModel.course.value?.isEmpty() == true){
                binding.emptyCartInfoContainer.visibility = View.VISIBLE
                binding.proceedButton.visibility = View.INVISIBLE
            }
        })

        viewModel.course.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()){
                binding.emptyCartInfoContainer.visibility = View.GONE
                binding.proceedButton.visibility = View.VISIBLE
            }
        })

        viewModel.proceedToPayment.observe(viewLifecycleOwner, {
            if (it == true){
                findNavController().navigate(CartFragmentDirections.actionCartFragmentToConfirmPaymentFragment(null))
                viewModel.onProceedNavigated()
            }
        })


        return binding.root
    }
}