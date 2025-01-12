package com.android.recipefoodapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.recipefoodapp.recipe.RecipeAdapter

class RecipeFragment : Fragment(), RecipeClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipes = listOf(
            Recipe(1, "Japanese Food", "https://images.pexels.com/photos/193056/pexels-photo-193056.jpeg"),
            Recipe(2, "Takoyaki", "https://images.pexels.com/photos/1624478/pexels-photo-1624478.jpeg")
        )

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = RecipeAdapter(recipes, this) { recipe ->

        }
    }

    override fun onItemClicked(recipe: Recipe) {
        Toast.makeText(requireContext(), "Clicked: ${recipe.id} - ${recipe.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onLikeClicked(recipe: Recipe) {
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

