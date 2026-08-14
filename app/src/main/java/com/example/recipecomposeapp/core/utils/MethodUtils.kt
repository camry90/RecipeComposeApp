package com.example.recipecomposeapp.core.utils

fun methodToString(instructions: List<String>?): String {
    return instructions?.joinToString(separator = "\n") ?: ""
}

fun methodToList(instructions: String): List<String> {
    return instructions.split(",")
}