package com.example.repo

import com.example.dp.table.*
import com.example.maper.Mapper
import com.example.maper.Mapper.getCommentsFromResultRow
import com.example.maper.Mapper.getLikesFromResultRow
import com.example.maper.PostMapper
import com.example.model.Comment
import com.example.model.Like
import com.example.response.Post
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

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

    override fun getUserPosts(username: String): List<Post> =
        PostTable.innerJoin(UserTable)
            .select(
                where = {
                    PostTable.username eq username
                }
            )
            .limit(8)
            .map {
                PostMapper.postFromResultRow(it)
            }

    override fun getFeedPosts(username: String): List<Post> {
        return transaction {
            val postList = arrayListOf<Post>()

            ConnectionTable
                .select(
                    where = {
                        ConnectionTable.followerUid eq username
                    }
                )
                .map {
                    Mapper.connectionsFromResultRow(it)
                }.map {
                    getUserPosts(it.uid)
                }.forEach {
                    it.forEach { post ->
                        getPostComments(post.pid).let { comments ->
                            post.comments = comments
                        }
                    }

                    it.forEach { post ->
                        getPostLikes(post.pid).let { likes ->
                            post.likes = likes
                        }
                    }

                    postList += it
                }

            return@transaction postList
        }
    }
}