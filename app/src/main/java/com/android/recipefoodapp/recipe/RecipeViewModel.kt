package com.android.recipefoodapp.recipe

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RecipeViewModel : ViewModel() {

    private val _originalRecipes = mutableListOf<RecipeModel>()
    private val _filteredRecipes = MutableStateFlow<List<RecipeModel>>(emptyList())
    val recipes: StateFlow<List<RecipeModel>> get() = _filteredRecipes

    fun setOriginalRecipes(recipes: List<RecipeModel>) {
        _originalRecipes.clear()
        _originalRecipes.addAll(recipes)
        _filteredRecipes.value = _originalRecipes
    }

    fun filterRecipes(query: String) {
        _filteredRecipes.update {
            when {
                query.length >= 3 -> {
                    _originalRecipes.filter {
                        it.name.contains(query, ignoreCase = true)
                    }
                }
                query.length in 1..2 || query.isEmpty() -> {
                    _originalRecipes
                }
                else -> emptyList()
            }
        }
    }
}
