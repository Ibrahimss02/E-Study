package com.drunken.e_study.ui.mainScreens.courseDetail.courseProgress

import StringDiffUtilCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.drunken.e_study.databinding.CourseDetailRvModulesItemBinding

class CourseDetailModulesAdapter : ListAdapter<String, CourseDetailModulesAdapter.CourseDetailModulesViewHolder>(StringDiffUtilCallback()) {

    class CourseDetailModulesViewHolder(private val binding : CourseDetailRvModulesItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(moduleTitle : String) {
            binding.title = moduleTitle
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseDetailModulesViewHolder {
        return CourseDetailModulesViewHolder(
            CourseDetailRvModulesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CourseDetailModulesViewHolder, position: Int) {
        val moduleTitle = getItem(position)
        moduleTitle?.let {
            holder.bind(it)
        }
    }
}