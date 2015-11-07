package com.backstopmedia.kotlin.ktwitter.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.backstopmedia.kotlin.ktwitter.R
import com.backstopmedia.kotlin.ktwitter.entities.FollowingUser
import com.backstopmedia.kotlin.ktwitter.interactors.FollowersInteractor
import com.backstopmedia.kotlin.ktwitter.interactors.FollowersInteractorImpl
import com.backstopmedia.kotlin.ktwitter.presenters.FollowersPresenter
import com.backstopmedia.kotlin.ktwitter.presenters.FollowersPresenterImpl
import com.backstopmedia.kotlin.ktwitter.ui.NavigationHelper
import com.backstopmedia.kotlin.ktwitter.ui.adapter.FollowersAdapter
import com.backstopmedia.kotlin.ktwitter.ui.view.FollowersView
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.models.User
import org.jetbrains.anko.toast
import kotlinx.android.synthetic.activity_followers.*

class FollowersActivity : AppCompatActivity(), FollowersView {

    private val interactor: FollowersInteractor by lazy { FollowersInteractorImpl(TwitterCore.getInstance().sessionManager.activeSession) }
    private val presenter: FollowersPresenter by lazy { FollowersPresenterImpl(interactor) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavigationHelper.setup(this, R.layout.activity_followers)
        recyclerView.layoutManager = LinearLayoutManager(this)
        presenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView(this)
    }

    override fun bind(users: List<FollowingUser>) {
        recyclerView.adapter = FollowersAdapter(users)
    }

    override fun showError(error: Throwable) {
        toast("Couldn't load list. You probably crossed the rate limit.")
    }
}