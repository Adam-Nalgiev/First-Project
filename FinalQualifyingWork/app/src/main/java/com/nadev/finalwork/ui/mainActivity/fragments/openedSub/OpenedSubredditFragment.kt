package com.nadev.finalwork.ui.mainActivity.fragments.openedSub

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.nadev.finalwork.App
import com.nadev.finalwork.R
import com.nadev.finalwork.data.models.CommentsDB
import com.nadev.finalwork.data.models.PageTypes
import com.nadev.finalwork.data.models.saved.comments.CommentData
import com.nadev.finalwork.data.retrofit
import com.nadev.finalwork.databinding.OpenedSubredditFragmentBinding
import com.nadev.finalwork.ui.mainActivity.fragments.favourites.CommentsAdapter
import com.nadev.finalwork.ui.mainActivity.fragments.subreddits.descr
import com.nadev.finalwork.ui.mainActivity.fragments.subreddits.img
import com.nadev.finalwork.ui.mainActivity.fragments.subreddits.itemID
import com.nadev.finalwork.ui.mainActivity.fragments.subreddits.itemName
import kotlinx.coroutines.launch

class OpenedSubredditFragment : Fragment() {

    private var _binding: OpenedSubredditFragmentBinding? = null
    private val binding get() = _binding!!
    private val openedSubredditsViewModel: OpenedSubredditsViewModel by viewModels()
    private val commentsAdapter = CommentsAdapter{type, id, comment -> onClick(type, id, comment)}
    private val dao = (requireActivity().application as App).db.dao()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OpenedSubredditFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.commentsRecycler.adapter = commentsAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            binding.name.text = itemName
            binding.description.text = descr
            Glide.with(binding.image).load(img).error(R.drawable.empty_image).into(binding.image)
            openedSubredditsViewModel.commentsFlow.collect {
                if (it != null) {
                    commentsAdapter.setData(it)
                    binding.noComments.visibility = View.GONE
                } else {
                    binding.noComments.visibility = View.VISIBLE
                }
            }
        }

        binding.shareButton.setOnClickListener {
            val sharedLink = "https://oauth.reddit.com/r/$itemID"
            val sharedText = getString(R.string.share)
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, sharedLink)
            sendIntent.type = "text/plain"
            startActivity(Intent.createChooser(sendIntent, sharedText))
        }
        binding.subscribe.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                if (binding.subscribeIcon.isVisible) {
                    binding.unsubscribeIcon.visibility = View.VISIBLE
                    kotlin.runCatching {
                        retrofit.subscribe(sr = "itemID")
                    }
                    binding.subscribeIcon.visibility = View.GONE
                } else {
                    binding.unsubscribeIcon.visibility = View.GONE
                    kotlin.runCatching {
                        retrofit.unsubscribe(sr = "itemID")
                    }
                    binding.subscribeIcon.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun onClick(action:PageTypes, id:String, comment:CommentData){
        viewLifecycleOwner.lifecycleScope.launch {
            when(action){
                PageTypes.Save -> {
                    kotlin.runCatching {
                        retrofit.saveThing(category = "t1", id = id)
                    }
                }
                PageTypes.Download -> {
                    Toast.makeText(requireContext(), getText(R.string.saved), Toast.LENGTH_SHORT).show()
                    dao.save(CommentsDB(id = comment.id!!, name = comment.name, description = comment.body))
                }
                else -> {
                    return@launch}
            }
        }
    }
}