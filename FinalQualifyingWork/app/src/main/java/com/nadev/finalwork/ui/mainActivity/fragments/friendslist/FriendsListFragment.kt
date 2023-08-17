package com.nadev.finalwork.ui.mainActivity.fragments.friendslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.nadev.finalwork.R
import com.nadev.finalwork.databinding.FriendsListFragmentBinding
import com.nadev.finalwork.databinding.ProfileFragmentBinding
import com.nadev.finalwork.ui.mainActivity.fragments.profile.FriendsListAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FriendsListFragment : Fragment() {
    private val listViewModel:FriendsListViewModel by viewModels()
    private val friendsListAdapter = FriendsListAdapter()
    private var _binding: FriendsListFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FriendsListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.friendsListRecycler.adapter = friendsListAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            listViewModel.listFlow.collect{
                friendsListAdapter.setData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
