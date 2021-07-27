package com.drunken.e_study.ui.mainScreens.courseDetail.courseProgress

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.R
import com.drunken.e_study.databinding.FragmentCourseProgressBinding
import com.drunken.e_study.ui.mainScreens.courseDetail.CourseDetailFragment
import com.drunken.e_study.ui.mainScreens.courseDetail.CourseDetailVideosAdapter
import com.drunken.e_study.ui.mainScreens.courseDetail.VideoClickListener

class CourseProgressFragment : Fragment() {

    private lateinit var binding : FragmentCourseProgressBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCourseProgressBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val args = CourseProgressFragmentArgs.fromBundle(requireArguments())
        val viewModel = CourseProgressViewModel(args.id, args.type)

        binding.upBtnDatailCourse.setOnClickListener {
            findNavController().navigateUp()
        }

        val adapter = when(args.type){
            CourseDetailFragment.VIDEOS_ITEM_TYPE -> {
                CourseDetailVideosAdapter(VideoClickListener { url ->
                    val packageManager = context?.packageManager ?: return@VideoClickListener
                    val httpUri = Uri.parse(url)
                    var intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + httpUri.getQueryParameter("v")))
                    if(intent.resolveActivity(packageManager) == null) {
                        // YouTube app isn't found, use the web url
                        intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    }

                    startActivity(intent)
                })
            }
            CourseDetailFragment.MODULES_ITEM_TYPE -> {
                CourseDetailModulesAdapter()
            }
            CourseDetailFragment.QUIZ_ITEM_TYPE -> {
                CourseDetailQuizAdapter()
            }
            else -> {
                null
            }
        }

        binding.rvProgressCourseVideo.adapter = adapter
        binding.viewModel = viewModel

        return binding.root
    }

}