package com.backstopmedia.kotlin.chapter4.entities

import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.models.User

/**
 * Created by Tudor Luca on 14/10/15.
 */
data class Profile(val handle: String, val avatarUrl: String) {

    companion object {
        fun fromUser(user: User): Profile = with(user) {
            Profile(handle = "@$screenName", avatarUrl = profileImageUrl.replace("_normal", ""))
        }
    }
}

data class Image(val title: String, val retweetCount: Int, val mediaUrl: String) {

    companion object {
        fun fromTweet(tweet: Tweet): Image {
            val title = tweet.text?.split("http")?.firstOrNull() ?: ""
            val url = tweet.entities?.media?.firstOrNull()?.mediaUrl?.concat(":large") ?: ""
            return Image(title, tweet.retweetCount, url)
        }
    }
}

