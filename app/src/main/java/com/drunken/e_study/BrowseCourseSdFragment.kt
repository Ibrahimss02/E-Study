package com.drunken.e_study

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.drunken.e_study.adapters.BrowseCourseAdapter
import com.drunken.e_study.models.Course
import com.drunken.e_study.databinding.FragmentBrowseCourseSdBinding

class BrowseCourseSdFragment : Fragment() {

    private lateinit var binding : FragmentBrowseCourseSdBinding
    private lateinit var courseSd : ArrayList<Course>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBrowseCourseSdBinding.inflate(layoutInflater)
        setupCourseSd()

        binding.browseSdRv.layoutManager = LinearLayoutManager(requireContext())
        binding.browseSdRv.adapter = BrowseCourseAdapter(requireContext(), courseSd)

        binding.browseCourseBackBtn.setOnClickListener {
            findNavController().navigateUp()
        }




        return binding.root
    }

    private fun setupCourseSd() {
        courseSd = arrayListOf(
            Course(
                "TMKV1","Tematik Jilid I", "Penerapan Tematik pada Siswa Sekolah Dasar", "Budi, Ifadatul", "Kelas 2 SD", 50000, 3.0F, R.drawable.sample_course_img_sd
            ),
            Course(
                "TMKV2","Tematik Jilid II", "Penerapan Tematik pada Siswa Sekolah Dasar", "Budi, Ifadatul", "Kelas 2 SD", 55000, 3.5F, R.drawable.sample_course_img_sd
            ),
            Course(
                "TMKV3","Tematik Jilid III", "Penerapan Tematik pada Siswa Sekolah Dasar", "Budi, Ifadatul", "Kelas 2 SD", 60000, 2.7F, R.drawable.sample_course_img_sd
            ),
            Course(
                "TMKV4","Tematik Jilid IV", "Penerapan Tematik pada Siswa Sekolah Dasar", "Budi, Ifadatul", "Kelas 2 SD", 55000, 4F, R.drawable.sample_course_img_sd
            ),
            Course(
                "TMKV5","Tematik Jilid V", "Penerapan Tematik pada Siswa Sekolah Dasar", "Budi, Ifadatul", "Kelas 2 SD", 39000, 3.5F, R.drawable.sample_course_img_sd
            ),
            Course(
                "BID","Bahasa Indonesia", "Pengenalan Bahasa Indonesia yang Baik dan Benar", "Glen, Diego", "Kelas 3 SD", 30000, 4F, R.drawable.sample_bahasa_course
            ),
            Course(
                "BIDV1","Bahasa Indonesia Jilid I", "Pengenalan Bahasa Indonesia yang Baik dan Benar", "Budi, Ifadatul", "Kelas 2 SD", 35000, 3F, R.drawable.sample_bahasa_course
            )
        )
    }
}