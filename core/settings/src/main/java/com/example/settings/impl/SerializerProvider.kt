package com.example.settings.impl

import androidx.datastore.core.Serializer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

internal object SerializerProvider {
    inline fun <reified T> provideSerializer(
        json: Json,
        ioDispatcher: CoroutineDispatcher,
        default: T
    ): Serializer<T> {
        return object : Serializer<T> {
            override val defaultValue: T
                get() = default

            override suspend fun readFrom(input: InputStream): T {
                val bytes = withContext(ioDispatcher) {
                    input.use { it.readBytes() }
                }
                return json.decodeFromString(bytes.decodeToString())
            }

            override suspend fun writeTo(t: T, output: OutputStream) {
                val jsonString = json.encodeToString(t)
                val bytes = jsonString.toByteArray()
                withContext(ioDispatcher) {
                    output.use {
                        it.write(bytes)
                    }
                }
            }

        }
    }
}