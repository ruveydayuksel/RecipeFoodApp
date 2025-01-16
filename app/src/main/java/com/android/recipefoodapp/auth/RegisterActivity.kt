package com.android.recipefoodapp.auth

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.recipefoodapp.CredentialsManager
import com.android.recipefoodapp.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)

        val fullNameInput = findViewById<TextInputEditText>(R.id.fullName)
        val emailInput = findViewById<TextInputEditText>(R.id.registerEmailLayout)
        val phoneInput = findViewById<TextInputEditText>(R.id.editText3)
        val passwordInput = findViewById<TextInputEditText>(R.id.editText2)
        val registerButton = findViewById<Button>(R.id.textView)
        val alreadyLogin = findViewById<ImageView>(R.id.alreadyLogin)

        // Şifre alanını "*" olarak göster
        passwordInput.inputType =
            android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD

        alreadyLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        registerButton.setOnClickListener {
            val fullName = fullNameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val phone = phoneInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            sharedPreferences.edit().apply {
                putString("email", email)
                putString("password", password)
                apply()
            }

            if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (!CredentialsManager.isEmailValid(email)) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!CredentialsManager.isPasswordValid(password)) {
                Toast.makeText(this, "Password must not be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Coroutine başlatıyoruz
            CoroutineScope(Dispatchers.Main).launch {
                val registrationResult = CredentialsManager.register(email, password)

                if (registrationResult.isSuccess) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Registration successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        registrationResult.exceptionOrNull()?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}