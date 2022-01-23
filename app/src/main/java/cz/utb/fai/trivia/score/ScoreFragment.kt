package cz.utb.fai.trivia.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.utb.fai.trivia.R
import cz.utb.fai.trivia.databinding.FragmentScoreBinding


class ScoreFragment : Fragment() {

  private val viewModel: ScoreViewModel by activityViewModels {
    ScoreViewModelFactory((activity?.application as ScoreApplication).repository)
  }

  private var _binding: FragmentScoreBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentScoreBinding.inflate(inflater, container, false)

    val recyclerView = binding.recyclerView
    val adapter = ScoreListAdapter()
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(this.context)

    viewModel.allScores.observe(this.viewLifecycleOwner) { scores ->
      scores.let {
        adapter.submitList(it)
      }
    }

    return binding.root
  }

}