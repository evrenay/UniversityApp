package com.evren.detail.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.evren.detail.databinding.DetailImageItemBinding
import com.evren.repository.model.entities.university.detail.Image


class DetailImageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items : List<Image> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DetailImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailImageItemViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DetailImageItemViewHolder).bind(items[position])
    }

    fun updateList(items : List<Image>?){
        items?.let {
            this.items = it
            notifyDataSetChanged()
        }
    }


}