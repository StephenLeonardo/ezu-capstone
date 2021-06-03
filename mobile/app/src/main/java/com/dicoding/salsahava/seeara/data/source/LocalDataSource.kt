package com.dicoding.salsahava.seeara.data.source

import androidx.lifecycle.LiveData
import com.dicoding.salsahava.seeara.data.entity.RecordingEntity
import com.dicoding.salsahava.seeara.data.room.RecordingDao

class LocalDataSource private constructor(private val mRecordingDao: RecordingDao){

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(recordingDao: RecordingDao) : LocalDataSource =
            INSTANCE ?: LocalDataSource(recordingDao).apply { INSTANCE = this }
    }

    fun getAllRecord(): LiveData<List<RecordingEntity>> =  mRecordingDao.getAllRecord()

    fun insertRecord(record: RecordingEntity) = mRecordingDao.insertRecord(record)

    fun deleteRecord(record: RecordingEntity) = mRecordingDao.deleteRecord(record)

    fun updateRecord(record: RecordingEntity) = mRecordingDao.updateRecord(record)

}