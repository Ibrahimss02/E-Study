package com.drunken.e_study.ui.mainScreens.courseDetailShop

import StringDiffUtilCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.drunken.e_study.databinding.CourseDetailShopRvItemBinding

class CourseDetailShopVideosAdapter : ListAdapter<String, CourseDetailShopVideosAdapter.CourseDetailVideosViewHolder>(StringDiffUtilCallback()) {

    class CourseDetailVideosViewHolder(private val binding : CourseDetailShopRvItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(videosTitle : String) {
            binding.title = videosTitle
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseDetailVideosViewHolder {
        return CourseDetailVideosViewHolder(
            CourseDetailShopRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CourseDetailVideosViewHolder, position: Int) {
        val videosTitle = getItem(position)
        videosTitle?.let {
            holder.bind(videosTitle)
        }
    }
}