package com.example.route

object Routing {
    const val SIGNUP = "/signup"
    const val LOGIN = "/login"
    const val USER = "/User"
}

object Parameters {
    const val PASSWORD = "password"
    const val ACCESS_TOKEN: String = "token"
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
    const val GOOGLE = "google"
}

object AuthenticationParameters {
    const val COUNT: String = "count"
    const val USERNAME = "username"
}