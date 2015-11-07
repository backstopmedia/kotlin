package com.backstopmedia.kotlin.ktwitter.ui.view

import com.backstopmedia.kotlin.ktwitter.entities.FollowingUser

interface FollowersView {

    fun bind(users: List<FollowingUser>)

    fun showError(error: Throwable)
}