package com.backstopmedia.kotlin.ktwitter.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.backstopmedia.kotlin.ktwitter.R
import com.backstopmedia.kotlin.ktwitter.api.KTwitterApiClient
import com.bumptech.glide.Glide
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.models.User

import kotlinx.android.synthetic.list_item_user.view.*
import org.jetbrains.anko.toast
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by Aaron Sarazan on 11/4/15
 * Copyright(c) 2015 Level, Inc.
 */
class TopUsersAdapter(val users: List<User>, val onClick: (User) -> Unit) : RecyclerView.Adapter<UsersViewHolder>() {

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder? {
        return UsersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false))
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(users[position], onClick)
    }
}

class UsersViewHolder : RecyclerView.ViewHolder {
    constructor(itemView: View?) : super(itemView)

    fun bind(user: User, onClick: (User) -> Unit) {
        itemView.name.text = user.name
        itemView.screen_name.text = user.screenName
        Glide.with(itemView.context).load(user.profileImageUrlHttps).into(itemView.image)
        itemView.setOnClickListener {
            onClick(user)
        }
        itemView.follow.setOnClickListener {
            KTwitterApiClient(Twitter.getSessionManager().activeSession).kTwitterApi.follow(user.id, true)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        itemView.context.toast("Followed ${user.screenName}!")
                    }
        }
    }

}
