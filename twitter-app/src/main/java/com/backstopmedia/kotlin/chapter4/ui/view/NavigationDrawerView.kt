package com.backstopmedia.kotlin.chapter4.ui.view

import com.backstopmedia.kotlin.chapter4.entities.Profile

interface NavigationDrawerView {

    fun bind(userProfile: Profile)
}