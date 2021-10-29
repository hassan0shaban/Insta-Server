package com.example.repo

import com.example.dp.table.*
import com.example.maper.Mapper.getCommentsFromResultRow
import com.example.maper.Mapper.getLikesFromResultRow
import com.example.maper.PostMapper
import com.example.model.Comment
import com.example.model.Like
import com.example.model.PostDetails
import com.example.repo.Constants.UserPostsLimit
import com.example.request.PostRequest
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.joda.time.DateTime
import java.sql.Timestamp
import java.time.Instant

object Constants {
    const val UserPostsLimit = 15
}

class PostRepositoryImpl(private val database: Database) : PostRepository {

    override fun getPostComments(pid: Int): List<Comment> =
        CommentTable
            .select(
                where = {
                    CommentTable.pid eq pid
                }
            )
            .limit(30)
            .map {
                getCommentsFromResultRow(it)
            }

    override fun getPostLikes(pid: Int): List<Like> =
        LikeTable
            .select(
                where = {
                    LikeTable.pid eq pid
                }
            )
            .map {
                getLikesFromResultRow(it)
            }

    override fun getUserPosts(username: String): List<PostDetails> =
        PostTable.innerJoin(UserTable)
            .select(
                where = {
                    PostTable.username eq username
                }
            )
            .limit(UserPostsLimit)
            .map {
                PostMapper.postFromResultRow(it)
            }

    override fun insertPost(postRequest: PostRequest, username: String): Int? =
        PostTable.insert {
            it[PostTable.username] = username
            it[imageUrl] = postRequest.imageUrl
            it[caption] = postRequest.caption
            it[time] = DateTime.now()
        }.getOrNull(PostTable.pid)
}