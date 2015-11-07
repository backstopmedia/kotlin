package com.backstopmedia.kotlin.ktwitter.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.backstopmedia.kotlin.ktwitter.R
import com.backstopmedia.kotlin.ktwitter.entities.FollowingUser
import com.backstopmedia.kotlin.ktwitter.ui.adapter.ListItem.FollowerItem
import com.backstopmedia.kotlin.ktwitter.ui.adapter.ListItem.HeaderItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.list_item_follower.view.*
import kotlinx.android.synthetic.list_item_header.view.*
import org.jetbrains.anko.layoutInflater
import java.util.*

class FollowersAdapter(val users: List<FollowingUser>) : RecyclerView.Adapter<FollowersViewHolder>() {

    companion object {
        private val VIEW_TYPE_HEADER = 0
        private val VIEW_TYPE_FOLLOWER = 1
    }

    private val items = ArrayList<ListItem>()

    init {
        var lastHeader = ' '
        for (i in users.indices) {
            val header = users[i].user.name.first().toUpperCase()
            if (header != lastHeader) {
                lastHeader = header
                items.add(HeaderItem(header))
            }
            items.add(FollowerItem(users[i]))
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = when (items[position]) {
        is HeaderItem -> VIEW_TYPE_HEADER
        is FollowerItem -> VIEW_TYPE_FOLLOWER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder? {
        val view = when (viewType) {
            VIEW_TYPE_HEADER -> parent.context.layoutInflater.inflate(R.layout.list_item_header, parent, false)
            else -> parent.context.layoutInflater.inflate(R.layout.list_item_follower, parent, false)
        }
        return FollowersViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }
}

class FollowersViewHolder : RecyclerView.ViewHolder {
    constructor(itemView: View?) : super(itemView)

    fun bind(item: ListItem) {
        when (item) {
            is HeaderItem -> {
                with(item) {
                    itemView.headerView.text = header.toString()
                }
            }
            is FollowerItem -> {
                val (user, followed) = item.user
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
}

sealed class ListItem() {
    class HeaderItem(val header: Char) : ListItem()
    class FollowerItem(val user: FollowingUser) : ListItem()
}