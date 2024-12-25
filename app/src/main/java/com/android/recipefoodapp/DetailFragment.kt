package com.android.recipefoodapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        // Arguments ile gelen veriyi al
        val recipeName = arguments?.getString("name")
        val recipeImage = arguments?.getString("image")

        // View'ları güncelle
        view.findViewById<TextView>(R.id.productName).text = recipeName
        view.findViewById<ImageView>(R.id.imageRecipe).apply {
            Glide.with(this).load(recipeImage).into(this)
        }
        return view
    }
}
