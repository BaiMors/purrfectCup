package com.example.task1.view.mainAct.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.task1.Model.Branches
import com.example.task1.Model.Pets
import com.example.task1.R
import com.example.task1.domain.Constants
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun Pets(navHost: NavHostController, viewModel: MainViewModel){
    var pets by remember { mutableStateOf<List<Pets>>(listOf()) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                pets = Constants.supabase.from("Pets").select {
                    filter {
                        //eq("branch", selectedBranch)
                    }
                }.decodeList()
                pets.forEach{it->
                    println(it.name)
                }
                println("Success get pets")
            } catch (e: Exception) {
                println("Error get branches")
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
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn {
                items(
                    pets,
                    key = { pet -> pet.id }
                ){
                        pet ->
                    val imageState = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current).data(pet.image)
                            .size(Size.ORIGINAL).build()
                    ).state
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color(0xFFd4a373))
                            .padding(5.dp)
                            .clickable {
                                //navHost.navigate("Pets/${branch.id}")
                            }
                    ){
                        if (imageState is AsyncImagePainter.State.Error) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        if (imageState is AsyncImagePainter.State.Success) {
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                painter = imageState.painter,
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .background(Color.Black.copy(alpha = 0.5f)),
                            )
                        }
                        Text(
                            pet.name,
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.BottomStart),
                            color = Color(0xFFfefae0),
                            fontSize = 20.sp
                        )
                    }
                }
            }

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
                            .width(70.dp)
                            .background(Color(0xFFfefae0)),
                        colors = ButtonDefaults.buttonColors(Color(0xFFfefae0)),

                        ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_keyboard_double_arrow_right_24),
                            contentDescription = "",
                            tint = Color(0xFFd4a373),
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
}