package com.nadev.finalwork.ui.mainActivity.fragments.friendslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.nadev.finalwork.databinding.FriendsListFragmentBinding
import com.nadev.finalwork.ui.mainActivity.fragments.profile.FriendsListAdapter
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
        viewLifecycleOwner.lifecycleScope.launch {
            binding.friendsListRecycler.adapter = friendsListAdapter
            listViewModel.listFlow.collect{
                friendsListAdapter.setData(it)
                if (it != null && it.isEmpty()){
                    binding.noFriends.visibility = View.VISIBLE
                }else{
                    binding.noFriends.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
