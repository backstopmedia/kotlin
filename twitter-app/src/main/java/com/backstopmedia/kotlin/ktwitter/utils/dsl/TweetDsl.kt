package com.backstopmedia.kotlin.ktwitter.utils.dsl

import android.util.Log
import android.widget.Toast
import com.backstopmedia.kotlin.ktwitter.utils.kallback
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.models.*

/**
 * Allows us to build a tweet via DSL, then post it asynchronously using the API service.
 */
fun Tweet.post(callback: Callback<Tweet>) {
    Twitter.getApiClient().statusesService.update(
            text,
            inReplyToStatusId,
            possiblySensitive,
            coordinates?.latitude,
            coordinates?.longitude,
            place?.id,
            true,
            false,
            null,
            callback)
}

/**
 * This will allow you to use a block-based DSL syntax for constructing a tweet.
 * It's a really nice alternative to the builder pattern
 * (and is generally even compatible with it, via extensions).
 *
 * @sample
 * tweet {
 *  text = "HAY YOU GUYS"
 * }
 */

fun tweetAboutHowGreatKotlinBuildersAre() {
    tweet {
        text = "Hey I'm tweeting with Kotlin builders!"
		possiblySensitive = false
    }.post(kallback<Tweet> {
        Log.d("Tweets", "Successfully tweeted ${it.data.id}")
    } onFail {
        Log.e("Tweets", "That didn't work")
    })
}

inline fun tweet(init: TweetBuilder.() -> Unit): Tweet {
    return TweetBuilder().apply(init).build()
}

/**
 * These vars were auto-generated based on the methods in [TweetBuilder]
 * We copy/pasted the methods over from that (decompiled) source file,
 * then we came up with a pretty gnarly regex to convert them into extension vars.
 * You can do this pretty easily by using Android Studio's Find/Replace feature.
 *
 * Unfortunately, because the members themselves have private visibility, we're unable to create
 * getters for the vars, so if you try to get() them, you'll crash.
 *
 * @sample:
 * fun setId(id:Long):TweetBuilder {
 * 	  this.id = id
 * 	  return this
 * }
 *
 * @find
 * fun (\w*)\((\w*)\: ([\w\?\<\>]*)\)\: (\w*) \{\n\s*this.(\w*) = (\w*)\n\s*return this\n\} *
 *
 * @replace
 * var $4.$2: $3\n\tget() = throw UnsupportedOperationException()\n\tset(value) { $1(value) }
 */

var TweetBuilder.coordinates: Coordinates
    get() = throw UnsupportedOperationException()
    set(value) { setCoordinates(value) }

var TweetBuilder.createdAt: String
	get() = throw UnsupportedOperationException()
	set(value) { setCreatedAt(value) }

var TweetBuilder.currentUserRetweet: Any
	get() = throw UnsupportedOperationException()
	set(value) { setCurrentUserRetweet(value) }

var TweetBuilder.entities: TweetEntities
	get() = throw UnsupportedOperationException()
	set(value) { setEntities(value) }

var TweetBuilder.favoriteCount: Int?
	get() = throw UnsupportedOperationException()
	set(value) { setFavoriteCount(value) }

var TweetBuilder.favorited: Boolean
	get() = throw UnsupportedOperationException()
	set(value) { setFavorited(value) }

var TweetBuilder.filterLevel: String
	get() = throw UnsupportedOperationException()
	set(value) { setFilterLevel(value) }

var TweetBuilder.id: Long
	get() = throw UnsupportedOperationException()
	set(value) { setId(value) }

var TweetBuilder.idStr: String
	get() = throw UnsupportedOperationException()
	set(value) { setIdStr(value) }

var TweetBuilder.inReplyToScreenName: String
	get() = throw UnsupportedOperationException()
	set(value) { setInReplyToScreenName(value) }

var TweetBuilder.inReplyToStatusId: Long
	get() = throw UnsupportedOperationException()
	set(value) { setInReplyToStatusId(value) }

var TweetBuilder.inReplyToStatusIdStr: String
	get() = throw UnsupportedOperationException()
	set(value) { setInReplyToStatusIdStr(value) }

var TweetBuilder.inReplyToUserId: Long
	get() = throw UnsupportedOperationException()
	set(value) { setInReplyToUserId(value) }

var TweetBuilder.inReplyToUserIdStr: String
	get() = throw UnsupportedOperationException()
	set(value) { setInReplyToUserIdStr(value) }

var TweetBuilder.lang: String
	get() = throw UnsupportedOperationException()
	set(value) { setLang(value) }

var TweetBuilder.place: Place
	get() = throw UnsupportedOperationException()
	set(value) { setPlace(value) }

var TweetBuilder.possiblySensitive: Boolean
	get() = throw UnsupportedOperationException()
	set(value) { setPossiblySensitive(value) }

var TweetBuilder.scopes: Any
	get() = throw UnsupportedOperationException()
	set(value) { setScopes(value) }

var TweetBuilder.retweetCount: Int
	get() = throw UnsupportedOperationException()
	set(value) { setRetweetCount(value) }

var TweetBuilder.retweeted: Boolean
	get() = throw UnsupportedOperationException()
	set(value) { setRetweeted(value) }

var TweetBuilder.retweetedStatus: Tweet
	get() = throw UnsupportedOperationException()
	set(value) { setRetweetedStatus(value) }

var TweetBuilder.source: String
	get() = throw UnsupportedOperationException()
	set(value) { setSource(value) }

var TweetBuilder.text: String
	get() = throw UnsupportedOperationException()
	set(value) { setText(value) }

var TweetBuilder.truncated: Boolean
	get() = throw UnsupportedOperationException()
	set(value) { setTruncated(value) }

var TweetBuilder.user: User
	get() = throw UnsupportedOperationException()
	set(value) { setUser(value) }

var TweetBuilder.withheldCopyright: Boolean
	get() = throw UnsupportedOperationException()
	set(value) { setWithheldCopyright(value) }

var TweetBuilder.withheldInCountries: List<String>
	get() = throw UnsupportedOperationException()
	set(value) { setWithheldInCountries(value) }

var TweetBuilder.withheldScope: String
	get() = throw UnsupportedOperationException()
	set(value) { setWithheldScope(value) }