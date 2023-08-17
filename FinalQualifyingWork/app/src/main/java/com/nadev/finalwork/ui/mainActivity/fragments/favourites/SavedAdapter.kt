package com.nadev.finalwork.ui.mainActivity.fragments.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nadev.finalwork.databinding.ModelCommentsBinding

class SavedAdapter: RecyclerView.Adapter<SavedViewHolder>() {
    private lateinit var bind: ModelCommentsBinding
    private var savedList: List<String> = (emptyList())

    fun setData(data: List<String>){
        this.savedList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        bind = ModelCommentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedViewHolder(bind)
    }

    override fun getItemCount(): Int = savedList.size

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        val item = savedList[position]
        with(holder.binding){
            item.let {
                //приделать здесь реализацию
            }
        }
    }
}

class SavedViewHolder(val binding: ModelCommentsBinding) : RecyclerView.ViewHolder(binding.root)