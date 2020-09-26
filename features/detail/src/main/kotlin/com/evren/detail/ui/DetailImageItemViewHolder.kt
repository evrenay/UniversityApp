package com.evren.detail.ui

import androidx.recyclerview.widget.RecyclerView
import com.evren.detail.databinding.DetailImageItemBinding
import com.evren.repository.model.entities.university.detail.Image


class DetailImageItemViewHolder(val binding: DetailImageItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item : Image){
        binding.entity = item
        binding.executePendingBindings()
    }

}