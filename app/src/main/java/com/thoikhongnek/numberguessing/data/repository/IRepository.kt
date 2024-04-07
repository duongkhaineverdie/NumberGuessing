package com.thoikhongnek.numberguessing.data.repository

import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getHighScore(): Flow<Int>
    suspend fun saveHighScore(score: Int)
}