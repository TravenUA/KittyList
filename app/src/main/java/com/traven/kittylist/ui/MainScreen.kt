package com.traven.kittylist.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.traven.kittylist.Const
import com.traven.kittylist.model.dto.KittyDTO


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MyViewModel) {

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = Unit, block = t)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        if (viewModel.loadingState.value && !viewModel.hasData()) InitScreen()
        if (viewModel.error) ErrorScreen(snackbarHostState)
        else ListScreen(viewModel)
    }
}

@Composable
fun InitScreen() {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(snackbarHostState: SnackbarHostState) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(scope) {
        snackbarHostState.showSnackbar(
            message = Const.MSG_ERR,
            duration = SnackbarDuration.Short
        )
    }
}

@Composable
fun ListScreen(viewModel: MyViewModel) {

    viewModel.dataList.let { list ->
        LazyColumn(Modifier.padding(10.dp), LazyListState()) {
            if (list.isNotEmpty()) itemsIndexed(list)
            { idx, dto ->
                viewModel.updateScrollPosition(idx)
                ItemCard(dto)
            }
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemCard(kittyDTO: KittyDTO) {
    Box(Modifier.padding(10.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth(1f)
                .background(Color.White),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(5.dp)
        ) {
            GlideImage(
                model = kittyDTO.url,
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
            )
        }
    }
}