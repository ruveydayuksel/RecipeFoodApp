package com.android.recipefoodapp.recipe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.recipefoodapp.CredentialsManager
import com.android.recipefoodapp.DetailFragment
import com.android.recipefoodapp.R
import com.android.recipefoodapp.auth.LoginActivity
import kotlinx.coroutines.launch

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

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val progressBar: View = view.findViewById(R.id.progressBar)
        val searchView = view.findViewById<androidx.appcompat.widget.SearchView>(R.id.searchView)
        val logoutButton: Button = view.findViewById(R.id.btnLogout)

        // Adapter ayarları
        recipeAdapter = RecipeAdapter(emptyList(), this) { recipe -> }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recipeAdapter

        // ViewModel gözlemi
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { uiState ->
                progressBar.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE
                recyclerView.visibility = if (uiState.isLoading) View.GONE else View.VISIBLE
                recipeAdapter.updateData(uiState.recipes)
            }
        }
        logoutButton.setOnClickListener {
            lifecycleScope.launch {
                progressBar.visibility = View.VISIBLE // ProgressBar'ı göster
                val result = CredentialsManager.logout()
                progressBar.visibility = View.GONE // ProgressBar'ı gizle

                if (result.isSuccess) {
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    Toast.makeText(requireContext(), "Çıkış işlemi başarısız.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Orijinal listeyi ayarla
        viewModel.setLoadingState(true)
        viewModel.setOriginalRecipes(
            listOf(
                RecipeModel(1, "Japanese Food", "https://images.pexels.com/photos/193056/pexels-photo-193056.jpeg"),
                RecipeModel(2, "Takoyaki", "https://images.pexels.com/photos/1624478/pexels-photo-1624478.jpeg")
            )
        )

        // SearchView arama mantığı
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setLoadingState(true)
                viewModel.filterRecipes(newText.orEmpty())
                lifecycleScope.launch {
                    // Yükleme durumunu 2 saniye sonra kapat
                    kotlinx.coroutines.delay(2000)
                    viewModel.setLoadingState(false)
                }
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

