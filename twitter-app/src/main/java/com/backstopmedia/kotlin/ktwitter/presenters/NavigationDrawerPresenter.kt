package com.backstopmedia.kotlin.ktwitter.presenters

import com.backstopmedia.kotlin.ktwitter.interactors.UserInteractor
import com.backstopmedia.kotlin.ktwitter.ui.view.NavigationDrawerView
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

interface NavigationDrawerPresenter : Presenter<NavigationDrawerView>

class NavigationDrawerPresenterImpl(val interactor: UserInteractor) : NavigationDrawerPresenter {

    val compositeSubscription = CompositeSubscription()

    override fun takeView(view: NavigationDrawerView) {
        val subscription = interactor.getCurrentUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.bind(it)
                })
        compositeSubscription.add(subscription)
    }

    override fun dropView(view: NavigationDrawerView) {
        compositeSubscription.unsubscribe()
    }
}