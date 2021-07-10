package com.drunken.e_study.mainScreens.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val viewModel = HomeFragmentViewModel()

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.args.observe(viewLifecycleOwner, {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToBrowseCourseFragment(
                it[0], it[1]
            ))
        })

        return binding.root
    }
}