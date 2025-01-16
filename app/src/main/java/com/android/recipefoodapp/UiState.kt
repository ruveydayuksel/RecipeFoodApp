package com.android.recipefoodapp

import com.android.recipefoodapp.recipe.RecipeModel

data class UiState(
    val isLoading: Boolean = false,
    val recipes: List<RecipeModel> = emptyList()
)
