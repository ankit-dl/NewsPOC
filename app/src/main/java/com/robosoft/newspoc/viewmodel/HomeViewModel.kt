package com.robosoft.newspoc.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robosoft.newspoc.model.News
import com.robosoft.newspoc.model.NewsRepository
import com.robosoft.newspoc.model.NewsResponse
import com.robosoft.newspoc.model.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(private val repo:NewsRepository) : ViewModel() {
    val popularnews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val topnews: MutableLiveData<News> = MutableLiveData()
    var popularnewsPage = 1
    var newsResponse: NewsResponse? = null

    init {
        getTopNews()
        getnews()
    }

    fun getnews() = viewModelScope.launch {
        popularnews.postValue(Resource.Loading())
        val response = repo.getPopularNews(popularnewsPage)
        popularnews.postValue(handleNewsResponse(response))
    }

    fun getTopNews() = viewModelScope.launch {

        val response = repo.getTopNews(popularnewsPage)
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                resultResponse.articles.firstOrNull()?.let {
                    topnews.postValue(it)
                }
            }
        }
        popularnews.postValue(handleNewsResponse(response))
    }
    private fun handleNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                popularnewsPage++
                if(newsResponse == null) {
                    newsResponse = resultResponse
                } else {
                    val oldArticles = newsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(newsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}