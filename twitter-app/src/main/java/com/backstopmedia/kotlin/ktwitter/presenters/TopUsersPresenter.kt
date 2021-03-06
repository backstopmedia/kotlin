package com.backstopmedia.kotlin.ktwitter.presenters

import com.backstopmedia.kotlin.ktwitter.entities.Image
import com.backstopmedia.kotlin.ktwitter.entities.RankedUser
import com.backstopmedia.kotlin.ktwitter.interactors.TopUsersInteractor
import com.backstopmedia.kotlin.ktwitter.ui.view.TopUsersView
import com.backstopmedia.kotlin.ktwitter.utils.BaseSubscriber
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.models.User
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

interface TopUsersPresenter : Presenter<TopUsersView>

class TopUsersPresenterImpl(val interactor: TopUsersInteractor, val userId: Long? = null) : TopUsersPresenter {

    private val compositeSubscription = CompositeSubscription()

    override fun takeView(view: TopUsersView) {
        val nonNullUid = userId ?: Twitter.getSessionManager().activeSession.userId
        val sub = interactor.getTopUsers(nonNullUid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseSubscriber<List<RankedUser>>() {
                    override fun onNext(t: List<RankedUser>) {
                        view.bind(t)
                    }
                    override fun onError(e: Throwable) {
                        view.showError(error = e)
                    }
                })
        compositeSubscription.add(sub)
    }

    override fun dropView(view: TopUsersView) {
        compositeSubscription.unsubscribe()
    }
}