package com.backstopmedia.kotlin.chapter4.ui.view

import com.backstopmedia.kotlin.chapter4.entities.Image

interface TopImagesView {

    fun bind(images: List<Image>)

    fun showError(error: Throwable)
}