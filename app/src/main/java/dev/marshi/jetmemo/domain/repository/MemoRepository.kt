package dev.marshi.jetmemo.domain.repository

interface MemoRepository {

    suspend fun add(text: String)
}
