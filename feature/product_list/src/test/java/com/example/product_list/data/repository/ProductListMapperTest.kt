package com.example.product_list.data.repository

import com.example.product_list.domain.api.model.ShortProductInfo
import org.junit.Test
import kotlin.random.Random
import kotlin.test.assertEquals
import com.example.network.api.ShortProductInfo as ShortProductInfoDto

class ProductListMapperTest {
    private val mapper = ProductListMapper()

    @Test
    fun `GIVEN dto WHEN map it to domain THEN mapping is correct`() {
        val id = Random.nextLong()
        val title = Random.nextLong().toString()
        val price = Random.nextDouble()
        val brand = Random.nextDouble().toString()
        assertEquals(
            ShortProductInfo(
                id = id,
                title = title,
                price = price,
                brand = brand
            ),
            mapper.mapToDomain(
                ShortProductInfoDto(
                    id = id,
                    title = title,
                    price = price,
                    brand = brand
                )
            )
        )
    }
}