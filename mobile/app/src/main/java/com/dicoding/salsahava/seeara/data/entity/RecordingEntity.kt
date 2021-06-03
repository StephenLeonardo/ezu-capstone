package com.dicoding.salsahava.seeara.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class RecordingEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "filename")
    var fileName: String? = null,

    @ColumnInfo(name = "url")
    var downloadUrl: String? = null,

    @ColumnInfo(name = "translation")
    var translation: String? = null,

    @ColumnInfo(name = "date")
    var date: String? = null
) : Parcelable