package com.example.dictionaryapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.dictionaryapp.databinding.FragmentFirstBinding
import com.example.dictionaryapp.domain.model.Meaning

class FirstFragment : Fragment() {

    private var meaning: Meaning? = null
    private lateinit var binding: FragmentFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            meaning = it.getParcelable(ARG_MEANING)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentFirstBinding.inflate(layoutInflater)
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView: TextView = binding.name
        val tvDefinition: TextView = binding.definition
        val tvExample: TextView = binding.example

        meaning?.let {
            textView.text = it.partOfSpeech
            if (it.definitions.isNotEmpty()) {
                tvDefinition.text = it.definitions[0].definition
                tvExample.text = it.definitions[0].example ?: "No example available"
            } else {
                tvDefinition.text = "No definition available"
                tvExample.text = "No example available"
            }
        }
    }


    companion object {
        private const val ARG_WORD_INFO = "wordInfo"
        private const val ARG_MEANING = "meaning"
//
//        fun newInstance(meaning: Meaning) = FirstFragment().apply {
//            arguments = Bundle().apply {
//                putParcelable(ARG_MEANING, meaning)
//            }
//        }
    }
}


