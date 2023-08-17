package com.nadev.finalwork.ui.mainActivity.fragments.subreddits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.nadev.finalwork.data.models.subreddit.Children
import com.nadev.finalwork.databinding.ModelSubredditsBinding
import com.nadev.finalwork.ui.mainActivity.fragments.favourites.CommentsAdapter

class AdapterSubreddits(private val subsClick: (String, String) -> Unit) :
    RecyclerView.Adapter<SubredditsViewHolder>() {
    private lateinit var bind: ModelSubredditsBinding
    private var subsList: ArrayList<Children>? = (arrayListOf())
    fun setData(data: ArrayList<Children>?) {
        this.subsList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubredditsViewHolder {
        bind = ModelSubredditsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubredditsViewHolder(bind)
    }

    override fun getItemCount(): Int = subsList!!.size

    override fun onBindViewHolder(holder: SubredditsViewHolder, position: Int) {
        val item = subsList?.get(position)
        holder.binding.commentsRecycler.adapter = CommentsAdapter()
        with(holder.binding) {
            item.let {
                if (it?.data?.userIsSubscriber != null && it.data?.userIsSubscriber == true) {
                    unsubscribeIcon.visibility = View.VISIBLE
                    subscribeIcon.visibility = View.GONE
                }
                topic.text = it?.data?.title
                descriptionPreview.text = it?.data?.publicDescription
                description.text = it?.data?.description

            }

        }
        holder.binding.root.setOnClickListener {
            subsClick("showComs", "${item?.kind}")
            holder.binding.description.isVisible = holder.binding.description.visibility != View.VISIBLE
            holder.binding.commentsBar.isVisible = holder.binding.commentsBar.visibility != View.VISIBLE
        }
        holder.binding.subscribe.setOnClickListener {
            if (holder.binding.subscribeIcon.isVisible) {
                holder.binding.unsubscribeIcon.visibility = View.VISIBLE
                subsClick("sub", "${item?.kind}")
                holder.binding.subscribeIcon.visibility = View.GONE
            } else {
                holder.binding.unsubscribeIcon.visibility = View.GONE
                subsClick("unsub", "${item?.kind}")
                holder.binding.subscribeIcon.visibility = View.VISIBLE
            }
        }
    }
}

class SubredditsViewHolder(val binding: ModelSubredditsBinding) :
    RecyclerView.ViewHolder(binding.root)