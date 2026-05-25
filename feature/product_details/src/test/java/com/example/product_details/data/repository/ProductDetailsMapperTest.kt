package com.example.product_details.data.repository

import com.example.database.api.ProductDetailsDbo
import com.example.network.api.ProductInfo
import com.example.product_details.domain.api.model.ProductDetails
import org.junit.Test
import kotlin.random.Random
import kotlin.test.assertEquals

class ProductDetailsMapperTest {
    private val mapper = ProductDetailsMapper()
    private val id = Random.nextLong()
    private val title = Random.nextDouble().toString()
    private val isActual = Random.nextBoolean()
    private val thumbnail = ""
    private val rating = 2.25
    private val price = 4.33
    private val availabilityStatus = ""
    private val warrantyInformation = ""
    private val weight = 3.3
    private val description = ""
    private val updatedAt = System.currentTimeMillis()
    private val domain = ProductDetails(
        id = id,
        title = title,
        isActual = isActual,
        thumbnail = thumbnail,
        description = description,
        rating = rating,
        price = price,
        weight = weight,
        availabilityStatus = availabilityStatus,
        warrantyInformation = warrantyInformation
    )
    private val dbo = ProductDetailsDbo(
        id = id,
        thumbnail = thumbnail,
        updatedAt = updatedAt,
        title = title,
        description = description,
        rating = rating,
        price = price,
        weight = weight,
        availabilityStatus = availabilityStatus,
        warrantyInformation = warrantyInformation
    )
    private val dto = ProductInfo(
        id = id,
        thumbnail = thumbnail,
        title = title,
        description = description,
        rating = rating,
        price = price,
        weight = weight,
        availabilityStatus = availabilityStatus,
        warrantyInformation = warrantyInformation
    )

    @Test
    fun `GIVEN dto WHEN map it to domain THEN mapping is correct`() {
        assertEquals(
            domain,
            mapper.mapToDomain(dto, isActual)
        )
    }

    @Test
    fun `GIVEN dbo WHEN map it to domain THEN mapping is correct`() {
        assertEquals(
            domain,
            mapper.mapToDomain(dbo, isActual)
        )
    }

    @Test
    fun `GIVEN dto WHEN map it to dbo THEN mapping is correct`() {
        assertEquals(
            dbo,
            mapper.mapRemoteToLocal(id, dto)
        )
    }
}