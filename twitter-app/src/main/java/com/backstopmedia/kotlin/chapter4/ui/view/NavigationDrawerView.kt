package com.backstopmedia.kotlin.chapter4.ui.view

import com.backstopmedia.kotlin.chapter4.entities.Profile

/**
 * Created by Tudor Luca on 14/10/15.
 */
interface NavigationDrawerView {

    fun bind(userProfile: Profile)
}