package com.example.diplom.screen.workerBookings

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diplom.model.Booking
import com.example.diplom.model.Review
import com.example.diplom.model.Specialist
import com.example.diplom.model.WorkProgress
import com.example.diplom.model.WorkProgressResponse
import com.example.diplom.repository.BookingsRepository
import com.example.diplom.repository.SharedPreferencesRepository
import com.example.diplom.repository.SpecialistRepository
import com.example.diplom.repository.WorkProgressRepository
import com.example.diplom.screen.workerMain.WorkerMainEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WorkerBookingsViewModel @Inject constructor(
    private val repository: BookingsRepository,
    val sharedPreferencesRepository: SharedPreferencesRepository,
    private val workProgressRepository: WorkProgressRepository
) : ViewModel() {
    fun getSpecIDD() = sharedPreferencesRepository.getSpecId()

    fun getBookingIDD() = sharedPreferencesRepository.getBookingId()
    fun getProgressIDD() = sharedPreferencesRepository.getProgressID()


    private val _bookingsList = mutableStateOf(listOf<Booking>())
    val bookingsListState: List<Booking> get() = _bookingsList.value

    private val _workProgress = mutableStateOf<WorkProgressResponse?>(null)
    val workProgress: WorkProgressResponse? get() = _workProgress.value


    var progress by mutableStateOf("")
        private set

    fun updateProgress(input: String) {
        progress = input
    }


    var comment by mutableStateOf("")
        private set

    fun updateComment(input: String) {
        comment = input
    }

    fun handleEvent(workerBookingsEvent: WorkerBookingsEvent) {
        when (workerBookingsEvent) {
            is WorkerBookingsEvent.loadBookings -> {
                viewModelScope.launch {
                    val SpecID = getSpecIDD()
                    val bookings = Booking(SpecialistID = SpecID)
                    try {
                        val response = repository.getBookingsForSpec(bookings)
                        if (response.isSuccessful) {
                            _bookingsList.value = response.body() ?: emptyList()
                            Log.d("LoadServices", bookingsListState.toString())
                            println(response)
                        } else {
                            println("Ошибка сервера: ${response.code()}")
                        }
                    } catch (e: Exception) {
                        println("Произошла ошибка: ${e.message}")
                    }
                }
            }

            is WorkerBookingsEvent.rejectButtonClicked -> {
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
                            println("Ошибка сервиса: ${response.code()}")
                        }
                    } catch (e: Exception) {
                        println("Произошла ошибка: ${e.message}")
                    }
                }
            }

            is WorkerBookingsEvent.getWorkProgress -> {//Dopisatt
                viewModelScope.launch {
                    val BookingID = getBookingIDD()
                    val progress = WorkProgress(BookingID = BookingID)
                    try {
                        val response = workProgressRepository.getWorkProgress(progress)
                        if (response.isSuccessful) {
                            Log.d("LoadProgress", bookingsListState.toString())
                            _workProgress.value = response.body()
                            response.body()?.let {
                                sharedPreferencesRepository.setProgressID(it.ProgressID)
                            }
                            println(response.body())
                        } else {
                            println("Ошибка сервиса: ${response.code()}")
                        }
                    } catch (e: Exception) {
                        println("Произошла ошибка: ${e.message}")
                    }
                }
            }

            is WorkerBookingsEvent.updatePercentage -> {
                viewModelScope.launch {
                    try {
                        // Получение BookingID из SharedPreferences
                        val bookingID = getBookingIDD()

                        // Проверка, что BookingID не равен нулю
                        if (bookingID != 0) {
                            val progressId = getProgressIDD()
                            val percentage = progress.toIntOrNull()
                            val com = comment

                            // Первый запрос: обновление процента
                            val progressRequest =
                                WorkProgressResponse(ProgressID = progressId, Percentage = percentage)
                            val response = workProgressRepository.updatePercentage(progressRequest)

                            if (response?.isSuccessful == true) {
                                // Обновление _workProgress.value новым значением
                                _workProgress.value = response.body()
                                println("Процент успешно обновлен: ${response.body()}")

                                // Второй запрос: добавление комментария
                                val commentRequest =
                                    WorkProgressResponse(ProgressID = progressId, Comment = com)
                                val responsee = workProgressRepository.addComment(commentRequest)

                                if (responsee?.isSuccessful == true) {
                                    // Обновление _workProgress.value новым значением
                                    _workProgress.value = responsee.body()
                                    println("Комментарий успешно добавлен: ${responsee.body()}")
                                } else {
                                    println("Ошибка при добавлении комментария: ${responsee?.code()}")
                                }
                            } else {
                                println("Ошибка при обновлении процента: ${response?.code()}")
                            }
                        } else {
                            println("BookingID не установлен или равен нулю")
                        }
                    } catch (e: Exception) {
                        println("Произошла ошибка: ${e.message}")
                    }
                }
            }



            is WorkerBookingsEvent.createWorkProgress -> {
                viewModelScope.launch {
                    val BookingID = getBookingIDD()
                    val workProgress =
                        WorkProgress(BookingID = BookingID, Comment = "", Percentage = 0)
                    try {
                        val response = workProgressRepository.createWorkProgress(workProgress)
                        if (response.isSuccessful) {
                            Log.d("LoadBookings", bookingsListState.toString())
                            println(response)
                        } else {
                            println("Ошибка сервиса: ${response.code()}")
                        }
                    } catch (e: Exception) {
                        println("Произошла ошибка: ${e.message}")
                    }
                }
            }

            is WorkerBookingsEvent.confirmButtonClicked -> {
                viewModelScope.launch {
                    val BookingID = getBookingIDD()
                    val Status = "В процессе"
                    val bookings = Booking(BookingID = BookingID, Status = Status)
                    val book = Booking(BookingID = BookingID)
                    try {
                        val response = repository.updateStatus(bookings)
                        if (response.isSuccessful) {
                            val loadResponse = repository.getBookingForUser(book)
                            if (loadResponse.isSuccessful) {
                                _bookingsList.value = loadResponse.body() ?: emptyList()
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
