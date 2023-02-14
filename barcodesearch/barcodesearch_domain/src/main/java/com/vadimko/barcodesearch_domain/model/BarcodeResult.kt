package com.vadimko.barcodesearch_domain.model

data class BarcodeResult(
    val keywords: List<String>?,
    val allergens: String?,
    val allergensFromIngredients: String?,
    val brands: String?,
    val brands_tags: List<String>?,
    val categories: String?,
    val categories_hierarchy: List<String>?,
    val countries: String?,
    val imageFrontUrl: String?,
    val imageIngredientsUrl: String?,
    val imageNutritionUrl: String?,
    val ingredientsText: String?,
    val ingredients: List<IngredientsRes>,
    val nutrientLevels: NutrientLevels?,
    val nutriments: Nutriments?,
    val packaging: String?,
    val productName: String?,
    val servingSize: String?,
)