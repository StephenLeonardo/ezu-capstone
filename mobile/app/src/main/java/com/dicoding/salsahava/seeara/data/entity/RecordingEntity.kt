package com.dicoding.salsahava.seeara.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecordingEntity(
    var fileName: String,
    var downloadUrl: String,
    var translation: String,
    var date: String
) : Parcelable