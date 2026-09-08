package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.SankalpaDay
import kotlinx.coroutines.flow.Flow

@Dao
interface SankalpaDao {

    @Query("SELECT * FROM sankalpa_days WHERE cycleId = :cycleId ORDER BY dayNumber ASC")
    fun getDaysForCycle(cycleId: Int): Flow<List<SankalpaDay>>

    @Query("SELECT * FROM sankalpa_days WHERE cycleId = :cycleId AND dayNumber = :dayNumber LIMIT 1")
    fun getDay(cycleId: Int, dayNumber: Int): Flow<SankalpaDay?>

    @Query("SELECT * FROM sankalpa_days WHERE cycleId = :cycleId AND dayNumber = :dayNumber LIMIT 1")
    suspend fun getDaySync(cycleId: Int, dayNumber: Int): SankalpaDay?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDays(days: List<SankalpaDay>)

    @Update
    suspend fun updateDay(day: SankalpaDay)

    @Query("DELETE FROM sankalpa_days WHERE cycleId = :cycleId")
    suspend fun deleteCycle(cycleId: Int)

    @Query("SELECT COUNT(*) FROM sankalpa_days WHERE cycleId = :cycleId")
    suspend fun getDayCountForCycle(cycleId: Int): Int

    @Query("SELECT MAX(cycleId) FROM sankalpa_days")
    suspend fun getMaxCycleId(): Int?
}
