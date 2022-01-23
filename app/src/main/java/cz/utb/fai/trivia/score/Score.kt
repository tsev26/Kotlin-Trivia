package cz.utb.fai.trivia.score

import androidx.room.*
import java.sql.Date

@Entity(tableName = "score_table")
data class Score (
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,
  @ColumnInfo(name = "name")
  val name: String,
  @ColumnInfo(name = "score")
  val score: Int,
  @ColumnInfo(name = "date")
  val date: String
)
