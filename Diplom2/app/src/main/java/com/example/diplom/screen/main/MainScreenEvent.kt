package com.example.diplom.screen.main

sealed class MainScreenEvent {
    object loadMoreEstablishments :
        MainScreenEvent()

    data class searchQueryChanged(val queryString: String) :
        MainScreenEvent()

    object searchEstablishments :
        MainScreenEvent()
}