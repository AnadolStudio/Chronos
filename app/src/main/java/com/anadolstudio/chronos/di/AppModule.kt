package com.anadolstudio.chronos.di

import dagger.Module

@Module(includes = [DatabaseModule::class, UseCaseModule::class])
class AppModule
