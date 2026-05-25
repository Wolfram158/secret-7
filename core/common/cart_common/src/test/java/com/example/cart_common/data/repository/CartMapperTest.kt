package com.example.cart_common.data.repository

import com.example.cart_common.domain.api.model.CartElement
import com.example.database.api.CartElementDbo
import org.junit.Test
import kotlin.random.Random
import kotlin.test.assertEquals

class CartMapperTest {
    private val mapper = CartMapper()

    @Test
    fun `GIVEN dbo WHEN map it to domain THEN mapping is correct`() {
        val id = Random.nextLong()
        val title = Random.nextLong().toString()
        val count = Random.nextLong()
        assertEquals(
            CartElement(
                id = id,
                title = title,
                count = count
            ),
            mapper.mapLocalToDomain(
                CartElementDbo(
                    id = id,
                    title = title,
                    count = count,
                )
            )
        )
    }
}