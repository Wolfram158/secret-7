package com.example.settings.api

import kotlinx.coroutines.flow.Flow

interface SettingsDataSource {
    fun getRemindAboutPurchaseFlow(): Flow<Boolean>

    suspend fun changeRemindAboutPurchase()

    fun getSettingsFlow(): Flow<Settings>
}