package cz.utb.fai.trivia.welcome

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cz.utb.fai.trivia.R
import cz.utb.fai.trivia.databinding.FragmentWelcomeBinding
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import cz.utb.fai.trivia.network.Category


class WelcomeFragment : Fragment() {

  /**
   * Lazily initialize our [WelcomeViewModel].
   */
  private val viewModel: WelcomeViewModel by lazy {
    ViewModelProvider(this).get(WelcomeViewModel::class.java)
  }

  /**
   * Inflates the layout with Data Binding, sets its lifecycle owner to the WelcomeFragment
   * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
   */
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {

    val binding = FragmentWelcomeBinding.inflate(inflater)

    // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
    binding.lifecycleOwner = this

    // Giving the binding access to the WelcomeViewModel
    binding.viewModel = viewModel

    val spinner = binding.spinner

    val adapter: ArrayAdapter<String> =
      ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinner.adapter = adapter


    setHasOptionsMenu(true)
    return binding.root
  }

  /*
  /**
   * Inflates the overflow menu that contains filtering options.
   */
  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.overflow_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }
  */
}