package com.backstopmedia.kotlin.twitter.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import butterknife.bindView
import com.backstopmedia.kotlin.R
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter
import com.twitter.sdk.android.tweetui.UserTimeline


/**
 * Created by Aaron Sarazan on 10/11/15.
 * Copyright(c) 2015 Level, Inc.
 */
class TimelineActivity : AppCompatActivity() {

    val list: ListView by bindView(android.R.id.list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)
        val timeline = UserTimeline.Builder().screenName("asarazan").build()
        list.adapter = TweetTimelineListAdapter(this, timeline)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.logout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                Twitter.logOut();
                startActivity(Intent(this, LandingActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}