package com.android.recipefoodapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.recipefoodapp.recipe.RecipeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Fragment'ı yükle
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RecipeFragment())
                .commit()
        }
    }
}