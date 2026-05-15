package com.example.database.impl

import androidx.room.Dao
import androidx.room.Query
import com.example.database.api.CartElementDbo
import com.example.database.api.CartLocalDataSource

@Dao
internal interface CartDao : CartLocalDataSource {
    @Query("select * from cart")
    override suspend fun getCart(): List<CartElementDbo>

    @Query(
        "insert into cart(id, title, count)" +
                " values (:id, :title, 1) on conflict(id)" +
                " do update set count = count + 1"
    )
    override suspend fun incrementCartElementCount(id: Long, title: String)

    @Query("delete from cart")
    override suspend fun clearCart()
}