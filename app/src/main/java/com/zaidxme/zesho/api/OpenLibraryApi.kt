package com.zaidxme.zesho.api

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenLibraryApi {
    @GET("search.json")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("limit") limit: Int = 10
    ): OpenLibraryResponse
}

data class OpenLibraryResponse(
    val docs: List<Doc>
)

data class Doc(
    val key: String,
    val title: String,
    val author_name: List<String>?,
    val cover_i: Int?,
    val first_publish_year: Int?,
    val subject: List<String>?
)
