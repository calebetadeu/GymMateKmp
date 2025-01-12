package org.calebetadeu.streamingtowatch.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.calebetadeu.streamingtowatch.data.DatabaseFactory
import org.calebetadeu.streamingtowatch.data.GymMateDatabase
import org.calebetadeu.streamingtowatch.presentation.viewModel.GymMateViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<GymMateDatabase>().exerciseDao }

    viewModelOf(::GymMateViewModel)

}