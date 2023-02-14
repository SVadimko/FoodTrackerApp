package com.vadimko.barcodesearch_data.remote.dto

import com.squareup.moshi.Json

data class Product(
    @field:Json(name = "_keywords")
    val keywords: List<String>?,
    @field:Json(name = "allergens")
    val allergens: String?,
    @field:Json(name = "allergens_from_ingredients")
    val allergensFromIngredients: String?,
    @field:Json(name = "brands")
    val brands: String?,
    @field:Json(name = "brands_tags")
    val brands_tags: List<String>?,
    @field:Json(name = "categories")
    val categories: String?,
    @field:Json(name = "categories_hierarchy")
    val categories_hierarchy: List<String>?,
    @field:Json(name = "countries")
    val countries: String?,
    @field:Json(name = "image_front_url")
    val imageFrontUrl: String?,
    @field:Json(name = "image_ingredients_url")
    val imageIngredientsUrl: String?,
    @field:Json(name = "image_nutrition_url")
    val imageNutritionUrl: String?,
    @field:Json(name = "ingredients_text")
    val ingredientsText: String?,
    @field:Json(name = "ingredients")
    val ingredients: List<Ingredients>,
    @field:Json(name = "nutrient_levels")
    val nutrientLevels: NutrientLevels?,
    val nutriments: Nutriments?,
    @field:Json(name = "packaging")
    val packaging: String?,
    @field:Json(name = "product_name")
    val productName: String?,
    @field:Json(name = "serving_size")
    val servingSize: String?,
    )


