package org.sopt.play.presentation.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.R
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.core.extensions.noRippleClickable
import org.sopt.play.core.extensions.toPriceFormat
import org.sopt.play.presentation.home.model.ProductModel

@Composable
fun HomeProductCard(
    product: ProductModel,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val favoriteIcon = if (isFavorite) R.drawable.ic_favorite_filled_24
    else R.drawable.ic_favorite_default_24

    val favoriteColor = if (isFavorite) PlaySoptTheme.colors.red
    else PlaySoptTheme.colors.white

    val textColor = PlaySoptTheme.colors.black
    val textStyle = PlaySoptTheme.typography.m14

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box {
            Image(
                painter = painterResource(product.imageRes ?: R.drawable.empty),
                contentDescription = null,
                modifier = Modifier.aspectRatio(4f / 5f),
                contentScale = ContentScale.Crop,
            )

            Icon(
                painter = painterResource(id = favoriteIcon),
                contentDescription = null,
                tint = favoriteColor,
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .noRippleClickable(onClick = onFavoriteClick)
                    .padding(all = 8.dp)
                    .size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = product.storeName,
                color = textColor,
                style = textStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text = product.productName,
                color = textColor,
                style = PlaySoptTheme.typography.r14,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "${product.discountRate}%",
                    color = PlaySoptTheme.colors.red,
                    style = textStyle,
                )

                Text(
                    text = product.price.toPriceFormat(),
                    color = textColor,
                    style = textStyle,
                )
            }
        }
    }
}


private val longTextProduct = ProductModel(
    id = 1,
    storeName = "스토어명 최대 한 줄스토어명 최대 한 줄스토어명 최대 한 줄스토어명 최대 한 줄",
    productName = "상품명 최대 한 줄상품명 최대 한 줄상품명 최대 한 줄상품명 최대 한 줄상품명 최대 한 줄",
    discountRate = 49,
    price = 29370,
)

private val shortTextProduct = ProductModel(
    id = 2,
    storeName = "스토어명 최대 한 줄",
    productName = "상품명 최대 한 줄",
    discountRate = 49,
    price = 29370,
)

@Preview(showBackground = true)
@Composable
private fun HomeProductCardPreview() {
    PlaySoptTheme {
        Row(
            modifier = Modifier.padding(all = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            HomeProductCard(
                product = longTextProduct,
                isFavorite = false,
                onFavoriteClick = {},
                modifier = Modifier.width(160.dp),
            )

            HomeProductCard(
                product = shortTextProduct,
                isFavorite = true,
                onFavoriteClick = {},
                modifier = Modifier.width(160.dp),
            )
        }
    }
}
