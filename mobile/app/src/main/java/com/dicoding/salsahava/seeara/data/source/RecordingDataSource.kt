package com.dicoding.salsahava.seeara.data.source

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import com.dicoding.salsahava.seeara.data.entity.RecordingEntity

interface RecordingDataSource {

    fun getDownloadUrl(): LiveData<Uri>

    fun getRecording(context: Context, downloadUrl: String): LiveData<RecordingEntity>
}