package com.android.recipefoodapp.recipe

import androidx.lifecycle.ViewModel
import com.android.recipefoodapp.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RecipeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> get() = _uiState

    private val _originalRecipes = mutableListOf<RecipeModel>()

    fun setOriginalRecipes(recipes: List<RecipeModel>) {
        _originalRecipes.clear()
        _originalRecipes.addAll(recipes)
        _uiState.update { it.copy(isLoading = false, recipes = _originalRecipes) }
    }

    fun filterRecipes(query: String) {
        _uiState.update {
            val filteredList = when {
                query.length >= 3 -> _originalRecipes.filter { it.name.contains(query, ignoreCase = true) }
                else -> _originalRecipes
            }
            it.copy(recipes = filteredList)
        }
    }

    fun setLoadingState(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }
}
