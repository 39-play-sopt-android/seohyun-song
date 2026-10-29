package org.sopt.play.presentation.home.model

import androidx.annotation.DrawableRes

data class ProductModel(
    val id: Int,
    val storeName: String,
    val productName: String,
    val discountRate: Int,
    val price: Int,
    @DrawableRes val imageRes: Int? = null,
)
