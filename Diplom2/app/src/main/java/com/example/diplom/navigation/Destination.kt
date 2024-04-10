package com.example.diplom.navigation

sealed class Destination(protected val route: String, vararg params: String) {
    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }

    sealed class NoArgumentsDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
    }

    sealed class NoArgumentsDestinationBottom(
        route: String,
        val title: Int,
        val icon: Int
    ) : Destination(route) {
        operator fun invoke(): String = route
    }

    object RegistrationScreen : NoArgumentsDestination(REGISTRATION_ROUTE)
    object AuthorizationScreen : NoArgumentsDestination(AUTHORIZATION_ROUTE)
    object EntryScreen : NoArgumentsDestination(ENTRY_ROUTE)
    object ClientRegScreen : NoArgumentsDestination(CLIENT_ROUTE)


    companion object {
        private const val REGISTRATION_ROUTE = "registration"
        private const val AUTHORIZATION_ROUTE = "authorization"
        private const val ENTRY_ROUTE = "entry"
        private const val CLIENT_ROUTE = "client"
    }

}

internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)

    params.forEach {
        it.second?.toString()?.let { arg ->
            builder.append("/$arg")
        }
    }

    return builder.toString()
}
