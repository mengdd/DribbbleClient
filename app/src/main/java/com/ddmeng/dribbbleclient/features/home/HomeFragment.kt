package com.ddmeng.dribbbleclient.features.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ddmeng.dribbbleclient.R
import com.ddmeng.dribbbleclient.databinding.FragmentHomeBinding
import com.ddmeng.dribbbleclient.di.Injectable

class HomeFragment : Fragment(), Injectable {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false)
        return binding.root
    }
}