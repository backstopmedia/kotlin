package com.backstopmedia.kotlin.twitter

import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.models.Search
import com.twitter.sdk.android.core.services.SearchService
import com.twitter.sdk.android.core.services.params.Geocode

fun SearchService.tweets(s: String? = null, geocode: Geocode? = null, s1: String? = null, s2: String? = null,
                         s3: String? = null,integer: Int? = null, s4: String? = null, aLong: Long? = null,
                         aLong1: Long? = null, aBoolean: Boolean? = null, callback: Callback<Search>) =
        tweets(s, geocode, s1, s2, s3, integer, s4, aLong, aLong1, aBoolean, callback)