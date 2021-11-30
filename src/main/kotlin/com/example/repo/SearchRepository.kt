package com.example.repo

import com.example.model.Post
import com.example.response.PostResponse
import com.example.response.SearchedPost

interface SearchRepository {
    fun searchPosts(text: String): Result<List<SearchedPost>>
}