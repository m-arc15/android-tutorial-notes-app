package com.artishevsky.notes.core.di

import com.artishevsky.notes.feature.note.domain.model.Note
import com.artishevsky.notes.feature.note.domain.repository.NoteRepository
import com.artishevsky.notes.feature.note.domain.use_case.GetNotesUseCase
import com.artishevsky.notes.feature.note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteRepository(): NoteRepository {
        return object : NoteRepository {
            override fun getNotes(): Flow<List<Note>> {
                TODO("Not yet implemented")
            }
        }
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repo: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotesUseCase(repo)
        )
    }

}
