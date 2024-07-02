package com.example.diplom.screen.workerMain

import com.example.diplom.screen.main.MainScreenEvent

sealed class WorkerMainEvent {
    object loadServices :
        WorkerMainEvent()

    data class searchQueryChanged(val queryString: String) :
        WorkerMainEvent()

    object confirmButtonClicked :
            WorkerMainEvent()
}