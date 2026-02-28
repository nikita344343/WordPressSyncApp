package com.wordpresssync.app.api.model

data class WpUser(
    val id: Long,
    val name: String?,
    val slug: String?,
    val avatar_urls: Map<String, String>? = null
)
