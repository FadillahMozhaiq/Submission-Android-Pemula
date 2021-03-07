package id.fadillah.pemulasubmission.ui.fragment.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import id.fadillah.pemulasubmission.databinding.FragmentHomeBinding
import id.fadillah.pemulasubmission.ui.adapter.MangaAdapter
import id.fadillah.pemulasubmission.viewmodel.ViewModelFactory

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private lateinit var mangaAdapter: MangaAdapter
    private lateinit var viewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        mangaAdapter = MangaAdapter()
        with(binding?.rvHome) {
            this?.layoutManager = GridLayoutManager(context, 2)
            this?.setHasFixedSize(true)
            this?.adapter = mangaAdapter
        }
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance()
            viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

            showRecyclerView(false)
            viewModel.getAllManga().observe(viewLifecycleOwner, {manga ->
                showRecyclerView(true)
                mangaAdapter.setData(manga)
                mangaAdapter.notifyDataSetChanged()
            })

            binding?.edtSearch?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) { }
                override fun afterTextChanged(s: Editable?) { }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    showRecyclerView(false)
                    viewModel.getQuestManga(s.toString()).observe(viewLifecycleOwner, {manga ->
                        if (manga.isNotEmpty()) {
                            showEmptyLayout(false)
                            showRecyclerView(true)
                            mangaAdapter.setData(manga)
                            mangaAdapter.notifyDataSetChanged()
                        } else {
                            showRecyclerView(true)
                            showEmptyLayout(true)
                        }
                    })
                }
            })

            binding?.swipeLayout?.setOnRefreshListener {
                showRecyclerView(false)
                viewModel.getAllManga().observe(viewLifecycleOwner, {manga ->
                    mangaAdapter.setData(manga)
                    mangaAdapter.notifyDataSetChanged()
                    showRecyclerView(true)
                    binding?.swipeLayout?.isRefreshing = false
                })
            }
        }
    }

    private fun showEmptyLayout(show: Boolean) {
        if (show) {
            binding?.layoutEmpty?.visibility = View.VISIBLE
            binding?.rvHome?.visibility = View.GONE
            binding?.tvCollection?.visibility = View.GONE
        } else {
            binding?.layoutEmpty?.visibility = View.GONE
            binding?.rvHome?.visibility = View.VISIBLE
            binding?.tvCollection?.visibility = View.VISIBLE
        }
    }

    private fun showRecyclerView(show: Boolean) {
        if (show) {
            binding?.layoutShimmer?.visibility = View.GONE
            binding?.rvHome?.visibility = View.VISIBLE
            binding?.tvCollection?.visibility = View.VISIBLE
        } else {
            binding?.layoutShimmer?.visibility = View.VISIBLE
            binding?.rvHome?.visibility = View.GONE
            binding?.tvCollection?.visibility = View.GONE
        }
    }
}