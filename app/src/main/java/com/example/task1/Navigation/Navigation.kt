package com.example.task1.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.task1.view.mainAct.components.BranchesList
import com.example.task1.view.mainAct.components.MainViewModel
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
        composable("BranchesList"){
            BranchesList(navController, viewModel)
        }
    }
}