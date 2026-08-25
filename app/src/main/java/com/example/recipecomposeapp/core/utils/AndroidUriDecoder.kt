package com.example.recipecomposeapp.core.utils

import android.net.Uri
import javax.inject.Inject

class AndroidUriDecoder @Inject constructor() : UriDecoder {
    override fun decode(value: String): String = Uri.decode(value)
}