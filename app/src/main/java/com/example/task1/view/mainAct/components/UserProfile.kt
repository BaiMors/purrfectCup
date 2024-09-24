package com.example.task1.view.mainAct.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavHostController
import com.example.task1.Model.Branches
import com.example.task1.Model.Clients
import com.example.task1.R
import com.example.task1.domain.Constants
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun UserProfile(navHost: NavHostController, viewModel: MainViewModel) {
    var ids by remember { mutableStateOf<List<Clients>>(listOf()) }
    val name = remember { mutableStateOf("") }
    var surname = remember { mutableStateOf("") }
    var email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val sh = 17
    val upsertResult by viewModel.upsertResult.collectAsState()
    val ctx = LocalContext.current

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
//                email = Constants.supabase.auth.currentUserOrNull()?.email.toString()
                ids = Constants.supabase.from("Clients").select {
                    filter {
                        eq("client_id", Constants.supabase.auth.currentUserOrNull()!!.id)
                    }
                }.decodeList()
                if (ids.isNotEmpty()) println("ids is not empty, need update")
                println("Success get client")
            } catch (e: Exception) {
                println("Error get client")
                println(e.message)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFfefae0))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .background(Color(0xFFd4a373))
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_keyboard_arrow_left_24),
                contentDescription = "",
                modifier = Modifier
                    .background(Color(0xFFd4a373))
                    .padding(top = 35.dp, start = 20.dp)
                    .align(Alignment.CenterStart)
                    .clickable { navHost.popBackStack() }
            )
        }

        /*Icon(
            painter = painterResource(Icons.Default.Face.hashCode()),
            contentDescription = "",
            modifier = Modifier
                .padding(top = 35.dp)
                .align(Alignment.CenterHorizontally),
            //contentScale = ContentScale.Crop
        )*/

        Box(
            modifier = Modifier
                .padding(bottom = 20.dp, top = 70.dp)
                .fillMaxWidth()
        )
        {
            TextField(
                value = name.value,
                label = { Text("Имя") },
                textStyle = TextStyle(fontSize = 15.sp),
                onValueChange = { newText -> name.value = newText },
                shape = RoundedCornerShape(sh.dp),
                modifier = Modifier
                    .border(2.dp, Color(0xFFd4a373), shape = RoundedCornerShape(sh.dp))
                    .align(Alignment.Center),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFfefae0),
                    focusedContainerColor = Color(0xFFfefae0),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color(0xFFd4a373),
                    unfocusedTextColor = Color(0xFFd4a373)
                )
            )
        }
        Box(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        )
        {
            TextField(
                value = surname.value,
                label = { Text("Фамилия") },
                textStyle = TextStyle(fontSize = 15.sp),
                onValueChange = { newText -> surname.value = newText },
                shape = RoundedCornerShape(sh.dp),
                modifier = Modifier
                    .border(2.dp, Color(0xFFd4a373), shape = RoundedCornerShape(sh.dp))
                    .align(Alignment.Center),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFfefae0),
                    focusedContainerColor = Color(0xFFfefae0),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color(0xFFd4a373),
                    unfocusedTextColor = Color(0xFFd4a373)
                )
            )
        }

        Box(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        )
        {
            TextField(
                value = email.value,
                label = { Text("Электронная почта") },
                textStyle = TextStyle(fontSize = 15.sp),
                onValueChange = { newText -> email.value = newText },
                shape = RoundedCornerShape(sh.dp),
                modifier = Modifier
                    .border(2.dp, Color(0xFFd4a373), shape = RoundedCornerShape(sh.dp))
                    .align(Alignment.Center),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFfefae0),
                    focusedContainerColor = Color(0xFFfefae0),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color(0xFFd4a373),
                    unfocusedTextColor = Color(0xFFd4a373)
                )
            )
        }
        Box(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        )
        {
            TextField(
                value = password.value,
                label = { Text("Пароль") },
                textStyle = TextStyle(fontSize = 15.sp),
                onValueChange = { newText -> password.value = newText },
                shape = RoundedCornerShape(sh.dp),
                modifier = Modifier
                    .border(2.dp, Color(0xFFd4a373), shape = RoundedCornerShape(sh.dp))
                    .align(Alignment.Center),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFfefae0),
                    focusedContainerColor = Color(0xFFfefae0),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color(0xFFd4a373),
                    unfocusedTextColor = Color(0xFFd4a373)
                )
            )
        }

        when (upsertResult) {
            is MainViewModel.Result.Success -> {
                navHost.navigate("UserProfileMain")
                Toast.makeText(ctx, "Данные успешно обновлены", Toast.LENGTH_SHORT).show()
            }
            is MainViewModel.Result.Error -> {
                Toast.makeText(ctx, "Error: ${(upsertResult as MainViewModel.Result.Error).message}", Toast.LENGTH_SHORT).show()
            }
            null -> {
                // Ожидание результата, ничего не делаем
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 50.dp, end = 50.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(Color(0xFFd4a373)),
            shape = RoundedCornerShape(sh.dp),
            onClick = {
                viewModel.updateUserProfile(name.value, surname.value, email.value, password.value)
                navHost.navigate("UserProfileMain")
                Toast.makeText(ctx, "Данные успешно обновлены", Toast.LENGTH_SHORT).show()
            }){
            Text("Сохранить изменения", fontSize = 17.sp, color = Color(0xFFfefae0))
        }
    }
}
