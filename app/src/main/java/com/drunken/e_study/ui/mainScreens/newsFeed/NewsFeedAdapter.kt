package com.drunken.e_study.ui.mainScreens.newsFeed

import ArticleResponseDiffUtilCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.drunken.e_study.databinding.NewsFeedRvItemBinding
import com.kwabenaberko.newsapilib.models.Article
import com.kwabenaberko.newsapilib.models.response.ArticleResponse

class NewsFeedAdapter(private val newsItemClickListener: NewsItemClickListener) : ListAdapter<Article, NewsFeedAdapter.NewsFeedViewHolder>(ArticleResponseDiffUtilCallback()) {

    inner class NewsFeedViewHolder(private val binding : NewsFeedRvItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(article : Article){
            binding.article = article
            binding.clickListener = newsItemClickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedViewHolder {
        return NewsFeedViewHolder(NewsFeedRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NewsFeedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class NewsItemClickListener(val clickListener : (newsUrl : String) -> Unit){
    fun onClick(article: Article) = clickListener(article.url)
}