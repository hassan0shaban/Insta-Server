package com.example.repo

import com.example.dp.table.*
import com.example.maper.Mapper.getCommentsFromResultRow
import com.example.maper.Mapper.getLikesFromResultRow
import com.example.maper.PostMapper
import com.example.model.Comment
import com.example.model.Like
import com.example.model.Post
import com.example.repo.Constants.UserPostsLimit
import com.example.request.PostRequest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class PostRepositoryImpl(private val database: Database) : PostRepository {

    override fun getPostComments(pid: Int): List<Comment> =
        transaction {
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
        }

    override fun getPostLikes(pid: Int): List<Like> =
        transaction {
            LikeTable
                .select(
                    where = {
                        LikeTable.pid eq pid
                    }
                )
                .map {
                    getLikesFromResultRow(it)
                }
        }

    override fun getUserPosts(username: String, limit: Int): List<Post> =
        transaction {
            PostTable
                .select(
                    where = {
                        PostTable.username eq username
                    }
                )
                .limit(UserPostsLimit)
                .map {
                    PostMapper.postFromResultRow(it)
                }
        }

    override fun getPost(pid: Int): Post? =
        transaction {
            PostTable
                .select(
                    where = {
                        PostTable.pid eq pid
                    }
                )
                .firstOrNull()
                ?.let {
                    PostMapper.postFromResultRow(it)
                }
        }

    override fun updatePostImageUrl(pid: Int, imageUrl: String) =
        transaction {
            PostTable.update(
                where = {
                    PostTable.pid eq pid
                },
            ) {
                it.set(this.imageUrl, imageUrl)
            }
        }

    override fun insertPost(caption: String, imageUrl: String, username: String): Int? =
        transaction {
            PostTable.insert {
                it.set(this.username, username)
                it.set(this.imageUrl, imageUrl)
                it.set(this.caption, caption)
                it.set(this.time, DateTime.now())
            }.getOrNull(PostTable.pid)
        }
}