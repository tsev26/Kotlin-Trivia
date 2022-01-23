package cz.utb.fai.trivia.score

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class ScoreRepository(private val scoreDao: ScoreDao) {
  // Room executes all queries on a separate thread.
  // Observed Flow will notify the observer when the data has changed.
  val allScores: Flow<List<Score>> = scoreDao.getScores()

  @Suppress("RedundantSuspendModifier")
  @WorkerThread
  suspend fun insert(score: Score) {
    scoreDao.insert(score)
  }
}