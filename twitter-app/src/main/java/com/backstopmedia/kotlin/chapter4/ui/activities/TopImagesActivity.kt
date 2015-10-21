package com.backstopmedia.kotlin.chapter4.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import com.backstopmedia.kotlin.chapter4.R
import com.backstopmedia.kotlin.chapter4.entities.Image
import com.backstopmedia.kotlin.chapter4.interactors.TopImagesInteractor
import com.backstopmedia.kotlin.chapter4.interactors.TopImagesInteractorImpl
import com.backstopmedia.kotlin.chapter4.presenters.TopImagesPresenter
import com.backstopmedia.kotlin.chapter4.presenters.TopImagesPresenterImpl
import com.backstopmedia.kotlin.chapter4.ui.NavigationHelper
import com.backstopmedia.kotlin.chapter4.ui.adapter.TopImagesAdapter
import com.backstopmedia.kotlin.chapter4.ui.view.TopImagesView
import com.twitter.sdk.android.core.TwitterCore
import kotlinx.android.synthetic.activity_top_images.recycler_view
import org.jetbrains.anko.toast

class TopImagesActivity : AppCompatActivity(), TopImagesView {

    private val interactor: TopImagesInteractor by lazy { TopImagesInteractorImpl(TwitterCore.getInstance().sessionManager.activeSession) }
    private val presenter: TopImagesPresenter by lazy { TopImagesPresenterImpl(interactor) }

    val onImageClick: (Image) -> Unit = {
        toast(it.title)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavigationHelper.setup(this, R.layout.activity_top_images)

        recycler_view.layoutManager = GridLayoutManager(this, 2)
        recycler_view.itemAnimator = DefaultItemAnimator()

        presenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView(this)
    }

    override fun bind(images: List<Image>) {
        recycler_view.adapter = TopImagesAdapter(images, onImageClick)
    }

    override fun showError(error: Throwable) {
        toast("Something went wrong")
    }
}