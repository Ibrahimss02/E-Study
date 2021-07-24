package com.drunken.e_study.ui.mainScreens.courseDetailShop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.database.Database
import com.drunken.e_study.databinding.FragmentCourseDetailShopBinding

class CourseDetailShopFragment : Fragment() {

    private lateinit var binding : FragmentCourseDetailShopBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCourseDetailShopBinding.inflate(layoutInflater)
        val application = requireActivity().application
        val args = CourseDetailShopFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = CourseDetailShopViewModelFactory(args.id, Database.getInstance(application).userDatabaseDao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(CourseDetailShopViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.onCourseFetchingError.observe(viewLifecycleOwner, {
            if (it){
                Toast.makeText(requireContext(), "Error Fetching Course. Please Try Again", Toast.LENGTH_SHORT).show()
                viewModel.onCourseFetchingDone()
            }
        })

        viewModel.notifyAddedToCart.observe(viewLifecycleOwner, {
            if (it != null) {
                if (it.first){
                    Toast.makeText(requireContext(), "Added to cart", Toast.LENGTH_SHORT).show()
                    if (it.second){
                        findNavController().navigate(CourseDetailShopFragmentDirections.actionCourseDetailFragmentToCartFragment())
                    }
                    viewModel.notified()
                }
            }
        })

        binding.upBtnDatailCourse.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.rvDetailCourseVideo.adapter = CourseDetailShopVideosAdapter()


        return binding.root
    }
}