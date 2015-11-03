package com.backstopmedia.kotlin.ktwitter.interactors

import com.backstopmedia.kotlin.ktwitter.api.KTwitterApiClient
import com.backstopmedia.kotlin.ktwitter.utils.functional.sortKeysByValue
import com.backstopmedia.kotlin.ktwitter.utils.functional.toMultimapBy
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.models.User
import rx.Observable
import rx.functions.FuncN
import java.util.*

interface RecommendInteractor {

    /**
     * Based on your favorite users, fetch their favorite users.
     * @see [UserInteractor.getFavoriteUsers]
     * @return A list of transitive user favorites, sorted by popularity.
     */
    fun getRecommendedUsers(cursor: Int = 0): Observable<List<User>>
}

class RecommendInteractorImpl(val session: TwitterSession) : RecommendInteractor {

    override fun getRecommendedUsers(cursor: Int): Observable<List<User>> {
        val userInteractor = UserInteractorImpl(session)
        return userInteractor.getFavoriteUsers(session.userId, 20, cursor).map {
            it.map { userInteractor.getFavoriteUsers(it.first.id, 5) }
        }.concatMap {
            Observable.merge(it)
        }.map {
            val userMap = it.map { it.first }.toMapBy { it.id } // lookup users by id (user can't hash as key)
            it.toMultimapBy { it.first.id }                     // Map<Id, List<Int>>
                    .mapValues { it.value.sumBy { it.second } } // Map<Id, Int>
                    .sortKeysByValue { it }                     // List of uids sorted by corresponding fave count
                    .asReversed()                               // Reversed view to get descending order
                    .map { userMap[it]!! }                      // Reify user by id
        }
    }
}