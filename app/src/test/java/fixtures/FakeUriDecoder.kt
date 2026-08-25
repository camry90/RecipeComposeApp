package fixtures

import com.example.recipecomposeapp.core.utils.UriDecoder

class FakeUriDecoder : UriDecoder {
    override fun decode(value: String): String = value // ничего не делаем, как надо в тестах
}