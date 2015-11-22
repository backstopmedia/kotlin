package com.backstopmedia.kotlin.ktwitter.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.backstopmedia.kotlin.ktwitter.R
import com.backstopmedia.kotlin.ktwitter.entities.FollowingUser
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.list_item_follower.view.*
import org.jetbrains.anko.layoutInflater

class FollowersAdapter(val users: List<FollowingUser>) : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {

    override fun getItemCount() = users.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder? {
        return FollowersViewHolder(parent.context.layoutInflater.inflate(R.layout.list_item_follower, parent, false))
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        val item = users[position]
        holder.bind(item)
    }

    class FollowersViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: FollowingUser) {
            val (user, followed) = item
            with(user) {
                itemView.nameView.text = name
                itemView.screenNameView.text = "@$screenName"
                itemView.followedView.text = if (followed) "followed" else ""
                itemView.followersCountView.text = "$followersCount followers"
                Glide.with(itemView.context).load(profileImageUrlHttps).into(itemView.imageView)
            }
        }
    }
}