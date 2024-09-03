package com.example.dictionaryapp.ui.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dictionaryapp.domain.model.Meaning
import com.example.dictionaryapp.ui.fragments.ViewPagerFragment

class ViewPagerAdapter(
    private val meanings: List<Meaning>,
    fragment: FragmentActivity,
    private val bundle:Bundle
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = meanings.size

    override fun createFragment(position: Int): Fragment {
        val fragment = ViewPagerFragment()
        fragment.arguments = bundle.apply {
            putParcelable("meaning", meanings[position])
        }
        return fragment
    }
}



