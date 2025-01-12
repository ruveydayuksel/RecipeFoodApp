package com.android.recipefoodapp.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.recipefoodapp.DetailFragment
import com.android.recipefoodapp.R

class RecipeFragment : Fragment(), RecipeClickListener {
    private lateinit var originalRecipes: List<RecipeModel>
    private lateinit var filteredRecipes: MutableList<RecipeModel>
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var viewModel: RecipeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[RecipeViewModel::class.java]

        // Orijinal listeyi ViewModel'den ayarlayın
        viewModel.setOriginalRecipes(
            listOf(
                RecipeModel(1, "Japanese Food", "https://images.pexels.com/photos/193056/pexels-photo-193056.jpeg"),
                RecipeModel(2, "Takoyaki", "https://images.pexels.com/photos/1624478/pexels-photo-1624478.jpeg"),
                RecipeModel(1, "Japanese Food", "https://images.pexels.com/photos/193056/pexels-photo-193056.jpeg"),
                RecipeModel(2, "Takoyaki", "https://images.pexels.com/photos/1624478/pexels-photo-1624478.jpeg"),
                RecipeModel(1, "Japanese Food", "https://images.pexels.com/photos/193056/pexels-photo-193056.jpeg"),
                RecipeModel(2, "Takoyaki", "https://images.pexels.com/photos/1624478/pexels-photo-1624478.jpeg")
            )
        )

        // Orijinal listeyi al ve başlat
        originalRecipes = viewModel.recipes.value
        filteredRecipes = originalRecipes.toMutableList()

        // RecyclerView ve Adapter
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recipeAdapter = RecipeAdapter(filteredRecipes, this) { recipe ->
            // Item click logic (opsiyonel)
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recipeAdapter

        // Liste değişikliklerini gözlemle
        lifecycleScope.launchWhenStarted {
            viewModel.recipes.collect { filteredList ->
                recipeAdapter.updateData(filteredList)
            }
        }

        // SearchView logic
        val searchView = view.findViewById<androidx.appcompat.widget.SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterRecipes(newText.orEmpty())
                return true
            }
        })
    }

    override fun onItemClicked(recipe: RecipeModel) {
        Toast.makeText(requireContext(), "Clicked: ${recipe.id} - ${recipe.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onLikeClicked(recipe: RecipeModel) {
        Toast.makeText(requireContext(), "Clicked: ${recipe.id} - ${recipe.name}", Toast.LENGTH_SHORT).show()
        val bundle = Bundle().apply {
            putString("name", recipe.name)
            putString("image", recipe.imageUrl)
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DetailFragment::class.java, bundle)
            .addToBackStack(null)
            .commit()
    }
}

