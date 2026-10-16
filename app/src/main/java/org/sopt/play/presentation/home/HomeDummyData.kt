package org.sopt.play.presentation.home

import org.sopt.play.R
import org.sopt.play.data.model.home.ProductModel

internal object HomeDummyData {
    val banners = listOf(
        R.drawable.banner_1,
        R.drawable.banner_2,
        R.drawable.banner_3,
    )

    val products = listOf(
        ProductModel(
            id = 1,
            storeName = "트랜디어패럴",
            productName = "[카프리1위🏆/5부,7부,9부기장/MADE] 클립 스판 슬릿 바이커 카프리 데님 팬츠",
            discountRate = 36,
            price = 28800,
            imageRes = R.drawable.product_1,
        ),
        ProductModel(
            id = 2,
            storeName = "슬로우앤드",
            productName = "[🍁가을/3기장선택!] made. 모먼트 투커버 올데이슬랙스 (+숏/베이직/롱ver) - 4 size",
            discountRate = 50,
            price = 26820,
            imageRes = R.drawable.product_2,
        ),
        ProductModel(
            id = 3,
            storeName = "어텀",
            productName = "보울7부티셔츠",
            discountRate = 10,
            price = 17100,
            imageRes = R.drawable.product_3,
        ),
        ProductModel(
            id = 4,
            storeName = "슬로우앤드",
            productName = "[오픈할인중] made. 도브 클래식 탄탄핏 무지셔츠 - 4 color",
            discountRate = 40,
            price = 35200,
            imageRes = R.drawable.product_4,
        ),
    )

    val longTextProduct = ProductModel(
        id = 1,
        storeName = "스토어명 최대 한 줄스토어명 최대 한 줄스토어명 최대 한 줄스토어명 최대 한 줄",
        productName = "상품명 최대 한 줄상품명 최대 한 줄상품명 최대 한 줄상품명 최대 한 줄상품명 최대 한 줄",
        discountRate = 49,
        price = 29370,
    )

    val shortTextProduct = ProductModel(
        id = 2,
        storeName = "스토어명 최대 한 줄",
        productName = "상품명 최대 한 줄",
        discountRate = 49,
        price = 29370,
    )
}
