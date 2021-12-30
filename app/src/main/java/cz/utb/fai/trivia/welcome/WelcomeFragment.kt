package cz.utb.fai.trivia.welcome

import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import cz.utb.fai.trivia.databinding.FragmentWelcomeBinding
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.AdapterView.OnItemSelectedListener
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import cz.utb.fai.trivia.R
import cz.utb.fai.trivia.network.CategoryData
import cz.utb.fai.trivia.questions.QuestionFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation


class WelcomeFragment : Fragment() {

  private val viewModel: WelcomeViewModel by lazy {
    ViewModelProvider(this).get(WelcomeViewModel::class.java)
  }

  var idSelectedCategory: Int? = null

  /**
   * Inflates the layout with Data Binding, sets its lifecycle owner to the WelcomeFragment
   * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
   */
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                    savedInstanceState: Bundle?): View? {

    val binding = FragmentWelcomeBinding.inflate(inflater)
    binding.lifecycleOwner = this
    binding.viewModel = viewModel

    val categoryData = CategoryData()

    var list: MutableList<String> = arrayListOf()

    for (cat in categoryData.cateforyList){
      list.add(cat.name)
    }

    val spinner = binding.spinner
    var adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, list)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinner.adapter = adapter

    spinner.onItemSelectedListener = object : OnItemSelectedListener {
      override fun onItemSelected(
        parent: AdapterView<*>, v: View?,
        postion: Int, arg3: Long
      ) {
        val spinnerValue = parent.getItemAtPosition(postion).toString()
        Toast.makeText(
          requireContext(),
          "Selected category $spinnerValue",
          Toast.LENGTH_SHORT
        ).show()

        idSelectedCategory = categoryData.findCategory(spinnerValue)
      }

      override fun onNothingSelected(arg0: AdapterView<*>?) {
        // TODO Auto-generated method stub
      }
    }


    binding.btnStart.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
    { view: View ->
      if (binding.etName.text.isNullOrEmpty())
      {
        Toast.makeText(
          requireContext(),
          "Please enter your name",
          Toast.LENGTH_SHORT
        ).show()
      }
      else if (idSelectedCategory == null) {
        Toast.makeText(
          requireContext(),
          "Please select category",
          Toast.LENGTH_SHORT
        ).show()
      }
      else
      {
        val bundle = Bundle()
        bundle.putString("USER", binding.etName.text.toString())
        bundle.putInt("ID_CATEGORY", idSelectedCategory!!)

        view.findNavController().navigate(R.id.action_welcomeFragment_to_questionFragment, bundle)
      }

    }

    setHasOptionsMenu(true)
    return binding.root
  }
}

