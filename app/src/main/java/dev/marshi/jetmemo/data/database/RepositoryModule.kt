package dev.marshi.jetmemo.data.database

import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.marshi.jetmemo.data.database.datasource.MemoDatasource
import dev.marshi.jetmemo.domain.repository.MemoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun memoRepository(memoDatasource: MemoDatasource): MemoRepository
}
