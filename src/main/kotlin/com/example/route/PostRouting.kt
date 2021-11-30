package com.example.route

import com.example.AuthMethod
import com.example.route.Parameters.IMAGE_NAME
import com.example.route.Parameters.PID
import com.example.route.Parameters.USERNAME
import com.example.route.utils.Utils.getUsername
import com.example.service.PostService
import com.example.utils.Paths.POSTS_IMAGES_PATH
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import java.io.File
import kotlin.math.min

fun Route.postRouting() {

    val postService: PostService by inject()

    authenticate(AuthMethod.method) {
        get("/{$USERNAME}/posts") {
            val page = call.parameters.get("page")?.toInt().takeIf { (it ?: 0) > 0 } ?: 1
            val pageSize = call.parameters.get("page-size")?.toInt().takeIf { (it ?: 0) > 0 } ?: 25

            val username = call.parameters[USERNAME] ?: kotlin.run {
                call.respond(message = "username is not correct", status = HttpStatusCode.BadRequest)
                return@get
            }

            val posts = postService.getUserPosts(username, page, pageSize)

            call.respond(
                if (page <= 1) {
                    posts.subList(0, min(posts.size, pageSize))
                } else if (posts.size < page * pageSize) {
                    emptyList()
                } else {
                    posts.subList((page - 1) * pageSize, min(posts.size, page * pageSize))
                }
            )
        }
    }

    authenticate(AuthMethod.method) {
        get("/profile/posts") {
            val username = getUsername(call)
            val page = call.parameters.get("page")?.toInt() ?: 1
            val pageSize = call.parameters.get("page-size")?.toInt() ?: 25

            call.respond(
                message = postService.getUserPosts(username, page, pageSize),
                status = HttpStatusCode.OK
            )
        }
    }

    authenticate(AuthMethod.method) {
        get("/posts/{$PID}") {
            val pid = call.parameters[PID]?.toInt() ?: kotlin.run {
                call.respond(message = "username is not correct", status = HttpStatusCode.BadRequest)
                return@get
            }

            val post = postService.getPost(pid) ?: kotlin.run {
                call.respond(message = "cannot insert post", status = HttpStatusCode.BadRequest)
                return@get
            }

            call.respond(
                message = post,
                status = HttpStatusCode.OK
            )
        }
    }

    authenticate(AuthMethod.method) {
        get("/images/posts/{$IMAGE_NAME}") {
            val imageUrl = call.parameters[IMAGE_NAME] ?: kotlin.run {
                call.respond(message = "post id must be passed", status = HttpStatusCode.BadRequest)
                return@get
            }

            call.response.header(
                name = HttpHeaders.ContentDisposition,
                value = ContentDisposition
                    .Attachment
                    .withParameter(ContentDisposition.Parameters.FileName, imageUrl.plus(".jpg"))
                    .toString()
            )

            call.respondFile(
                File("$POSTS_IMAGES_PATH/$imageUrl"),
            )
        }
    }


    authenticate(AuthMethod.method) {
        get("/posts/{$PID}/comments") {
            val pid = call.parameters[PID]?.toInt() ?: kotlin.run {
                call.respond(message = "post id must be passed", status = HttpStatusCode.BadRequest)
                return@get
            }

            call.respond(
                message = postService.getPostComments(pid),
                status = HttpStatusCode.OK
            )
        }
    }

    authenticate(AuthMethod.method) {
        get("/posts/{$PID}/likes") {
            val pid = call.parameters[PID]?.toInt() ?: kotlin.run {
                call.respond(message = "post id must be passed", status = HttpStatusCode.BadRequest)
                return@get
            }

            call.respond(
                message = postService.getLikes(pid),
                status = HttpStatusCode.OK
            )
        }
    }

    authenticate(AuthMethod.method) {
        post("/post") {

            val parts = HashMap<String, Any?>()

            call.receiveMultipart().forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        parts.put(part.name!!, part.value)
                    }
                    is PartData.FileItem -> {
                        parts.put(part.name!!, part.streamProvider().readBytes())
                    }
                }
            }

            if (parts.values.any { it == null }) {
                call.respond(message = "uncompleted info", status = HttpStatusCode.BadRequest)
                return@post
            }

            val username = getUsername(call)

            val pid = postService.insertPost(parts, username) ?: kotlin.run {
                call.respond(message = "cannot create post", status = HttpStatusCode.ExpectationFailed)
                return@post
            }

            val imageUrl = "$POSTS_IMAGES_PATH/$pid.jpg"
            File(imageUrl).writeBytes(parts.get("image") as ByteArray)

            postService.updatePostImageUrl(pid, imageUrl).let {
                if (it > 0)
                    call.respond(
                        message = "$pid",
                        status = HttpStatusCode.OK
                    )
                else
                    call.respond(message = "cannot create post", status = HttpStatusCode.ExpectationFailed)
            }
        }
    }

    get("user/{username}/posts") {
        val page = call.parameters.get("page")?.toInt().takeIf { (it ?: 0) > 0 } ?: 1
        val pageSize = call.parameters.get("page-size")?.toInt().takeIf { (it ?: 0) > 0 } ?: 25

        val username = call.parameters[USERNAME] ?: kotlin.run {
            call.respond(message = "username is not correct", status = HttpStatusCode.BadRequest)
            return@get
        }

        val posts = postService.getUserPosts(username, page, pageSize)

        call.respond(
            if (page <= 1) {
                posts.subList(0, min(posts.size, pageSize))
            } else if (posts.size < page * pageSize) {
                emptyList()
            } else {
                posts.subList((page - 1) * pageSize, min(posts.size, page * pageSize))
            }
        )
    }
}