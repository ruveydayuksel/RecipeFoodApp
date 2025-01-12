package com.android.recipefoodapp.recipe

interface RecipeClickListener {
    fun onItemClicked(recipe: RecipeModel)
    fun onLikeClicked(recipe: RecipeModel)
}