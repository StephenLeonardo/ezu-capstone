package com.dicoding.salsahava.seeara.data.entity

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecordingEntity(
    var fileName: String,
    var downloadUrl: Uri,
    var translation: String
) : Parcelable