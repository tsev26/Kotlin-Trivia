package cz.utb.fai.trivia.score

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cz.utb.fai.trivia.R
import cz.utb.fai.trivia.databinding.ItemScoreListBinding

class ScoreListAdapter : ListAdapter<Score, ScoreListAdapter.ScoreViewHolder>(DiffCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
    return ScoreViewHolder.create(parent)
  }

  override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
    val current = getItem(position)
    holder.bind(current)
  }

  class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val nameItemView: TextView = itemView.findViewById(R.id.score_name)
    private val scoreItemView: TextView = itemView.findViewById(R.id.score_score)
    //private val dateItemView: TextView = itemView.findViewById(R.id.score_date)

    fun bind(score: Score) {
      nameItemView.text = score.name
      scoreItemView.text = score.score.toString()
      //dateItemView.text = score.date
    }

    companion object {
      fun create(parent: ViewGroup): ScoreViewHolder {
        val view: View = LayoutInflater.from(parent.context)
          .inflate(R.layout.item_score_list, parent, false)
        return ScoreViewHolder(view)
      }
    }

  }

  companion object {
    private val DiffCallback = object : DiffUtil.ItemCallback<Score>() {
      override fun areItemsTheSame(oldItem: Score, newItem: Score): Boolean {
        return oldItem === newItem
      }

      override fun areContentsTheSame(oldItem: Score, newItem: Score): Boolean {
        return oldItem.name == newItem.name && oldItem.score == newItem.score && oldItem.date == newItem.date
      }
    }
  }

}