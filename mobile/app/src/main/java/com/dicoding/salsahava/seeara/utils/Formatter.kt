package com.dicoding.salsahava.seeara.utils

import androidx.lifecycle.LiveData
import com.dicoding.salsahava.seeara.data.source.local.entity.RecordingEntity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object Formatter {

    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd, hh:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }

    fun mapRecordingFromDatabaseToArrayList(liveData: LiveData<List<RecordingEntity>>?): ArrayList<RecordingEntity> {
        val recordingList = ArrayList<RecordingEntity>()

        val recordings = liveData?.value
        if (recordings != null) {
            for (recording in recordings) {
                recordingList.add(
                    RecordingEntity(
                    recording.id,
                    recording.fileName,
                    recording.downloadUrl,
                    recording.translation,
                    recording.date
                    )
                )
            }
        }

        return recordingList
    }
}