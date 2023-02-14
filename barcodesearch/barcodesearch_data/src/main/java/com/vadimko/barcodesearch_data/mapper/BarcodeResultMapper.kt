package com.vadimko.barcodesearch_data.mapper

import com.vadimko.barcodesearch_data.remote.dto.Ingredients
import com.vadimko.barcodesearch_data.remote.dto.Product
import com.vadimko.barcodesearch_domain.model.BarcodeResult
import com.vadimko.barcodesearch_domain.model.IngredientsRes
import com.vadimko.barcodesearch_domain.model.NutrientLevels
import com.vadimko.barcodesearch_domain.model.Nutriments

fun Product.toBarcodeResult(): BarcodeResult {
    return BarcodeResult(
        keywords = this.keywords,
        allergens = this.allergens,
        allergensFromIngredients = this.allergensFromIngredients,
        brands = this.brands,
        brands_tags = this.brands_tags,
        categories = this.categories,
        categories_hierarchy = this.categories_hierarchy,
        countries = this.countries,
        imageFrontUrl = this.imageFrontUrl,
        imageIngredientsUrl = this.imageIngredientsUrl,
        imageNutritionUrl = this.imageNutritionUrl,
        ingredients = this.ingredients.toResIngredients(),
        ingredientsText = this.ingredientsText,
        nutrientLevels = NutrientLevels(
            fat = this.nutrientLevels?.fat,
            saturatedFat = this.nutrientLevels?.saturatedFat,
            salt = this.nutrientLevels?.salt,
            sugars = this.nutrientLevels?.sugars
        ),
        nutriments = Nutriments(
            carbohydrates100g = this.nutriments?.carbohydrates100g,
            proteins100g = this.nutriments?.proteins100g,
            fat100g = this.nutriments?.fat100g,
            energyKcal100g = this.nutriments?.energyKcal100g,
            alcohol = this.nutriments?.alcohol,
            calcium = this.nutriments?.calcium,
            cellulose = this.nutriments?.cellulose,
            cu = this.nutriments?.cu,
            iron = this.nutriments?.iron,
            fiber = this.nutriments?.fiber,
            magnesium = this.nutriments?.magnesium,
            phosphorus = this.nutriments?.phosphorus,
            potassium = this.nutriments?.potassium,
            omega3 = this.nutriments?.omega3,
            omega6 = this.nutriments?.omega6,
            salt = this.nutriments?.salt,
            saturatedFat = this.nutriments?.saturatedFat,
            sodium = this.nutriments?.sodium,
            sugars = this.nutriments?.sugars,
            vitaminC = this.nutriments?.vitaminC,
            zinc = this.nutriments?.zinc,
        ),
        packaging = this.packaging,
        productName = this.productName,
        servingSize = this.servingSize,
    )


}

private fun List<Ingredients>.toResIngredients(): List<IngredientsRes> {
    val list: MutableList<IngredientsRes> = mutableListOf()
    this.forEachIndexed { _, ingredients ->
        list.add(IngredientsRes(ingredients.text, ingredients.percent_estimate))
    }
    return list
}