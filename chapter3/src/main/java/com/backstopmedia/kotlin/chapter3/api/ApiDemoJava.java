package com.backstopmedia.kotlin.chapter3.api;

import android.util.Log;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Search;

/**
 * Created by Aaron Sarazan on 10/11/15
 * Copyright(c) 2015 Level, Inc.
 */
public class ApiDemoJava {

    public static void basicSearch() {
        Twitter.getInstance().core.logInGuest(new Callback<AppSession>() {
            @Override
            public void success(Result<AppSession> result) {
                Twitter.getApiClient().getSearchService().tweets("Kotlin", null, null, null, null, null, null, null, null, null, new Callback<Search>() {
                    @Override
                    public void success(Result<Search> result) {
                        int tweetCount = result.data.tweets.size();
                        Log.i("ApiDemoKotlin", "Found " + tweetCount + " tweets.");
                    }

                    @Override
                    public void failure(TwitterException e) {
                    }
                });
            }

            @Override
            public void failure(TwitterException e) {
            }
        });
    }
}
