package com.example.dictionaryapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.dictionaryapp.R
import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.core.util.onBackButtonPressed
import com.example.dictionaryapp.core.util.performBackPress
import com.example.dictionaryapp.databinding.CustomTabLayoutBinding
import com.example.dictionaryapp.databinding.FragmentHomeBinding
import com.example.dictionaryapp.domain.model.WordInfo
import com.example.dictionaryapp.ui.adapters.ViewPagerAdapter
import com.example.dictionaryapp.ui.components.ExitBottomSheet
import com.example.dictionaryapp.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setOnMenuItemClickListener{
            when (it.itemId) {
                R.id.action_history -> {
                    Log.d("MainActivity", "Navigating to SearchedWords")
                    resetViewPagerAndTabs()
                    findNavController().navigate(R.id.action_mobile_navigation_to_searchedHistoryFragment)
                    true
                }

                else -> false
            }
        }

//        val menuHost: FragmentActivity? = activity
//
//        menuHost?.addMenuProvider(object : MenuProvider {
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                menuInflater.inflate(R.menu.toolbar_menu, menu)
//            }
//
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                return when (menuItem.itemId) {
//                    R.id.action_history -> {
//                        Log.d("MainActivity", "Navigating to SearchedWords")
//                        resetViewPagerAndTabs()
//                        findNavController().navigate(R.id.action_mobile_navigation_to_searchedHistoryFragment)
//                        true
//                    }
//
//                    else -> false
//                }
//            }
//        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        initializeView()
        initializeClickListener()

    }


    private fun initializeView() {
        binding.animationView.visibility = View.VISIBLE
        binding.animationView.playAnimation()

        binding.viewPager.visibility = View.GONE
        binding.wormDotsIndicator.visibility = View.GONE
        binding.tabLayout.visibility = View.GONE
    }

    private fun initializeClickListener() {
        binding.searchImageView.setOnClickListener {
            val query = binding.wordSearch.text.toString()
            if (query.isNotEmpty()) {
                binding.animationView.visibility = View.GONE
                binding.animationView.cancelAnimation()
                binding.viewPager.visibility = View.VISIBLE
                binding.tabLayout.visibility = View.VISIBLE
                mainViewModel.searchWord(query)
                observeViewModel()
            }
        }

        binding.cancel.setOnClickListener {
            binding.wordSearch.text.clear()
            resetViewPagerAndTabs()
        }
        onBackButtonPressed {
            showBottomSheet()
            true
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            mainViewModel.wordInfos.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        binding.animationView.cancelAnimation()
                        val wordInfoList = resource.data
                        if (!wordInfoList.isNullOrEmpty()) {
                            val wordInfo = wordInfoList[0]
                            setupTabs(wordInfo)
                            binding.wormDotsIndicator.visibility = View.VISIBLE
                        } else {
                            resetViewPagerAndTabs()
                            binding.animationView.playAnimation()
                        }
                    }

                    is Resource.Error -> {
                        resetViewPagerAndTabs()
                        binding.animationView.playAnimation()
                        Log.d("Error", "Error retrieving data: ${resource.message}")
                    }

                    is Resource.Loading -> {
                        // Handle loading state if needed
                    }
                }
            }
        }
    }


    private fun setupTabs(wordInfo: WordInfo) {

        val bundle = Bundle().apply {
            putParcelable("wordInfo", wordInfo)
        }

        val partsOfSpeech = wordInfo.meanings.map { it.partOfSpeech }

        val adapter = ViewPagerAdapter(wordInfo.meanings, requireActivity(), bundle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val tabView = CustomTabLayoutBinding.inflate(layoutInflater)
            val tabText: TextView = tabView.tabText
            tabText.text = partsOfSpeech[position]
            tab.customView = tabView.tabText
            binding.wormDotsIndicator.attachTo(binding.viewPager)
        }.attach()

    }


    private fun resetViewPagerAndTabs() {
        binding.wordSearch.text.clear()
        binding.viewPager.adapter = null
        binding.viewPager.visibility = View.GONE
        binding.tabLayout.removeAllTabs()
        binding.tabLayout.visibility = View.GONE
        binding.wormDotsIndicator.visibility = View.GONE
        binding.animationView.visibility = View.VISIBLE
        binding.animationView.playAnimation()
    }

    private fun showBottomSheet() {
        ExitBottomSheet.showBottomSheetDialog(requireActivity()) {
            performBackPress()
            requireActivity().finish()
        }
    }



//    override fun onCreateContextMenu(
//        menu: ContextMenu,
//        v: View,
//        menuInfo: ContextMenu.ContextMenuInfo?
//    ) {
//
//        requireActivity().menuInflater.inflate(R.menu.toolbar_menu, menu)
//        super.onCreateContextMenu(menu, v, menuInfo)
//    }
//
//
//    override fun onContextItemSelected(item: MenuItem): Boolean {
//
//        return when (item.itemId) {
//
//            R.id.action_history -> {
//                Log.d("MainActivity", "Navigating to SearchedWords")
//                resetViewPagerAndTabs()
//                findNavController().navigate(R.id.action_mobile_navigation_to_searchedHistoryFragment)
//                true
//            }
//
//            else -> super.onContextItemSelected(item)
//        }
//    }


}

