package com.backstopmedia.kotlin.ktwitter.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.backstopmedia.kotlin.ktwitter.R
import com.backstopmedia.kotlin.ktwitter.entities.Image
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.grid_item_top_image.view.media
import kotlinx.android.synthetic.grid_item_top_image.view.retweet
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.onClick

class TopImagesAdapter(val images: List<Image>, val onImageClick: (Image) -> Unit) : RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = parent.context.layoutInflater.inflate(R.layout.grid_item_top_image, parent, false)
        return ViewHolder(itemView, onImageClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(image = getItem(position))
    }

    override fun getItemCount(): Int = images.size()

    fun getItem(position: Int) = images.get(position)


}

class ViewHolder(view: View, val onImageClick: (Image) -> Unit) : RecyclerView.ViewHolder(view) {

    fun bind(image: Image) = with(image) {
        itemView.onClick { onImageClick(image) }
        itemView.retweet.text = retweetCount.toString()
        when (mediaUrl.isEmpty()) {
            true -> itemView.media.visibility = View.GONE
            else -> {
                itemView.media.visibility = View.VISIBLE
                Glide.with(itemView.context).load(mediaUrl).into(itemView.media)
            }
        }
    }
}