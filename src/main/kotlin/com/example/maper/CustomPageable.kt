package com.example.maper

import kotlin.math.min

object CustomPageable {

    fun <T : Any> pageable(page: Int, pageSize: Int, list: List<T>) =
        if (page <= 1) {
            list.subList(0, min(list.size, pageSize))
        } else if (list.size < page * pageSize) {
            emptyList()
        } else {
            list.subList((page - 1) * pageSize, min(list.size, page * pageSize))
        }
}