package com.example.diplom.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


private const val USER_PREF_FILE = "userPrefFile"
private const val EMAIL = "email"
private const val USER_ID = "id"
private const val DEFAULT_USER_ID = "0"
private const val DEFAULT_EMAIL = "email"
private const val USERNAME = "username"
private const val DEFAULT_USERNAME = "username"
private const val DEFAULT_SPECNAME = "specname"
private const val SERVICE_NAME = "servicename"
private const val SERVICE_ID = "serviceid"
private const val DESCRIPTION = "descriprion"
private const val SPEC_ID = "specid"
private const val SPEC_NAME = "specname"
private const val SPEC_SKILLS = "specskills"
private const val DEFAULT_SPEC_SKILLS = "specskills"
private const val DEFAULT_SPEC_ID = "0"
private const val SPEC_EXP = "specexp"
private const val DEFAULT_SPEC_EXP = "specexp"
private const val SPEC_RATES = "specsrates"
private const val DEFAULT_SPEC_RATES = "specsrates"
private const val SPEC_SHEDULE = "specshedule"
private const val SPEC_MAIL = "specmail"
private const val DEFAULT_SPECMAIL = "specmail"
private const val DEFAULT_DESCRIPTION = "description"
private const val BOOKING_ID = "bookingid"
private const val PROGRESSID = "workprogress"
private const val DEFAULT_WORKPROGRESS = "workprogress"


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

    fun getUserName(): String =
        userPreferences?.getString(USERNAME, DEFAULT_USERNAME) ?: DEFAULT_USERNAME


    fun setProgressID(progressId: Number) {
        userPreferences?.edit {
            putString(PROGRESSID, progressId.toString())
        }
    }

    fun getProgressID(): Int {
        return userPreferences?.getString(PROGRESSID, "0")?.toInt() ?: 0
    }

    fun setUserMail(email: String) {
        userPreferences?.edit() {
            putString(EMAIL, email)
        }
    }

    fun getUserMail(): String =
        userPreferences?.getString(EMAIL, DEFAULT_EMAIL) ?: DEFAULT_EMAIL

    fun setUserId(userId: Number) {
        userPreferences?.edit() {
            putString(USER_ID, userId.toString())
        }
    }


    fun setSpecName(specialistName: String) {
        userPreferences?.edit {
            putString(SPEC_NAME, specialistName)
        }
    }

    fun getSpecName(): String =
        userPreferences?.getString(SPEC_NAME, DEFAULT_SPECNAME) ?: DEFAULT_SPECNAME

    fun setShedule(specialistSchedule: String) {
        userPreferences?.edit {
            putString(SPEC_SHEDULE, specialistSchedule)
        }
    }

    fun setSpecialistMail(email: String) {
        userPreferences?.edit() {
            putString(EMAIL, email)
        }
    }

    fun getSpecialistMail(): String =
        userPreferences?.getString(SPEC_MAIL, DEFAULT_SPECMAIL) ?: DEFAULT_SPECMAIL

    fun getDescription(it1: String): String =
        userPreferences?.getString(DESCRIPTION, DEFAULT_DESCRIPTION) ?: DEFAULT_DESCRIPTION


    fun setSpecId(specId: Number) {
        userPreferences?.edit() {
            putString(SPEC_ID, specId.toString())
        }
    }


    fun setSpecSkills(skills: String) {
        userPreferences?.edit() {
            putString(SPEC_SKILLS, skills.toString())
        }
    }

    fun getSpecSkills(): String =
        userPreferences?.getString(SPEC_SKILLS, DEFAULT_SPEC_SKILLS) ?: DEFAULT_SPEC_SKILLS

    fun setSpecExp(experience: String) {
        userPreferences?.edit() {
            putString(SPEC_EXP, experience.toString())
        }
    }

    fun getSpecExp(): String =
        userPreferences?.getString(SPEC_EXP, DEFAULT_SPEC_EXP) ?: DEFAULT_SPEC_EXP


    fun setSpecRates(rates: String) {
        userPreferences?.edit() {
            putString(SPEC_RATES, rates.toString())
        }
    }

    fun getSpecRates(): String =
        userPreferences?.getString(SPEC_RATES, DEFAULT_SPEC_RATES) ?: DEFAULT_SPEC_RATES

    fun setServiceName(serviceName: String) {
        userPreferences?.edit {
            putString(SERVICE_NAME, serviceName)
        }
    }

    fun setDescription(description: String) {
        userPreferences?.edit {
            putString(DESCRIPTION, description)
        }
    }

    fun setServiceId(serviceId: Number) {
        userPreferences?.edit() {
            putString(SERVICE_ID, serviceId.toString())
        }
    }

    fun getUserId(): Int {
        return userPreferences?.getString(USER_ID, "0")?.toInt() ?: 0
    }


    fun getSpecId(): Int {
        return userPreferences?.getString(SPEC_ID, "0")?.toInt() ?: 0
    }

    fun setBookingId(bookingId: Number) {
        userPreferences?.edit() {
            putString(BOOKING_ID, bookingId.toString())
        }
    }

    fun getBookingId(): Int {
        return userPreferences?.getString(BOOKING_ID, "0")?.toInt() ?: 0
    }


}