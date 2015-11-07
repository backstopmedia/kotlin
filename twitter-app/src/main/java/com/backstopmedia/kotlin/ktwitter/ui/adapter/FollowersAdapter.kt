package com.backstopmedia.kotlin.ktwitter.ui.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.backstopmedia.kotlin.ktwitter.R
import com.backstopmedia.kotlin.ktwitter.entities.FollowingUser
import com.bumptech.glide.Glide
import com.twitter.sdk.android.core.models.User
import kotlinx.android.synthetic.list_item_follower.view.*
import org.jetbrains.anko.textColor

class FollowersAdapter(val users: List<FollowingUser>) : RecyclerView.Adapter<FollowersViewHolder>() {

    override fun getItemCount() = users.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder? {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_follower, parent, false)
        return FollowersViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        val (follower, followed) = users[position]
        holder.bind(follower, followed)
    }
}

class FollowersViewHolder : RecyclerView.ViewHolder {
    constructor(itemView: View?) : super(itemView)

    fun bind(user: User, followed: Boolean) {
        with(user) {
            itemView.nameView.text = name
            itemView.screenNameView.text = "@$screenName"
            if (followed) {
                itemView.followedView.text = "followed"
                itemView.followedView.textColor = Color.parseColor("#4CAF50")
            } else {
                itemView.followedView.text = "not followed"
                itemView.followedView.textColor = Color.parseColor("#F44336")
            }
            itemView.followersCountView.text = "$followersCount followers"
            Glide.with(itemView.context).load(profileImageUrlHttps).into(itemView.image)
        }
    }
}