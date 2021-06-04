package com.dicoding.salsahava.seeara.data

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import com.dicoding.salsahava.seeara.data.source.local.entity.RecordingEntity
import com.dicoding.salsahava.seeara.vo.Resource

interface RecordingDataSource {

    fun getDownloadUrl(): LiveData<Uri>

    fun getRecording(context: Context, downloadUrl: String): LiveData<Resource<RecordingEntity>>

    fun getAllRecord(): LiveData<List<RecordingEntity>>
}