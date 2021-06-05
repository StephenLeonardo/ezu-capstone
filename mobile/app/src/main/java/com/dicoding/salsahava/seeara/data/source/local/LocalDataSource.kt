package com.dicoding.salsahava.seeara.data.source.local

import androidx.lifecycle.LiveData
import com.dicoding.salsahava.seeara.data.source.local.entity.RecordingEntity
import com.dicoding.salsahava.seeara.data.source.local.room.RecordingDao

class LocalDataSource private constructor(private val mRecordingDao: RecordingDao){

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(recordingDao: RecordingDao) : LocalDataSource =
            INSTANCE ?: LocalDataSource(recordingDao).apply { INSTANCE = this }
    }

    fun getAllRecord(): LiveData<List<RecordingEntity>> =  mRecordingDao.getAllRecord()

    fun getRecordById(id: Int): LiveData<RecordingEntity> = mRecordingDao.getRecordById(id)

    fun insertRecord(record: RecordingEntity) = mRecordingDao.insertRecord(record)

    fun setRecorded(record: RecordingEntity) {
        mRecordingDao.updateRecord(record)
    }

//    fun deleteRecord(record: RecordingEntity) = mRecordingDao.deleteRecord(record)
//
//    fun updateRecord(record: RecordingEntity) = mRecordingDao.updateRecord(record)

}