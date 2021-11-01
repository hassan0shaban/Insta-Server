package com.example.route

object Routing {
    const val SIGNUP = "/signup"
    const val LOGIN = "/login"
    const val USER = "/User"
}

object Parameters {
    const val ACCESS_TOKEN: String = "access_token"
    const val UID: String = "uid"
    const val Login_Method = "method"
    const val Signup_Method = "method"
    const val USERNAME = "username"
    const val IMAGE_NAME = "image_name"
    const val PID = "pid"
}

object LoginMethods {
    const val EMAIL = "email"
    const val FACEBOOK = "facebook"
    const val Google = "google"
}

object AuthenticationParameters {
    const val USERNAME = "username"
}