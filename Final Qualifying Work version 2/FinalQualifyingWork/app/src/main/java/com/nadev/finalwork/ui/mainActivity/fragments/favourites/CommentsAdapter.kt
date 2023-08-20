package com.nadev.finalwork.ui.mainActivity.fragments.favourites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nadev.finalwork.data.models.PageTypes
import com.nadev.finalwork.data.models.posts.comments.Comment
import com.nadev.finalwork.data.models.saved.comments.CommentData
import com.nadev.finalwork.databinding.ModelCommentsBinding
import java.time.Instant
import java.time.format.DateTimeFormatter

class CommentsAdapter(val click:(PageTypes, String, CommentData) -> Unit): RecyclerView.Adapter<CommentsViewHolder>() {
    private lateinit var bind: ModelCommentsBinding
    private var commentsList: ArrayList<Comment>? = (arrayListOf())
    private var time = ""
    private var counts = 0
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
            item.let {if (it?.data != null) {
                counts = it.data?.score!!
                refactorTime(it.data?.created!!)
                Glide.with(logo).load(it.data?.linkAuthor).into(logo)//там не приходит фотография
                name.text = it.data?.authorFullname.toString() //даже не понятно что из приходящего текст - какой то набор букв куда не глянь
                time.text = "$time"
                description.text = it.data?.body.toString()
                count.text = it.data?.score.toString()
            }}
        }
        holder.binding.downloadButton.setOnClickListener {
            if (item?.data != null && item != null) {click(PageTypes.Download, "", CommentData(name = item.data?.name, id = item.data?.id, body = item.data?.body))}

        }
        holder.binding.addButtons.setOnClickListener {
            holder.binding.addButton.visibility = View.GONE
            holder.binding.addedButton.visibility = View.VISIBLE
            click(PageTypes.Save, item?.data?.id.toString(), CommentData())
        }

        holder.binding.buttonUp.setOnClickListener {
            if (!holder.binding.buttonDown.isEnabled){counts++}
            counts++
            holder.binding.count.text = counts.toString()
            holder.binding.buttonUp.isEnabled = false
            holder.binding.buttonDown.isEnabled = true
        }
        holder.binding.buttonDown.setOnClickListener {
            if (!holder.binding.buttonUp.isEnabled){counts--}
            counts--
            holder.binding.count.text = counts.toString()
            holder.binding.buttonUp.isEnabled = true
            holder.binding.buttonDown.isEnabled = false
        }
    }

    private fun refactorTime(timeL: Long){
        time = DateTimeFormatter.ISO_INSTANT.format(Instant.ofEpochMilli(timeL))
    }
}

class CommentsViewHolder(val binding:ModelCommentsBinding) : RecyclerView.ViewHolder(binding.root)