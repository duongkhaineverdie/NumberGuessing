package com.thoikhongnek.numberguessing.domain.interactors

import com.thoikhongnek.numberguessing.data.repository.IRepository
import com.thoikhongnek.numberguessing.domain.interactors.type.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class SaveHighScoreUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Int, Unit>(dispatcher) {
    override suspend fun block(param: Int): Unit = repository.saveHighScore(score = param)
}