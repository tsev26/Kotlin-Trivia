package cz.utb.fai.trivia.score

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Score::class], version = 1, exportSchema = false)
abstract class ScoreRoomDatabase : RoomDatabase() {

  abstract fun scoreDao(): ScoreDao

  companion object {
    @Volatile
    private var INSTANCE: ScoreRoomDatabase? = null

    fun getDatabase(context: Context,
                    scope: CoroutineScope): ScoreRoomDatabase {
      // if the INSTANCE is not null, then return it,
      // if it is, then create the database
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          ScoreRoomDatabase::class.java,
          "score_database"
        )
          // Wipes and rebuilds instead of migrating if no Migration object.
          .fallbackToDestructiveMigration()
          .addCallback(WordDatabaseCallback(scope))
          .build()
        INSTANCE = instance
        // return instance
        instance
      }
    }

    private class WordDatabaseCallback(
      private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
      /**
       * Override the onCreate method to populate the database.
       */
      override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        // If you want to keep the data through app restarts,
        // comment out the following line.
        INSTANCE?.let { database ->
          scope.launch(Dispatchers.IO) {
            populateDatabase(database.scoreDao())
          }
        }
      }
    }

    /**
     * Populate the database in a new coroutine.
     * If you want to start with more words, just add them.
     */
    suspend fun populateDatabase(scoreDao: ScoreDao) {
      scoreDao.deleteAll()

      var score = Score(1,"Tom1", 8, "2022-01-23 15:50:20")
      scoreDao.insert(score)
      score = Score(2,"Tomas2", 4, "2022-01-23 16:05:33")
      scoreDao.insert(score)
    }
  }
}