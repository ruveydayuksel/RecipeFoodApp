package com.android.recipefoodapp

import android.util.Patterns
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object CredentialsManager {


    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }


    suspend fun register(email: String, password: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                Thread.sleep(1000)
                Result.success(Unit)
            } catch (e: Exception) {

                Result.failure(e)
            }
        }
    }


    suspend fun login(email: String, password: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                Thread.sleep(1000)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun logout(): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {

                Thread.sleep(500)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }


}