package com.drunken.e_study.ui.mainScreens.newsFeed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.Article
import com.kwabenaberko.newsapilib.models.request.EverythingRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse

class NewsFeedViewModel(private val apiKey : String) : ViewModel() {

    private val _newsItem = MutableLiveData<ArrayList<Article>>()
    val newsItem : LiveData<ArrayList<Article>>
    get() = _newsItem

    init {
        fetchNews()
    }

    private fun fetchNews(){
        val newsApiClient = NewsApiClient(apiKey)
        val newsItem = arrayListOf<Article>()

        newsApiClient.getEverything(
            EverythingRequest.Builder()
                .q("beasiswa OR sd OR smp OR sma OR smk OR universitas OR kampus OR belajar OR pendidikan")
                .language("id")
                .build(),
            object : NewsApiClient.ArticlesResponseCallback {
                override fun onSuccess(response: ArticleResponse) {
                    response.articles.forEach {
                        newsItem.add(it)
                    }
                    _newsItem.value = newsItem
                }

                override fun onFailure(throwable: Throwable) {
                    throwable.message?.let { Log.e("Error", it) }
                }
            }
        )
    }
}