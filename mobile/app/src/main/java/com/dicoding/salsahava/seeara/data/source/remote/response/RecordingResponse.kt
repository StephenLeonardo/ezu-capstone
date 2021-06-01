package com.dicoding.salsahava.seeara.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecordingResponse(

	@field:SerializedName("file_url")
	val fileUrl: String,

	@field:SerializedName("filename")
	val fileName: String,

	@field:SerializedName("translation")
	val translation: String,

	@field:SerializedName("message")
	val message: String
) : Parcelable
