package com.example.dictionaryapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.core.util.onBackButtonPressed
import com.example.dictionaryapp.core.util.performBackPress
import com.example.dictionaryapp.databinding.ActivityMainBinding
import com.example.dictionaryapp.databinding.CustomTabLayoutBinding
import com.example.dictionaryapp.domain.model.WordInfo
import com.example.dictionaryapp.domain.repository.WordInfoRepo
import com.example.dictionaryapp.ui.activities.SearchedWords
import com.example.dictionaryapp.ui.adapters.ViewPagerAdapter
import com.example.dictionaryapp.ui.components.ExitBottomSheet
import com.example.dictionaryapp.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    @Inject
    lateinit var wordInfoRepo: WordInfoRepo //viewmodel
    // private val mainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initializeView()
        initializeClickListener()
    }

    private fun initializeView() {
        binding.animationView.playAnimation()
    }

    private fun initializeClickListener() {
        binding.searchImageView.setOnClickListener {
            val query = binding.wordSearch.text.toString()
            if (query.isNotEmpty()) {
                binding.animationView.visibility = View.GONE
                binding.viewPager.visibility = View.VISIBLE
                filterData(query)
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

    private fun filterData(query: String) {
        lifecycleScope.launch {
            val words: Flow<Resource<List<WordInfo>>> = wordInfoRepo.getWordInfo(query)
            words.collect { resource ->
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
                        Log.d("Error", "onCreate: No data fetched")
                    }

                    is Resource.Loading -> {
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

        val adapter = ViewPagerAdapter(wordInfo.meanings, this, bundle)
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
        binding.viewPager.adapter = null
        binding.tabLayout.removeAllTabs()
        binding.wormDotsIndicator.visibility = View.GONE
        binding.animationView.visibility = View.VISIBLE
        binding.animationView.playAnimation()
    }

    private fun showBottomSheet() {
        ExitBottomSheet.showBottomSheetDialog(this) {
            performBackPress()
            finish()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_history -> {
                Log.d("MainActivity", "Navigating to SearchedWords")
                startActivity(Intent(this, SearchedWords::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}










