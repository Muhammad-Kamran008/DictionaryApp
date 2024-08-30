package com.example.dictionaryapp

import android.R
import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.databinding.ActivityMainBinding
import com.example.dictionaryapp.databinding.BottomSheetLayoutBinding
import com.example.dictionaryapp.databinding.CustomTabLayoutBinding
import com.example.dictionaryapp.domain.model.WordInfo
import com.example.dictionaryapp.domain.repository.WordInfoRepo
import com.example.dictionaryapp.ui.adapters.ViewPagerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var searchEditText: EditText
    private lateinit var searchImageView: ImageView
    private lateinit var cancelImageView: ImageView
    private lateinit var binding: ActivityMainBinding
    private lateinit var animationView: LottieAnimationView
    private lateinit var viewPager: ViewPager2
    private lateinit var dotIndicator: WormDotsIndicator
    private lateinit var bottomSheetDialog: BottomSheetDialog

    @Inject
    lateinit var wordInfoRepo: WordInfoRepo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tabLayout = binding.tabLayout
        searchEditText = binding.wordSearch
        searchImageView = binding.searchImageView
        cancelImageView = binding.cancel
        animationView = binding.animationView
        viewPager = binding.viewPager
        dotIndicator = binding.wormDotsIndicator

        animationView.playAnimation()
        searchImageView.setOnClickListener {
            val query = searchEditText.text.toString()
            filterData(query)
        }

        cancelImageView.setOnClickListener {
            searchEditText.text.clear()
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
                        animationView.cancelAnimation()
                        val wordInfoList = resource.data
                        if (!wordInfoList.isNullOrEmpty()) {
                            val wordInfo = wordInfoList[0]
                            setupTabs(wordInfo)
                        } else {
                            resetViewPagerAndTabs()
                            animationView.playAnimation()
                        }
                    }

                    is Resource.Error -> {
                        resetViewPagerAndTabs()
                        animationView.playAnimation()
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
        viewPager.adapter = adapter



        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val tabView = CustomTabLayoutBinding.inflate(layoutInflater)
            val tabText: TextView = tabView.tabText
            tabText.text = partsOfSpeech[position]
            tab.customView = tabView.tabText
            dotIndicator.attachTo(viewPager)
        }.attach()

    }

    private fun resetViewPagerAndTabs() {
        viewPager.adapter = null
        tabLayout.removeAllTabs()
        dotIndicator.removeAllViews()
    }


    private fun Activity.onBackButtonPressed(callback: (() -> Boolean)) {
        (this as? FragmentActivity)?.onBackPressedDispatcher?.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!callback()) {
                        remove()
                        performBackPress()
                    }
                }
            })
    }

    fun Activity.performBackPress() {
        (this as? FragmentActivity)?.onBackPressedDispatcher?.onBackPressed()
    }

    private fun showBottomSheet() {
        val bottomSheetView = BottomSheetLayoutBinding.inflate(layoutInflater)
        bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(bottomSheetView.root)

        bottomSheetView.btnExit.setOnClickListener {

            bottomSheetDialog.dismiss()
            performBackPress()
            finish()
        }

        bottomSheetView.btnCancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

}










