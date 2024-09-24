package com.example.task1.view.mainAct.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.task1.Model.Branches
import com.example.task1.R
import com.example.task1.domain.Constants
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun BranchesList(navHost: NavHostController, viewModel: MainViewModel)
{
    var branches by remember { mutableStateOf<List<Branches>>(listOf()) }
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                branches = Constants.supabase.from("Branches").select().decodeList()
                branches.forEach{it->
                    println(it.address)
                }
            } catch (e: Exception) {
                println("Error get branches")
                println(e.message)
            }
        }
    }

/*    NavHost(navController, startDestination = navController.currentDestination!!) {
        //composable(UserRoutes.Users.route) { Users(employees, navController) }
        composable("Pets",
            arguments = listOf(navArgument("branch") { type = NavType.IntType })) {
                stackEntry ->
            val selectedBranch = stackEntry.arguments?.getString("branch")
            Pets(selectedBranch!!)
        }
    }*/

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFfefae0))
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            LazyColumn {
                items(
                    branches,
                    key = { branch -> branch.id }
                )
                {
                        branch ->
                    val imageState = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current).data(branch.image)
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
                            branch.address,
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.BottomStart),
                            color = Color(0xFFfefae0),
                            fontSize = 20.sp
                        )
                        Icon(
                            painter = painterResource(R.drawable.baseline_keyboard_arrow_right_24),
                            contentDescription = "",
                            tint = Color(0xFFfefae0),
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(bottom = 8.dp, end = 20.dp)
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
                    modifier = Modifier.weight(0.5f).padding(start = 20.dp, end = 10.dp)
                ){
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
                            .align(Alignment.CenterEnd)
                            .background(Color(0xFFfefae0)),
                        colors = ButtonDefaults.buttonColors(Color(0xFFfefae0)),

                        ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_yard_24),
                            contentDescription = "",
                            tint = Color(0xFFd4a373),
                        )
                    }
                }
                Box(
                    modifier = Modifier.weight(0.5f).padding(end = 20.dp, start = 10.dp)
                ){
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
}