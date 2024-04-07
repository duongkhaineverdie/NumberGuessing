package com.thoikhongnek.numberguessing.domain.interactors

import com.thoikhongnek.numberguessing.data.repository.IRepository
import com.thoikhongnek.numberguessing.domain.interactors.type.BaseUseCase
import com.thoikhongnek.numberguessing.domain.interactors.type.BaseUseCaseFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetHighScoreUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCaseFlow<Unit, Int>(dispatcher) {
    override suspend fun build(param: Unit): Flow<Int> = repository.getHighScore()

}