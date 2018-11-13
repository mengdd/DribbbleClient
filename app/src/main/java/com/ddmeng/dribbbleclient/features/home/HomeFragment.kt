package com.ddmeng.dribbbleclient.features.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ddmeng.dribbbleclient.R
import com.ddmeng.dribbbleclient.data.model.Shot
import com.ddmeng.dribbbleclient.data.valueobject.Resource
import com.ddmeng.dribbbleclient.databinding.FragmentHomeBinding
import com.ddmeng.dribbbleclient.di.Injectable
import com.ddmeng.dribbbleclient.viewmodel.ShotViewModelFactory
import com.ddmeng.dribbbleclient.viewmodel.ShotsViewModel
import javax.inject.Inject

class HomeFragment : Fragment(), Injectable {

    @Inject
    lateinit var shotViewModelFactory: ShotViewModelFactory
    private lateinit var shotsViewModel: ShotsViewModel
    private lateinit var shotsRecyclerView: RecyclerView
    private lateinit var shotsListAdapter: ShotsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false)
        shotsRecyclerView = binding.shotsList
        shotsRecyclerView.layoutManager = LinearLayoutManager(context)
        shotsListAdapter = ShotsListAdapter()
        shotsRecyclerView.adapter = shotsListAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        shotsViewModel = ViewModelProviders.of(this, shotViewModelFactory)
                .get(ShotsViewModel::class.java)
        shotsViewModel.shots.observe(this, Observer<Resource<List<Shot>>> { shotsResource ->
            shotsListAdapter.shots = shotsResource?.data
        })
        shotsViewModel.fetch(false)
    }
}
