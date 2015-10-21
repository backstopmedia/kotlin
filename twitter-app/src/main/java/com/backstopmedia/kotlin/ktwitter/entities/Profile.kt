package com.backstopmedia.kotlin.ktwitter.entities

import com.twitter.sdk.android.core.models.User

data class Profile(val handle: String, val avatarUrl: String) {

    companion object {
        fun fromUser(user: User): Profile = with(user) {
            Profile(handle = "@$screenName", avatarUrl = profileImageUrl.replace("_normal", ""))
        }
    }
}