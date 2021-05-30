package com.dicoding.salsahava.seeara.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class History(
    var description: String? = null
) : Parcelable