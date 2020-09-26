package com.evren.university.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.evren.repository.model.entities.university.UniversityEntityItem
import com.evren.university.databinding.UniversityItemBinding


class UniversityAdapter(val itemClickListener: (UniversityEntityItem) -> Unit) : RecyclerView.Adapter<UniversityAdapter.UniversityViewHolder>(){

    private var items : List<UniversityEntityItem> = emptyList()

    //region Functions


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UniversityViewHolder(UniversityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: UniversityViewHolder, position: Int) {
        holder.bind(items[position],itemClickListener)
    }

    fun updateList(items : List<UniversityEntityItem>?){
        items?.let {
            this.items = it
            notifyDataSetChanged()
        }
    }
    //endregion

    class UniversityViewHolder(private val binding: UniversityItemBinding) : RecyclerView.ViewHolder(binding.root) {

        //region Functions

         fun bind(item: UniversityEntityItem,itemClickListener: (UniversityEntityItem) -> Unit) {
            binding.entity = item
            binding.imgGoDetail.setOnClickListener {
                itemClickListener(item)
            }
            binding.executePendingBindings()
        }
        //endregion
    }
}


