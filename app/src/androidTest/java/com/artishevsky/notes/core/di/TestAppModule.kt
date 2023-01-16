package com.artishevsky.notes.core.di

import android.app.Application
import androidx.room.Room
import com.artishevsky.notes.feature.note.data.data_source.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestAppModule {

    @Provides
    @Singleton
    fun provideInMemoryNoteDatabase(app: Application): NoteDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            NoteDatabase::class.java
        ).allowMainThreadQueries()
            .build()
    }

}
