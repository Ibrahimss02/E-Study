package com.drunken.e_study.ui.mainScreens.courseDetail

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.R
import com.drunken.e_study.databinding.FragmentCourseDetailBinding
import kotlin.math.roundToInt


class CourseDetailFragment : Fragment() {

    private lateinit var binding : FragmentCourseDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCourseDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val args = CourseDetailFragmentArgs.fromBundle(requireArguments())
        val viewModel = CourseDetailViewModel(args.id)
        binding.viewModel = viewModel

        binding.fabDialog.setOnClickListener {
            showDialog(requireActivity(), args.id)
        }

        val adapter = CourseDetailVideosAdapter()
        binding.rvDetailCourseVideo.adapter = adapter


        return binding.root
    }

    private fun showDialog(activity: FragmentActivity, id : String) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.course_detail_dialog)

        val window = dialog.window
        val windowsManager = window!!.attributes
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        windowsManager.gravity = (Gravity.END or Gravity.BOTTOM)
        window.attributes = windowsManager


        // TODO(implement all clickable button inside dialog)
        val videosContainer = dialog.findViewById<LinearLayout>(R.id.video_container)
        val modulesContainer = dialog.findViewById<LinearLayout>(R.id.module_container)
        val quizContainer = dialog.findViewById<LinearLayout>(R.id.quiz_container)
        val reviewContainer = dialog.findViewById<LinearLayout>(R.id.review_container)
        val discussContainer = dialog.findViewById<LinearLayout>(R.id.discuss_container)

        videosContainer.setOnClickListener {
            findNavController().navigate(CourseDetailFragmentDirections.actionCourseDetailFragment2ToCourseProgressFragment(id, VIDEOS_ITEM_TYPE))
            dialog.hide()
        }

        modulesContainer.setOnClickListener {
            findNavController().navigate(CourseDetailFragmentDirections.actionCourseDetailFragment2ToCourseProgressFragment(id, MODULES_ITEM_TYPE))
            dialog.hide()
        }

        quizContainer.setOnClickListener {
            findNavController().navigate(CourseDetailFragmentDirections.actionCourseDetailFragment2ToCourseProgressFragment(id, QUIZ_ITEM_TYPE))
            dialog.hide()
        }

        dialog.show()
    }

    companion object{
        const val VIDEOS_ITEM_TYPE = 0
        const val MODULES_ITEM_TYPE = 1
        const val QUIZ_ITEM_TYPE = 2
    }
}