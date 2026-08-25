package com.example.recipecomposeapp.features.recipes.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.recipecomposeapp.data.repository.RecipesRepository
import fixtures.FakeUriDecoder
import fixtures.RecipeTestFixtures
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class RecipesViewModelTest {

    private val repository = mockk<RecipesRepository>()
    private val uriDecoder = FakeUriDecoder()
    private lateinit var viewModel: RecipesViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    private fun createViewModel(
        categoryId: Int,
        title: String,
        imageUrl: String
    ) = RecipesViewModel(
        repository = repository,
        saveStateHandle = SavedStateHandle(
            mapOf(
                "categoryId" to categoryId,
                "categoryTitle" to title,
                "categoryImageUrl" to imageUrl
            )
        ),
        uriDecoder = uriDecoder,
    )

    @Test
    fun `load recipes for category`() = runTest {
        every { repository.getRecipesByCategory(1) } returns flowOf(
            listOf(
                RecipeTestFixtures.createRecipeDto()
            )
        )
        viewModel = createViewModel(categoryId = 1, title = "Пасты", imageUrl = "pastes.jpg")
        viewModel.loadRecipes()
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(1, state.recipes.size)
            assertFalse(state.isLoading)
        }
    }

    @Test
    fun `state reflects category title from savedState`() = runTest {
        every { repository.getRecipesByCategory(1) } returns flowOf(
            listOf(
                RecipeTestFixtures.createRecipeDto()
            )
        )
        viewModel = createViewModel(categoryId = 1, title = "Завтраки", imageUrl = "omelet.jpg")
        viewModel.loadRecipes()
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("Завтраки", state.categoryTitle)
        }
    }

    @Test
    fun `shows error when repository throws`() = runTest {
        every { repository.getRecipesByCategory(1) } returns flow { throw IOException("Network error") }
        viewModel = createViewModel(categoryId = 1, title = "Пасты", imageUrl = "pastes.jpg")
        viewModel.loadRecipes()
        viewModel.uiState.test {
            val state = awaitItem()
            assertNotNull(state.error)
        }
    }
}