package com.example.diplom.screen.bookingsScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplom.model.Booking
import com.example.diplom.repository.BookingsRepository
import com.example.diplom.repository.SharedPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookingsScreenViewModel @Inject constructor(
    private val repository: BookingsRepository,
    val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {
    fun getUserIDD() = sharedPreferencesRepository.getUserId()

    fun getBookingIDD() = sharedPreferencesRepository.getBookingId()

    private val _bookingsList = mutableStateOf(listOf<Booking>()) // Используйте mutableStateOf
    val bookingsListState: List<Booking> get() = _bookingsList.value // Добавьте геттер

    fun handleEvent(bookingsEvent: BookingsEvent) {
        when (bookingsEvent) {
            is BookingsEvent.loadBookings -> {
                viewModelScope.launch {
                    val UserID = getUserIDD()
                    val bookings = Booking(UserID = UserID)
                    try {
                        val response = repository.getBookingForUser(bookings)

                        if (response.isSuccessful) {
                            _bookingsList.value = response.body() ?: emptyList()
                            Log.d("LoadBookings", bookingsListState.toString())
                            println(response)
                        } else {
                            println("Ошибка сервера: ${response.code()}")
                        }
                    } catch (e: Exception) {
                        println("Произошла ошибка: ${e.message}")
                    }
                }
            }

            is BookingsEvent.rejectButtonClicked -> {
                viewModelScope.launch {
                    val BookingID = getBookingIDD()
                    val bookings = Booking(BookingID = BookingID)
                    try {
                        val response = repository.deleteBooking(bookings)
                        if (response.isSuccessful) {
                            _bookingsList.value = response.body() ?: emptyList()
                            Log.d("LoadBookings", bookingsListState.toString())
                            println(response)
                        } else {
                            println("Ошибка сервера: ${response.code()}")
                        }
                    } catch (e: Exception) {
                        println("Произошла ошибка: ${e.message}")
                    }
                }
            }

            is BookingsEvent.confirmButtonClicked -> {
                viewModelScope.launch {
                    val BookingID = getBookingIDD()
                    val Status = "В процессе"
                    val bookings = Booking(BookingID = BookingID, Status = Status)
                    val book = Booking(BookingID = BookingID)
                    try {
                        val response = repository.updateStatus(bookings)
                        if (response.isSuccessful) {
                            // Загрузите данные снова после обновления
                            val loadResponse = repository.getBookingForUser(book)
                            if (loadResponse.isSuccessful) {
                                _bookingsList.value = loadResponse.body() ?: emptyList() // Обновите весь список
                                Log.d("LoadBookings", bookingsListState.toString())
                                println(loadResponse)
                            } else {
                                println("Ошибка сервера при загрузке: ${loadResponse.code()}")
                            }
                        } else {
                            println("Ошибка сервера при обновлении: ${response.code()}")
                        }
                    } catch (e: Exception) {
                        println("Произошла ошибка: ${e.message}")
                    }
                }
            }


        }
    }
}
