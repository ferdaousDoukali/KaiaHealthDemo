package com.kaiahealth.demo.presentation.summary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kaiahealth.demo.core.model.Exercise
import com.kaiahealth.demo.databinding.HomeItemBinding
import com.kaiahealth.demo.databinding.SummaryItemBinding
import com.kaiahealth.demo.presentation.home.view_holder.HomeItemViewHolder
import com.kaiahealth.demo.presentation.summary.view_holder.SummaryItemViewHolder

class SummaryItemAdapter : RecyclerView.Adapter<SummaryItemViewHolder>() {

    private var itemList = mutableListOf<Exercise>()
    private var doneExercises = mutableListOf<Exercise>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SummaryItemViewHolder {

        val itemBinding =
            SummaryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return SummaryItemViewHolder(itemBinding)
    }


    override fun onBindViewHolder(holder: SummaryItemViewHolder, position: Int) {
        val exercise = itemList[position]
        val isDone = doneExercises.contains(exercise)
        holder.bindView(exercise, isDone)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateItemList(items: List<Exercise>, doneItems: List<Exercise>) {
        itemList.clear()
        itemList.addAll(items)
        doneExercises.clear()
        doneExercises.addAll(doneItems)
        notifyDataSetChanged()
    }
}