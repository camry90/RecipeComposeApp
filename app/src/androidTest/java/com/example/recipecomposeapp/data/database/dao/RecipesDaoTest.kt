package com.example.recipecomposeapp.data.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.recipecomposeapp.data.database.RecipesDatabase
import com.example.recipecomposeapp.data.database.entity.CategoryEntity
import com.example.recipecomposeapp.data.database.entity.RecipeEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecipesDaoTest {

    private lateinit var database: RecipesDatabase
    private lateinit var categoryDao: CategoryDao
    private lateinit var recipeDao: RecipeDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, RecipesDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        categoryDao = database.categoryDao()
        recipeDao = database.recipeDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndRetrievesCategories() = runTest {
        val categories =
            listOf(
                CategoryEntity(id = 1, name = "Завтраки", description = "Лёгкие", imageUrl = ""),
                CategoryEntity(id = 2, name = "Обеды", description = "Основные", imageUrl = "")
            )
        categoryDao.insertCategories(categories)
        val retrieved = categoryDao.getAllCategories().first()

        assertEquals(2, retrieved.size)
    }

    @Test
    fun insertReplacesDuplicateCategory() = runTest {
        categoryDao.insertCategories(
            listOf(
                CategoryEntity(id = 1, name = "Завтраки", description = "Лёгкие", imageUrl = ""),
            )
        )
        categoryDao.insertCategories(
            listOf(
                CategoryEntity(id = 1, name = "Завтраки", description = "Основные", imageUrl = ""),
            )
        )
        val retrieved = categoryDao.getAllCategories().first()
        assertEquals(1, retrieved.size)
        assertEquals("Основные", retrieved.single().description)
    }

    @Test
    fun getRecipesByCategoryReturnsCorrectItems() = runTest {
        categoryDao.insertCategories(
            listOf(
                CategoryEntity(id = 1, name = "Завтраки", description = "Лёгкие", imageUrl = ""),
                CategoryEntity(id = 2, name = "Обеды", description = "Основные", imageUrl = "")
            )
        )

        val recipes = listOf(
            RecipeEntity(
                id = 1,
                categoryId = 1,
                title = "Омлет",
                imageUrl = "omelet.jpg",
                ingredients = "Яйца, масло",
                method = "Разогреть масло, добавить яйца"
            ),
            RecipeEntity(
                id = 2,
                categoryId = 2,
                title = "Паста карбонара",
                imageUrl = "pasta.jpg",
                ingredients = "Макараны",
                method = "Приготовить пасту"
            ),
            RecipeEntity(
                id = 3,
                categoryId = 2,
                title = "Салат цезарь",
                imageUrl = "caesar_salad.jpg",
                ingredients = "Курица, айсберг, черри, соус",
                method = "Приготовить салат"
            )
        )
        recipeDao.insertRecipes(recipes)

        val retrieved = recipeDao.getRecipesByCategoryId(2).first()
        assertEquals(2, retrieved.size)
        assertEquals(setOf(2, 3), retrieved.map { it.id }.toSet())
    }

    @Test
    fun emptyDatabaseReturnsEmptyList() = runTest {
        val emptyList = categoryDao.getAllCategories().first()
        assertEquals(0, emptyList.size)
    }
}