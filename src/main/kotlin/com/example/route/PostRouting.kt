package com.example.route

import com.example.AuthMethod
import com.example.route.Parameters.IMAGE_NAME
import com.example.route.Parameters.PID
import com.example.route.Parameters.USERNAME
import com.example.service.PostService
import com.example.utils.Paths.POSTS_IMAGES_PATH
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import java.io.File

fun Route.postRouting() {

    val postService: PostService by inject()

    authenticate(AuthMethod.method) {
        get("/{$USERNAME}/posts") {
            val username = call.parameters[USERNAME] ?: kotlin.run {
                call.respond(message = "username is not correct", status = HttpStatusCode.BadRequest)
                return@get
            }

            call.respond(
                message = postService.getUserPosts(username),
                status = HttpStatusCode.OK
            )
        }
    }

    authenticate(AuthMethod.method) {
        get("/profile/posts") {
            val username = Utils.getUsername(call)

            call.respond(
                message = postService.getUserPosts(username),
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
                message = post.apply {
//                    TODO change base URL
                    post.post.postImageUrl = "http://localhost:8080/${post.post.postImageUrl}"
                },
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
            var imageBytes: ByteArray? = null
            var caption: String? = null
            call.receiveMultipart().forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        caption = part.value
                    }
                    is PartData.FileItem -> {
                        imageBytes = part.streamProvider().readBytes()
                    }
                }
            }

            if (imageBytes == null || caption == null) {
                call.respond(message = "uncompleted info", status = HttpStatusCode.BadRequest)
                return@post
            }

            val principal = call.principal<JWTPrincipal>()
            val username = principal!!.payload.getClaim(AuthenticationParameters.USERNAME).asString()

            val pid = postService.insertPost(caption!!, "imageUrl", username) ?: kotlin.run {
                call.respond(message = "cannot create post", status = HttpStatusCode.ExpectationFailed)
                return@post
            }

            val imageUrl = "$POSTS_IMAGES_PATH/$pid.jpg"
            File(imageUrl).writeBytes(imageBytes!!)

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
        val username = call.parameters.get("username")
        if (username == null)
            call.respond(message = "null", status = HttpStatusCode.BadRequest).also { return@get }

        val posts = postService.getUserPosts(username!!)
        call.respond(message = posts, status = HttpStatusCode.OK)
    }
}