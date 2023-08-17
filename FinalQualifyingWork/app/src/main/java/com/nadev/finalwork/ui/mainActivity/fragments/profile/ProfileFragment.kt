package com.nadev.finalwork.ui.mainActivity.fragments.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.nadev.finalwork.R
import com.nadev.finalwork.databinding.ProfileFragmentBinding

class ProfileFragment : Fragment() {
    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel:ProfileViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profile = profileViewModel.getProfile()
        Log.d("Profile", profile.toString())

        Glide.with(binding.profilePhoto).load(profile.iconImg).circleCrop().into(binding.profilePhoto)

        binding.name.text = profile.name
        binding.username.text = profile.id
        binding.commentsCount.text = profile.commentKarma.toString()
        binding.subredditsCount.text = profile.subreddit?.subscribers.toString() //да там же нет таких переменных

        binding.friendsListButton.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_profile_to_friendsListFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}