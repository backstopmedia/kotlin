package com.backstopmedia.kotlin.ktwitter.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.backstopmedia.kotlin.ktwitter.R
import com.backstopmedia.kotlin.ktwitter.ui.NavigationHelper
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter
import com.twitter.sdk.android.tweetui.UserTimeline
import kotlinx.android.synthetic.activity_timeline.empty
import kotlinx.android.synthetic.activity_timeline.timeline_list

class TimelineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavigationHelper.setup(this, R.layout.activity_timeline)

        val userTimeline = UserTimeline.Builder()
                .screenName("fabric")
                .build()

        val adapter = TweetTimelineListAdapter.Builder(this)
                .setTimeline(userTimeline)
                .build()

        timeline_list.adapter = adapter
        timeline_list.emptyView = empty
    }
}