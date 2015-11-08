package com.backstopmedia.kotlin.ktwitter.utils.twitter

import com.backstopmedia.kotlin.ktwitter.entities.RankedUser
import com.twitter.sdk.android.core.models.User

fun List<RankedUser>.userMap(): Map<Long, RankedUser> = toMapBy { it.user.id }