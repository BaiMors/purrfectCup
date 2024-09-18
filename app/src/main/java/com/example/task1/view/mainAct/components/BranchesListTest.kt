package com.example.task1.view.mainAct.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.task1.Model.Branches
import com.example.task1.domain.Constants
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun BranchesListTest()
{
    var branches by remember { mutableStateOf<List<Branches>>(listOf()) }
/*    var bucket: ByteArray? = null
    var bytes:ByteArray? = null
    var files: List<BucketItem>? = null*/
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
/*            branches = Constants.supabase.from("branches")
                .select().decodeList<BranchesTest>()
            branches.forEach{it->

            }*/
            try {
                branches = Constants.supabase.postgrest.from("Branches").
                select().decodeList<Branches>()
                branches.forEach{it->
                    println(it.address)
                }
            } catch (e: Exception) {
                println("Error")
                println(e.message)
            }
        }
    }

  /*  LazyColumn {
        items(
            branches,
            key = { branch -> branch.id},
        ) {
            branch ->
            val imageState = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current).data(branch.image)
                    .size(Size.ORIGINAL).build()
            ).state
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
        }
    }*/
}