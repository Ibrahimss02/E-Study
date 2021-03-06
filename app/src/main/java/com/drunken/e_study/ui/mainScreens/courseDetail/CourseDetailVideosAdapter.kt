package com.drunken.e_study.ui.mainScreens.courseDetail

import MapDiffUtilCallback
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.drunken.e_study.databinding.CourseDetailRvVideosItemBinding

class CourseDetailVideosAdapter(private val videoClickListener : VideoClickListener) : ListAdapter<Map<String, String>?, CourseDetailVideosAdapter.CourseDetailVideosViewHolder>(
        MapDiffUtilCallback()
    ) {

    inner class CourseDetailVideosViewHolder(private val binding: CourseDetailRvVideosItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(videosTitle: String, url: String) {
            binding.title = videosTitle
            binding.url = url
            binding.videoCallback = videoClickListener
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseDetailVideosViewHolder {
        return CourseDetailVideosViewHolder(
            CourseDetailRvVideosItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
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

class VideoClickListener(val url : (String) -> Unit){
    fun onClick(url : String) = url(url)
}