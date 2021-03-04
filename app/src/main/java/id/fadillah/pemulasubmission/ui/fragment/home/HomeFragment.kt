package id.fadillah.pemulasubmission.ui.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import id.fadillah.pemulasubmission.R
import id.fadillah.pemulasubmission.data.MangaRepository
import id.fadillah.pemulasubmission.databinding.FragmentHomeBinding
import id.fadillah.pemulasubmission.ui.adapter.MangaAdapter
import id.fadillah.pemulasubmission.viewmodel.ViewModelFactory

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            val factory = ViewModelFactory.getInstance()
            val viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
            val mangaAdapter = MangaAdapter()

            setLayout(false)
            viewModel.getAllManga().observe(this, {manga ->
                setLayout(true)
                mangaAdapter.setData(manga)
                mangaAdapter.notifyDataSetChanged()
            })

            with(binding?.rvHome) {
                this?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                this?.setHasFixedSize(true)
                this?.adapter = mangaAdapter
            }
        }
    }

    private fun setLayout(isDone: Boolean) {
        if (isDone) {
            binding?.layoutShimmer?.visibility = View.INVISIBLE
            binding?.layoutRvHome?.visibility = View.VISIBLE
        } else {
            binding?.layoutShimmer?.visibility = View.VISIBLE
            binding?.layoutRvHome?.visibility = View.INVISIBLE
        }
    }
}