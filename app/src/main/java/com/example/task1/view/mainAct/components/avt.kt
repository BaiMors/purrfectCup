package com.example.task1.view.mainAct.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import com.example.task1.R


@Composable
fun avt(navHost: NavHostController, viewModel: MainViewModel) {
    val viewModel = MainViewModel()
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Column(
        Modifier

            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color(0xFFfaedcd)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Column (
            modifier = Modifier.padding(20.dp)
        ){
            TextField(
                value = email.value,
                textStyle = TextStyle(fontSize = 15.sp),
                onValueChange = { newText -> email.value = newText },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.border(2.dp, Color(0xFFd4a373), shape = RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFfefae0),
                    focusedContainerColor = Color(0xFFfefae0),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )

        }

        var passwordVisibility: Boolean by remember { mutableStateOf(false) }
        TextField(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.border(2.dp, Color(0xFFd4a373), shape = RoundedCornerShape(10.dp)),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFfefae0),
                focusedContainerColor = Color(0xFFfefae0),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
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
                .padding(20.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFd4a373)),
            onClick = {
            viewModel.onSignInEmailPassword(email.value,password.value)
        }){
            Text("Войти", fontSize = 25.sp)
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFd4a373)),
            onClick = {
            viewModel.onSignUpEmail(email.value,password.value)
        }){
            Text("Зарегистрироваться", fontSize = 25.sp)
        }
    }
}