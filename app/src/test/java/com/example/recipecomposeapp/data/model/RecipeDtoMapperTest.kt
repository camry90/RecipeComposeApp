package com.example.recipecomposeapp.data.model

import com.example.recipecomposeapp.Constants
import com.example.recipecomposeapp.features.recipes.presentation.model.toUiModel
import fixtures.RecipeTestFixtures
import org.junit.Assert.assertEquals
import org.junit.Test

class RecipeDtoMapperTest {
    @Test
    fun `maps DTO to UI model correctly`() {
        val dto = RecipeTestFixtures.createRecipeDto()
        val result = dto.toUiModel()
        assertEquals("Паста Карбонара", result.title)
    }

    @Test
    fun `prepends base url to relative imageUrl`() {
        val dto = RecipeTestFixtures.createRecipeDto()
        val result = dto.toUiModel()
        assertEquals(Constants.IMAGES_BASE_URL + dto.imageUrl, result.imageUrl)
    }

    @Test
    fun `preserves full imageUrl starting with http`() {
        val dto = RecipeTestFixtures.createRecipeDto(
            imageUrl = "https://example.com/pasta.jpg"
        )
        val result = dto.toUiModel()
        assertEquals("https://example.com/pasta.jpg", result.imageUrl)
    }
}
