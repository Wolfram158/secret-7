package com.example.settings.di

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.settings.api.Settings
import com.example.settings.api.SettingsDataSource
import com.example.settings.impl.SerializerProvider
import com.example.settings.impl.SettingsDataSourceImpl
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json

@ContributesTo(AppScope::class)
@BindingContainer
object SettingsDataSourceBinding {
    @SingleIn(AppScope::class)
    @Provides
    fun provideSettingsDataSource(context: Context, json: Json): SettingsDataSource {
        val dataStore = DataStoreFactory.create(
            serializer = SerializerProvider.provideSerializer(
                json = json,
                ioDispatcher = Dispatchers.IO,
                default = Settings.DEFAULT
            ),
            produceFile = {
                context.preferencesDataStoreFile("settings-preferences")
            }
        )
        return SettingsDataSourceImpl(dataStore)
    }
}