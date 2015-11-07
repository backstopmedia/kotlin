package com.backstopmedia.kotlin.ktwitter.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.backstopmedia.kotlin.ktwitter.R
import com.backstopmedia.kotlin.ktwitter.entities.Image
import com.backstopmedia.kotlin.ktwitter.interactors.TopUsersInteractor
import com.backstopmedia.kotlin.ktwitter.interactors.TopUsersInteractorImpl
import com.backstopmedia.kotlin.ktwitter.presenters.TopUsersPresenter
import com.backstopmedia.kotlin.ktwitter.presenters.TopUsersPresenterImpl
import com.backstopmedia.kotlin.ktwitter.ui.NavigationHelper
import com.backstopmedia.kotlin.ktwitter.ui.adapter.TopImagesAdapter
import com.backstopmedia.kotlin.ktwitter.ui.adapter.TopUsersAdapter
import com.backstopmedia.kotlin.ktwitter.ui.view.TopUsersView
import com.backstopmedia.kotlin.ktwitter.utils.os.getExtra
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.models.User
import kotlinx.android.synthetic.activity_top_images.*
import org.jetbrains.anko.toast

/**
 * Created by Aaron Sarazan on 11/4/15
 * Copyright(c) 2015 Level, Inc.
 */
class TopUsersActivity : AppCompatActivity(), TopUsersView {

    private val userId by lazy {
        getExtra<Long>("userId")
    }

    private val interactor: TopUsersInteractor by lazy { TopUsersInteractorImpl(TwitterCore.getInstance().sessionManager.activeSession) }
    private val presenter: TopUsersPresenter by lazy { TopUsersPresenterImpl(interactor, userId) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavigationHelper.setup(this, R.layout.activity_coordinator_recycler)
        recycler_view.layoutManager = LinearLayoutManager(this)
        presenter.takeView(this)
        if (userId == null) {
            toast("Tap somebody to see their favorite users.")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView(this)
    }

    override fun bind(users: List<User>) {
        recycler_view.adapter = TopUsersAdapter(users) {
            startActivity(Intent(this, TopUsersActivity::class.java).putExtra("userId", it.id))
        }
    }

    override fun showError(error: Throwable) {
        toast("Something went wrong")
    }
}