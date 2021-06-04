package com.dicoding.salsahava.seeara.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "recordEntities")
@Parcelize
data class RecordingEntity(

    @PrimaryKey
    @NonNull
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