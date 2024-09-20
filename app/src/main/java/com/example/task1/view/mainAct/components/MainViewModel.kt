package com.example.task1.view.mainAct.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task1.domain.Constants
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.providers.builtin.OTP
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel():ViewModel() {
    // Класс для хранения результата авторизации
    sealed class AuthResult {
        data class Success(val user: Any) : AuthResult()  // Успех
        data class Error(val message: String) : AuthResult()  // Ошибка
    }

    // Состояние для результата авторизации
    private val _authResult = MutableStateFlow<AuthResult?>(null)
    val authResult: StateFlow<AuthResult?> = _authResult

    private val _regResult = MutableStateFlow<AuthResult?>(null)
    val regResult: StateFlow<AuthResult?> = _regResult

    fun onSignInEmailCode(emailUser: String) {
        viewModelScope.launch {
            try {
                Constants.supabase.auth.signInWith(OTP) {
                    email = emailUser
                    createUser = false
                }

            } catch (e: Exception) {
                println(e.message.toString())

            }

        }
    }

    fun onSignInEmailPassword(emailUser: String, passwordUser: String) {
        viewModelScope.launch {
            try {
                val user = Constants.supabase.auth.signInWith(Email) {
                    email = emailUser
                    password = passwordUser
                }
                println(user.toString())
                println(Constants.supabase.auth.currentUserOrNull()!!.id)
                println("Success")
                _authResult.value = AuthResult.Success(user)
            } catch (e: Exception) {
                println("Error")
                println(e.message.toString())
                _authResult.value = AuthResult.Error(e.message.toString())  // Ошибка входа
            }
        }
    }
    fun onSignUpEmail(emailUser: String, passwordUser: String) {
        viewModelScope.launch {
            try{
                var  user =  Constants.supabase.auth.signUpWith(Email) {
                    email = emailUser
                    password = passwordUser
                }
                println(user.toString())
                println(Constants.supabase.auth.currentUserOrNull()!!.id)
                println("Success")
                _regResult.value = AuthResult.Success(user = user!!)
            }
            catch (e: Exception) {
                println("Error")
                println(e.message.toString())
                _regResult.value = AuthResult.Error(e.message.toString())
            }

        }
    }
}