package com.example.diplom.screen.main

sealed class MainScreenEvent {
    object loadSpecialists :
        MainScreenEvent()

    data class searchQueryChanged(val queryString: String) :
        MainScreenEvent()

    object searchServices :
        MainScreenEvent()
}