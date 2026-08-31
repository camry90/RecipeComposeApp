package com.example.recipecomposeapp.core.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteDataStoreTest {

    private lateinit var context: Context
    private lateinit var manager: FavoriteDataStoreManager

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        manager = FavoriteDataStoreManager(context)
    }

    @After
    fun tearDown() {
        runBlocking { context.dataStore.edit { it.clear() } }
    }

    @Test
    fun addFavoriteSavesRecipeId() = runTest {
        manager.addFavorite(recipeId = 42)
        val favoriteIds = manager.getFavoriteIdsFlow().first()
        assertTrue(favoriteIds.contains("42") )
    }

    @Test
    fun removeFromFavoritesDeletesRecipeId() = runTest {
        manager.addFavorite(42)
        manager.removeFavorite(42)
        val favoriteIds = manager.getFavoriteIdsFlow().first()
        assertFalse(favoriteIds.contains("42"))
    }

    @Test
    fun favoritesFlowEmitsUpdatesReactively() = runTest {
        manager.addFavorite(42)

        manager.getFavoriteIdsFlow().test {
            val loaded = awaitItem()
            assertTrue(loaded.contains("42"))
        }
    }

}