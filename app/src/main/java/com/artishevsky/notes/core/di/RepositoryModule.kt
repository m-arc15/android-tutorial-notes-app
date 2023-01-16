package com.artishevsky.notes.core.di

import com.artishevsky.notes.feature.note.data.data_source.NoteDatabase
import com.artishevsky.notes.feature.note.data.repository.NoteRepositoryImpl
import com.artishevsky.notes.feature.note.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

}
