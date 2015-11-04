package com.backstopmedia.kotlin.ktwitter.ui

import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.backstopmedia.kotlin.ktwitter.R
import com.backstopmedia.kotlin.ktwitter.entities.Profile
import com.backstopmedia.kotlin.ktwitter.entities.RankedUser
import com.backstopmedia.kotlin.ktwitter.interactors.TopUsersInteractorImpl
import com.backstopmedia.kotlin.ktwitter.interactors.UserInteractor
import com.backstopmedia.kotlin.ktwitter.interactors.UserInteractorImpl
import com.backstopmedia.kotlin.ktwitter.presenters.NavigationDrawerPresenter
import com.backstopmedia.kotlin.ktwitter.presenters.NavigationDrawerPresenterImpl
import com.backstopmedia.kotlin.ktwitter.ui.activities.TimelineActivity
import com.backstopmedia.kotlin.ktwitter.ui.activities.TopImagesActivity
import com.backstopmedia.kotlin.ktwitter.ui.view.NavigationDrawerView
import com.backstopmedia.kotlin.ktwitter.utils.functional.toMultimapBy
import com.backstopmedia.kotlin.ktwitter.utils.glide.CircleTransformation
import com.bumptech.glide.Glide
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.TwitterCore
import org.jetbrains.anko.find
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import kotlin.properties.Delegates

class NavigationHelper : Fragment(), NavigationDrawerView {

    companion object {
        fun setup(activity: AppCompatActivity, @LayoutRes resId: Int): NavigationHelper {
            val navigationHelper = activity.attachNavigationHelper()
            navigationHelper.setContentViewForActivity(activity, resId)
            return navigationHelper
        }
    }

    private val interactor: UserInteractor by lazy { UserInteractorImpl(TwitterCore.getInstance().sessionManager.activeSession) }
    private val presenter: NavigationDrawerPresenter  by lazy { NavigationDrawerPresenterImpl(interactor) }

    private var isSubdecorInstalled = false
    private var drawerLayout: DrawerLayout by Delegates.notNull()
    private var drawerToggle: ActionBarDrawerToggle by Delegates.notNull()
    private var navigationView: NavigationView by Delegates.notNull()
    private var content: ViewGroup by Delegates.notNull()
    private var username: TextView by Delegates.notNull()
    private var avatar: ImageView by Delegates.notNull()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNavigationView()
        setHasOptionsMenu(true)
        presenter.takeView(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.setupSimpleToolbar(toolbarId = R.id.toolbar)
    }

    override fun onStart() {
        super.onStart()
        drawerToggle.syncState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.dropView(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (drawerToggle.onOptionsItemSelected(item)) {
        true -> true
        else -> super.onOptionsItemSelected(item)
    }

    override fun bind(userProfile: Profile) {
        username.text = userProfile.handle
        Glide.with(activity)
                .load(userProfile.avatarUrl)
                .transform(CircleTransformation(activity))
                .into(avatar)
    }

    private fun setupNavigationView() {
        navigationView.setNavigationItemSelectedListener {
            if (it.itemId == R.id.drawer_recommend) {
                Twitter.getSessionManager().activeSession.let {
                    TopUsersInteractorImpl(it).getTopUsers(it.userId)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe {
                                val log = it.map { "${it.user.screenName} (${it.rank})" }
                                Log.d("Recommend", log.toString())
                            }
                }
            }
            val intent = when (it.itemId) {
                R.id.drawer_timeline ->
                    if (activity is TimelineActivity) null
                    else Intent(activity, TimelineActivity::class.java)
                R.id.drawer_top_images ->
                    if (activity is TopImagesActivity) null
                    else Intent(activity, TopImagesActivity::class.java)
                else -> null
            }

            drawerLayout.closeDrawers()

            intent?.let {
                activity.startActivity(it)
                activity.finish()
            }

            true
        }
    }

    private fun setContentViewForActivity(activity: AppCompatActivity, @LayoutRes resId: Int) {
        ensureSubDecor(activity)
        content.removeAllViews()
        LayoutInflater.from(activity).inflate(resId, content, true)
        activity.window.callback.onContentChanged()
    }

    private fun ensureSubDecor(activity: AppCompatActivity) {
        if (isSubdecorInstalled) {
            return
        }

        activity.setContentView(R.layout.navigation_drawer_base)
        findViewsIn(activity)

        drawerToggle = ActionBarDrawerToggle(activity, drawerLayout, R.string.drawer_open, R.string.drawer_close)
        drawerLayout.setDrawerListener(drawerToggle)

        isSubdecorInstalled = true
    }

    private fun findViewsIn(activity: AppCompatActivity) {
        content = activity.find(R.id.content)
        drawerLayout = activity.find(R.id.drawer_layout)
        navigationView = activity.find(R.id.navigation_view)
        username = activity.find(R.id.username)
        avatar = activity.find(R.id.avatar)
    }
}

fun AppCompatActivity.attachNavigationHelper(): NavigationHelper {
    val fm = this.supportFragmentManager

    var frag = fm.findFragmentByTag(FRAG_TAG) as NavigationHelper?
    if (frag == null) {
        frag = NavigationHelper()
        fm.beginTransaction().add(frag, FRAG_TAG).commit()
    }

    return frag
}

fun AppCompatActivity.setupSimpleToolbar(@IdRes toolbarId: Int): Toolbar {
    val toolbar = find<Toolbar>(toolbarId)
    setSupportActionBar(toolbar)

    val actionBar = supportActionBar
    actionBar?.let {
        it.setHomeButtonEnabled(true)
        it.setDisplayHomeAsUpEnabled(true)
        it.setDisplayShowTitleEnabled(true)
    }

    return toolbar
}

private const val FRAG_TAG: String = "NavigationHelper"
