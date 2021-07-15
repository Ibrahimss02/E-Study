package com.drunken.e_study.mainScreens.courseDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.R
import com.drunken.e_study.databinding.FragmentCourseDetailBinding

class CourseDetailFragment : Fragment() {

    private lateinit var binding : FragmentCourseDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCourseDetailBinding.inflate(layoutInflater)
        val args = CourseDetailFragmentArgs.fromBundle(requireArguments())
        val viewModel = CourseDetailViewModel(args.courseId)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.onCourseFetchingError.observe(viewLifecycleOwner, {
            if (it){
                Toast.makeText(requireContext(), "Error Fetching Course. Please Try Again", Toast.LENGTH_SHORT).show()
                viewModel.onCourseFetchingDone()
            }
        })

        binding.upBtnDatailCourse.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.rvDetailCourseVideo.adapter = CourseDetailVideosAdapter()


        return binding.root
    }
}