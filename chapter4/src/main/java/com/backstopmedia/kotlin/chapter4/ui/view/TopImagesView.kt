package com.backstopmedia.kotlin.chapter4.ui.view

import com.backstopmedia.kotlin.chapter4.entities.Image

/**
 * Created by Tudor Luca on 14/10/15.
 */
interface TopImagesView {

    fun bind(images: List<Image>)

    fun showError(error: Throwable)
}