package com.backstopmedia.kotlin.chapter4.entities

import com.twitter.sdk.android.core.models.Tweet

data class Image(val title: String, val retweetCount: Int, val mediaUrl: String) {

    companion object {
        fun fromTweet(tweet: Tweet): Image {
            val title = tweet.text?.split("http")?.firstOrNull() ?: ""
            val url = tweet.entities?.media?.firstOrNull()?.mediaUrl?.concat(":large") ?: ""
            return Image(title, tweet.retweetCount, url)
        }
    }
}