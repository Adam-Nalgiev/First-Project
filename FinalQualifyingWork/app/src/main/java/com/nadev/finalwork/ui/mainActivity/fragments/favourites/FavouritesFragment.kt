package com.nadev.finalwork.ui.mainActivity.fragments.favourites

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.nadev.finalwork.R
import com.nadev.finalwork.databinding.FavouritesFragmentBinding
import com.nadev.finalwork.ui.mainActivity.fragments.subreddits.AdapterSubreddits

class FavouritesFragment : Fragment() {

    private var _binding: FavouritesFragmentBinding? = null
    private val binding get() = _binding!!
    private val favouritesViewModel: FavouritesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavouritesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.firstGroup.setOnCheckedChangeListener{ _, isChecked ->
            if (!isChecked){

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}