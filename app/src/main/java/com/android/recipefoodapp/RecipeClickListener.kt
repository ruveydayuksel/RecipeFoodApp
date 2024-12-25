package com.android.recipefoodapp

interface RecipeClickListener {
    fun onItemClicked(recipe: Recipe)
    fun onLikeClicked(recipe: Recipe)
}