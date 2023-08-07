package com.nadev.finalwork.ui.mainActivity.fragments.subreddits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.nadev.finalwork.databinding.ModelSubredditsBinding
import com.nadev.finalwork.entity.SubredditsPreview

class AdapterSubreddits : RecyclerView.Adapter<SubredditsViewHolder>() {
    private lateinit var bind: ModelSubredditsBinding
    private var subsList: List<SubredditsPreview> = (emptyList())
    fun setData(data:List<SubredditsPreview>) {
        this.subsList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubredditsViewHolder {
        bind = ModelSubredditsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubredditsViewHolder(bind)
    }

    override fun getItemCount(): Int = subsList.size

    override fun onBindViewHolder(holder: SubredditsViewHolder, position: Int) {
        val item = subsList[position]
        with(holder.binding) {
            item.let {
                topic.text = it.after
            }
            subscribe.setOnClickListener {
                if (subscribeIcon.isVisible) {
                    unsubscribeIcon.visibility = View.VISIBLE
                    //подписка
                    subscribeIcon.visibility = View.GONE
                }else{
                    unsubscribeIcon.visibility = View.GONE
                    //отписка
                    subscribeIcon.visibility = View.VISIBLE
                }
            }
        }
    }
}

class SubredditsViewHolder(val binding: ModelSubredditsBinding) :
    RecyclerView.ViewHolder(binding.root)