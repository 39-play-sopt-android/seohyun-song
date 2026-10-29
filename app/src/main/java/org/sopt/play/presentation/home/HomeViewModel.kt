package org.sopt.play.presentation.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.sopt.play.presentation.home.model.HomeCategory
import org.sopt.play.presentation.home.model.HomeMenu

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun selectMenu(menu: HomeMenu) {
        _uiState.update { it.copy(selectedMenu = menu) }
    }

    fun selectCategory(category: HomeCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun toggleFavorite(productId: Int) {
        _uiState.update { state ->
            val ids = state.favoriteProductIds
            state.copy(
                favoriteProductIds =
                if (productId in ids) ids - productId
                else ids + productId
            )
        }
    }
}
