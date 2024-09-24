package com.example.task1.view.mainAct.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.task1.Model.Carousel
import com.example.task1.Model.Clients
import com.example.task1.Model.Roles
import com.example.task1.R
import com.example.task1.domain.Constants
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun Introduction(navHost: NavHostController, viewModel: MainViewModel) {
    var photos by remember { mutableStateOf<List<Carousel>>(listOf()) }
    var clients by remember { mutableStateOf<List<Clients>>(listOf()) }
    var roles by remember { mutableStateOf<List<Roles>>(listOf()) }
    val sale = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var currentClient by remember { mutableStateOf<List<Clients>>(listOf()) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                photos = Constants.supabase.from("Carousel").select().decodeList()
                photos.forEach { it ->
                    println(it.id)
                }
/*                clients = Constants.supabase.from("Clients").select().decodeList()
                currentClient = Constants.supabase.from("Clients")
                    .select{
                    filter {
                        eq("client_id", Constants.supabase.auth.currentUserOrNull()!!.id)
                    }
                }.decodeList()
                //currentClient.forEach{it->
                    println("!!!!!!!!!role"+currentClient.first().role.toString())
                //}
                roles = Constants.supabase.from("Roles").select().decodeList()*/
            } catch (e: Exception) {
                println("Error")
                println(e.message)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFfefae0))
            .verticalScroll(ScrollState(0))
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Это —",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 90.dp, bottom = 20.dp),
                color = Color(0xFFd4a373)
            )
            Text(
                "Purrfect Cup!",
                fontSize = 50.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 200.dp, bottom = 20.dp),
                color = Color(0xFFd4a373)
            )
        }

        Icon(
            painter = painterResource(R.drawable.free_icon_paw_3659404),
            contentDescription = "",
            tint = Color(0xFFd4a373),
            modifier = Modifier
                .padding(bottom = 40.dp)
                .width(312.dp)
                .height(312.dp)
                .align(Alignment.CenterHorizontally)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(bottom = 0.dp)
        ) {
            Text(
                "Галерея работников месяца",
                fontWeight = FontWeight.Bold,
                fontSize = 19.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.CenterVertically),
                color = Color(0xFFd4a373)
            )

            Box(
                modifier = Modifier.fillMaxSize().align(Alignment.CenterVertically)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_right_24),
                    contentDescription = "",
                    tint = Color(0xFFd4a373),
                    modifier = Modifier.align(Alignment.CenterEnd).height(39.dp).width(39.dp)
                )
            }
        }

        LazyRow(
            modifier = Modifier.padding(bottom = 20.dp)
        ) {
            items(photos) { photo ->
                val imageState = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current).data(photo.image)
                        .size(Size.ORIGINAL).build()
                ).state
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .border(2.dp, Color(0xFFd4a373), RoundedCornerShape(13.dp))
                        .padding(5.dp)
                ) {
                    if (imageState is AsyncImagePainter.State.Error) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(300.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    if (imageState is AsyncImagePainter.State.Success) {
                        Image(
                            modifier = Modifier
                                .fillMaxHeight()
                                //.width(300.dp),
                                .fillParentMaxWidth()
                                .clip(RoundedCornerShape(13.dp)),
                            painter = imageState.painter,
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(bottom = 0.dp)
            ){
                Text(
                    "Скидка недели",
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .align(Alignment.CenterVertically),
                    color = Color(0xFFd4a373)
                )
                Box(
                    modifier = Modifier.fillMaxSize().align(Alignment.CenterVertically)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_right_24),
                        contentDescription = "",
                        tint = Color(0xFFd4a373),
                        modifier = Modifier.align(Alignment.CenterEnd).height(39.dp).width(39.dp)
                    )
                }
            }
            TextField(
                value = "Черные котики тоже заслуживают любви! Гости во всем черном, нарядившиеся в поддержку черных кошек, получают скидку 15%!",
                textStyle = TextStyle(fontSize = 15.sp),
                onValueChange = { newText -> sale.value = newText },
                readOnly = true,
                minLines = 2,
                shape = RoundedCornerShape(13.dp),
                modifier = Modifier
                    .border(2.dp, Color(0xFFfefae0), shape = RoundedCornerShape(10.dp))
                    .padding(bottom = 70.dp, start = 20.dp, end = 20.dp)
                    .onKeyEvent {
                        if (it.key == Key.Enter) {
                            keyboardController!!.hide()
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

    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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
                        .width(70.dp)
                        .background(Color(0xFFfefae0)),
                    colors = ButtonDefaults.buttonColors(Color(0xFFfefae0)),

                    ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_auto_awesome_24),
                        contentDescription = "",
                        tint = Color(0xFFd4a373),
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
                        .align(Alignment.CenterEnd),
                    colors = ButtonDefaults.buttonColors(Color(0xFFd4a373)),

                    ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_person_24),
                        contentDescription = "",
                        tint = Color(0xFFfefae0),
                    )
                }
            }
        }

    }
}

/*fun readOnlySale(currentClient: String):Boolean{
    if (currentClient == "7edb89a9-4f56-4f4d-981a-248a91ecaa80") return true
    else return false
}*/

