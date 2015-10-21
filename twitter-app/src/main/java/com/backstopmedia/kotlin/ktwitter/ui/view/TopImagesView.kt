package com.backstopmedia.kotlin.ktwitter.ui.view

import com.backstopmedia.kotlin.ktwitter.entities.Image

interface TopImagesView {

    fun bind(images: List<Image>)

    fun showError(error: Throwable)
}