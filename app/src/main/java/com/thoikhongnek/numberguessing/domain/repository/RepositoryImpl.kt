package com.thoikhongnek.numberguessing.domain.repository

import com.thoikhongnek.numberguessing.data.datastore.DataStoreManager
import com.thoikhongnek.numberguessing.data.repository.IRepository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(
    private val dataStoreManager: DataStoreManager,
): IRepository {
    override fun getHighScore(): Flow<Int> = dataStoreManager.highScore

    override suspend fun saveHighScore(score: Int) = dataStoreManager.storeHighScore(score)
}