package com.wordpresssync.app.api

import com.wordpresssync.app.api.model.WpPost
import com.wordpresssync.app.api.model.WpUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Интерфейс WordPress REST API.
 * Базовый URL задаётся при создании Retrofit (динамически из настроек).
 */
interface WordPressApi {

    @GET("wp/v2/posts")
    suspend fun getPosts(
        @Query("per_page") perPage: Int = 20,
        @Query("page") page: Int = 1,
        @Query("_embed") embed: Boolean = true
    ): Response<List<WpPost>>

    @GET("wp/v2/users")
    suspend fun getUsers(
        @Query("per_page") perPage: Int = 50,
        @Query("page") page: Int = 1
    ): Response<List<WpUser>>
}
