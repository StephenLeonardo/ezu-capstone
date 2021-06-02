package com.dicoding.salsahava.seeara.data.entity

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
    var id: Int? = null ,

    @ColumnInfo(name = "filename")
    var fileName: String? = null,

    @ColumnInfo(name = "url")
    var downloadUrl: String? = null,

    @ColumnInfo(name = "translation")
    var translation: String? = null,

    @ColumnInfo(name = "date")
    var date: String? = null
) : Parcelable