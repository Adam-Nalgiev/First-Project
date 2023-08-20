package com.nadev.finalwork.ui.mainActivity.fragments.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nadev.finalwork.R
import com.nadev.finalwork.data.models.friends.Friend
import com.nadev.finalwork.databinding.ModelFriensListBinding

class FriendsListAdapter: RecyclerView.Adapter<FriendsListVH>() {

    private lateinit var bind: ModelFriensListBinding

    private var friendsList: ArrayList<Friend>? = (arrayListOf())

    fun setData(data: ArrayList<Friend>?) {
        this.friendsList = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsListVH {
        bind = ModelFriensListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendsListVH(bind)
    }

    override fun getItemCount(): Int = friendsList!!.size


    override fun onBindViewHolder(holder: FriendsListVH, position: Int) {
        val item = friendsList?.get(position)
        with(holder.binding){
            item.let {
                Glide.with(profilePhoto).load(R.drawable.profile_icon).circleCrop().into(profilePhoto) // не приходит фотография профиля (её вообще нет в запросе)
                name.text = it?.name
                username.text = it?.relId
            }
        }
    }
}

class FriendsListVH(val binding: ModelFriensListBinding) :RecyclerView.ViewHolder(binding.root)