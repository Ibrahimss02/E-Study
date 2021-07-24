package com.drunken.e_study.ui.mainScreens.account

import ItemDiffUtilCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.drunken.e_study.databinding.AccountCourseItemBinding
import com.drunken.e_study.dto.Course

class AccountCourseAdapter(private val clickListener : AccountCourseListener) : ListAdapter<Course, AccountCourseAdapter.AccountRVViewHolder>(
    ItemDiffUtilCallback()){

    inner class AccountRVViewHolder(private val binding : AccountCourseItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(course: Course){
            binding.course = course
            binding.clickListener = clickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountRVViewHolder {
        return AccountRVViewHolder(
            AccountCourseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AccountRVViewHolder, position: Int) {
        val course = getItem(position)
        course?.let {
            holder.bind(course)
        }
    }
}

class AccountCourseListener(val clickListener: (courseId : String) -> Unit){
    fun onClick(course : Course) = clickListener(course.id)
}