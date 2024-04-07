package com.thoikhongnek.numberguessing

import android.app.Application
import com.thoikhongnek.numberguessing.di.dataSourceModule
import com.thoikhongnek.numberguessing.di.dispatcherModule
import com.thoikhongnek.numberguessing.di.repositoryModule
import com.thoikhongnek.numberguessing.di.useCaseModule
import com.thoikhongnek.numberguessing.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                viewModelModule,
                dispatcherModule,
                dataSourceModule,
                useCaseModule,
                repositoryModule,
            )
        }
    }
}