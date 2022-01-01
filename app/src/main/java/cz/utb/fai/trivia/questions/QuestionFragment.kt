package cz.utb.fai.trivia.questions

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.utb.fai.trivia.R
import cz.utb.fai.trivia.databinding.FragmentQuestionBinding
import cz.utb.fai.trivia.welcome.WelcomeViewModel

class QuestionFragment : Fragment() {

  private val viewModel: QuestionViewModel by lazy {
    ViewModelProvider(this).get(QuestionViewModel::class.java)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    arguments = getArguments()
    val user = arguments?.getString("USER")
    val idCategory = arguments?.getInt("ID_CATEGORY")

    val binding = FragmentQuestionBinding.inflate(inflater)
    binding.lifecycleOwner = this
    binding.viewModel = viewModel

    val questions = viewModel.getCategoryProperties(idCategory!!)


    return binding.root
  }


}