package com.robosoft.newspoc.model

import com.robosoft.newspoc.di.NewsAPI


class NewsRepository(val api: NewsAPI) {
    suspend fun getPopularNews(page:Int) = api.getPopular(page=page)

    suspend fun getTopNews(page:Int) = api.getTopNews(page=page)




}