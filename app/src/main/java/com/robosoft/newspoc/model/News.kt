package com.robosoft.newspoc.model

import kotlinx.android.parcel.Parcelize


data class News(
    var id: Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
):java.io.Serializable
