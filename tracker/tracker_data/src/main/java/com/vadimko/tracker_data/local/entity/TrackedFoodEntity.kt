package com.vadimko.tracker_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackedFoodEntity(
    val name: String,
    val carbs: Int,
    val fat: Int,
    val protein: Int,
    val imageUrl: String? = null,
    val type: String,
    val amount: Int,
    val date: String? = null,
    val dayOfMonth: Int,
    val month: Int,
    val year: Int,
    val calories: Int,
    @PrimaryKey
    val id: Int? = null
)
