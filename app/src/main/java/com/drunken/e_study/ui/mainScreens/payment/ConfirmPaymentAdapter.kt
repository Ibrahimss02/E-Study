package com.drunken.e_study.ui.mainScreens.payment

import ItemDiffUtilCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.drunken.e_study.database.Course
import com.drunken.e_study.databinding.PaymentRvItemBinding
import com.drunken.e_study.ui.mainScreens.cart.CourseListener

class ConfirmPaymentAdapter(private val clickListener: CourseListener) : ListAdapter<Course, ConfirmPaymentAdapter.PaymentViewHolder>(ItemDiffUtilCallback()) {

    class PaymentViewHolder(private val binding : PaymentRvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(course : Course, clickListener: CourseListener){
            binding.course = course
            binding.clickListener = clickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        return PaymentViewHolder(
            PaymentRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val course = getItem(position)
        course?.let {
            holder.bind(course, clickListener)
        }
    }
}

class CourseListener(val clickListener: (courseId: String) -> Unit) {
    fun onClick(course: Course) = clickListener(course.id)
}