//____________DataClasses______________
package com.example.vk_test

import android.icu.text.CaseMap.Title
import com.google.gson.annotations.SerializedName

data class DataResult (
    @SerializedName("data") val res: List<DataObject>
)
data class  DataObject(
    @SerializedName("title") val title: String,
    @SerializedName("images") val images: DataImage
)
data class DataImage(
    @SerializedName("original") val ogImage:ogImage
)
data class ogImage(
    val url: String
)