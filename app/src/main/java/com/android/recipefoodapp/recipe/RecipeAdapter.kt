package com.android.recipefoodapp.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.recipefoodapp.R
import com.bumptech.glide.Glide

class RecipeAdapter(
    private var recipes: List<RecipeModel>,
    private val listener: RecipeClickListener,
    private val onLikeClicked: (RecipeModel) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {


    fun updateData(newRecipes: List<RecipeModel>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.recipe_name)
        private val likeButton: Button = itemView.findViewById(R.id.like_button)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView) // ImageView added here

        fun bind(recipe: RecipeModel) {
            nameTextView.text = recipe.name

            // Load image with Glide
            Glide.with(itemView.context)
                .load(recipe.imageUrl)
                .into(imageView)

            // Item click event
            itemView.setOnClickListener {
                listener.onItemClicked(recipe)
                onLikeClicked(recipe)
            }

            // Like button click event
            likeButton.setOnClickListener {
                listener.onLikeClicked(recipe)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount(): Int = recipes.size
}


