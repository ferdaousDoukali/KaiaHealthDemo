package com.kaiahealth.demo.presentation.home.view_holder

import androidx.recyclerview.widget.RecyclerView
import com.kaiahealth.demo.R
import com.kaiahealth.demo.core.model.Exercise
import com.kaiahealth.demo.databinding.HomeItemBinding
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.util.*

class HomeItemViewHolder(
    private val itemViewBinding: HomeItemBinding,
    private val onClick: (Exercise) -> Unit
) :
    RecyclerView.ViewHolder(itemViewBinding.root) {

    fun bindView(item: Exercise, isInFavorite:Boolean) {

        itemViewBinding.run {
            itemTitle.text = item.name
            Picasso.get().load(item.coverImageUrl).into(itemImage)

            toggleAddToFavoriteButton(isInFavorite)

            addToFavorite.setOnClickListener {
                val addToFavorite = (it.tag == null || it.tag == R.drawable.ic_add_favorite_24dp)
                toggleAddToFavoriteButton(addToFavorite)
                onClick(item)
            }
            item
        }
    }

    private fun toggleAddToFavoriteButton(isInFavorite: Boolean) {
        if (isInFavorite) {
            itemViewBinding.addToFavorite.setImageResource(R.drawable.ic_add_favorite_on_24dp)
            itemViewBinding.addToFavorite.tag = R.drawable.ic_add_favorite_on_24dp
        } else {
            itemViewBinding.addToFavorite.setImageResource(R.drawable.ic_add_favorite_24dp)
            itemViewBinding.addToFavorite.tag = R.drawable.ic_add_favorite_24dp
        }
    }
}