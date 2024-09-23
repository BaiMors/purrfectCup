package com.example.task1.view.mainAct.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task1.Model.Clients
import com.example.task1.domain.Constants
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.contracts.contract

class MainViewModel:ViewModel() {
    // Класс для хранения результата авторизации
    sealed class AuthResult {
        data class Success(val user: Any) : AuthResult()  // Успех
        data class Error(val message: String) : AuthResult()  // Ошибка
    }

    // Состояние для результата авторизации
    private val _authResult = MutableStateFlow<AuthResult?>(null)
    val authResult: StateFlow<AuthResult?> = _authResult

/*    private val _regResult = MutableStateFlow<AuthResult?>(null)
    val regResult: StateFlow<AuthResult?> = _regResult*/

/*    private val _email = MutableStateFlow("")
    val email: Flow<String> = _email
    private val _password = MutableStateFlow("")
    val password = _password
    fun onEmailChange(email: String) {
        _email.value = email
    }
    fun onPasswordChange(password: String) {
        _password.value = password
    }*/

    fun addUserProfile(
        newName: String,
        newSurname: String,
    ){
        viewModelScope.launch {
/*            try {
                val user = Clients(client_id = Constants.supabase.auth.currentUserOrNull()!!.id, name = newName, surname = newSurname)
                Constants.supabase.from("Clients").insert(user)
            }
            catch (e: Exception){
                println(e.message.toString())
            }*/
            try {
                val currentUser = Constants.supabase.auth.currentUserOrNull()

                // Проверяем, что пользователь существует и берем его id
                val userId = currentUser?.id ?: throw IllegalStateException("User not logged in")

                // Создаем объект с правильным UUID
                val user = Clients(client_id = userId, name = newName, surname = newSurname)

                // Вставляем в базу данных
                Constants.supabase.from("Clients").insert(user)
            } catch (e: Exception) {
                println(e.message.toString())
            }
        }
    }

    fun updateUserProfile(
        newName: String,
        newSurname: String,
        newEmail: String,
        newPassword: String
    ) {
        println("enter upd")
        viewModelScope.launch {
            try {
                val toUpsert = Clients(client_id = Constants.supabase.auth.currentUserOrNull()!!.id, name = newName, surname = newSurname)
                Constants.supabase.from("Clients").upsert(
/*                    {
                        set("name", newName)
                        set("surname", newSurname)
                    }*/
                    toUpsert
                ) {
                    filter {
                        eq("client_id", Constants.supabase.auth)
                    }
                }
                Constants.supabase.auth.updateUser {
                    password = newPassword
                    email = newEmail
                }
                println("Success updating")
            } catch (e: Exception) {
                println(e.message.toString())
            }
        }
    }

/*    fun onSignInEmailCode(emailUser: String) {
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
    }*/

    fun onSignInEmailPassword(emailUser: String, passwordUser: String) {
        viewModelScope.launch {
            try {
                val user = Constants.supabase.auth.signInWith(Email) {
                    email = emailUser
                    password = passwordUser
                }
                println(user.toString())
                println(Constants.supabase.auth.currentUserOrNull()!!.id)
                println("Success authorization")
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
                val  user =  Constants.supabase.auth.signUpWith(Email) {
                    email = emailUser
                    password = passwordUser
                }
                println(user.toString())
                println(Constants.supabase.auth.currentUserOrNull()!!.id)
                println("Success registration")
                //_regResult.value = AuthResult.Success(user = user!!)
            }
            catch (e: Exception) {
                println("Error")
                println(e.message.toString())
                //_regResult.value = AuthResult.Error(e.message.toString())
            }

        }
    }
}