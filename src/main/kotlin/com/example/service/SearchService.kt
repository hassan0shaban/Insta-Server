package com.example.service

import com.example.repo.SearchRepository
import com.example.response.SearchedPost

class SearchService(
    private val searchRepository: SearchRepository
) {
    fun searchPosts(text: String): List<SearchedPost> =
        searchRepository.searchPosts(text)
            .getOrElse {
                it.printStackTrace()
                emptyList()
            }
}