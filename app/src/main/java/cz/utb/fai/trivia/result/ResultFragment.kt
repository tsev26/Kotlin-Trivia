package cz.utb.fai.trivia.result

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import cz.utb.fai.trivia.R
import cz.utb.fai.trivia.databinding.FragmentResultBinding


class ResultFragment : Fragment() {

  private val viewModel: ResultViewModel by lazy {
    ViewModelProvider(this).get(ResultViewModel::class.java)
  }

  private var mCorrectAnswers : Int = 0
  private var mUserName : String? = null

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    arguments = getArguments()
    mUserName = arguments?.getString("USER")
    mCorrectAnswers = arguments?.getInt("CORRECT_ANSWERS")!!

    val binding = FragmentResultBinding.inflate(inflater)
    binding.lifecycleOwner = this
    binding.viewModel = viewModel

    viewModel.showResult(mUserName!!,mCorrectAnswers)

    binding.btnFinish.setOnClickListener {
      it.findNavController().navigate(R.id.action_resultFragment_to_welcomeFragment)
    }

    return binding.root
  }


}