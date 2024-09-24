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

class MainViewModel:ViewModel() {
    // Класс для хранения результата авторизации
    sealed class Result {
        data class Success(val user: Any) : Result()  // Успех
        data class Error(val message: String) : Result()  // Ошибка
    }

    // Состояние для результата авторизации
    private val _authResult = MutableStateFlow<Result?>(null)
    val authResult: StateFlow<Result?> = _authResult

    private val _regResult = MutableStateFlow<Result?>(null)
    val regResult: StateFlow<Result?> = _regResult

    private val _upsertResult = MutableStateFlow<Result?>(null)
    val upsertResult: StateFlow<Result?> = _upsertResult

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

    /*fun addUserProfile(
        newName: String,
        newSurname: String,
    ){
        viewModelScope.launch {
           try {
                val user = Clients(client_id = Constants.supabase.auth.currentUserOrNull()!!.id, name = newName, surname = newSurname)
                Constants.supabase.from("Clients").insert(user)
            }
            catch (e: Exception){
                println(e.message.toString())
            }
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
    }*/

    fun updateUserProfile(
        newName: String,
        newSurname: String,
        newEmail: String,
        newPassword: String
    ) {
        println("enter upd")
        viewModelScope.launch {
            try {
                val toUpsert = Clients(client_id = Constants.supabase.auth.currentUserOrNull()!!.id, name = newName, surname = newSurname, role = null)
                Constants.supabase.from("Clients").upsert(
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
                _authResult.value = Result.Success(user)
            } catch (e: Exception) {
                println("Error")
                println(e.message.toString())
                _authResult.value = Result.Error(e.message.toString())  // Ошибка входа
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
                _regResult.value = Result.Success(user = user!!)
            }
            catch (e: Exception) {
                println("Error")
                println(e.message.toString())
                _regResult.value = Result.Error(e.message.toString())
            }

        }
    }
}