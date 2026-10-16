package org.sopt.play.data.model.home

import androidx.annotation.DrawableRes

data class ProductModel(
    val storeName: String,
    val productName: String,
    val discountRate: Int,
    val price: Int,
    @DrawableRes val imageRes: Int? = null,
)
