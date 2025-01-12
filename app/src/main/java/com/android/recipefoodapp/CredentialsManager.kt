package com.android.recipefoodapp

import android.util.Patterns
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object CredentialsManager {

    // E-posta formatını doğrulama
    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Şifre doğrulama: En az 6 karakter
    fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }

    // Kayıt işlemi
    suspend fun register(email: String, password: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                // Burada, örnek olarak kayıt işlemi simülasyonu yapılmaktadır
                // Gerçek kayıt işlemi yapılabilir (Firebase, API vb. kullanarak)
                Thread.sleep(1000) // Simülasyon amacıyla
                // Örnek: Kayıt başarılı ise
                Result.success(Unit)
            } catch (e: Exception) {
                // Kayıt hatası durumu
                Result.failure(e)
            }
        }
    }

    // Kullanıcı girişi yapma
    suspend fun login(email: String, password: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                // Burada, örnek olarak giriş işlemi simülasyonu yapılmaktadır
                // Gerçek giriş işlemi yapılabilir (Firebase, API vb. kullanarak)
                Thread.sleep(1000) // Simülasyon amacıyla
                // Örnek: Giriş başarılı ise
                Result.success(Unit)
            } catch (e: Exception) {
                // Giriş hatası durumu
                Result.failure(e)
            }
        }
    }


}