package com.example.task1.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.task1.view.mainAct.components.BranchesList
import com.example.task1.view.mainAct.components.Introduction
import com.example.task1.view.mainAct.components.MainViewModel
import com.example.task1.view.mainAct.components.Pets
import com.example.task1.view.mainAct.components.UserProfile
import com.example.task1.view.mainAct.components.UserProfileMain
import com.example.task1.view.mainAct.components.avt

/*Класс для перемещения по страницам*/
@Composable
fun Navigation(viewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController,//контроллер реагирующий и отвечающий за перемещения
        startDestination = "avt")
    {
        composable("avt"){
            avt(navController, viewModel)
        }
        composable("Introduction"){
            Introduction(navController, viewModel)
        }
        composable("BranchesList"){
            BranchesList(navController, viewModel)
        }
        composable("UserProfile"){
            UserProfile(navController, viewModel)
        }
        composable("UserProfileMain"){
            UserProfileMain(navController, viewModel)
        }
        composable("Pets"){
            Pets(navController, viewModel)
        }
    }
}