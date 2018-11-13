package com.ddmeng.dribbbleclient.features.home

import android.support.v7.widget.RecyclerView
import com.ddmeng.dribbbleclient.data.model.Shot
import com.ddmeng.dribbbleclient.databinding.ShotItemLayoutBinding

class ShotViewHolder(private val itemLayoutBinding: ShotItemLayoutBinding) :
        RecyclerView.ViewHolder(itemLayoutBinding.root) {


    fun bind(shot: Shot) {
        itemLayoutBinding.title.text = shot.title
        itemLayoutBinding.executePendingBindings()
    }
}