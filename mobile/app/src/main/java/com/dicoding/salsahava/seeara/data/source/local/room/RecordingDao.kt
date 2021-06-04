package com.dicoding.salsahava.seeara.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*

import com.dicoding.salsahava.seeara.data.source.local.entity.RecordingEntity

@Dao
interface RecordingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(record: RecordingEntity)

    @Update
    fun updateRecord(record: RecordingEntity)

    @Delete
    fun deleteRecord(record: RecordingEntity)

    @Query("SELECT * FROM recordEntities ORDER BY id ASC")
    fun getAllRecord(): LiveData<List<RecordingEntity>>

}