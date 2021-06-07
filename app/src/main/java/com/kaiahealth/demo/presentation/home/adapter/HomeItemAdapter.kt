package com.kaiahealth.demo.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kaiahealth.demo.core.model.Exercise
import com.kaiahealth.demo.databinding.HomeItemBinding
import com.kaiahealth.demo.presentation.home.view_holder.HomeItemViewHolder

class HomeItemAdapter(private val onClick: (Exercise) -> Unit) :
    RecyclerView.Adapter<HomeItemViewHolder>() {

    private var itemList = mutableListOf<Exercise>()
    private var favoriteItemList = mutableListOf<Exercise>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeItemViewHolder {

        val itemBinding =
            HomeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return HomeItemViewHolder(itemBinding, onClick)
    }


    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        val exercise = itemList[position]
        val isInFavorite = favoriteItemList.contains(exercise)
        holder.bindView(exercise, isInFavorite)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateItemList(items: List<Exercise>, favoriteItems: List<Exercise>) {
        itemList.clear()
        itemList.addAll(items)
        favoriteItemList.clear()
        favoriteItemList.addAll(favoriteItems)
        notifyDataSetChanged()
    }
}