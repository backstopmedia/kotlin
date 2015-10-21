package com.backstopmedia.kotlin.chapter4.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.backstopmedia.kotlin.chapter4.R
import com.backstopmedia.kotlin.chapter4.ui.NavigationHelper
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter
import com.twitter.sdk.android.tweetui.UserTimeline
import kotlinx.android.synthetic.activity_timeline.empty
import kotlinx.android.synthetic.activity_timeline.timeline_list
import org.jetbrains.anko.alert
import org.jetbrains.anko.async
import org.jetbrains.anko.frameLayout

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

        frameLayout {
            visibility = View.GONE
        }
    }
}