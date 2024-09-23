package com.example.task1.view.mainAct.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun Pets(selectedBranch: String){
    var pets by remember { mutableStateOf<List<Pets>>(listOf()) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                pets = Constants.supabase.from("Pets").select {
                    filter {
                        eq("branch", selectedBranch)
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
                    .clickable {  }
            )
        }

        LazyColumn {
            items(
                pets,
                key = { pet -> pet.id }
            )
            {
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
                    Icon(
                        painter = painterResource(R.drawable.baseline_keyboard_arrow_right_24),
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(bottom = 8.dp, end = 20.dp)
                    )
                }
            }
        }
    }
}