package com.nadev.finalwork.ui.mainActivity.fragments.openedProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.nadev.finalwork.data.retrofit
import com.nadev.finalwork.databinding.OpenedProfileFragmentBinding
import com.nadev.finalwork.ui.mainActivity.fragments.subreddits.AdapterSubreddits
import kotlinx.coroutines.launch

class OpenedProfile : Fragment() {
    private var _binding: OpenedProfileFragmentBinding? = null
    private val binding get() = _binding!!
    private val openedProfileViewModel:OpenedProfileViewModel by viewModels()
    private val adapter = AdapterSubreddits{action, id -> onSubsClick(action = action, id)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OpenedProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val profile = openedProfileViewModel.getUserProfile()
        binding.profileSubreddits.adapter
        Glide.with(binding.logo).load(profile?.iconImg).into(binding.logo)
        binding.name.text = profile?.name
        binding.username.text = profile?.id
        binding.commentsCount.text = profile?.commentKarma.toString()

        binding.addButton.setOnClickListener {
            binding.addButton.visibility = View.GONE
            binding.removeButton.visibility = View.VISIBLE
            viewLifecycleOwner.lifecycleScope.launch {
                kotlin.runCatching {
                    retrofit.addToFriends(username = profile!!.name!!, requestBody = "\"name\": ${profile.id}, \"note\": empty")
                }
            }
        }
        binding.removeButton.setOnClickListener {
            binding.addButton.visibility = View.VISIBLE
            binding.removeButton.visibility = View.GONE
            viewLifecycleOwner.lifecycleScope.launch {
                kotlin.runCatching {
                    retrofit.removeFromFriends(username = profile!!.name!!)
                }
            }
        }
    }

    private fun onSubsClick(action:String, itemID:String){
        lifecycleScope.launch {
            if (action == "sub") {retrofit.subscribe(sr = itemID)}
            else { retrofit.unsubscribe(sr = itemID)}
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}