package com.example.repo

import com.example.db.table.*
import com.example.maper.Mapper.getCommentsFromResultRow
import com.example.maper.Mapper.getLikeFromResultRow
import com.example.maper.PostMapper
import com.example.model.Comment
import com.example.response.LikeResponse
import com.example.model.Post
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class PostRepositoryImpl(private val database: Database) : PostRepository {

    override fun getPostComments(pid: Int): List<Comment> =
        transaction {
            CommentTable.join(
                UserTable, JoinType.INNER
            ) { UserTable.username eq CommentTable.username }
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

    override fun getPostLikes(pid: Int): List<LikeResponse> =
        transaction {
            LikeTable
                .select(
                    where = {
                        LikeTable.pid eq pid
                    }
                )
                .map {
                    getLikeFromResultRow(it)
                }
        }

    override fun getUserPosts(username: String, page: Int, pageSize: Int): List<Post> =
        transaction {
            PostTable
                .select(
                    where = {
                        PostTable.username eq username
                    }
                )
                .limit(pageSize * page)
                .sortedByDescending { PostTable.time }
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

    override fun insertPost(caption: String, username: String, type: Int): Int? =
        transaction {
            PostTable.insert {
                it.set(this.username, username)
                it.set(this.caption, caption)
                it.set(this.type, type)
                it.set(this.time, DateTime.now())
            }.getOrNull(PostTable.pid)
        }
}