package com.nadev.finalwork.ui.mainActivity.fragments.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.nadev.finalwork.App
import com.nadev.finalwork.R
import com.nadev.finalwork.databinding.ProfileFragmentBinding
import com.nadev.finalwork.ui.onboarding.OnboardingActivity
import com.nadev.finalwork.ui.onboarding.PREFERENCE_NAME
import com.nadev.finalwork.ui.registration.accessToken
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()
    private val dao = (requireActivity().application as App).db.dao()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = requireContext().getSharedPreferences(PREFERENCE_NAME, AppCompatActivity.MODE_PRIVATE)
        val editor = preferences.edit()
        viewLifecycleOwner.lifecycleScope.launch {
           val profile = profileViewModel.getProfile()

            Glide.with(binding.profilePhoto).load(profile.icon_img).circleCrop()
                .into(binding.profilePhoto)

            binding.name.text = profile.name
            binding.username.text = "id: @${profile.id}"
            binding.commentsCount.text = profile.comment_karma.toString()
            binding.subredditsCount.text =
                profile.subreddit?.subscribers.toString() //да там же нет таких переменных

            binding.friendsListButton.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_profile_to_friendsListFragment)
            }
            binding.exitButton.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    dao.deleteAll()
                    val cacheName = requireContext().cacheDir.name
                    requireContext().deleteFile(cacheName)
                    editor.clear()
                    editor.commit()
                    accessToken = ""
                    val intent = Intent(requireActivity(), OnboardingActivity::class.java)
                    startActivity(intent)
                }
            }
            binding.clearButton.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    dao.deleteAll()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}