package com.example.diplom.screen.workerMain

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.diplom.R
import com.example.diplom.components.ProfilePicture
import com.example.diplom.components.WorkersSearchTextField
import com.example.diplom.model.Service
import com.example.diplom.screen.main.MainScreenEvent
import com.example.diplom.screen.main.MainScreenViewModel
import com.example.diplom.screen.main.SpecialistsInfo

@Composable
fun WorkerMainScreen(viewModel: WorkerMainViewModel = hiltViewModel()) {
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
    vModel: WorkerMainViewModel = hiltViewModel(),
    onEndReached: () -> Unit
) {
    val lazyListState = rememberLazyStaggeredGridState()
    var selectedService by remember { mutableStateOf<Service?>(null) }

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
                    .padding(bottom = 10.dp)
                    .clickable { selectedService = it },
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 7.dp,
                color = Color(135,206,235)
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
                if (selectedService != null) {
                    selectedService!!.ServiceName?.let { it1 ->
                        viewModel.sharedPreferencesRepository.setServiceName(
                            it1
                        )
                    }
                    selectedService!!.Description?.let { it1 ->
                        viewModel.sharedPreferencesRepository.getDescription(
                            it1
                        )
                    }
                    selectedService!!.UserID?.let { it1 ->
                        viewModel.sharedPreferencesRepository.setUserId(
                            it1
                        )
                    }
                    ServiceInfo(
                        services = listOf(selectedService!!),
                        onDismiss = { selectedService = null },
                        onConfirm = { vModel.handleEvent(WorkerMainEvent.confirmButtonClicked) }
                    )
                }
            }
        }
    }
}


@OptIn
@Composable
fun ServiceInfo(
    services: List<Service>,
    viewModel: MainScreenViewModel = hiltViewModel(),
    onDismiss: () -> Unit, onConfirm: () -> Unit, onChange: (String) -> Unit = {},
) {
    var hasFocus by remember { mutableStateOf(false) }

    var shape = RoundedCornerShape(15.dp)
    Dialog(
        onDismissRequest = { onDismiss }, properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            shape = shape,
            modifier = Modifier.fillMaxWidth(0.95f)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                    }
                    ProfilePicture()
                    Divider()
                    LazyColumn {
                        items(services) { service ->
                            Text(
                                text = service.ServiceName ?: "",
                                modifier = Modifier.padding(top = 6.dp),
                                color = Color.Black
                            )
                            Text(
                                text = service.Description ?: "",
                                modifier = Modifier.padding(top = 6.dp),
                                color = Color.Black
                            )

                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { onConfirm() },
                            colors = ButtonDefaults.buttonColors(Color(100, 216, 230))
                        ) {
                            Text(text = "Откликнуться")
                        }
                        Button(
                            onClick = { onDismiss() },
                            colors = ButtonDefaults.buttonColors(Color(100, 216, 230))
                        ) {
                            Text(text = "Закрыть")
                        }
                    }
                }
            }
        }

    }
}
