package com.backstopmedia.kotlin.ktwitter.presenters

import com.backstopmedia.kotlin.ktwitter.entities.Image
import com.backstopmedia.kotlin.ktwitter.interactors.TopImagesInteractor
import com.backstopmedia.kotlin.ktwitter.ui.view.TopImagesView
import com.backstopmedia.kotlin.ktwitter.utils.BaseSubscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

interface TopImagesPresenter : Presenter<TopImagesView>

class TopImagesPresenterImpl(val interactor: TopImagesInteractor) : TopImagesPresenter {

    val compositeSubscription = CompositeSubscription()

    override fun takeView(view: TopImagesView) {
        val subscription = interactor.getMostRecentImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseSubscriber<List<Image>>() {
                    override fun onError(e: Throwable) {
                        view.showError(error = e)
                    }

                    override fun onNext(t: List<Image>) {
                        view.bind(images = t)
                    }
                })
        compositeSubscription.add(subscription)
    }

    override fun dropView(view: TopImagesView) {
        compositeSubscription.unsubscribe()
    }
}
