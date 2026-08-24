package fixtures

import com.example.recipecomposeapp.data.model.CategoryDto

object CategoryTestFixtures {
    fun createCategoryDto(
        id: Int = 1,
        title: String = "Пасты",
        description: String = "Паста на любой вкус и цвет",
        imageUrl: String = "pastes.jpg"
    ) = CategoryDto(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl
    )

    fun createCategoryDtoList(count: Int = 3) =
        List(count) { index -> createCategoryDto(id = index + 1, title = "Рецепт ${index + 1}") }
}
