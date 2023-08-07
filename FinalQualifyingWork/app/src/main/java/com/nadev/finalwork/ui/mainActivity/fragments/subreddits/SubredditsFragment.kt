package com.nadev.finalwork.ui.mainActivity.fragments.subreddits

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.nadev.finalwork.data.retrofit
import com.nadev.finalwork.databinding.SubredditsFragmentBinding
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SubredditsFragment : Fragment() {

    private var _binding: SubredditsFragmentBinding? = null
    private val subredditsViewModel:SubredditsViewModel by viewModels ()
    private val binding get() = _binding!!
    private val subredditsAdapter = AdapterSubreddits()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SubredditsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        ObjectAnimator.ofArgb(
            binding.newButton,
            "textColor",
            Color.parseColor("#FF01579B")
        ).start()
        binding.newButton.setOnClickListener {
            ObjectAnimator.ofArgb(
                binding.newButton,
                "textColor",
                Color.parseColor("#FF01579B")
            ).apply {
                duration = 500
            }.start()
            kotlin.runCatching {

            }
            ObjectAnimator.ofArgb(
                binding.popularButton,
                "textColor",
                Color.parseColor("#66000000")
            ).apply {
                duration = 500
            }.start()
        }

        binding.popularButton.setOnClickListener {
            ObjectAnimator.ofArgb(
                binding.popularButton,
                "textColor",
                Color.parseColor("#FF01579B")
            ).apply {
                duration = 500
            }.start()

            ObjectAnimator.ofArgb(
                binding.newButton,
                "textColor",
                Color.parseColor("#66000000")
            ).apply {
                duration = 500
            }.start()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            val subs = retrofit.getNewSubreddits()
            subredditsAdapter.setData(subs)
            binding.newSubsRecycler.adapter = subredditsAdapter
            subredditsViewModel.subsFlow.onEach{
                subredditsAdapter.setData(it)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}