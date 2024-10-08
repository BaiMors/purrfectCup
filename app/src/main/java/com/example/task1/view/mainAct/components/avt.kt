package com.example.task1.view.mainAct.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.task1.R


@Composable
fun avt(navHost: NavHostController, viewModel: MainViewModel) {
    val viewModel = MainViewModel()
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val authResult by viewModel.authResult.collectAsState()
    val regResult by viewModel.regResult.collectAsState()
    val ctx = LocalContext.current
    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color(0xFFd4a373)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Icon(
            painter = painterResource(R.drawable.free_icon_paw_3659404),
            contentDescription = "",
            tint = Color(0xFFfefae0),
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Column (
            modifier = Modifier.padding(bottom = 20.dp)
        ){
            TextField(
                value = email.value,
                textStyle = TextStyle(fontSize = 15.sp),
                onValueChange = { newText -> email.value = newText },
                maxLines = 1,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .border(2.dp, Color(0xFFfefae0), shape = RoundedCornerShape(10.dp))
                    .focusRequester(focusRequester1)
                    .onKeyEvent {
                        if (it.key == Key.Enter) {
                            focusRequester2.requestFocus()
                            true
                        } else {
                            false
                        }
                    },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFd4a373),
                    focusedContainerColor = Color(0xFFd4a373),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color(0xFFfefae0),
                    unfocusedTextColor = Color(0xFFfefae0)
                )
            )

        }

        var passwordVisibility: Boolean by remember { mutableStateOf(false) }
        TextField(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .border(2.dp, Color(0xFFfefae0), shape = RoundedCornerShape(10.dp))
                .focusRequester(focusRequester2)
                .onKeyEvent {
                    if (it.key == Key.Enter) {
                        keyboardController!!.hide()
                        true
                    } else {
                        false
                    }
                },
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFd4a373),
                focusedContainerColor = Color(0xFFd4a373),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color(0xFFfefae0),
                unfocusedTextColor = Color(0xFFfefae0)
            ),
            value = password.value,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    if(passwordVisibility) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_person_24),
                            contentDescription = ""
                        )
                    }
                    else {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_person_off_24),
                            contentDescription = ""
                        )
                    }
                }
            },
            onValueChange = { newText -> password.value = newText })

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 40.dp, end = 40.dp, bottom = 20.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFfefae0)),
            onClick = {
            viewModel.onSignInEmailPassword(email.value,password.value)
        }){
            Text("Войти", fontSize = 20.sp, color = Color(0xFF000000))
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp, start = 40.dp, end = 40.dp, bottom = 20.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFfefae0)),
            onClick = {
            viewModel.onSignUpEmail(email.value,password.value)
        }){
            Text("Зарегистрироваться", fontSize = 20.sp, color = Color(0xFF000000))
        }

        // Обработка результата авторизации
        when (authResult) {
            is MainViewModel.Result.Success -> {
                // Если авторизация успешна, навигация на другой экран
                navHost.navigate("Introduction")
            }
            is MainViewModel.Result.Error -> {
                // Если произошла ошибка, показываем сообщение об ошибке
                Toast.makeText(ctx, "Error: ${(authResult as MainViewModel.Result.Error).message}", Toast.LENGTH_SHORT).show()
            }
            null -> {
                // Ожидание результата, ничего не делаем
            }
        }

            when (regResult) {
            is MainViewModel.Result.Success -> {
                Toast.makeText(ctx, "Регистрация успешна. Подтвердите почту и войдите", Toast.LENGTH_SHORT).show()
            }
            is MainViewModel.Result.Error -> {
                Toast.makeText(ctx, "Error: ${(regResult as MainViewModel.Result.Error).message}", Toast.LENGTH_SHORT).show()
            }
            null -> {
            }
        }
    }


}

@Override
public fun onBackPressed(){

}