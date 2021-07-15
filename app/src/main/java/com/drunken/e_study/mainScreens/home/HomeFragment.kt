package com.drunken.e_study.mainScreens.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.database.Database
import com.drunken.e_study.databinding.FragmentHomeBinding
import com.drunken.e_study.utils.FirestoreUtil

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val application = requireActivity().application
        val viewModelFactory = HomeViewModelFactory(Database.getInstance(application).userDatabaseDao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.args.observe(viewLifecycleOwner, {
            if(it != null){
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToBrowseCourseFragment(
                    it.first, it.second
                ))
                viewModel.doneNavigating()
            }

        })

        binding.ivHomeAccount.setOnClickListener {
            Toast.makeText(requireContext(), "Uploading", Toast.LENGTH_LONG).show()
            FirestoreUtil().setupCourses()
        }

        return binding.root
    }
}