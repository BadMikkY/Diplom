package com.example.diplom.screen.workerMain

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diplom.components.SearchTextField
import com.example.diplom.components.WorkersSearchTextField
import com.example.diplom.model.Service
import com.example.diplom.screen.main.MainScreenEvent
import com.example.diplom.screen.main.MainScreenViewModel

@Composable
fun WorkerMainScreen(viewModel:WorkerMainViewModel = hiltViewModel()){
    LaunchedEffect(key1 = true) {
        viewModel.handleEvent(WorkerMainEvent.loadServices)
    }

    val serviceState = viewModel.serviceistListState

    Column {
        WorkersSearchTextField(viewModel)

        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 22.dp)
                .fillMaxWidth(),
            thickness = 1.dp,
            color = Color.Black
        )

        com.example.diplom.screen.workerMain.WorkersCardGrid(
            services = serviceState,
            onEndReached = { viewModel.handleEvent(WorkerMainEvent.loadServices) })
    }
}


@Composable
fun WorkersCardGrid(
    services: List<Service>,
    viewModel: MainScreenViewModel = hiltViewModel(),
    onEndReached: () -> Unit
) {
    val lazyListState = rememberLazyStaggeredGridState()

    LazyVerticalStaggeredGrid(
        state = lazyListState,
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 2.dp,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 75.dp)
    ) {

        items(services) {
            Log.d("WWWWWWWWWWWWWWWWW", "CardGrid: " + it)
            if (services.indexOf(it) == services.size - 1) {
                Log.d("End", services.toString())
                onEndReached()
                Log.d("AfterEnd", services.toString())
            }


            Surface(
                modifier = Modifier
                    .size(156.dp, 205.dp)
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 7.dp,
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 10.dp)
                        .padding(top = 120.dp)
                ) {


                    it.ServiceName?.let { it1 ->
                        Text(
                            text = it1,
                            modifier = Modifier.padding(top = 6.dp),
                            color = Color.Black
                        )
                    }
                    it.Description?.let { it1 ->
                        Text(
                            text = it1,
                            modifier = Modifier.padding(top = 6.dp),
                            color = Color.Black
                        )
                    }
                }
            }
        }

    }
}