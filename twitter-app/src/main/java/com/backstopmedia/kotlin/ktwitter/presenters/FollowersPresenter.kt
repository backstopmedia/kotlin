package com.backstopmedia.kotlin.ktwitter.presenters

import com.backstopmedia.kotlin.ktwitter.entities.FollowingUser
import com.backstopmedia.kotlin.ktwitter.interactors.FollowersInteractor
import com.backstopmedia.kotlin.ktwitter.ui.view.FollowersView
import com.backstopmedia.kotlin.ktwitter.utils.BaseSubscriber
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.models.User
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

interface FollowersPresenter : Presenter<FollowersView>

class FollowersPresenterImpl(val interactor: FollowersInteractor) : FollowersPresenter {

    private val compositeSubscription = CompositeSubscription()

    override fun takeView(view: FollowersView) {
        val userId = Twitter.getSessionManager().activeSession.userId
        val subscription = interactor.getFollowers(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseSubscriber<List<FollowingUser>>() {
                    override fun onError(e: Throwable) {
                        view.showError(e)
                    }

                    override fun onNext(t: List<FollowingUser>) {
                        view.bind(t)
                    }
                })
        compositeSubscription.add(subscription)
    }

    override fun dropView(view: FollowersView) {
        compositeSubscription.unsubscribe()
    }
}