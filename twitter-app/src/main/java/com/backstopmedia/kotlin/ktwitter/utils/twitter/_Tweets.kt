package com.backstopmedia.kotlin.ktwitter.utils.twitter

import com.backstopmedia.kotlin.ktwitter.utils.functional.toMultimapBy
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.models.User
import rx.Observable

fun List<Tweet>.tweetsByUser(): Map<Long, List<Tweet>> {
    return toMultimapBy { it.user.id }
}
