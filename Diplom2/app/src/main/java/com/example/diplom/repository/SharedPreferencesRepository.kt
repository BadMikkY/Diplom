package com.example.diplom.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.diplom.navigation.AppNavigator
import com.example.diplom.navigation.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton


private const val USER_PREF_FILE = "userPrefFile"
private const val EMAIL = "email"
private const val USER_ID = "id"
private const val DEFAULT_USER_ID = "0"
private const val DEFAULT_EMAIL = "email"
private const val USERNAME = "username"
private const val DEFAULT_USERNAME = "username"

@Singleton
class SharedPreferencesRepository @Inject constructor(
    @ApplicationContext context: Context
) {
    private var userPreferences: SharedPreferences? = null

    init {
        userPreferences = context.getSharedPreferences(USER_PREF_FILE, Context.MODE_PRIVATE)
    }

    fun setUsername(username: String) {
        userPreferences?.edit {
            putString(USERNAME, username)
        }
    }

    fun setUserMail(email: String) {
        userPreferences?.edit() {
            putString(EMAIL, email)
        }
    }

    fun setUserId(userId: Number) {
        userPreferences?.edit() {
            putString(USER_ID, userId.toString())
        }
    }



    fun setSpecName(specialistName: String) {
        userPreferences?.edit {
            putString(USERNAME, specialistName)
        }
    }

    fun setSpecialistMail(email: String) {
        userPreferences?.edit() {
            putString(EMAIL, email)
        }
    }

    fun setSpecId(specId: Number) {
        userPreferences?.edit() {
            putString(USER_ID, specId.toString())
        }
    }

    fun setSpecSkills(skills: String) {
        userPreferences?.edit() {
            putString(USER_ID, skills.toString())
        }
    }
    fun setSpecExp(experience: String) {
        userPreferences?.edit() {
            putString(USER_ID, experience.toString())
        }
    }

    fun setShedule(shedule: String) {
        userPreferences?.edit() {
            putString(USER_ID, shedule.toString())
        }
    }

    fun setSpecRates(rates: String) {
        userPreferences?.edit() {
            putString(USER_ID, rates.toString())
        }
    }
}