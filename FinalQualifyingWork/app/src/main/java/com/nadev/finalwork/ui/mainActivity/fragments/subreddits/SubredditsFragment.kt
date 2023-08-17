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
import com.nadev.finalwork.databinding.SubredditsFragmentBinding
import com.nadev.finalwork.data.models.PageTypes
import com.nadev.finalwork.data.retrofit
import com.nadev.finalwork.ui.mainActivity.fragments.favourites.CommentsAdapter
import kotlinx.coroutines.launch

class SubredditsFragment : Fragment() {

    private var _binding: SubredditsFragmentBinding? = null
    private val subredditsViewModel:SubredditsViewModel by viewModels ()
    private val binding get() = _binding!!
    private val subredditsAdapter = AdapterSubreddits{action, id -> onSubsClick(action, id)}
    private val comsAdapter = CommentsAdapter()
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
        binding.subsRecycler.adapter = subredditsAdapter
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

            ObjectAnimator.ofArgb(
                binding.popularButton,
                "textColor",
                Color.parseColor("#66000000")
            ).apply {
                duration = 500
            }.start()
            subredditsViewModel.setSubs(PageTypes.NewSubs) //проверить он полностью меняет значение или добавляет новые данные в конец списка
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
            subredditsViewModel.setSubs(PageTypes.PopularSubs) //проверить он полностью меняет значение или добавляет новые данные в конец списка
        }
        viewLifecycleOwner.lifecycleScope.launch {
            subredditsViewModel.subsFlow.collect {
                subredditsAdapter.setData(it)
            }
        }
        binding.searchButton.setOnClickListener {
            val requestText = binding.searchEditor.text.toString()
            viewLifecycleOwner.lifecycleScope.launch {
                if (requestText.length > 2) {
                    subredditsViewModel.setFoundSubs(requestText)
                    subredditsViewModel.subsFlow.collect {
                        subredditsAdapter.setData(it)
                    }
                }else {
                    subredditsViewModel.setSubs(PageTypes.NewSubs)
                    subredditsViewModel.subsFlow.collect {
                        subredditsAdapter.setData(it)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun onSubsClick(action:String, itemID:String){
        lifecycleScope.launch {
            if (action == "sub") {retrofit.subscribe(sr = itemID)}
            else if (action == "unsub") { retrofit.unsubscribe(sr = itemID)}
            else{
                subredditsViewModel.commentsFlow.collect {
                comsAdapter.setData(it)
            } }
        }
    }
}