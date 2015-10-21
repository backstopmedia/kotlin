package com.backstopmedia.kotlin.ktwitter.ui.view

import com.backstopmedia.kotlin.ktwitter.entities.Profile

interface NavigationDrawerView {

    fun bind(userProfile: Profile)
}