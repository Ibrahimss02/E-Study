package com.drunken.e_study.ui.mainScreens.courseDetail.courseProgress

import StringDiffUtilCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.drunken.e_study.databinding.CourseDetailRvQuizItemBinding

class CourseDetailQuizAdapter : ListAdapter<String, CourseDetailQuizAdapter.CourseDetailQuizViewHolder>(StringDiffUtilCallback()) {

    class CourseDetailQuizViewHolder(private val binding : CourseDetailRvQuizItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(moduleTitle : String) {
            binding.title = moduleTitle
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseDetailQuizViewHolder {
        return CourseDetailQuizViewHolder(
            CourseDetailRvQuizItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CourseDetailQuizViewHolder, position: Int) {
        val moduleTitle = getItem(position)
        moduleTitle?.let {
            holder.bind(it)
        }
    }
}