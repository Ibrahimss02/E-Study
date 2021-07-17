package com.drunken.e_study.ui.mainScreens.courseDetail

import StringDiffUtilCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.drunken.e_study.databinding.CourseDetailRvItemBinding

class CourseDetailVideosAdapter : ListAdapter<String, CourseDetailVideosAdapter.CourseDetailVideosViewHolder>(StringDiffUtilCallback()) {

    class CourseDetailVideosViewHolder(private val binding : CourseDetailRvItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(videosTitle : String) {
            binding.title = videosTitle
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseDetailVideosViewHolder {
        return CourseDetailVideosViewHolder(
            CourseDetailRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CourseDetailVideosViewHolder, position: Int) {
        val videosTitle = getItem(position)
        videosTitle?.let {
            holder.bind(videosTitle)
        }
    }
}