package com.example.route

import com.example.AuthMethod
import com.example.route.utils.Utils.getUsername
import com.example.service.NotificationsService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject


fun Route.notificationsRouting() {

    val notificationsService: NotificationsService by inject()

    authenticate(AuthMethod.method) {
        get("/notifications") {
            val username = getUsername(call)
            val page = call.request.headers.get("page").let { it ?: "25" }.toInt()
            val pageSize = call.request.headers.get("page-size").let { it ?: "25" }.toInt()

            call.respond(
                message = notificationsService
                    .getNotifications(
                        username = username,
                        pageSize = pageSize,
                        page = page
                    ),
                status = HttpStatusCode.OK
            )
        }
    }
}