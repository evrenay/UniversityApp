package com.evren.detail.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.evren.detail.databinding.DepartmentsItemBinding
import com.evren.repository.model.entities.university.detail.MajorDetail


class DepartmentAdapter : RecyclerView.Adapter<DepartmentAdapter.UniversityViewHolder>(){

    private var items : List<MajorDetail> = emptyList()

    //region Functions


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UniversityViewHolder(DepartmentsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: UniversityViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateList(items : List<MajorDetail>?){
        items?.let {
            this.items = it
            notifyDataSetChanged()
        }
    }
    //endregion

    class UniversityViewHolder(private val binding: DepartmentsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        //region Functions

        fun bind(item: MajorDetail) {
            binding.entity = item
            binding.executePendingBindings()
        }
        //endregion
    }
}