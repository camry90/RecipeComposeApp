package fixtures

import com.example.recipecomposeapp.data.database.entity.RecipeEntity
import com.example.recipecomposeapp.data.model.IngredientDto
import com.example.recipecomposeapp.data.model.RecipeDto

object RecipeTestFixtures {
    fun createIngredientDto(
        quantity: String = "200",
        unitOfMeasure: String = "г",
        description: String = "Паста"
    ) = IngredientDto(
        quantity = quantity,
        unitOfMeasure = unitOfMeasure,
        description = description
    )

    fun createRecipeDto(
        id: Int = 1,
        title: String = "Паста Карбонара",
        ingredients: List<IngredientDto> = listOf(createIngredientDto()),
        method: List<String> = listOf("Отварить", "Смешать"),
        imageUrl: String = "pasta.jpg",
    ) = RecipeDto(
        id = id,
        title = title,
        ingredients = ingredients,
        method = method,
        imageUrl = imageUrl
    )

    fun createRecipeEntity(
        id: Int = 1,
        categoryId: Int = 1,
        title: String = "Омлет",
        imageUrl: String = "omelet.jpg",
        ingredientsJson: String = """[{"quantity":"2","unitOfMeasure":"шт","description":"Яйца"}]""",
        methodJson: String = """["Расколить масло","Разбить яйца"]"""
    ) = RecipeEntity(
        id = id,
        categoryId = categoryId,
        title = title,
        imageUrl = imageUrl,
        ingredients = ingredientsJson,
        method = methodJson
    )

    fun createRecipeDtoList(count: Int = 3) =
        List(count) { index -> createRecipeDto(id = index + 1, title = "Рецепт ${index + 1}") }
}
