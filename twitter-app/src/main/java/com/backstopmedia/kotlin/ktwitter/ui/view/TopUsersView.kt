package com.backstopmedia.kotlin.ktwitter.ui.view

import com.backstopmedia.kotlin.ktwitter.entities.RankedUser
import com.twitter.sdk.android.core.models.User

interface TopUsersView {

    fun bind(users: List<RankedUser>)

    fun showError(error: Throwable)
}