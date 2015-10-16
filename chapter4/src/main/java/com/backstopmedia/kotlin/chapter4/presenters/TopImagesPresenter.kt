package com.backstopmedia.kotlin.chapter4.presenters

import com.backstopmedia.kotlin.chapter4.entities.Image
import com.backstopmedia.kotlin.chapter4.interactors.TopImagesInteractor
import com.backstopmedia.kotlin.chapter4.ui.view.TopImagesView
import com.backstopmedia.kotlin.chapter4.utils.BaseSubscriber
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
