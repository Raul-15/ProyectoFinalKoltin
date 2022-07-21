package com.example.android_proyecto.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Product(
    val user_id: String = "",
    val user_name: String = "",
    val title: String = "",
    val mobile: String = "",
    val web: String = "",
    val description: String = "",
    val image: String = "",
    var product_id: String = "",
) : Parcelable