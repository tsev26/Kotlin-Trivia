package cz.utb.fai.trivia.score

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ScoreApplication : Application() {

  val applicationScope = CoroutineScope(SupervisorJob())

  private val database by lazy { ScoreRoomDatabase.getDatabase(this, applicationScope) }
  val repository by lazy { ScoreRepository(database.scoreDao()) }
}