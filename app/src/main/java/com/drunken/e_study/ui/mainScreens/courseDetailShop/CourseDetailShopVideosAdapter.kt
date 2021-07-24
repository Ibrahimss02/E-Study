package com.drunken.e_study.ui.mainScreens.courseDetailShop

import MapDiffUtilCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.drunken.e_study.databinding.CourseDetailShopRvItemBinding

class CourseDetailShopVideosAdapter : ListAdapter<Map<String, String>?, CourseDetailShopVideosAdapter.CourseDetailVideosViewHolder>(MapDiffUtilCallback()) {

    class CourseDetailVideosViewHolder(private val binding : CourseDetailShopRvItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(videosTitle : String, url : String) {
            binding.title = videosTitle
            binding.url = url
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
        val videos = getItem(position)
        val videosTitle = videos?.keys?.toList()?.get(0)
        videosTitle?.let {
            holder.bind(it, videos[it]!!)
        }
    }
}