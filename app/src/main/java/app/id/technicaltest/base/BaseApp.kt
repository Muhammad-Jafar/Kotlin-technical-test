package app.id.technicaltest.base

import android.app.Application
import app.id.technicaltest.data.local.PreferenceStore
import app.id.technicaltest.data.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val datStoreModule = module {
            single { PreferenceStore(get()) }
        }

        val viewModelModule = module {
            viewModel { MainViewModel(get()) }
        }

        startKoin {
            androidContext(this@BaseApp)
            modules(datStoreModule, viewModelModule)
        }
    }
}
