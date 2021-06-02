package com.dicoding.salsahava.seeara.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.salsahava.seeara.data.entity.RecordingEntity

@Dao
interface RecordingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(record: List<RecordingEntity>)

    @Delete
    fun deleteRecord(record: List<RecordingEntity>)

    @Query("SELECT * FROM recordEntities ORDER BY id ASC")
    fun getAllRecord(): LiveData<List<RecordingEntity>>

}