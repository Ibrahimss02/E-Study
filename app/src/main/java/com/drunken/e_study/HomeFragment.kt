package com.drunken.e_study

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.kursusSdTitle.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.kursus_sd_title -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToBrowseCourseSdFragment())
            }
        }
    }
}