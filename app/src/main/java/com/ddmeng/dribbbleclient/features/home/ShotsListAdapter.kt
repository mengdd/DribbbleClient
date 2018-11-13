package com.ddmeng.dribbbleclient.features.home

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ddmeng.dribbbleclient.R
import com.ddmeng.dribbbleclient.data.model.Shot
import com.ddmeng.dribbbleclient.databinding.ShotItemLayoutBinding

class ShotsListAdapter : RecyclerView.Adapter<ShotViewHolder>() {
    var shots: List<Shot>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShotViewHolder {
        val shotItemLayoutBinding: ShotItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.shot_item_layout, parent, false)
        return ShotViewHolder(shotItemLayoutBinding)
    }

    override fun getItemCount(): Int {
        return shots?.size ?: 0
    }

    override fun onBindViewHolder(holder: ShotViewHolder, position: Int) {
        val shot = shots!![position]
        holder.bind(shot)
    }
}