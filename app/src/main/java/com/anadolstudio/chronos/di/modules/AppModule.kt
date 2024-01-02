package com.anadolstudio.chronos.di.modules

import dagger.Module

@Module(
    includes = [
        DatabaseModule::class,
        RepositoryModule::class
    ]
)
class AppModule
