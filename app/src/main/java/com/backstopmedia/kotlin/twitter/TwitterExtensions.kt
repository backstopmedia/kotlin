package com.backstopmedia.kotlin.twitter

import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.models.Search
import com.twitter.sdk.android.core.services.SearchService
import com.twitter.sdk.android.core.services.params.Geocode

fun SearchService.tweets(query: String? = null, geocode: Geocode? = null, lang: String? = null, locale: String? = null,
                         resultType: String? = null, count: Int? = null, until: String? = null, sinceId: Long? = null,
                         maxId: Long? = null, includeEntities: Boolean? = null, callback: Callback<Search>) =
        tweets(query, geocode, lang, locale, resultType, count, until, sinceId, maxId, includeEntities, callback)