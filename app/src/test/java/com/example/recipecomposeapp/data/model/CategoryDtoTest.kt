package com.example.recipecomposeapp.data.model

import com.example.recipecomposeapp.features.categories.presentation.model.toUiModel
import fixtures.CategoryTestFixtures
import org.junit.Assert.assertEquals
import org.junit.Test

class CategoryDtoTest {
    @Test
    fun `converts DTO to UI model`() {
        val dto = CategoryTestFixtures.createCategoryDto()
        val result = dto.toUiModel()
        assertEquals("Пасты", result.title)
    }

    @Test
    fun `mapper maps empty title correctly`() {
        val dto = CategoryTestFixtures.createCategoryDto(title = "")
        val result = dto.toUiModel()
        assertEquals("", result.title)
    }

    @Test
    fun `mapper preserves very long description`() {
        val longDescription = "Паста".repeat(30)
        val dto = CategoryTestFixtures.createCategoryDto(description = longDescription)
        val result = dto.toUiModel()
        assertEquals(longDescription, result.description)
    }
}
