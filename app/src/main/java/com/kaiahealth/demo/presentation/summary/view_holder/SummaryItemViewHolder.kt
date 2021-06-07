package com.kaiahealth.demo.presentation.summary.view_holder

import androidx.recyclerview.widget.RecyclerView
import com.kaiahealth.demo.R
import com.kaiahealth.demo.core.model.Exercise
import com.kaiahealth.demo.databinding.HomeItemBinding
import com.kaiahealth.demo.databinding.SummaryItemBinding
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.util.*

class SummaryItemViewHolder(
    private val itemViewBinding: SummaryItemBinding) :
    RecyclerView.ViewHolder(itemViewBinding.root) {

    fun bindView(item: Exercise, isDone:Boolean) {

        itemViewBinding.run {
            itemTitle.text = item.name
            Picasso.get().load(item.coverImageUrl).into(itemImage)
            toggleDoneButton(isDone)
            item
        }
    }

    private fun toggleDoneButton(isDone: Boolean) {
        if (isDone) {
            itemViewBinding.doneIcon.setImageResource(R.drawable.ic_baseline_check_circle_24)
            itemViewBinding.doneIcon.tag = R.drawable.ic_baseline_check_circle_24
        } else {
            itemViewBinding.doneIcon.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
            itemViewBinding.doneIcon.tag = R.drawable.ic_baseline_check_circle_outline_24
        }
    }
}