package com.drunken.e_study.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drunken.e_study.models.Course
import com.drunken.e_study.databinding.CourseItemBinding

class BrowseCourseAdapter(private val context : Context, private val item : ArrayList<Course>) : RecyclerView.Adapter<BrowseCourseAdapter.RecyclerViewViewHolder>() {

    class RecyclerViewViewHolder(private val binding : CourseItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(course : Course){
            binding.apply {
                courseTitle.text = course.tittle
                courseClass.text = course.classCategory
                courseMentor.text = course.mentor
                courseRatingBar.rating = course.rating
                courseRatingNum.text = course.rating.toString()
                courseImage.setImageResource(course.courseImg)

                val price = "%,d".format(course.price)
                coursePrice.text = "Rp $price"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        return RecyclerViewViewHolder(
            CourseItemBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        val course = item[position]
        holder.bind(course)
    }

    override fun getItemCount(): Int {
        return item.size
    }
}