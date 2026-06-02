package com.example.settings.impl

import androidx.datastore.core.DataStore
import com.example.settings.api.Settings
import com.example.settings.api.SettingsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SettingsDataSourceImpl(
    private val dataStore: DataStore<Settings>
) : SettingsDataSource {
    override fun getRemindAboutPurchaseFlow(): Flow<Boolean> {
        return dataStore.data.map { it.remindAboutPurchase }
    }

    override suspend fun changeRemindAboutPurchase() {
        dataStore.updateData {
            it.copy(remindAboutPurchase = !it.remindAboutPurchase)
        }
    }

    override fun getSettingsFlow(): Flow<Settings> {
        return dataStore.data
    }
}