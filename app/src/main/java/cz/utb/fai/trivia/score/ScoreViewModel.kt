package cz.utb.fai.trivia.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

class ScoreViewModel(private val repository: ScoreRepository) : ViewModel() {
  // Using LiveData and caching what allWords returns has several benefits:
  // - We can put an observer on the data (instead of polling for changes) and only update the
  //   the UI when the data actually changes.
  // - Repository is completely separated from the UI through the ViewModel.
  val allScores: LiveData<List<Score>> = repository.allScores.take(10).asLiveData() //

  /**
   * Launching a new coroutine to insert the data in a non-blocking way
   */
  fun insert(score: Score) = viewModelScope.launch {
    repository.insert(score)
  }
}

class ScoreViewModelFactory(private val repository: ScoreRepository) : ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return ScoreViewModel(repository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}