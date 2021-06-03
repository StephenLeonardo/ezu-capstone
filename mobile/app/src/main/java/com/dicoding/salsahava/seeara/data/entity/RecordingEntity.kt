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
    var id: Int,

    @ColumnInfo(name = "filename")
    var fileName: String,

    @ColumnInfo(name = "url")
    var downloadUrl: String,

    @ColumnInfo(name = "translation")
    var translation: String,

    @ColumnInfo(name = "date")
    var date: String
) : Parcelable