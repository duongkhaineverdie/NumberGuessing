package com.thoikhongnek.numberguessing.di

import com.thoikhongnek.numberguessing.data.datastore.DataStoreManager
import com.thoikhongnek.numberguessing.data.repository.IRepository
import com.thoikhongnek.numberguessing.domain.interactors.GetHighScoreUseCase
import com.thoikhongnek.numberguessing.domain.interactors.SaveHighScoreUseCase
import com.thoikhongnek.numberguessing.domain.repository.RepositoryImpl
import com.thoikhongnek.numberguessing.presentation.MainViewModel
import com.thoikhongnek.numberguessing.presentation.ui.countdowntime.CountdownTimeViewModel
import com.thoikhongnek.numberguessing.presentation.ui.home.HomeViewModel
import com.thoikhongnek.numberguessing.presentation.ui.numberguessinggame.NumberGuessingGameViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val viewModelModule = module {
    single { MainViewModel() }
    single { HomeViewModel() }
    factory { CountdownTimeViewModel(get()) }
    factory { NumberGuessingGameViewModel(get(), get(), get()) }
}

val dispatcherModule = module {
    factory { Dispatchers.Default }
}

val dataSourceModule = module {
    single { DataStoreManager(get()) }
}

val useCaseModule = module {
    factory { GetHighScoreUseCase(get(), get()) }
    factory { SaveHighScoreUseCase(get(), get()) }
}

val repositoryModule = module {
    single<IRepository> { RepositoryImpl(get()) }
}
