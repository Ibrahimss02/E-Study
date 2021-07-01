package com.drunken.e_study.adapters

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.drunken.e_study.R
import com.drunken.e_study.databinding.FragmentWelcomeScreenBinding
import com.drunken.e_study.databinding.WelcomeImgItemBinding

class ViewPagerAdapter(private val context : Context, private val images : ArrayList<Int>) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(private val binding : WelcomeImgItemBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root){
        fun bind(index : Int, position: Int){
            binding.ivWelcome.setImageResource(index)
            when(position){
                0 -> {
                    binding.welcomeTitle.text = context.resources.getString(R.string.welcome_title1)
                    binding.welcomeDesc.text = context.resources.getString(R.string.welcome_desc1)
                }
                1 -> {
                    binding.welcomeTitle.text = context.resources.getString(R.string.welcome_title2)
                    binding.welcomeDesc.text = context.resources.getString(R.string.welcome_desc2)
                }
                2 -> {
                    binding.welcomeTitle.text = context.resources.getString(R.string.welcome_title3)
                    binding.welcomeDesc.text = context.resources.getString(R.string.welcome_desc3)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        return ViewPagerViewHolder(
            WelcomeImgItemBinding.inflate(LayoutInflater.from(context), parent, false), context
        )
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val image = images[position]
        holder.bind(image, position)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}