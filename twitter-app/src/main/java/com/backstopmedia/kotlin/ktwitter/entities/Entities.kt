package com.backstopmedia.kotlin.ktwitter.entities

import com.google.gson.annotations.SerializedName
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.models.User

data class Image(val title: String, val retweetCount: Int, val mediaUrl: String) {

    companion object {
        fun fromTweet(tweet: Tweet): Image {
            val title = tweet.text?.split("http")?.firstOrNull() ?: ""
            val url = tweet.entities?.media?.firstOrNull()?.mediaUrl?.concat(":large") ?: ""
            return Image(title, tweet.retweetCount, url)
        }
    }
}

data class Profile(val handle: String, val avatarUrl: String) {

    companion object {
        fun fromUser(user: User): Profile = with(user) {
            Profile(handle = "@$screenName", avatarUrl = profileImageUrl.replace("_normal", ""))
        }
    }
}

data class RankedUser(val user: User, val tweets: List<Tweet>, val rank: Int, var following: Boolean) {

    companion object {

        /**
         * Retweets are worth twice as much as faves.
         */
        fun fromRetweets(user: User, tweets: List<Tweet>, following: Set<Long>): RankedUser {
            return RankedUser(user, tweets, tweets.size * 2, user.id in following)
        }

        /**
         * Faves are okay too, I guess.
         */
        fun fromFaves(user: User, tweets: List<Tweet>, following: Set<Long>): RankedUser {
            return RankedUser(user, tweets, tweets.size, user.id in following)
        }
    }
}

/**
 * Gson object for a list of user ids.
 */
open class UserIdList {

    @JvmField
    @SerializedName("ids")
    var ids: List<Long> = listOf()

}

data class FollowingUser(val user: User, val followed: Boolean)

class UsersList {

    @JvmField
    @SerializedName("users")
    var users: List<User> = listOf()

}