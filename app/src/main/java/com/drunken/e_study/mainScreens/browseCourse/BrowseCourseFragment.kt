package com.drunken.e_study.mainScreens.browseCourse

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.drunken.e_study.databinding.FragmentBrowseCourseBinding

class BrowseCourseFragment : Fragment(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentBrowseCourseBinding
    private lateinit var adapter: BrowseCourseAdapter
    private lateinit var arguments : BrowseCourseFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBrowseCourseBinding.inflate(layoutInflater)
        arguments = BrowseCourseFragmentArgs.fromBundle(requireArguments())
        binding.lifecycleOwner = this
        val viewModelFactory = BrowseCourseViewModelFactory(arguments.path)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(BrowseCourseViewModel::class.java)
        binding.viewModel = viewModel

        adapter = BrowseCourseAdapter()
        binding.browseSdRv.adapter = adapter

        viewModel.courses.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        binding.browseCourseToolbar.apply {
            title = arguments.tittle
            setNavigationOnClickListener { findNavController().navigateUp() }
        }


        return binding.root
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
        // This method can be used whenever a query is submitted. e.g. creating search history in local DB
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return true
    }
}