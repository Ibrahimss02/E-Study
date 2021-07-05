package com.drunken.e_study

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.drunken.e_study.adapters.ViewPagerAdapter
import com.drunken.e_study.databinding.FragmentWelcomeScreenBinding

class WelcomeScreenFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentWelcomeScreenBinding
    private val images = arrayListOf(R.drawable.ic_page_1, R.drawable.ic_page_2, R.drawable.ic_page_3)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeScreenBinding.inflate(layoutInflater)

        binding.welcomeViewPager.adapter = ViewPagerAdapter(requireContext(), images)
        binding.wormIndicator.setViewPager2(binding.welcomeViewPager)
        binding.welcomeViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                pageChanged(position)
            }
        })
        binding.btnStart.setOnClickListener(this)


        return binding.root
    }

    private fun pageChanged(position : Int){
        when(position){
            (images.size - 1) ->{
                binding.btnStart.isClickable = true
                binding.btnStart.animate().apply {
                    duration = 500
                    alphaBy(1.0F)
                }.start()
            }
            else -> {
                binding.btnStart.isClickable = false
                binding.btnStart.animate().apply {
                    duration = 500
                    alpha(0.0F)
                }.start()
            }
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_start -> {
                findNavController().navigate(WelcomeScreenFragmentDirections.actionWelcomeScreenToUserRegisterFragment())
            }
        }
    }
}