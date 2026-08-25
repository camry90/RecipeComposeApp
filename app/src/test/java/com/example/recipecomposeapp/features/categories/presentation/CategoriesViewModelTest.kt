package com.example.recipecomposeapp.features.categories.presentation

import app.cash.turbine.test
import com.example.recipecomposeapp.data.repository.RecipesRepository
import fixtures.CategoryTestFixtures
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class CategoriesViewModelTest {

    private val repository = mockk<RecipesRepository>()
    private lateinit var viewModel: CategoriesViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        every { repository.getCategories() } returns flowOf(emptyList())
        viewModel = CategoriesViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `load categories from repository`() = runTest {
        every { repository.getCategories() } returns flowOf(
            listOf(
                CategoryTestFixtures.createCategoryDto()
            )
        )
        viewModel.loadCategories()

        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertEquals(1, state.categories.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `shows empty list when repository returns no data`() = runTest {
        every { repository.getCategories() } returns flowOf(emptyList())
        viewModel.loadCategories()

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.categories.isEmpty())
            assertNull(state.error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `shows error when repository throws`() = runTest {
        every { repository.getCategories() } returns flow { throw IOException("Network error") }
        viewModel.loadCategories()

        viewModel.uiState.test {
            val state = awaitItem()
            assertNotNull(state.error)
            cancelAndIgnoreRemainingEvents()
        }
    }
}