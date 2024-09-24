package com.example.task1.view.mainAct.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.task1.Model.Clients
import com.example.task1.R
import com.example.task1.domain.Constants
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun UserProfileMain(navHost: NavHostController, viewModel: MainViewModel)
{
    var ids by remember { mutableStateOf<List<Clients>>(listOf()) }
    val name = remember { mutableStateOf("") }
    var surname = remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val sh = 17

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                email = Constants.supabase.auth.currentUserOrNull()?.email.toString()
                ids = Constants.supabase.from("Clients").select {
                    filter {
                        eq("client_id", Constants.supabase.auth.currentUserOrNull()!!.id)
                    }
                }.decodeList()
                name.value = Constants.supabase.from("Clients")
                    .select{filter{eq("client_id",Constants.supabase.auth.currentUserOrNull()!!.id)}}.decodeList<Clients>().first().name
                surname.value = Constants.supabase.from("Clients")
                    .select{filter{eq("client_id",Constants.supabase.auth.currentUserOrNull()!!.id)}}.decodeList<Clients>().first().surname
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .background(Color(0xFFd4a373))
        ) {
            Text(
                "Профиль",
                modifier = Modifier.padding(top = 50.dp, start = 10.dp),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFfefae0)
            )
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
                        .padding(top = 35.dp, end = 20.dp)
                        .align(Alignment.CenterEnd)
                        .clickable { navHost.navigate("UserProfile") }
                )
            }
        }

        Icon(
            painter = painterResource(R.drawable.free_icon_paw_3659404),
            contentDescription = "",
            tint = Color(0xFFd4a373),
            modifier = Modifier.padding(bottom = 20.dp, top = 50.dp).align(Alignment.CenterHorizontally)
        )

        Box(
            modifier = Modifier
                .padding(bottom = 20.dp, top = 70.dp)
                .fillMaxWidth()
        )
        {
            TextField(
                value = name.value,
                label = { Text("Имя") },
                readOnly = true,
                textStyle = TextStyle(fontSize = 15.sp),
                onValueChange = { newText -> name.value = newText },
                shape = RoundedCornerShape(sh.dp),
                modifier = Modifier
                    .border(2.dp, Color(0xFFd4a373), shape = RoundedCornerShape(sh.dp))
                    .align(Alignment.Center),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
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
                readOnly = true,
                textStyle = TextStyle(fontSize = 15.sp),
                onValueChange = { newText -> surname.value = newText },
                shape = RoundedCornerShape(sh.dp),
                modifier = Modifier
                    .border(2.dp, Color(0xFFd4a373), shape = RoundedCornerShape(sh.dp))
                    .align(Alignment.Center),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
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
                value = email,
                label = { Text("Электронная почта") },
                textStyle = TextStyle(fontSize = 15.sp),
                readOnly = true,
                onValueChange = { newText -> email = newText },
                shape = RoundedCornerShape(sh.dp),
                modifier = Modifier
                    .border(2.dp, Color(0xFFd4a373), shape = RoundedCornerShape(sh.dp))
                    .align(Alignment.Center),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color(0xFFd4a373),
                    unfocusedTextColor = Color(0xFFd4a373)
                )
            )
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ){
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color(0xFFd4a373))
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 20.dp, end = 10.dp)
                ) {
                    Button(
                        onClick = { navHost.navigate("Introduction") },
                        modifier = Modifier
                            .width(70.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFFd4a373)),

                        ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_auto_awesome_24),
                            contentDescription = "",
                            tint = Color(0xFFfefae0),
                        )
                    }
                    Button(
                        onClick = { navHost.navigate("BranchesList") },
                        modifier = Modifier
                            .width(70.dp)
                            .align(Alignment.CenterEnd),
                        colors = ButtonDefaults.buttonColors(Color(0xFFd4a373)),

                        ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_yard_24),
                            contentDescription = "",
                            tint = Color(0xFFfefae0),
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(end = 20.dp, start = 10.dp)
                ) {
                    Button(
                        onClick = { navHost.navigate("Pets") },
                        modifier = Modifier
                            .width(70.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFFd4a373)),

                        ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_keyboard_double_arrow_right_24),
                            contentDescription = "",
                            tint = Color(0xFFfefae0),
                        )
                    }
                    Button(
                        onClick = { navHost.navigate("UserProfileMain") },
                        modifier = Modifier
                            .width(70.dp)
                            .align(Alignment.CenterEnd)
                            .background(Color(0xFFfefae0)),
                        colors = ButtonDefaults.buttonColors(Color(0xFFfefae0)),

                        ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_person_24),
                            contentDescription = "",
                            tint = Color(0xFFd4a373),
                        )
                    }
                }
            }
        }
    }
}