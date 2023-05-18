package com.robosoft.newspoc.di


import com.robosoft.newspoc.Const.API_KEY
import com.robosoft.newspoc.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getTopNews(
        @Query("country")
        countryCode: String = "in",
        @Query("pagesize")
        pageNumber: Int = 10,
        @Query("page")
        page: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun getPopular(
        @Query("q")
        countryCode: String = "ipl",
        @Query("pagesize")
        pageNumber: Int = 10,
        @Query("page")
        page: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}