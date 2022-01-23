package cz.utb.fai.trivia.score

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {

  @Query("SELECT * FROM score_table ORDER BY score DESC")
  fun getScores(): Flow<List<Score>>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insert(score: Score)

  @Query("DELETE FROM score_table")
  suspend fun deleteAll()
}