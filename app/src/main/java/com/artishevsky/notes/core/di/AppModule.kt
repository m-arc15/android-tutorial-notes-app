package com.artishevsky.notes.core.di

import com.artishevsky.notes.feature.note.data.repository.NoteRepositoryImpl
import com.artishevsky.notes.feature.note.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteRepository(): NoteRepository {
        return NoteRepositoryImpl()
    }

}
