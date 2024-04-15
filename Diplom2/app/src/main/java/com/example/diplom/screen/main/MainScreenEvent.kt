package com.example.diplom.screen.main

sealed class MainScreenEvent {
    object loadServices :
        MainScreenEvent()

    data class searchQueryChanged(val queryString: String) :
        MainScreenEvent()

    object searchServices :
        MainScreenEvent()
}