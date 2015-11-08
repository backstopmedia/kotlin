package com.backstopmedia.kotlin.ktwitter.ui.view

import com.backstopmedia.kotlin.ktwitter.entities.RankedUser
import com.twitter.sdk.android.core.models.User

/**
 * Created by Aaron Sarazan on 11/4/15
 * Copyright(c) 2015 Level, Inc.
 */
interface TopUsersView {

    fun bind(users: List<RankedUser>)

    fun showError(error: Throwable)
}