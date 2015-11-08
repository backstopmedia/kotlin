package com.backstopmedia.kotlin.ktwitter.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.backstopmedia.kotlin.ktwitter.R
import com.backstopmedia.kotlin.ktwitter.api.KTwitterApiClient
import com.backstopmedia.kotlin.ktwitter.entities.RankedUser
import com.bumptech.glide.Glide
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.models.User

import kotlinx.android.synthetic.list_item_user.view.*
import org.jetbrains.anko.toast
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class TopUsersAdapter(users: List<RankedUser>, val onClick: (RankedUser) -> Unit) : RecyclerView.Adapter<TopUsersAdapter.UsersViewHolder>() {

    private val users = users.toArrayList()

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder? {
        return UsersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false))
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(users[position])
    }

    public inner class UsersViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: RankedUser) {
            with(user.user) {
                itemView.name.text = name
                itemView.screen_name.text = screenName
                Glide.with(itemView.context).load(profileImageUrlHttps).into(itemView.image)
                itemView.setOnClickListener {
                    onClick(user)
                }

                val applyFollowing = {
                    with(user.following) {
                        itemView.follow.isEnabled = !this
                        itemView.follow.text = if (this) "Following" else "Follow"
                        itemView.follow.alpha = if (this) 0.5f else 1f
                    }
                }
                applyFollowing()
                
                itemView.follow.setOnClickListener {
                    itemView.follow.isEnabled = false
                    KTwitterApiClient(Twitter.getSessionManager().activeSession).kTwitterApi.follow(id, true)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe {
                                itemView.context.toast("Followed $screenName!")
                                user.following = true
                                applyFollowing()
                            }
                }
            }
        }

    }
}
