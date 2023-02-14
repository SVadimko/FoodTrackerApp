package com.vadimko.barcodesearch_domain.use_case

import android.content.Context
import com.vadimko.barcodesearch_domain.R
import com.vadimko.barcodesearch_domain.model.BarcodeResult
import com.vadimko.barcodesearch_domain.model.BarcodeState
import com.vadimko.barcodesearch_domain.model.IngredientsRes
import com.vadimko.barcodesearch_domain.model.Parameter

class GetListsFromResult(val context: Context) {
    operator fun invoke(
        barcodeResult: BarcodeResult?,
    ): BarcodeState {
        val listResult: MutableList<Parameter> = mutableListOf()
        if (barcodeResult == null) {
            return BarcodeState(emptyList(), emptyList())
        } else {
            prepareItemFromString(
                context.getString(R.string.productName),
                barcodeResult.productName
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.countries),
                barcodeResult.countries
            )?.let {
                listResult.add(it)
            }

            prepareItemFromString(
                context.getString(R.string.allergens),
                barcodeResult.allergens
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.allergensFromIngredients),
                barcodeResult.allergensFromIngredients
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(context.getString(R.string.brands), barcodeResult.brands)?.let {
                listResult.add(it)
            }
            prepareItemFromList(
                context.getString(R.string.brands_tags),
                barcodeResult.brands_tags
            )?.let {
                listResult.add(it)
            }
            /* prepareItemFromString(
                 context.getString(R.string.categories),
                 barcodeResult.categories
             )?.let {
                 listResult.add(it)
             }*/
            /* prepareItemFromList(
                 context.getString(R.string.categories_hierarchy),
                 barcodeResult.categories_hierarchy
             )?.let {
                 listResult.add(it)
             }*/

            prepareItemFromString(
                context.getString(R.string.kcal),
                barcodeResult.nutriments?.energyKcal100g.toString()
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.protein),
                barcodeResult.nutriments?.proteins100g.toString()
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.fat),
                barcodeResult.nutriments?.fat100g.toString()
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.carbs),
                barcodeResult.nutriments?.carbohydrates100g.toString()
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.alcohol),
                barcodeResult.nutriments?.alcohol.toString()
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.calcium),
                barcodeResult.nutriments?.calcium.toString()
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.cellulose),
                barcodeResult.nutriments?.cellulose.toString()
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.cu),
                barcodeResult.nutriments?.cu.toString()
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.iron),
                barcodeResult.nutriments?.iron.toString()
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.fiber),
                barcodeResult.nutriments?.fiber.toString()
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.magnesium),
                barcodeResult.nutriments?.magnesium.toString()
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.phosphorus),
                barcodeResult.nutriments?.phosphorus.toString()
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.potassium),
                barcodeResult.nutriments?.potassium.toString()
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.omega3),
                barcodeResult.nutriments?.omega3.toString()
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.omega6),
                barcodeResult.nutriments?.omega6.toString()
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.sodium),
                barcodeResult.nutriments?.sodium.toString()
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.vitaminC),
                barcodeResult.nutriments?.vitaminC.toString()
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.zinc),
                barcodeResult.nutriments?.zinc.toString()
            )?.let {
                listResult.add(it)
            }
            /*   prepareItemFromString(
                   context.getString(R.string.imageFrontUrl),
                   barcodeResult.imageFrontUrl
               )?.let {
                   listResult.add(it)
               }
               prepareItemFromString(
                   context.getString(R.string.imageIngredientsUrl),
                   barcodeResult.imageIngredientsUrl
               )?.let {
                   listResult.add(it)
               }*/
            /* prepareItemFromString(
                 context.getString(R.string.imageNutritionUrl),
                 barcodeResult.imageNutritionUrl
             )?.let {
                 listResult.add(it)
             }*/
            /*prepareItemFromString(
                context.getString(R.string.ingredientsText),
                barcodeResult.ingredientsText
            )?.let {
                listResult.add(it)
            }*/
            prepareIngredientsItem(barcodeResult.ingredients)?.let {
                it.forEach { param ->
                    listResult.add(param)
                }
            }
            prepareItemFromString(
                context.getString(R.string.fat),
                barcodeResult.nutrientLevels?.fat
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.saturatedFat),
                barcodeResult.nutrientLevels?.saturatedFat
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.salt),
                barcodeResult.nutrientLevels?.salt
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.sugars),
                barcodeResult.nutrientLevels?.sugars
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.packaging),
                barcodeResult.packaging
            )?.let {
                listResult.add(it)
            }
            prepareItemFromString(
                context.getString(R.string.servingSize),
                barcodeResult.servingSize
            )?.let {
                listResult.add(it)
            }
            prepareItemFromList(context.getString(R.string.keywords), barcodeResult.keywords)?.let {
                listResult.add(it)
            }
            return BarcodeState(
                listOf(
                    barcodeResult.imageNutritionUrl,
                    barcodeResult.imageFrontUrl,
                    barcodeResult.imageIngredientsUrl
                ).filter { !it.isNullOrEmpty() },
                listResult)
        }
    }

    private fun prepareItemFromString(paramName: String, value: String?): Parameter? {
        return if (value.isNullOrEmpty()) {
            null
        } else {
            Parameter(paramName, value)
        }
    }

    private fun prepareItemFromList(
        paramName: String,
        valueList: List<String>?
    ): Parameter? {
        var stringToList: String = ""
        valueList?.forEachIndexed { index, value ->
            if (index != valueList.lastIndex)
                stringToList = stringToList + value + "\n"
            else stringToList += value
        }
        return if (stringToList.isNotEmpty())
            Parameter(paramName, stringToList)
        else null
    }

    private fun prepareIngredientsItem(itemList: List<IngredientsRes>): List<Parameter>? {
        val list: MutableList<Parameter> = mutableListOf()
        itemList.forEach {
            list.add(Parameter(it.text, it.percent_estimate.toString()))
        }
        return list
    }
}