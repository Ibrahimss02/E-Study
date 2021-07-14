package com.drunken.e_study.mainScreens.browseCourse

import ItemDiffUtilCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.drunken.e_study.database.Course
import com.drunken.e_study.databinding.CourseItemBinding

class BrowseCourseAdapter(private val courseList : ArrayList<Course> = ArrayList(),
private val clickListener: CourseListener): androidx.recyclerview.widget.ListAdapter<Course, BrowseCourseAdapter.RecyclerViewViewHolder>(
    ItemDiffUtilCallback()
){

    var cloneItem: MutableList<Course> = courseList

    class RecyclerViewViewHolder(private val binding: CourseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(course: Course, clickListener: CourseListener) {
            binding.course = course
            binding.clickListener = clickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        return RecyclerViewViewHolder(
            CourseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        val course = getItem(position)
        holder.bind(course, clickListener)
    }

    // TODO Filter for search view *(not working yet)
//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val charString = constraint.toString()
//                if (charString.isEmpty()) {
//                    cloneItem = courseList
//                } else {
//                    val filteredList = courseList.filter {
//                        it.title?.lowercase()?.contains(charString)!!
//                        it.desc?.lowercase()?.contains(charString)!!
//                        it.classCategory?.lowercase()?.contains(charString)!!
//                        it.mentor?.lowercase()?.contains(charString)!!
//                    }.toMutableList()
//                    cloneItem = filteredList
//                }
//                val filterResults = FilterResults()
//                filterResults.values = cloneItem
//
//                return filterResults
//            }
//
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                submitList(results!!.values as MutableList<Course>)
//                notifyDataSetChanged()
//            }
//
//        }
//    }
}

class CourseListener(val clickListener: (courseId: String) -> Unit) {
    fun onClick(course: Course) = clickListener(course.id)
}