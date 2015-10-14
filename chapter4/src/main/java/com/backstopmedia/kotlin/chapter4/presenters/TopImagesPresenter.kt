package com.backstopmedia.kotlin.chapter4.presenters

import com.backstopmedia.kotlin.chapter4.interactors.TopImagesInteractor
import com.backstopmedia.kotlin.chapter4.ui.view.TopImagesView
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

/**
 * Created by Tudor Luca on 14/10/15.
 */
interface TopImagesPresenter : Presenter<TopImagesView>

class TopImagesPresenterImpl(val interactor: TopImagesInteractor) : TopImagesPresenter {

    val compositeSubscription = CompositeSubscription()

    override fun takeView(view: TopImagesView) {
        val subscription = interactor.getMostRecentImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.bind(it)
                }, {
                    view.showError(it)
                })
        compositeSubscription.add(subscription)
    }

    override fun dropView(view: TopImagesView) {
        compositeSubscription.unsubscribe()
    }
}
