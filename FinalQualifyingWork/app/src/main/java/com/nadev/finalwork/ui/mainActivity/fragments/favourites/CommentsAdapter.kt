package com.nadev.finalwork.ui.mainActivity.fragments.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadev.finalwork.data.models.posts.comments.Comment
import com.nadev.finalwork.databinding.ModelCommentsBinding

class CommentsAdapter: RecyclerView.Adapter<CommentsViewHolder>() {
    private lateinit var bind: ModelCommentsBinding
    private var commentsList: ArrayList<Comment>? = (arrayListOf())

    fun setData(data: ArrayList<Comment>?){
        this.commentsList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        bind = ModelCommentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentsViewHolder(bind)
    }

    override fun getItemCount(): Int = commentsList!!.size

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val item = commentsList?.get(position)
        with(holder.binding){
            item.let {
                name.text = it?.data?.authorFullname
                time.text = "Created at ${it?.data?.createdUtc} ago"
                description.text = it?.data?.body
                count.text = it?.data?.score.toString()
                //доделать кнопку сохранить
            }
        }
    }
}

class CommentsViewHolder(val binding:ModelCommentsBinding) : RecyclerView.ViewHolder(binding.root)