package com.drunken.e_study.ui.mainScreens.newsFeed

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.drunken.e_study.databinding.FragmentNewsFeedBinding
import com.drunken.e_study.ui.mainScreens.MainActivity


class NewsFeedFragment : Fragment() {

    private lateinit var binding : FragmentNewsFeedBinding

    private val viewModel : NewsFeedViewModel by lazy {
        val apiKey = (activity as MainActivity).apiKey
        NewsFeedViewModel(apiKey)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsFeedBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        val adapter = NewsFeedAdapter(NewsItemClickListener {
            val uri = Uri.parse(it)
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        })

        binding.rvNews.adapter = adapter
        binding.rvNews.addItemDecoration(DividerItemDecoration(binding.rvNews.context, DividerItemDecoration.VERTICAL))

        viewModel.newsItem.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()){
                binding.newsProgressBar.visibility = View.GONE
                binding.rvNews.visibility = View.VISIBLE
            }
        })


        return binding.root
    }


}