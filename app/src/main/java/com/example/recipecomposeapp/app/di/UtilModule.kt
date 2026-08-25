package com.example.recipecomposeapp.app.di

import com.example.recipecomposeapp.core.utils.AndroidUriDecoder
import com.example.recipecomposeapp.core.utils.UriDecoder
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilModule {
    @Binds
    abstract fun bindUriDecoder(impl: AndroidUriDecoder): UriDecoder
}