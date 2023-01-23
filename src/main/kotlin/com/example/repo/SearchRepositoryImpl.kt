package com.example.repo

import com.example.db.table.PostTable
import com.example.db.table.UserTable
import com.example.maper.PostMapper
import com.example.response.SearchedPost
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class SearchRepositoryImpl(
    private val database: Database
) : SearchRepository {

    override fun searchPosts(text: String): Result<List<SearchedPost>> = kotlin.runCatching {
        transaction {
            PostTable.join(UserTable, JoinType.INNER) {
                UserTable.username eq PostTable.username
            }.select {
                PostTable.caption like ("%$text%")
            }.map {
                PostMapper.mapToSearchedPost(it)
            }
        }
    }
}