package com.sdj2022.tp08helpsearch.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sdj2022.tp08helpsearch.databinding.FragmentSearchListBinding

class SearchListFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    val binding:FragmentSearchListBinding by lazy { FragmentSearchListBinding.inflate(layoutInflater) }
}