package com.drunken.e_study.ui.mainScreens.cart

import ItemDiffUtilCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.drunken.e_study.dto.Course
import com.drunken.e_study.databinding.CartRvItemBinding

class CartAdapter(private val clickListener: CourseListener) : ListAdapter<Course, CartAdapter.CartViewHolder>(ItemDiffUtilCallback()) {

    class CartViewHolder(private val binding : CartRvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(course : Course, clickListener: CourseListener){
            binding.course = course
            binding.clickListener = clickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            CartRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val course = getItem(position)
        course?.let {
            holder.bind(course, clickListener)
        }
    }
}

class CourseListener(val clickListener: (courseId: String) -> Unit, val deleteListener : (course: Course) -> Unit) {
    fun onClick(course: Course) = clickListener(course.id)
    fun onDelete(course : Course) = deleteListener(course)
}