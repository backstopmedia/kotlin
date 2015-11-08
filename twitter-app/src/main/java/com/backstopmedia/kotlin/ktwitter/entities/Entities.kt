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

/**
 * A User object, with FaveRank™ technology
 *
 * We've also added a [following] boolean, which is mutable so that we don't have to
 * hit the server again just to reflect that a follow operation succeeded.
 */
data class RankedUser(val user: User, val rank: Int, var following: Boolean) {

    companion object {

        /**
         * Retweets are worth twice as much as faves.
         */
        fun fromRetweets(user: User, tweets: List<Tweet>, following: Set<Long>): RankedUser {
            return RankedUser(user, tweets.size * 2, user.id in following)
        }

        /**
         * Faves are still pretty good.
         */
        fun fromFaves(user: User, tweets: List<Tweet>, following: Set<Long>): RankedUser {
            return RankedUser(user, tweets.size, user.id in following)
        }
    }

    operator fun plus(other: RankedUser?): RankedUser {
        if (other == null) return this
        if (user.id != other.user.id) throw IllegalArgumentException("Illegal attempt to combine user ${user.id} with ${other.user.id}")
        return RankedUser(user, rank + other.rank, following || other.following)
    }

    override fun equals(other: Any?): Boolean {
        return other is RankedUser && other.user.id == user.id
    }

    override fun hashCode(): Int {
        return user.id.hashCode()
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