package com.wordpresssync.app.api.model

import com.google.gson.annotations.SerializedName

data class WpPost(
    val id: Long,
    val date: String?,
    val title: Rendered?,
    val content: Rendered?,
    val excerpt: Rendered?,
    @SerializedName("featured_media") val featuredMedia: Long = 0,
    val _embedded: Embedded? = null
) {
    data class Rendered(val rendered: String?)
    data class Embedded(
        @SerializedName("wp:featuredmedia") val featuredMedia: List<FeaturedMedia>?
    )
    data class FeaturedMedia(
        val source_url: String?
    )

    fun getFeaturedImageUrl(): String? =
        _embedded?.featuredMedia?.firstOrNull()?.source_url
}
